document.addEventListener("DOMContentLoaded", function() {
    const tableBody = document.querySelector("#table tbody");

    function populateTable(movies) {
        tableBody.innerHTML = '';

        movies.forEach((movie) => {
            const row = document.createElement("tr");

            const indexCell = document.createElement("td");
            indexCell.textContent = movie.id;

            const titleCell = document.createElement("td");
            titleCell.textContent = movie.title;
            titleCell.classList.add("title-class");

            const dateCell = document.createElement("td");
            dateCell.textContent = formatDate(movie.releaseDateInc);
            dateCell.classList.add("date-class");

            const deleteCell = document.createElement("td");
            const deleteIcon = document.createElement("i");
            deleteIcon.classList.add("material-icons", "button", "delete", "delete-class");
            deleteIcon.textContent = "delete";
            deleteIcon.addEventListener("click", function() {
                delMovie(movie.id, row);
            });
            deleteCell.appendChild(deleteIcon);

            row.appendChild(indexCell);
            row.appendChild(titleCell);
            row.appendChild(dateCell);
            row.appendChild(deleteCell);
            tableBody.appendChild(row);
        });
    }

    async function fetchMovies() {
        try {
            const response = await fetch("http://localhost:8090/api/listIncMoviesAsc");
            if (!response.ok) {
                throw new Error("Network response was not ok " + response.statusText);
            }
            const data = await response.json();
            populateTable(data.movies);
        } catch (error) {
            console.error('Fetch error:', error);
        }
    }

    async function delMovie(movieId, row) {
        try {
            const response = await fetch(`http://localhost:8090/api/incMovieDelete/${movieId}`, {
                method: 'DELETE'
            });
            if (!response.ok) {
                throw new Error("Network response was not ok " + response.statusText);
            }
            const result = await response.text();
            console.log(result);
            tableBody.removeChild(row);
        } catch (error) {
            console.error('Delete error:', error);
        }
    }

    document.getElementById('button1').addEventListener('click', function() {
        const inputsDiv = document.getElementById('inputsDiv');
        
        inputsDiv.innerHTML = '';
    
        const inputsAndButtonContainer = document.createElement('div');
        inputsAndButtonContainer.classList.add('inputs-and-button-container');
    
        const titleContainer = document.createElement('div');
        titleContainer.classList.add('input-container');
    
        const titleLabel = document.createElement('label');
        titleLabel.innerText = 'Título: ';
        titleLabel.setAttribute('for', 'titleInput');
    
        const titleInput = document.createElement('input');
        titleInput.type = 'text';
        titleInput.id = 'titleInput';
        titleInput.name = 'title';
        titleInput.classList.add('input-field');
    
        titleContainer.appendChild(titleLabel);
        titleContainer.appendChild(titleInput);
    
        inputsAndButtonContainer.appendChild(titleContainer);
    
        const dateContainer = document.createElement('div');
        dateContainer.classList.add('input-container');
    
        const dateLabel = document.createElement('label');
        dateLabel.innerText = 'Fecha: ';
        dateLabel.setAttribute('for', 'dateInput');
    
        const dateInput = document.createElement('input');
        dateInput.type = 'date';
        dateInput.id = 'dateInput';
        dateInput.name = 'date';
        dateInput.classList.add('input-field');
    
        dateContainer.appendChild(dateLabel);
        dateContainer.appendChild(dateInput);
    
        inputsAndButtonContainer.appendChild(dateContainer);
    
        const saveButton = document.createElement('button');
        saveButton.innerText = 'Guardar';
        saveButton.classList.add('buttonSave');
    
        saveButton.addEventListener('click', function() {
            const title = titleInput.value;
            const date = dateInput.value;
    
            if (title && date) {
                const data = { title: title, releaseDateInc: date };
                fetch('/api/incMovieCreate', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(data)
                })
                .then(response => response.json())
                .then(newMovie => {
                    alert('Nuevo lanzamiento añadido correctamente');
                    titleInput.value = '';
                    dateInput.value = '';
    
                    fetchMovies();
                })
                .catch((error) => {
                    console.error('Error:', error);
                    alert('Hubo un error al añadir el lanzamiento');
                });
            } else {
                alert('Por favor, rellene todos los campos');
            }
        });
    
        inputsAndButtonContainer.appendChild(saveButton);
    
        inputsDiv.appendChild(inputsAndButtonContainer);
    });

    fetchMovies();
});

function formatDate(dateString) {
    const [year, month, day] = dateString.split('-');
    return `${day}-${month}-${year}`;
}