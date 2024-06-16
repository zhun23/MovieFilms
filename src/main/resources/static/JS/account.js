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
    fetchUserDetails();
    checkIfAdmin();

    $('#button2').click(function() {
        window.location.href = '/myOrders';
    });

    getUserId(username)
        .then(userId => {
            createAddressDetail(userId);
        });
});

function fetchUserDetails() {
    $.ajax({
        url: 'http://localhost:8090/api/user/user/nicknameDto/' + username,
        type: 'GET',
        success: function(response) {
            displayUserDetails(response);
        },
        error: function(error) {
            console.error('Error al obtener los detalles del usuario:', error);
        }
    });
}

function displayUserDetails(user) {
    const userDetailsContainer = $('#userDetails');
    const userHTML = `
        <h2>Datos de usuario</h2>
        <p>Usuario: ${user.nickname}</p>
        <p>Nombre: ${user.firstname}</p>
        <p>Apellidos: ${user.lastname}</p>
        <p>Correo electrónico: ${user.mail}</p>
    `;
    userDetailsContainer.html(userHTML);
}

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

function getUserId(username) {
    return new Promise((resolve) => {
        $.ajax({
            url: '/api/user/nickname/' + username + '/id',
            type: 'GET',
            success: function(data) {
                resolve(data);
            }
        });
    });
}

function createAddressDetail(userId) {
    $.ajax({
        url: '/api/getAddress/' + userId,
        type: 'GET',
        dataType: 'json',
        success: function(response) {
            if (response) {
                displayAddress(response);
            } else {
                displayNoAddressMessage();
            }
        }
    });
}

function displayNoAddressMessage() {
    const addressesContainer = document.getElementById('addresses');
    addressesContainer.innerHTML = '<p>No hay ninguna dirección disponible.</p>';
    createAddAddressButton();
}

function displayAddress(address) {
    const addressesContainer = document.getElementById('addresses');
    if (!addressesContainer) {
        console.error('Element with ID "addresses" not found');
        return;
    }
    addressesContainer.innerHTML = '';

    if (
        !address.addressLine1 && 
        !address.location && 
        !address.zipcode && 
        !address.country
    ) {
        addressesContainer.innerHTML = `
        <h2>Dirección de envío</h2>
        <p>No tienes ninguna dirección de envío registrada. <br></br>Pulsa en añadir dirección para agregar una.</p>`;
        createAddAddressButton();
        return;
    }

    const addressDiv = document.createElement('div');
    addressDiv.className = 'address-detail';

    if (address.defaultAddress) {
        addressDiv.classList.add('default');
    }

    let addressHTML = `
        <h2>Dirección de envío</h2>
        <p>Dir 1: ${address.addressLine1 || 'No especificada'}</p>
    `;

    if (address.addressLine2) {
        addressHTML += `<p>Dir 2: ${address.addressLine2}</p>`;
    }

    addressHTML += `
        <p>Localidad: ${address.location || 'No especificada'}</p>
        <p>Código Postal: ${address.zipcode || 'No especificado'}</p>
        <p>Provincia: ${address.province || 'No especificada'}</p>
        <p>País: ${address.country || 'No especificado'}</p>
    `;

    addressDiv.innerHTML = addressHTML;

    const deleteIcon = createDeleteIcon(address.addressid); // Aquí se pasa addressid
    const editIcon = createEditIcon(address);

    const buttonsContainer = document.createElement('div');
    buttonsContainer.className = 'buttons-container';

    buttonsContainer.appendChild(editIcon);
    buttonsContainer.appendChild(deleteIcon);

    addressDiv.appendChild(buttonsContainer);

    addressesContainer.appendChild(addressDiv);
}

function createAddAddressButton() {
    var button = document.createElement('button');
    button.id = 'addAddress';
    button.className = 'ButtonsAddress';
    button.textContent = 'Añadir dirección';

    button.addEventListener('click', function() {
        window.location.href = '/addAddress';
    });

    var container = document.getElementById('manageButtonsContainer');
    container.appendChild(button);
}

function createDeleteIcon(addressid) {
    const deleteIconDiv = document.createElement('div');
    deleteIconDiv.className = 'editsButtons';

    const deleteIconLink = document.createElement('a');
    deleteIconLink.href = '#';

    const deleteIcon = document.createElement('i');
    deleteIcon.id = 'deleteButton';
    deleteIcon.className = 'material-symbols-outlined';
    deleteIcon.textContent = 'delete';

    // Agregar el manejador de eventos
    deleteIcon.addEventListener('click', function(event) {
        event.preventDefault();

        const data = {
            "addressLine1": null,
            "addressLine2": null,
            "zipcode": null,
            "location": null,
            "province": "Barcelona",
            "country": null
        };

        getUserId(username)
            .then(userId => {
                fetch('/api/address/' + addressid, {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(data)
                })
                .then(response => response.json())
                .then(result => {
                    console.log('Success:', result);
                    alert('Dirección eliminada con éxito');
                    createAddressDetail(userId);
                })
                .catch(error => {
                    console.error('Error:', error);
                });
            });
    });

    deleteIconLink.appendChild(deleteIcon);
    deleteIconDiv.appendChild(deleteIconLink);

    return deleteIconDiv;
}

function createEditIcon(address) {
    const editIconDiv = document.createElement('div');
    editIconDiv.className = 'editsButtons';

    const editIconLink = document.createElement('a');
    editIconLink.href = '#';

    const editIcon = document.createElement('i');
    editIcon.id = 'editButton';
    editIcon.className = 'material-symbols-outlined';
    editIcon.textContent = 'edit';

    editIcon.addEventListener('click', function() {
        const params = new URLSearchParams({
            addressId: address.addressid,
            addressLine1: address.addressLine1,
            addressLine2: address.addressLine2,
            zipcode: address.zipcode,
            location: address.location,
            province: address.province,
            country: address.country
        });

        window.location.href = '/modifyAddress?' + params.toString();
    });

    editIconLink.appendChild(editIcon);
    editIconDiv.appendChild(editIconLink);

    return editIconDiv;
}
