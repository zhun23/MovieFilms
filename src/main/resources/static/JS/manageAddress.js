/*
document.getElementById('backButton').addEventListener('click', function() {
    window.location.href = "/account";
});
*/

document.addEventListener('DOMContentLoaded', function() {
    getUserId(username)
        .then(userId => {
            createAddressDetail(userId);
        });
});

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
        !address.province && 
        !address.country
    ) {
        addressesContainer.innerHTML = '<p>No tienes ninguna dirección guardada.</p>';
        createAddAddressButton();
        return;
    }

    const addressDiv = document.createElement('div');
    addressDiv.className = 'address-detail';

    if (address.defaultAddress) {
        addressDiv.classList.add('default');
    }

    let addressHTML = `
        <h3>Dirección</h3>
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

    const deleteIcon = createDeleteIcon();
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

function createDeleteIcon() {
    const deleteIconDiv = document.createElement('div');
    deleteIconDiv.className = 'editsButtons';

    const deleteIconLink = document.createElement('a');
    deleteIconLink.href = '#';

    const deleteIcon = document.createElement('i');
    deleteIcon.id = 'deleteButton';
    deleteIcon.className = 'material-symbols-outlined';
    deleteIcon.textContent = 'delete';

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

function createDeleteIcon() {
    const deleteIconDiv = document.createElement('div');
    deleteIconDiv.className = 'editsButtons';

    const deleteIconLink = document.createElement('a');
    deleteIconLink.href = '#';

    const deleteIcon = document.createElement('i');
    deleteIcon.id = 'deleteButton';
    deleteIcon.className = 'material-symbols-outlined';
    deleteIcon.textContent = 'delete';

    /*
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
    */

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
