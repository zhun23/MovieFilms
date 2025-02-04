document.getElementById('backButton').addEventListener('click', function() {
    window.history.back();
});

function createLoginBox() {
    const loginBoxDiv = document.createElement('div');
    loginBoxDiv.className = 'loginbox';

    const loginLink = document.createElement('a');
    loginLink.href = '/login';
    loginLink.className = 'loginreq';
    loginLink.textContent = 'Login';

    const registerLink = document.createElement('a');
    registerLink.href = '/register';
    registerLink.className = 'registerreq';
    registerLink.textContent = 'Registrarse';

    loginBoxDiv.appendChild(loginLink);
    loginBoxDiv.appendChild(registerLink);

    return loginBoxDiv;
}

document.addEventListener('DOMContentLoaded', function() {
    const loginBox = document.querySelector('.loginbox');

    if (!username || username.trim() === '') {
        const newLoginBox = createLoginBox();
        document.body.appendChild(newLoginBox);
    } else {
        if (loginBox) {
            loginBox.remove();
        }

        function createUserDetails(username) {
            const userDetailsDiv = document.createElement('div');
            userDetailsDiv.className = 'userdetails';
            
            const usernameLink = document.createElement('a');
            usernameLink.href = '/account';
            usernameLink.className = 'username';
            usernameLink.textContent = username;
            
            userDetailsDiv.appendChild(usernameLink);
            
            return userDetailsDiv;
        }

        const userDetails = createUserDetails(username);
        document.body.appendChild(userDetails);

        // Check if user is admin and only show cartIcon if not admin
        checkIfAdmin(username, function(isAdmin) {
            if (!isAdmin) {
                const cartIcon = createCartIcon();
                document.body.appendChild(cartIcon);
            }
            
            const cartIcon2 = createCartIcon2();
            document.body.appendChild(cartIcon2);
        });
    }
});

function createLoginBox() {
    const loginBoxDiv = document.createElement('div');
    loginBoxDiv.className = 'loginbox';

    const loginLink = document.createElement('a');
    loginLink.href = '/login';
    loginLink.className = 'loginreq';
    loginLink.textContent = 'Login';

    const registerLink = document.createElement('a');
    registerLink.href = '/register';
    registerLink.className = 'registerreq';
    registerLink.textContent = 'Registrarse';

    loginBoxDiv.appendChild(loginLink);
    loginBoxDiv.appendChild(registerLink);

    return loginBoxDiv;
}

function createCartIcon() {
    const cartIconLink = document.createElement('a');
    cartIconLink.id = 'cart-icon';
    cartIconLink.href = '/cart'; // Establecer la URL de destino como cart.html

    const cartIcon = document.createElement('i');
    cartIcon.className = 'material-symbols-outlined';
    cartIcon.style.fontSize = '40px';
    cartIcon.textContent = 'shopping_cart';

    cartIconLink.appendChild(cartIcon);

    return cartIconLink;
}

function createCartIcon2() {
    const cartIconDiv2 = document.createElement('div');
    cartIconDiv2.id = 'cart-icon2';

    const cartIconLink = document.createElement('a');
    cartIconLink.href = '/logout';

    const cartIcon2 = document.createElement('i');
    cartIcon2.id = 'cart-icon2a';
    cartIcon2.className = 'material-symbols-outlined';
    cartIcon2.style.fontSize = '40px';
    cartIcon2.textContent = 'logout';

    cartIconLink.appendChild(cartIcon2);
    cartIconDiv2.appendChild(cartIconLink);

    return cartIconDiv2;
}

function checkIfAdmin(nickname, callback) {
    fetch(`http://localhost:8090/api/user/isAdmin/${nickname}`)
        .then(response => response.json())
        .then(isAdmin => {
            if (typeof callback === 'function') {
                callback(isAdmin);
            }
        })
        .catch(error => {
            console.error('Error checking admin status:', error);
        });
}

$(document).ready(function() {
    checkIfAdmin();
    displayPurchase();
});

function checkIfAdmin() {
    $.ajax({
        url: 'http://localhost:8090/api/user/isAdmin/' + username,
        type: 'GET',
        success: function(isAdmin) {
            if (isAdmin) {
                $('#userDetails p:contains("Crédito:")').hide();
            }
        },
        error: function(error) {
            console.error('Error al verificar si el usuario es administrador:', error);
        }
    });
}

