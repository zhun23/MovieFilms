import {
    MOVIE_EXIST_IN_CART,
    ADD_ELEMENT_TO_CART,
    UPDATE_ELEMENT_IN_CART
} from './endpoints.js'

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

document.addEventListener('DOMContentLoaded', function() {
    const movieData = localStorage.getItem('selectedMovie');
    if (movieData) {
        const movie = JSON.parse(movieData);
        console.log('Datos de la película:', movie);

        document.getElementById('movie-title').classList.add('movie-title');
        document.getElementById('movie-description').classList.add('movie-description');
        document.getElementById('movie-img').classList.add('movie-img');
        document.getElementById('movie-price').classList.add('movie-price');
        document.getElementById('movie-stock').classList.add('movie-stock');
        document.getElementById('movie-newrelease').classList.add('movie-newrelease');
        document.getElementById('movie-title').textContent = movie.title;
        document.getElementById('movie-description').textContent = movie.description;
        document.getElementById('movie-img').src = movie.imgUrl;
        document.getElementById('movie-price').textContent = 'Precio: '+ movie.price +' €';
        document.getElementById('movie-stock').textContent = movie.stock > 0 ? '' : 'No disponible';

        if (movie.stock > 0) {
            const inputContainer = document.createElement('div');
            inputContainer.classList.add('input-container');

            const decrementButton = document.createElement('button');
            decrementButton.textContent = '-';
            decrementButton.classList.add('decrement-button');

            const quantityInput = document.createElement('input');
            quantityInput.type = 'number';
            quantityInput.value = 1;
            quantityInput.min = 1;
            quantityInput.max = movie.stock;
            quantityInput.classList.add('quantity-input');

            const incrementButton = document.createElement('button');
            incrementButton.textContent = '+';
            incrementButton.classList.add('increment-button');

            decrementButton.addEventListener('click', function() {
                if (quantityInput.value > 1) {
                    quantityInput.value = parseInt(quantityInput.value) - 1;
                }
            });

            incrementButton.addEventListener('click', function() {
                if (quantityInput.value < Math.min(movie.stock, 10)) {
                    quantityInput.value = parseInt(quantityInput.value) + 1;
                }
            });

            inputContainer.appendChild(decrementButton);
            inputContainer.appendChild(quantityInput);
            inputContainer.appendChild(incrementButton);

            const buyButton = document.createElement('button');
            buyButton.textContent = 'Comprar';
            buyButton.classList.add('buybutton');

            buyButton.addEventListener('click', function() {
                if (!username) {
                    window.location.href = '/login';
                    return;
                }
                const quantity = quantityInput.value;
                const movieId = movie.id;

                execute(username, movieId, quantity);
            });

            const buyButtonContainer = document.createElement('div');
            buyButtonContainer.classList.add('buy-button-container');

            buyButtonContainer.appendChild(inputContainer);
            buyButtonContainer.appendChild(buyButton);

            const buyButtonDiv = document.getElementById('buyButtonDiv');
            buyButtonDiv.appendChild(buyButtonContainer);
        }

        if (movie.newRelease === true) {
            document.getElementById('movie-newrelease').textContent = 'Novedad';
        }
    }
});

async function execute(username, movieId, quantity) {
    if (await existsInCart(username, movieId)) {
        updateInCart(username, movieId, quantity);
    } else {
        addToCart(username, movieId, quantity);
    }
}

async function existsInCart(username, movieId) {
    try {
        const response = await fetch(`${MOVIE_EXIST_IN_CART}/${username}/${movieId}`, { method: 'GET' })

        if (!response.ok) {
            throw new Error('Error fetching user ID');
        }
        const data = await response.json()
        return data.exists
    } catch(error) {
        console.error('Error checking cart:', error);
    }
}

//const addToCart = async function (userid) {
//const addToCart = async (userid) => {
async function addToCart(username, movieId, quantity) {
    try {
        const response = await fetch(`${ADD_ELEMENT_TO_CART}?nickname=${username}&catalogueid=${movieId}&quantity=${quantity}`, { method: 'POST' })

        if (!response.ok) {
            throw new Error('Error fetching user ID');
        }
        const data = await response.json()

        console.log('Item added to cart:', data);
        alert('Artículo añadido al carrito con éxito.');
    } catch(error) {
        console.error('Error adding item to cart:', error);
        alert('Error al añadir el artículo al carrito.');
    }
}

async function updateInCart(username, movieId, quantity) {
    try {
        const response = await fetch(`${UPDATE_ELEMENT_IN_CART}?nickname=${username}&catalogueid=${movieId}&quantity=${quantity}`, { method: 'POST' })

        if (!response.ok) {
            throw new Error('Error fetching user ID');
        }
        const data = await response.json()

        console.log('Item updated in cart:', data);
        alert('Artículo añadido al carrito con éxito.');
    } catch(error) {
        console.error('Error updating item to cart:', error);
        alert('Error al añadir el artículo al carrito.');
    }
}

