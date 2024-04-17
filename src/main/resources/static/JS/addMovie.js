document.addEventListener('DOMContentLoaded', () => {
    document.getElementById('buttonAcpt').addEventListener('click', async function(e) {
        e.preventDefault();

        try {
            const movie = {
                title: document.getElementById('titleInput').value,
                description: document.getElementById('descriptionInput').value,
                releaseDate: document.getElementById('releaseDateInput').value,
                genre: document.getElementById('genreSelect').value,
                director: document.getElementById('directorInput').value,
                newRelease: document.getElementById('newRelease').value === "True",
                imgUrl: document.getElementById('imgUrlInput').value,
                stock: parseInt(document.getElementById('stockInput').value)
            };

            const movieJSON = JSON.stringify(movie);

            console.log(movieJSON); //Guardo el consolelog para revisar como le pasa el JSON dado que he tenido que pelearme muchas veces con ello al hacer envios fallidos

            const response = await fetch('http://localhost:8089/movie', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: movieJSON,
            });

            const messageContainer = document.getElementById('msgShow');
            if (response.ok && response.headers.get("Content-Type")?.includes("application/json")) {
                const responseJson = await response.json();
                messageContainer.innerHTML = `Película con ID ${responseJson.id} añadida correctamente`;
                messageContainer.className = 'message success message-size';
            } else if (!response.ok) {
                const errorResponseJson = await response.json();
                let errorMessage = "<ul>";
                if (errorResponseJson.errors) {
                    Object.keys(errorResponseJson.errors).forEach(key => {
                        errorMessage += `<p>${key}: ${errorResponseJson.errors[key]}</p>`;
                    });
                } else {
                    errorMessage += `<p>${errorResponseJson.message}</p>`;
                }
                errorMessage += "</ul>";
                messageContainer.innerHTML = errorMessage;

                messageContainer.className = 'message error';
            } else {
                messageContainer.innerHTML = "Ha ocurrido un error al añadir la película.";

                messageContainer.className = 'message error';
            }
        } catch (error) {
            console.error('Error en la solicitud:', error);
            document.getElementById('msgShow').innerHTML = "Error al conectar con el servidor.";
        }
    });

    document.getElementById('buttonCncl').addEventListener('click', function(e) {
        e.preventDefault();

        document.getElementById('titleInput').value = '';
        document.getElementById('descriptionInput').value = '';
        document.getElementById('releaseDateInput').value = '';
        document.getElementById('genreSelect').selectedIndex = 0;
        document.getElementById('directorInput').value = '';
        document.getElementById('newRelease').selectedIndex = 0;
        document.getElementById('imgUrlInput').value = '';
        document.getElementById('stockInput').value = 0;

        const messageContainer = document.getElementById('msgShow');
        messageContainer.innerHTML = '';
        messageContainer.className = '';
    });
});
