document.getElementById('backButton').addEventListener('click', function() {
    window.location.href = "/account";
});

$(document).ready(function() {
    getUserId(username)
        .then(userId => {
            createAddressDetail(userId)
                .then(addressId => {
                    console.log("User ID:", userId);
                    console.log("Address ID:", addressId);

                    $('#addAddressForm').on('submit', function(event) {
                        event.preventDefault();

                        var addressData = {
                            addressLine1: $('#addressLine1').val(),
                            addressLine2: $('#addressLine2').val(),
                            zipcode: $('#zipcode').val(),
                            location: $('#location').val(),
                            province: $('#province').val(),
                            country: $('#country').val()
                        };

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
        });

    function getUserId(username) {
        return new Promise((resolve) => {
            $.ajax({
                url: '/api/user/nickname/' + username + '/id',
                type: 'GET',
                success: function(data) {
                    console.log("getUserId response:", data);
                    userMioId = data;
                    resolve(data);
                }
            });
        });
    }

    function createAddressDetail(userMioId) {
        return new Promise((resolve) => {
            $.ajax({
                url: '/api/getAddress/' + userMioId,
                type: 'GET',
                dataType: 'json',
                success: function(response) {
                    console.log("createAddressDetail response:", response);
                    addressMioId = response.addressid;
                    resolve(response.addressid);
                }
            });
        });
    }

    $('.close').on('click', function() {
        $('#successModal').hide();
    });
});