document.addEventListener('DOMContentLoaded', function() {
    getUserId(username)
        .then(userId => createCartDetail(userId));

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

function createCartDetail(userId){
    $.ajax({
        url: '/api/getCarts/' + userId,
        type: 'GET',
        success: function(cartData) {
            console.log('Datos del carrito:', cartData)
            const cartContainer = document.getElementById('cart-container');
            cartContainer.innerHTML = ''
            if (!cartData.details || cartData.details.length === 0) {
                const emptyMessageElement = document.createElement('p');
                emptyMessageElement.textContent = 'Actualmente no dispones de ningún artículo en tu carrito';
                emptyMessageElement.classList.add('emptyCartMessage')
                const emptyImageElement = document.createElement('img');
                emptyImageElement.src = '/images/net.png';
                emptyImageElement.alt = 'Tu carrito está vacío';
                emptyImageElement.classList.add('emptyImg');
                emptyImageElement.classList.add('emptyCartMessage')
                cartContainer.appendChild(emptyMessageElement);
                cartContainer.appendChild(emptyImageElement);
            } else {
                let totalItems = 0;
                let totalPrice = 0
                cartData.details.forEach(function(detail) {
                    totalItems += detail.quantity;
                    totalPrice += detail.catalogue.price * detail.quantity
                    const detailElement = document.createElement('div');
                    detailElement.classList.add('cart-detail')
                    const boxContainer = document.createElement('div');
                    boxContainer.classList.add('box-container')
                    const imageContainer = document.createElement('div');
                    imageContainer.classList.add('image-container');

                    const imageElement = document.createElement('img');
                    imageElement.src = detail.catalogue.imgUrl;
                    imageElement.classList.add('cartItemImg');
                    imageElement.style.width = '100px';
                    imageElement.style.height = '150px';
                    imageContainer.appendChild(imageElement)
                    const textContainer = document.createElement('div');
                    textContainer.classList.add('text-container');

                    const titleElement = document.createElement('h3');
                    titleElement.textContent = detail.catalogue.title;
                    titleElement.classList.add('titleMovie')
                    const priceElement = document.createElement('p');
                    priceElement.textContent = 'Precio: ' + detail.catalogue.price;
                    priceElement.classList.add('priceMovie')
                    const quantityElement = document.createElement('p');
                    quantityElement.textContent = 'Cantidad: ' + detail.quantity;
                    quantityElement.classList.add('quantityMovie')
                    const catalogueId = detail.catalogue.id;

                    const deleteIconDiv = createDeleteIcon(userId, catalogueId);
                    
                    textContainer.appendChild(titleElement);
                    textContainer.appendChild(priceElement);
                    textContainer.appendChild(quantityElement)
                    boxContainer.appendChild(imageContainer);
                    boxContainer.appendChild(textContainer);
                    boxContainer.appendChild(deleteIconDiv);
                    detailElement.appendChild(boxContainer);
                    cartContainer.appendChild(detailElement);
                })
                
                const priceWithoutIVA = (totalPrice / 1.21).toFixed(2)
                const ivaQuantity = (totalPrice - priceWithoutIVA).toFixed(2);

                const cartSummary = document.createElement('div');
                cartSummary.classList.add('cartSummary')
                const totalItemsBuyDiv = document.createElement('div');
                totalItemsBuyDiv.classList.add('totalItemsBuyDiv');
                const totalItemsBuy = document.createElement('p');
                totalItemsBuy.classList.add('totalItemsBuy');
                totalItemsBuy.textContent = 'Total artículos: ';
                const totalItemsBuyCount = document.createElement('p');
                totalItemsBuyCount.classList.add('totalItemsBuyCount');
                totalItemsBuyCount.textContent = totalItems;
                totalItemsBuyDiv.appendChild(totalItemsBuy);
                totalItemsBuyDiv.appendChild(totalItemsBuyCount)
                const priceDiv = document.createElement('div');
                priceDiv.classList.add('priceDiv');
                const priceItems = document.createElement('p');
                priceItems.classList.add('priceItems');
                priceItems.textContent = 'Precio artículos: ';
                const priceItemsCount = document.createElement('p');
                priceItemsCount.classList.add('priceItemsCount');
                priceItemsCount.textContent = priceWithoutIVA;
                priceDiv.appendChild(priceItems);
                priceDiv.appendChild(priceItemsCount)
                const ivaDiv = document.createElement('div');
                ivaDiv.classList.add('ivaDiv');
                const ivaItems = document.createElement('p');
                ivaItems.classList.add('ivaItems');
                ivaItems.textContent = '*21% IVA: ';
                const ivaItemsCount = document.createElement('p');
                ivaItemsCount.classList.add('ivaItemsCount');
                ivaItemsCount.textContent = ivaQuantity;
                ivaDiv.appendChild(ivaItems);
                ivaDiv.appendChild(ivaItemsCount)
                const totalPriceDiv = document.createElement('div');
                totalPriceDiv.classList.add('totalPriceDiv');
                const totalPriceItems = document.createElement('p');
                totalPriceItems.classList.add('totalPriceItems');
                totalPriceItems.textContent = 'Total: ';
                const totalPriceItemsCount = document.createElement('p');
                totalPriceItemsCount.classList.add('totalPriceItemsCount');
                totalPriceItemsCount.textContent = totalPrice.toFixed(2) + ' €';
                totalPriceDiv.appendChild(totalPriceItems);
                totalPriceDiv.appendChild(totalPriceItemsCount)
                const shippingCostsDiv = document.createElement('div');
                shippingCostsDiv.classList.add('shippingCostsDiv');
                const shippingCosts = document.createElement('p');
                shippingCosts.classList.add('shippingCosts');
                shippingCosts.textContent = '*Los gastos de envío se calcularán en el siguiente paso';
                shippingCostsDiv.appendChild(shippingCosts);

                const specificShippingInfo = document.createElement('p');
                specificShippingInfo.classList.add('specificShippingInfo');
                specificShippingInfo.textContent = '*Para envíos a Canarias, Ceuta y Melilla se aplicará el impuesto correspondiente';
                shippingCostsDiv.appendChild(specificShippingInfo);

                const buttonContinueDiv = document.createElement('div');
                buttonContinueDiv.classList.add('buttonContinueDiv');
                const buttonContinue = document.createElement('button');
                buttonContinue.classList.add('buttonContinue');
                buttonContinue.type = 'submit';
                buttonContinue.textContent = 'Continuar';

                buttonContinue.addEventListener('click', function() {
                    window.location.href = '/buy';
                });
                
                buttonContinueDiv.appendChild(buttonContinue)
                cartSummary.appendChild(totalItemsBuyDiv);
                cartSummary.appendChild(priceDiv);
                cartSummary.appendChild(ivaDiv);
                cartSummary.appendChild(totalPriceDiv);
                cartSummary.appendChild(shippingCostsDiv);
                cartSummary.appendChild(buttonContinueDiv)
                cartContainer.appendChild(cartSummary);
            }
        },
    });
}


function getUserId(username) {
    return new Promise((resolve, reject) => {
        $.ajax({
            url: '/api/user/nickname/' + username + '/id',
            type: 'GET',
            success: function(data) {
                resolve(data);
            }
        });
    });
}

function createDeleteIcon(userId, catalogueId) {
    const deleteIconDiv = document.createElement('div');
    deleteIconDiv.className = 'deleteIconDiv';

    const deleteIconLink = document.createElement('a');
    deleteIconLink.href = '#';

    const deleteIcon = document.createElement('i');
    deleteIcon.id = 'deleteButton';
    deleteIcon.className = 'material-symbols-outlined';
    deleteIcon.textContent = 'delete';

    deleteIcon.addEventListener('click', function() {
        $.ajax({
            url: '/api/deleteCatalogue/' + userId + '/' + catalogueId,
            type: 'DELETE',
            success: function(response) {
                console.log(response);
                rebuildCartSection(userId);
            }
        });
    });

    deleteIconLink.appendChild(deleteIcon);
    deleteIconDiv.appendChild(deleteIconLink);

    return deleteIconDiv;
}

function rebuildCartSection(userId) {
    createCartDetail(userId); // Vuelve a crear la sección del carrito
}