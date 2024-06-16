document.getElementById('backButton').addEventListener('click', function() {
    window.location.href = "/cart";
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

function getPreOrder() {
    $.ajax({
        url: 'http://localhost:8090/api/getPreOrder/' + username,
        type: 'GET',
        success: function(response) {
            console.log(response);
            displayArticles(response.cartDetails);
            displayAddress(response);
        }
    });
}

function displayArticles(cartDetails) {
    var articlesDetailDiv = $('#articlesDetail');
    var articlesList = $('<div class="articles-list"></div>');

    articlesList.append('<h2>Artículos</h2>');

    cartDetails.forEach(function(item) {
        var articleDiv = $('<div class="article"></div>');

        var title = $('<p class="article-title"></p>').text(item.catalogue.title);
        var quantity = $('<p class="article-quantity"></p>').text('Cantidad: ' + item.quantity);

        articleDiv.append(title, quantity);
        articlesList.append(articleDiv);
    });

    articlesDetailDiv.append(articlesList);
}

function displayAddress(address) {
    var addressDetailDiv = $('#addressDetail');
    var addressDiv = $('<div class="address"></div>');

    if (
        address.addressLine1 === null &&
        address.zipcode === null &&
        address.location === null &&
        address.country === null
    ) {
        var noAddressMessage = $('<p class="no-address">Todavía no has registrado la dirección, pulsa el botón para hacerlo</p>');
        var registerButton = $('<button class="register-button">Registrar dirección</button>');
        registerButton.on('click', function() {
            window.location.href = '/addAddress';
        });

        addressDiv.append(noAddressMessage, registerButton);
    } else {
        var title = $('<h2 class="address-title">Dirección</h2>');
        var direccion = $('<p class="address-detail">Dirección: ' + address.addressLine1 + '</p>');

        if (address.addressLine2 !== null) {
            var direccion2 = $('<p class="address-detail">Dirección2: ' + address.addressLine2 + '</p>');
        }

        var codigoPostal = $('<p class="address-detail">Código Postal: ' + address.zipcode + '</p>');
        var localidad = $('<p class="address-detail">Localidad: ' + address.location + '</p>');
        var provincia = $('<p class="address-detail">Provincia: ' + address.province + '</p>');
        var pais = $('<p class="address-detail">País: ' + address.country + '</p>');

        addressDiv.append(title);
        addressDiv.append(direccion);
        if (direccion2) {
            addressDiv.append(direccion2);
        }
        addressDiv.append(codigoPostal, localidad, provincia, pais);

        // Verifica si la dirección tiene todos los campos no nulos excepto 'province'
        if (address.addressLine1 !== null && address.zipcode !== null && address.location !== null && address.country !== null) {
            displayDeliveryAmount(address);
            $('#deliveryColumn').show(); // Mostrar el div sólo si se llama a displayDeliveryAmount
        }
    }

    addressDetailDiv.append(addressDiv);
}

function displayDeliveryAmount(preOrder) {
    var deliveryAmountDetailDiv = $('#deliveryAmountDetail');
    deliveryAmountDetailDiv.empty();
    
    var deliveryAmountDiv = $('<div class="delivery-amount"></div>');

    var title = $('<h2 class="delivery-amount-title">Total compra</h2>');
    deliveryAmountDiv.append(title);

    var totalItems = 0;
    var orderPrice = preOrder.orderPrice.toFixed(2);
    var shippingCost = preOrder.shippingCost.toFixed(2);
    var totalOrderAmount = preOrder.totalOrderAmount.toFixed(2);
    
    preOrder.cartDetails.forEach(function(item) {
        totalItems += item.quantity;
    });

    var taxRate;
    var taxLabel;
    switch(preOrder.province) {
        case 'Baleares':
        case 'Ceuta':
        case 'Las_Palmas':
        case 'Melilla':
        case 'Santa_Cruz_De_Tenerife':
            taxRate = preOrder.igic.toFixed(2);
            taxLabel = 'IGIC 7%';
            break;
        default:
            taxRate = preOrder.iva.toFixed(2);
            taxLabel = 'IVA 21%';
            break;
    }

    var titlesDiv = $('<div class="titlesDelivery"></div>');
    var valuesDiv = $('<div class="valuesDelivery"></div>');

    var totalItemsTitle = $('<p class="totalItems totalItemsTitle"></p>').text('Total artículos:');
    var totalItemsValue = $('<p class="totalItems"></p>').text(totalItems);

    var orderPriceTitle = $('<p class="totalItems orderPriceTitle"></p>').text('Total artículos (sin impuestos):');
    var orderPriceValue = $('<p class="totalItems"></p>').text(orderPrice + ' €');

    var taxLabelTitle = $('<p class="totalItems taxLabelTitle"></p>').text('Impuestos ' + taxLabel + ':');
    var taxRateValue = $('<p class="totalItems"></p>').text(taxRate + ' €');

    var totalWithTaxesTitle = $('<p class="totalItems totalWithTaxesTitle"></p>').text('Total con impuestos:');
    var totalWithTaxesValue = $('<p class="totalItems"></p>').text((parseFloat(orderPrice) + parseFloat(taxRate)).toFixed(2) + ' €');

    var shippingCostTitle = $('<p class="totalItems shippingCostTitle"></p>').text('Gastos de envío:');
    var shippingCostText = (preOrder.shippingCost === 0) ? 'Gratis' : shippingCost + ' €';
    var shippingCostValue = $('<p class="totalItems"></p>').text(shippingCostText);

    var totalOrderAmountTitle = $('<p class="totalItems totalOrderAmountTitle"></p>').text('Total pedido:');
    var totalOrderAmountValue = $('<p class="totalItems totalOrderAmountValue"></p>').text(totalOrderAmount + ' €');

    titlesDiv.append(
        totalItemsTitle,
        orderPriceTitle,
        taxLabelTitle,
        totalWithTaxesTitle,
        shippingCostTitle,
        totalOrderAmountTitle
    );

    valuesDiv.append(
        totalItemsValue,
        orderPriceValue,
        taxRateValue,
        totalWithTaxesValue,
        shippingCostValue,
        totalOrderAmountValue
    );

    var containerDiv = $('<div class="container"></div>');
    containerDiv.append(titlesDiv, valuesDiv);
    deliveryAmountDiv.append(containerDiv);

    var payButton = $('<button class="payButton">Pantalla de pago</button>');
    deliveryAmountDiv.append(payButton);

    deliveryAmountDetailDiv.append(deliveryAmountDiv);

    payButton.click(function() {
        createPaymentForm();
    });
}

$(document).ready(function() {
    getPreOrder(username);
});

function createPaymentForm() {
    var paymentFormHtml = `
        <div class="overlay"></div>
        <div class="paymentDiv">
            <div class="titleDiv">
                <h2 class="titlePay">Pago</h2>
            </div>
            <form onsubmit="event.preventDefault()" class="formCard" id="paymentForm" autocomplete="off">
                <div class="cardImages">
                    <div class="imagesContainer">
                        <div class='radio' data-value="visa">
                            <img class="fit-image" src="/images/visa.jpeg">
                        </div>
                        <div class='radio' data-value="master">
                            <img class="fit-image" src="/images/mastercard.jpeg">
                        </div>
                    </div>
                </div>

                <div class="dataDiv">
                    <div class="labelInputDiv">
                        <p class="labelInput1">Nombre</p>
                    </div>
                    <div class="valueInputDiv">
                        <input class="valueInput1" id="cardName" type="text" name="Name" placeholder="Nombre completo de la tarjeta" required>
                    </div>

                    <div class="labelInputDiv">
                        <p class="labelInput1">Número de la tarjeta</p>
                    </div>
                    <div class="valueInputDiv">
                        <input class="valueInput1" id="cardNumber" type="text" name="cardNumber" placeholder="0000 0000 0000 0000" maxlength="19" required>
                    </div>     

                    <div class="labelInputDiv">
                        <p class="labelInput1">Fecha de caducidad</p>
                    </div>
                    <div class="valueInputDiv">
                        <input class="valueInput1" id="expDate" type="text" name="expdate" placeholder="MM/YY" minlength="5" maxlength="5" required>
                    </div>   

                    <div class="labelInputDiv">
                        <p class="labelInput1">CVV</p>
                    </div>
                    <div class="valueInputDiv">
                        <input class="valueInput1" id="cvv" type="password" name="cvv" placeholder="&#9679;&#9679;&#9679;" minlength="3" maxlength="3" required>
                    </div>

                    <div class="submitDiv">
                        <button id="submit" class="submitButton">Pagar</button>
                    </div>
                </div>
            </form>
        </div>
    `;

    $('body').append(paymentFormHtml);

    // Event listener to close the payment form and overlay when clicking outside of the form
    $('.overlay').click(function() {
        $('.paymentDiv').remove();
        $('.overlay').remove();
    });
}