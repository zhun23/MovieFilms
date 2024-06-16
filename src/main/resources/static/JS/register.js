/*
document.getElementById("register").addEventListener("submit", function(event) {
    event.preventDefault();

    const password = document.getElementById('regpass').value;
    const confirmPassword = document.getElementById('password').value;
    
    if (password !== confirmPassword) {
        showError("Las contraseñas no coinciden");
        return;
    }

    var formData = {
        nickname: document.getElementById("nickname").value,
        firstname: document.getElementById("firstName").value,
        lastname: document.getElementById("lastName").value,
        mail: document.getElementById("regmail").value,
        password: document.getElementById("regpass").value,
        role: "USER"
    };

    fetch("/user/regUser", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(formData)
    })
    .then(response => {
        if (response.ok) {
            return response.json();
        } else {
            return response.json().then(data => {
                throw new Error(data.message);
            });
        }
    })
    .then(data => {
        const messageElement = document.createElement('p');
        messageElement.innerHTML = 'Usuario creado correctamente<br>Redirigiendo a la página de login...';
        messageElement.className = 'createdUser parpa';
        const registerButton = document.getElementById('registerButton');
        registerButton.insertAdjacentElement('afterend', messageElement);

        setTimeout(() => {
            window.location.href = '/login';
        }, 8000);
    })
    .catch(error => {
        showError(error.message);
    });
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
*/

document.addEventListener('DOMContentLoaded', function() {
    setTimeout(() => {
        var errors = document.querySelectorAll('.error-message');
        errors.forEach(error => {
            error.style.display = 'none';
        });
    }, 6000);
});