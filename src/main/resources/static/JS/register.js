document.addEventListener('DOMContentLoaded', (event) => {
    const form = document.querySelector('.registro');

    form.addEventListener('submit', function(e) {
        e.preventDefault();

        const password = document.getElementById('regpass').value;
        const confirmPassword = document.getElementById('password').value;
        
        // Verificar si las contraseñas coinciden
        if (password !== confirmPassword) {
            showError("Las contraseñas no coinciden");
            return; // Detiene la ejecución adicional si las contraseñas no coinciden
        }

        const formData = {
            nickname: document.getElementById('nickname').value,
            firstName: document.getElementById('firstName').value,
            lastName: document.getElementById('lastName').value,
            mail: document.getElementById('regmail').value,
            password: password,
        };

        const fetchOptions = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        };

        fetch('/regUser', fetchOptions)
        .then(response => {
            if (!response.ok) {
                return response.json().then(data => {
                    throw data;
                });
            }
            return response.json();
        })
        .then(data => {
            const messageElement = document.createElement('p');
            messageElement.innerHTML = 'Usuario creado correctamente<br>Redirigiendo a la página principal...';
            messageElement.className = 'createdUser parpa';
            const registerButton = document.getElementById('registerButton');
            registerButton.insertAdjacentElement('afterend', messageElement);

            setTimeout(() => {
                messageElement.remove();
                window.location.href = 'futuroindex.html';
            }, 10000);
        })
    });

    function showError(message) {
        const registerButton = document.getElementById('registerButton');
        let errorContainer = document.getElementById('errorContainer');
        if (!errorContainer) {
            errorContainer = document.createElement('div');
            errorContainer.id = 'errorContainer';
            registerButton.parentNode.insertBefore(errorContainer, registerButton.nextSibling);
        }

        errorContainer.innerHTML = '';

        const errorMessage = document.createElement('p');
        errorMessage.textContent = message;
        errorMessage.className = 'errorUser parpa';
        errorContainer.appendChild(errorMessage);

        setTimeout(() => {
            errorContainer.innerHTML = '';
        }, 6000);
    }
});

