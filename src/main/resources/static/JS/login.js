document.addEventListener('DOMContentLoaded', function() {
    document.getElementById('loginForm').addEventListener('submit', function(event) {
        event.preventDefault();
        var username = document.getElementById('username').value;
        var password = document.getElementById('password').value;

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
            window.location.href = '/index';
        })
        .catch(error => {
            document.getElementById('errorMessage').textContent = error.error;
        });
    });
});