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

function createMovieCard(movie) {
    const completeCard = document.createElement('div');
    completeCard.classList.add('completeCard');
    completeCard.id = 'completeCard';

    const completeCardTitle = document.createElement('h2');
    completeCardTitle.classList.add('completeCardTitle');
    completeCardTitle.id = 'completeCardTitle';
    completeCardTitle.textContent = movie.title;

    const card = document.createElement('div');
    card.classList.add('card');
    card.id = 'movie-card';

    const img = document.createElement('img');
    img.src = movie.imgUrl;
    img.alt = 'Descripción de la imagen';

    const cardContent = document.createElement('div');
    cardContent.classList.add('card-content');

    const description = document.createElement('p');
    description.textContent = movie.description;

    const alquilarLink = document.createElement('a');
    alquilarLink.href = '/movie';

    alquilarLink.addEventListener('click', function(event) {
        localStorage.setItem('selectedMovie', JSON.stringify(movie));
    });
    
    if (movie.stock > 0) {
        alquilarLink.textContent = 'Info';
        alquilarLink.classList.add('rent-button');
        const span = document.createElement('span');
        span.classList.add('material-symbols-outlined');
        span.textContent = 'arrow_right_alt';
        alquilarLink.appendChild(span);
    } else {
        alquilarLink.classList.add('rent-button-nonstock');
        alquilarLink.textContent = 'No disponible';
        alquilarLink.disabled = true;
    }

    completeCard.appendChild(completeCardTitle);
    
    card.appendChild(img);
    card.appendChild(cardContent);
    cardContent.appendChild(description);
    cardContent.appendChild(alquilarLink);

    return [completeCard, card];
}

// Función para crear y mostrar las tarjetas de película
function createMovieCards(data) {
    const mainContainer = document.getElementById('main-container');
    data.forEach(movie => {
        const [completeCard, card] = createMovieCard(movie);
        const container = document.createElement('div'); // Crear un contenedor para alinear los bloques horizontalmente
        container.classList.add('card-container');
        container.appendChild(completeCard); // Agregar completeCard al contenedor
        container.appendChild(card); // Agregar card al contenedor
        mainContainer.appendChild(container); // Agregar el contenedor al contenedor principal
    });
}

// Hacer la solicitud HTTP para obtener los datos de las películas
fetch('http://localhost:8090/genre/crime')
    .then(response => response.json())
    .then(data => {
        createMovieCards(data); // Crear y mostrar las tarjetas de película
    });