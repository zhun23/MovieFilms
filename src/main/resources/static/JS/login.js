document.addEventListener('DOMContentLoaded', function() {
    var modal = document.getElementById('errorModal');
    var closeButton = document.getElementsByClassName('close')[0];
    var loginForm = document.getElementById('loginForm');
    var usernameInput = document.getElementById('username');
    var passwordInput = document.getElementById('password');

    function closeModal() {
        modal.style.display = 'none';
        usernameInput.focus(); // Devolver el foco al campo de usuario después de cerrar el modal
    }

    document.addEventListener('keydown', function(event) {
        if (event.key === ' ' && modal.style.display === 'block') { // ' ' representa la tecla Space
            closeModal();
        }
    });

    closeButton.onclick = closeModal;

    loginForm.addEventListener('submit', function(event) {
        event.preventDefault();
        var username = usernameInput.value;
        var password = passwordInput.value;

        fetch('/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: 'username=' + encodeURIComponent(username) + '&password=' + encodeURIComponent(password)
        })
        .then(response => {
            if (!response.ok) {
                return response.json().then(error => { throw error; });
            }
            // Limpiar campos de nombre de usuario y contraseña después de enviar el formulario
            usernameInput.value = '';
            passwordInput.value = '';
            usernameInput.focus(); // Devolver el foco al campo de usuario
            window.location.href = '/index';
        })
        .catch(error => {
            var errorMessage = document.getElementById('errorMessage');
            errorMessage.textContent = error.error;
            modal.style.display = 'block';
        });
    });
});
