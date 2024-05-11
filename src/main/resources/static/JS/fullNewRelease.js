// Función para crear la estructura de HTML de la tarjeta de película
function createMovieCard(movie) {
    // Crear elementos de tarjeta de película
    const completeCard = document.createElement('div');
    completeCard.classList.add('completeCard');
    completeCard.id = 'completeCard';

    const completeCardTitle = document.createElement('h2');
    completeCardTitle.classList.add('completeCardTitle');
    completeCardTitle.id = 'completeCardTitle';
    completeCardTitle.textContent = movie.title; // Añadir el título de la película

    const card = document.createElement('div');
    card.classList.add('card');
    card.id = 'movie-card';

    const img = document.createElement('img');
    img.src = movie.imgUrl;
    img.alt = 'Descripción de la imagen';

    const cardContent = document.createElement('div');
    cardContent.classList.add('card-content');

    const description = document.createElement('p');
    description.textContent = movie.description; // Añadir la descripción de la película

    const alquilarLink = document.createElement('a');
    alquilarLink.href = '#';
    alquilarLink.classList.add('button');
    alquilarLink.textContent = 'Alquilar';

    const span = document.createElement('span');
    span.classList.add('material-symbols-outlined');
    span.textContent = 'arrow_right_alt';

    completeCard.appendChild(completeCardTitle);
    card.appendChild(img);
    card.appendChild(cardContent);
    cardContent.appendChild(description);
    alquilarLink.appendChild(span);
    alquilarLink.appendChild(document.createTextNode(' '));
    cardContent.appendChild(alquilarLink);

    // Devolver un array con los elementos
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
fetch('http://localhost:8090/newRelease/true')
    .then(response => response.json())
    .then(data => {
        createMovieCards(data); // Crear y mostrar las tarjetas de película
    });

   
