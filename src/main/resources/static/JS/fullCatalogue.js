window.onload = function(){
    showFullCatalogue();
}

let showFullCatalogue = async () => {
    const request = await fetch("http://localhost:8089/list", {
        method: "GET",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        }
    });

    const movies = await request.json();

    let contentTable = "";

    for (let movie of movies) {
        let newReleaseTrad = movie.newRelease ? "Sí" : "";

        let contentRow = `<tr id="row-${movie.id}">
            <td id="valor0">${movie.id}</td>
            <td>${movie.title}</td>
            <td id="releaseDateAllign">${movie.releaseDate}</td>
            <td>${movie.genre}</td>
            <td>${movie.director}</td>
            <td id="newReleaseAllign">${newReleaseTrad}</td>
            <td class="acciones">
                <i onClick="showFormEdit(${movie.id})" class="material-icons button edit">edit</i>
                <i onClick="delMovie(${movie.id})" class="material-icons button delete">delete</i>
            </td>
        </tr>`;

        contentTable += contentRow;
    }

    document.querySelector("#table tbody").innerHTML = contentTable;
}

async function showFormEdit(id) {
    let tableBody = document.querySelector("#table tbody");

    // Verificar si ya existe una fila de edición para este ID específico y eliminarla
    let existingEditRows = document.querySelectorAll(`.edit-row-${id}`);
    if (existingEditRows.length) {
        existingEditRows.forEach(row => row.remove());
        return; // Salir de la función, actuando como un "cancelar"
    }

    let row = document.getElementById(`row-${id}`);

    try {
        const response = await fetch(`http://localhost:8089/id/${id}`);
        if (!response.ok) {
            throw new Error(`Error al obtener los datos: ${response.statusText}`);
        }
        const movie = await response.json();

        // Crear fila para los botones y asignar una clase para identificarla
        let buttonsRow = document.createElement("tr");
        buttonsRow.classList.add("edit-row", `edit-row-${id}`);

        // Crear celda para el botón "Guardar cambios" y "Cancelar"
        let buttonsCell = document.createElement("td");
        buttonsCell.className = "ButtonLabelsTable";
        buttonsCell.colSpan = 6;

        // Crear formulario para contener los botones
        let form = document.createElement("form");
        form.onsubmit = function() {
            editMovie(id);
            return false;
        };

        let acceptButton = document.createElement("button");
        acceptButton.type = "submit";
        acceptButton.classList.add("ButtonLabels", "acceptButton");
        acceptButton.textContent = "Modificar cambios";
        
        let cancelButton = document.createElement("button");
        cancelButton.type = "button";
        cancelButton.classList.add("ButtonLabels", "cancelButton");
        cancelButton.textContent = "Cancelar";
        cancelButton.onclick = function() {
            document.querySelectorAll('.edit-row').forEach(row => row.remove());
        };

        form.appendChild(acceptButton);
        form.appendChild(cancelButton);
        buttonsCell.appendChild(form);
        buttonsRow.appendChild(buttonsCell);
        tableBody.insertBefore(buttonsRow, row.nextSibling);

    let newReleaseRow = document.createElement("tr");
    newReleaseRow.classList.add("edit-row", `edit-row-${id}`);
    let newReleaseCell = document.createElement("td");
    newReleaseCell.classList.add("Labels");
    newReleaseCell.textContent = "Novedad";
    let newReleaseInputCell = document.createElement("td");
    let newReleaseSelect = document.createElement("select");
    newReleaseSelect.id = "selectNewRelease";
    newReleaseSelect.value = movie.newRelease;
    let newReleaseOption1 = document.createElement("option");
    newReleaseOption1.value = "true"; 
    newReleaseOption1.textContent = "Yes";
    newReleaseSelect.appendChild(newReleaseOption1);
    let newReleaseOption2 = document.createElement("option");
    newReleaseOption2.value = "false";
    newReleaseOption2.textContent = "No";
    newReleaseSelect.appendChild(newReleaseOption2);
    newReleaseInputCell.appendChild(newReleaseSelect);
    newReleaseRow.appendChild(newReleaseCell);
    newReleaseRow.appendChild(newReleaseInputCell);

    let directorRow = document.createElement("tr");
    directorRow.classList.add("edit-row", `edit-row-${id}`);
    let directorCell = document.createElement("td");
    directorCell.classList.add("Labels");
    directorCell.textContent = "Director";
    let directorInputCell = document.createElement("td");
    let directorInput = document.createElement("input");
    directorInput.type = "text";
    directorInput.id = "inputDirector";
    directorInput.value = movie.director;
    directorInput.required = true;
    directorInputCell.appendChild(directorInput);
    directorRow.appendChild(directorCell);
    directorRow.appendChild(directorInputCell);

    let genreRow = document.createElement("tr");
    genreRow.classList.add("edit-row", `edit-row-${id}`);
    let genreCell = document.createElement("td");
    genreCell.classList.add("Labels");
    genreCell.textContent = "Género";
    let genreInputCell = document.createElement("td");
    let genreSelect = document.createElement("select");
    genreSelect.id = "selectGenre";
    genreSelect.value = movie.genre;
    let genres = ["Action", "Adventures", "ScienceFiction", "Fantasy", "Crime", "Comedy", "Romance", "Horror", "Drama", "Musical", "Thriller", "Animation", "Kids"];
    genres.forEach(genre => {
        let option = document.createElement("option");
        option.value = genre;
        option.textContent = genre;
        genreSelect.appendChild(option);
    });
    genreInputCell.appendChild(genreSelect);
    genreRow.appendChild(genreCell);
    genreRow.appendChild(genreInputCell);

    let releaseDateRow = document.createElement("tr");
    releaseDateRow.classList.add("edit-row", `edit-row-${id}`);
    let releaseDateCell = document.createElement("td");
    releaseDateCell.classList.add("Labels");
    releaseDateCell.textContent = "Fecha de Lanzamiento";
    let releaseDateInputCell = document.createElement("td");
    let releaseDateInput = document.createElement("input");
    releaseDateInput.type = "text";
    releaseDateInput.id = "inputReleaseDate";
    releaseDateInput.value = movie.releaseDate;
    releaseDateInput.required = true;
    releaseDateInputCell.appendChild(releaseDateInput);
    releaseDateRow.appendChild(releaseDateCell);
    releaseDateRow.appendChild(releaseDateInputCell);

    let descriptionRow = document.createElement("tr");
    descriptionRow.classList.add("edit-row", `edit-row-${id}`);
    let descriptionCell = document.createElement("td");
    descriptionCell.classList.add("Labels");
    descriptionCell.textContent = "Descripción";
    let descriptionInputCell = document.createElement("td");
    let descriptionInput = document.createElement("textarea");
    descriptionInput.id = "inputDescription";
    descriptionInput.value = movie.description;
    descriptionInput.required = true;
    descriptionInputCell.appendChild(descriptionInput);
    descriptionRow.appendChild(descriptionCell);
    descriptionRow.appendChild(descriptionInputCell);

    let titleRow = document.createElement("tr");
    titleRow.classList.add("edit-row", `edit-row-${id}`);
    let titleCell = document.createElement("td");
    titleCell.classList.add("Labels");
    titleCell.textContent = "Título";
    let titleInputCell = document.createElement("td");
    let titleInput = document.createElement("input");
    titleInput.classList.add("inputCustomTitle");
    titleInput.type = "text";
    titleInput.id = "inputTitle";
    titleInput.value = movie.title;
    titleInput.required = true;
    titleInputCell.appendChild(titleInput);
    titleRow.appendChild(titleCell);
    titleRow.appendChild(titleInputCell);

    form.appendChild(titleRow);

    tableBody.insertBefore(titleRow, row.nextSibling);
    tableBody.insertBefore(descriptionRow, titleRow.nextSibling);
    tableBody.insertBefore(releaseDateRow, descriptionRow.nextSibling);
    tableBody.insertBefore(genreRow, releaseDateRow.nextSibling);
    tableBody.insertBefore(directorRow, genreRow.nextSibling);
    tableBody.insertBefore(newReleaseRow, directorRow.nextSibling);

    let idRow = document.createElement("tr");
    idRow.classList.add("edit-row", `edit-row-${id}`, "IDrowEdit");
    let idCell = document.createElement("td");
    idCell.textContent = id;
    idCell.setAttribute("rowspan", "8"); 
    idRow.appendChild(idCell);

    tableBody.insertBefore(idRow, row.nextSibling);

    } catch (error) {
    console.error("No se pudo cargar la información de la película", error);
    }
}


let editMovie = async (id) => {
    let rowData = {
        "title": document.getElementById("inputTitle").value,
        "description": document.getElementById("inputDescription").value,
        "releaseDate": document.getElementById("inputReleaseDate").value,
        "genre": document.getElementById("selectGenre").value,
        "director": document.getElementById("inputDirector").value,
        "newRelease": document.getElementById("selectNewRelease").value === "true" ? true : false
    };

    let jsonData = JSON.stringify(rowData);

    //console.log("Sending data to server:", jsonData);

    const response = await fetch("http://localhost:8089/edit/" + id, {
        method: "PUT",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        body: jsonData
    });

    if (response.ok) {
        //console.log("Data sent successfully and response received from the server");
        showFullCatalogue();
    } else {
        //console.error("Error sending data to server");
        const errorData = await response.json();
        //console.error(`Error: ${errorData.error}, Message: ${errorData.message}`);
        alert(`Error: ${errorData.error}\nMensaje: ${errorData.message}`);
    }
}
        

let delMovie = async (id) => {
    const request = await fetch("http://localhost:8089/delete/" + id, {
        method: "DELETE",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        }
    });
    showFullCatalogue();
}