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
    cartIconLink.href = '/cart';

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

document.addEventListener("DOMContentLoaded", function() {
    const tableBody = document.querySelector("#table tbody");
    const titleHeader = document.querySelector("#titleCol"); // Encabezado de "Título"
    const dateHeader = document.querySelector("#DateCol"); // Encabezado de "Fecha Lanzamiento"
    let currentSort = 'asc'; // Estado actual de ordenamiento

    titleHeader.addEventListener('click', function() {
        fetchMoviesSortedByTitle();
    });

    dateHeader.addEventListener('click', function() {
        if (currentSort === 'asc') {
            fetchMoviesSortedByDateAsc();
        } else {
            fetchMoviesSortedByDateDesc();
        }
    });

    function fetchMovies() {
        const url = 'http://localhost:8090/api/listIncMoviesAsc';

        fetch(url)
            .then(response => {
                if (!response.ok) {
                    throw new Error("Error al obtener los datos: " + response.statusText);
                }
                return response.json();
            })
            .then(data => {
                populateTable(data.movies);
            })
            .catch(error => {
                console.error('Fetch error:', error);
            });
    }

    function fetchMoviesSortedByDateAsc() {
        const url = 'http://localhost:8090/api/listIncMoviesAsc';

        fetch(url)
            .then(response => {
                if (!response.ok) {
                    throw new Error("Error al obtener los datos: " + response.statusText);
                }
                return response.json();
            })
            .then(data => {
                populateTable(data.movies);
                currentSort = 'asc';
            })
            .catch(error => {
                console.error('Fetch error:', error);
            });
    }

    function fetchMoviesSortedByDateDesc() {
        const url = 'http://localhost:8090/api/listIncMoviesDesc';

        fetch(url)
            .then(response => {
                if (!response.ok) {
                    throw new Error("Error al obtener los datos: " + response.statusText);
                }
                return response.json();
            })
            .then(data => {
                populateTable(data.movies);
                currentSort = 'desc';
            })
            .catch(error => {
                console.error('Fetch error:', error);
            });
    }

    function fetchMoviesSortedByTitle() {
        const url = 'http://localhost:8090/api/listIncMoviesSortedByTitle';

        fetch(url)
            .then(response => {
                if (!response.ok) {
                    throw new Error("Error al obtener los datos: " + response.statusText);
                }
                return response.json();
            })
            .then(data => {
                populateTable(data.movies);
            })
            .catch(error => {
                console.error('Fetch error:', error);
            });
    }

    function populateTable(movies) {
        while (tableBody.firstChild) {
            tableBody.removeChild(tableBody.firstChild);
        }
        
        movies.forEach(movie => {
            const row = document.createElement("tr");

            const titleCell = document.createElement("td");
            titleCell.textContent = movie.title;
            titleCell.classList.add("titleClass");

            const dateCell = document.createElement("td");
            dateCell.textContent = formatDate(movie.releaseDateInc);
            dateCell.classList.add("dateClass");

            row.appendChild(titleCell);
            row.appendChild(dateCell);
            tableBody.appendChild(row);
        });
    }

    function formatDate(dateString) {
        const [year, month, day] = dateString.split('-');
        return `${day}-${month}-${year}`;
    }

    fetchMovies();
});

