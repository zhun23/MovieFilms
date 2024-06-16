document.getElementById('backButton').addEventListener('click', function() {
    window.location.href = "/account";
});

document.addEventListener('DOMContentLoaded', function() {

    const urlParams = new URLSearchParams(window.location.search);
    const addressId = urlParams.get('addressId');
    const addressLine1 = urlParams.get('addressLine1');
    const addressLine2 = urlParams.get('addressLine2');
    const zipcode = urlParams.get('zipcode');
    const location = urlParams.get('location');
    const province = urlParams.get('province');
    const country = urlParams.get('country');

    document.getElementById('addressId').value = addressId;
    document.getElementById('addressLine1').value = addressLine1;
    document.getElementById('addressLine2').value = addressLine2 !== null && addressLine2 !== 'null' ? addressLine2 : '';
    document.getElementById('zipcode').value = zipcode;
    document.getElementById('location').value = location;
    document.getElementById('province').value = province;
    document.getElementById('country').value = country;

    // Captura el evento de envío del formulario
    $('#modifyAddressForm').on('submit', function(event) {
        event.preventDefault();

        var addressData = {
            addressLine1: $('#addressLine1').val(),
            addressLine2: $('#addressLine2').val(),
            zipcode: $('#zipcode').val(),
            location: $('#location').val(),
            province: $('#province').val(),
            country: $('#country').val()
        };

        getUserId(username)
            .then(userId => {
                $.ajax({
                    url: '/api/address/' + addressId,
                    type: 'PUT',
                    contentType: 'application/json',
                    data: JSON.stringify(addressData),
                    success: function(response) {
                        console.log("Dirección actualizada:", response);
                        $('#successModal').show();
                    },
                    error: function(error) {
                        console.error("Error al actualizar la dirección:", error);
                    }
                });
            });
    });

    $('.close').on('click', function() {
        $('#successModal').hide();
    });
});

function getUserId(username) {
    return new Promise((resolve) => {
        $.ajax({
            url: '/api/user/nickname/' + username + '/id',
            type: 'GET',
            success: function(data) {
                console.log("getUserId response:", data);
                resolve(data);
            }
        });
    });
}
