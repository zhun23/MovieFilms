document.addEventListener('DOMContentLoaded', () => {

    const searchButton = document.getElementById('search');
    const userInput = document.getElementById('findGlobalUser');

    searchButton.addEventListener('click', async () => {
        const userValue = userInput.value;
        if (!userValue) {
            //aqui quiero mostrar por el HTML un <p></p> con este mensaje entre medias y en color rojo:
            // "Por favor, introduce un valor para buscar."
            alert('Por favor, introduce un valor para buscar.');
            return;
        }

        const urls = [
            `http://localhost:8089/user/id/${encodeURIComponent(userValue)}`,
            `http://localhost:8089/user/nickname/${encodeURIComponent(userValue)}`,
            `http://localhost:8089/user/mail/${encodeURIComponent(userValue)}`,
            `http://localhost:8089/user/firstname/${encodeURIComponent(userValue)}`,
            `http://localhost:8089/user/lastname/${encodeURIComponent(userValue)}`
        ];

        const fetchPromises = urls.map(url =>
            fetch(url, {
                method: "GET",
                headers: {
                    "Accept": "application/json",
                    "Content-Type": "application/json"
                }
            }).then(response => response.ok ? response.json() : Promise.reject('Consulta fallida'))
        );

        Promise.allSettled(fetchPromises).then(results => {
            const successfulResults = results.filter(result => result.status === 'fulfilled');

            if (successfulResults.length > 0) {
                successfulResults.forEach(result => {
                    console.log(result.value);
                });
            } else {
                alert('Usuario no encontrado.');
            }
        });
    });
});