function generatePDF(purchase, items, base64Image, username) {
    const { jsPDF } = window.jspdf;
    const doc = new jsPDF();

    const uniqueNumber = generateUniqueNumber(username, purchase.id);

    doc.setFontSize(18);
    doc.text(`Factura compra nº ${uniqueNumber}`, 14, 22);

    const imgWidth = 40 * 1;
    const imgHeight = 40 * 0.50;
    doc.addImage(base64Image, 'PNG', 150, 10, imgWidth, imgHeight);

    doc.setFontSize(12);
    doc.text(`Pedido #${purchase.id}`, 14, 60);
    doc.text(`Fecha de pedido: ${formatDate(purchase.date)}`, 14, 70);
    doc.text(`${purchase.address1}`, 14, 80);
    doc.text(`${purchase.address2 || ''}`, 14, 90);
    doc.text(`${purchase.zipcode}`, 14, 100);
    doc.text(`${purchase.location}`, 14, 110);
    doc.text(`${purchase.totalCost}`, 14, 120);

    let startY = 130;
    doc.text('Detalles de los artículos', 14, startY);
    startY += 10;

    items.forEach(item => {
        doc.text(`${item.title}`, 14, startY);
        doc.text(`Cantidad: ${item.quantity}`, 100, startY);
        startY += 10;
    });

    doc.save(`factura_compra_${uniqueNumber}.pdf`);
}

function loadPurchaseItemsAndGeneratePDF(purchaseId, base64Image) {
    $.ajax({
        url: 'http://localhost:8090/api/purchaseItems/' + purchaseId,
        type: 'GET',
        success: function(response) {
            const purchase = $(`.purchase-row[data-id=${purchaseId}]`).data('purchase');

            generatePDF(purchase, response, base64Image);
        }
    });
}

$(document).ready(function() {
    var imageUrl = '/images/115.png';
    loadImageToBase64(imageUrl, function(base64Image) {
        $(document).on('click', '.invoice-button', function() {
            const purchaseId = $(this).attr('data-id');
            loadPurchaseItemsAndGeneratePDF(purchaseId, base64Image, username);
        });

        function displayPurchase() {
            $.ajax({
                url: 'http://localhost:8090/api/purchase/' + username,
                type: 'GET',
                success: function(response) {
                    console.log(response);

                    $('#purchaseDetails').empty();

                    if (response.length === 0) {
                        $('#purchaseDetails').html('<p class="emptyOrdersUser">Todavía no has realizado ningún pedido.</p>');
                        return;
                    }

                    var purchaseHTML = `
                        <table>
                            <thead>
                                <tr>
                                    <th>#</th>
                                    <th>Fecha de pedido</th>
                                    <th>Dirección de entrega</th>
                                    <th>Complemento dirección</th>
                                    <th>Código postal</th>
                                    <th>Localidad</th>
                                    <th>Total</th>
                                    <th>Acciones</th>
                                </tr>
                            </thead>
                            <tbody>
                    `;

                    response.forEach(function(purchase) {
                        var formattedDate = formatDate(purchase.date);

                        var address2 = purchase.address2 ? purchase.address2 : '';

                        purchaseHTML += `
                            <tr class="purchase-row" data-id="${purchase.id}" data-purchase='${JSON.stringify(purchase)}'>
                                <td>${purchase.id}</td>
                                <td>${formattedDate}</td>
                                <td>${purchase.address1}</td>
                                <td>${address2}</td>
                                <td>${purchase.zipcode}</td>
                                <td>${purchase.location}</td>
                                <td>${purchase.totalCost}</td>
                                <td>
                                    <button class="details-button" data-id="${purchase.id}">Detalles</button>
                                    <button class="invoice-button" data-id="${purchase.id}">Factura</button>
                                </td>
                            </tr>
                        `;
                    });

                    purchaseHTML += `
                            </tbody>
                        </table>
                    `;

                    $('#purchaseDetails').html(purchaseHTML);

                    $('.details-button').click(function() {
                        var purchaseId = $(this).attr('data-id');
                        var row = $(this).closest('tr');

                        var nextRow = row.next('.purchase-item-details');
                        if (nextRow.length) {
                            nextRow.remove();
                        } else {
                            loadPurchaseItems(purchaseId, row);
                        }
                    });
                },
                error: function(xhr, status, error) {
                    console.error('Error al obtener los pedidos:', error);
                    $('#purchaseDetails').html('<p>Hubo un error al cargar los pedidos.</p>');
                }
            });
        }

        displayPurchase();
    });
});


