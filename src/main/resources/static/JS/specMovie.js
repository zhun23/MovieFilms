document.addEventListener("DOMContentLoaded", function() {
    const form = document.getElementById('searchForm');
    form.addEventListener('submit', function(e) {
        e.preventDefault();
        const id = document.getElementById('movieId').value;
        if (id) {
            fetch(`http://localhost:8090/id/${id}`)
                .then(response => {
                    if (!response.ok) {
                        throw new Error('No se encontró ninguna película con ese ID');
                    }
                    return response.json();
                })
                .then(data => {
                    const movieInfo = `
                        <div class="movie-container">
                            <div class="movie-image-container">
                                <img src="${data.imgUrl}" alt="Imagen de ${data.title}" class="movie-image">
                            </div>
                            <div class="movie-details">
                                <h2>${data.title} (${data.releaseDate})</h2>
                                <p class="classDescription"><strong>Descripción:</strong> ${data.description}</p>
                                <p><strong>Género:</strong> ${data.genre}</p>
                                <p><strong>Director:</strong> ${data.director}</p>
                                <p><strong>Novedad:</strong> ${data.newRelease ? 'Sí' : 'No'}</p>
                                <p><strong>Stock:</strong> ${data.stock}</p>
                            </div>
                        </div>
                    `;
                    document.getElementById('result').innerHTML = movieInfo;
                })
                .catch(error => {
                    console.error('Error:', error);
                    document.getElementById('result').innerHTML = `<p>${error.message}</p>`;
                });
        } else {
            document.getElementById('result').innerHTML = '<p>Por favor, introduzca un ID válido.</p>';
        }
    });
});