function formatDate(dateString) {
    var date = new Date(dateString);
    var day = ('0' + date.getDate()).slice(-2);
    var month = ('0' + (date.getMonth() + 1)).slice(-2);
    var year = date.getFullYear();
    var hours = ('0' + date.getHours()).slice(-2);
    var minutes = ('0' + date.getMinutes()).slice(-2);
    return `${day}-${month}-${year} ${hours}:${minutes}`;
}

function loadPurchaseItems(purchaseId, row) {
    $.ajax({
        url: 'http://localhost:8090/api/purchaseItems/' + purchaseId,
        type: 'GET',
        success: function(response) {
            console.log(response);

            var itemsHTML = `
                <tr class="purchase-item-details">
                    <td colspan="8">
                        <table class="details-table">
                            <thead>
                                <tr>
                                    <th>Artículo</th>
                                    <th>Cantidad</th>
                                </tr>
                            </thead>
                            <tbody>
            `;

            response.forEach(function(item) {
                itemsHTML += `
                    <tr>
                        <td>${item.title}</td>
                        <td>${item.quantity}</td>
                    </tr>
                `;
            });

            itemsHTML += `
                            </tbody>
                        </table>
                    </td>
                </tr>
            `;

            $(row).after(itemsHTML);

            $(row).next('.purchase-item-details').addClass('details-expanded');
        }
    });
}

function padZero(num) {
    return num.toString().padStart(2, '0');
}

function loadImageToBase64(url, callback) {
    var img = new Image();
    img.crossOrigin = 'Anonymous';
    img.onload = function() {
        var base64 = getBase64Image(img);
        callback(base64);
    };
    img.src = url;
}

function getBase64Image(img) {
    var canvas = document.createElement("canvas");
    canvas.width = img.width;
    canvas.height = img.height;
    var ctx = canvas.getContext("2d");
    ctx.drawImage(img, 0, 0);
    var dataURL = canvas.toDataURL("image/png");
    return dataURL.replace(/^data:image\/(png|jpg);base64,/, "");
}

function generatePDF(purchase, items, base64Image, username) {
    const { jsPDF } = window.jspdf;
    const doc = new jsPDF();

    const uniqueNumber = generateUniqueNumber(username, purchase.id);

    doc.setFontSize(18);
    doc.text(`Factura compra nº ${uniqueNumber}`, 14, 22);

    const originalWidth = 40;
    const originalHeight = 40;
    const reducedHeight = originalHeight * 0.75;

    doc.addImage(base64Image, 'PNG', 150, 10, originalWidth, reducedHeight);

    doc.setFontSize(12);
    doc.text(`Fecha de pedido: ${formatDate(purchase.date)}`, 120, 60);

    doc.text('Enviado a:', 18, 80);
    doc.text(`Dirección de entrega: ${purchase.address1}`, 14, 90);
    doc.text(`Complemento dirección: ${purchase.address2 || ''}`, 14, 97);
    doc.text(`Localidad: ${purchase.location}`, 14, 104);
    doc.text(`Código postal: ${purchase.zipcode}`, 14, 111);

    let startY = 131;
    doc.text('Detalles de los artículos:', 25, startY);
    startY += 10;

    items.forEach(item => {
        doc.text(`Título: ${item.title}`, 30, startY);
        doc.text(`Cantidad: ${item.quantity}`, 130, startY);
        startY += 8;
    });

    startY += 20;
    const shippingCostText = purchase.shippingCost === 0 ? 'Gratis' : `${purchase.shippingCost} €`;
    doc.text(`Gastos de envío: ${shippingCostText}`, 130, startY);
    startY += 8;
    doc.text(`Coste total: ${purchase.totalCost} €`, 145, startY);

    doc.save(`factura_compra_${uniqueNumber}.pdf`);
}

function loadPurchaseItemsAndGeneratePDF(purchaseId, base64Image, username) {
    $.ajax({
        url: 'http://localhost:8090/api/purchaseItems/' + purchaseId,
        type: 'GET',
        success: function(response) {
            const purchase = $(`.purchase-row[data-id=${purchaseId}]`).data('purchase');

            generatePDF(purchase, response, base64Image, username);
        },
        error: function(xhr, status, error) {
            console.error('Error al obtener los detalles de los artículos:', error);
        }
    });
}

function generateUniqueNumber(username, purchaseId) {
    let hash = 0;
    const str = username + purchaseId;
    for (let i = 0; i < str.length; i++) {
        const char = str.charCodeAt(i);
        hash = ((hash << 5) - hash) + char;
        hash |= 0;
    }
    return Math.abs(hash);
}