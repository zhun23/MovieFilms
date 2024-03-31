window.onload = function(){
    fullCatalogue();
}

let fullCatalogue = async () => {
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

function showFormEdit(id) {
    let tableBody = document.querySelector("#table tbody");

    // Verificar si ya existe una fila de edición para este ID específico y eliminarla
    let existingEditRows = document.querySelectorAll(`.edit-row-${id}`);
    if (existingEditRows.length) {
        existingEditRows.forEach(row => row.remove());
        return; // Salir de la función, actuando como un "cancelar"
    }

    let row = document.getElementById(`row-${id}`);

    // Crear fila para los botones y asignar una clase para identificarla
    let buttonsRow = document.createElement("tr");
    buttonsRow.classList.add("edit-row", `edit-row-${id}`);

    // Crear celda para el botón "Guardar cambios" y "Cancelar"
    let buttonsCell = document.createElement("td");
    buttonsCell.classList.add("ButtonLabels");
    buttonsCell.colSpan = "6";

    // Crear botón "Guardar cambios"
    let acceptButton = document.createElement("button");
    acceptButton.classList.add("ButtonLabels");
    acceptButton.textContent = "Guardar cambios";
    acceptButton.addEventListener("click", function() {
        editMovie(id);
        document.querySelectorAll('.edit-row').forEach(row => row.remove());
    });

    // Crear botón "Cancelar"
    let cancelButton = document.createElement("button");
    cancelButton.textContent = "Cancelar";
    cancelButton.addEventListener("click", function() {
        document.querySelectorAll('.edit-row').forEach(row => row.remove());
    });

    buttonsCell.appendChild(acceptButton);
    buttonsCell.appendChild(cancelButton);

    buttonsRow.appendChild(buttonsCell);

    tableBody.insertBefore(buttonsRow, row.nextSibling);

    // Creación de fila, celda y campo para "Novedad"
    let novedadRow = document.createElement("tr");
    novedadRow.classList.add("edit-row", `edit-row-${id}`);
    let novedadCell = document.createElement("td");
    novedadCell.classList.add("Labels");
    novedadCell.textContent = "Novedad";
    let novedadInputCell = document.createElement("td");
    let novedadSelect = document.createElement("select");
    novedadSelect.id = "selectNovedad";
    let novedadOption1 = document.createElement("option");
    novedadOption1.value = "true"; // Cambiado a true
    novedadOption1.textContent = "Sí";
    novedadSelect.appendChild(novedadOption1);
    let novedadOption2 = document.createElement("option");
    novedadOption2.value = "false"; // Cambiado a false
    novedadOption2.textContent = "No";
    novedadSelect.appendChild(novedadOption2);
    novedadInputCell.appendChild(novedadSelect);
    novedadRow.appendChild(novedadCell);
    novedadRow.appendChild(novedadInputCell);

    // Creación de fila, celda y campo para "Director"
    let directorRow = document.createElement("tr");
    directorRow.classList.add("edit-row", `edit-row-${id}`);
    let directorCell = document.createElement("td");
    directorCell.classList.add("Labels");
    directorCell.textContent = "Director";
    let directorInputCell = document.createElement("td");
    let directorInput = document.createElement("input");
    directorInput.type = "text";
    directorInput.id = "inputDirector";
    directorInputCell.appendChild(directorInput);
    directorRow.appendChild(directorCell);
    directorRow.appendChild(directorInputCell);

    // Creación de fila, celda y campo para "Género"
    let generoRow = document.createElement("tr");
    generoRow.classList.add("edit-row", `edit-row-${id}`);
    let generoCell = document.createElement("td");
    generoCell.classList.add("Labels");
    generoCell.textContent = "Género";
    let generoInputCell = document.createElement("td");
    let generoSelect = document.createElement("select");
    generoSelect.id = "selectGenero";
    let generos = ["Action", "Adventures", "ScienceFiction", "Fantasy", "Crime", "Comedy", "Romance", "Horror", "Drama", "Musical", "Thriller", "Animation", "Kids"];
    generos.forEach(genero => {
        let option = document.createElement("option");
        option.value = genero;
        option.textContent = genero;
        generoSelect.appendChild(option);
    });
    generoInputCell.appendChild(generoSelect);
    generoRow.appendChild(generoCell);
    generoRow.appendChild(generoInputCell);

    // Creación de fila, celda y campo para "Fecha lanzamiento"
    let fechaLanzamientoRow = document.createElement("tr");
    fechaLanzamientoRow.classList.add("edit-row", `edit-row-${id}`);
    let fechaLanzamientoCell = document.createElement("td");
    fechaLanzamientoCell.classList.add("Labels");
    fechaLanzamientoCell.textContent = "Fecha lanzamiento";
    let fechaLanzamientoInputCell = document.createElement("td");
    let fechaLanzamientoInput = document.createElement("input");
    fechaLanzamientoInput.type = "text";
    fechaLanzamientoInput.id = "inputFechaLanzamiento";
    fechaLanzamientoInputCell.appendChild(fechaLanzamientoInput);
    fechaLanzamientoRow.appendChild(fechaLanzamientoCell);
    fechaLanzamientoRow.appendChild(fechaLanzamientoInputCell);

    // Creación de fila, celda y campo para "Descripción"
    let descripcionRow = document.createElement("tr");
    descripcionRow.classList.add("edit-row", `edit-row-${id}`);
    let descripcionCell = document.createElement("td");
    descripcionCell.classList.add("Labels");
    descripcionCell.textContent = "Descripción";
    let descripcionInputCell = document.createElement("td");
    let descripcionInput = document.createElement("textarea");
    descripcionInput.id = "descripcionInput";
    descripcionInputCell.appendChild(descripcionInput);
    descripcionRow.appendChild(descripcionCell);
    descripcionRow.appendChild(descripcionInputCell);

    // Creación de fila, celda y campo para "Título"
    let tituloRow = document.createElement("tr");
    tituloRow.classList.add("edit-row", `edit-row-${id}`);
    let tituloCell = document.createElement("td");
    tituloCell.classList.add("Labels");
    tituloCell.textContent = "Título";
    let tituloInputCell = document.createElement("td");
    let tituloInput = document.createElement("input");
    tituloInput.classList.add("inputCustomTitle");
    tituloInput.type = "text";
    tituloInput.id = "inputTitulo";
    tituloInputCell.appendChild(tituloInput);
    tituloRow.appendChild(tituloCell);
    tituloRow.appendChild(tituloInputCell);

    tableBody.insertBefore(tituloRow, row.nextSibling);
    tableBody.insertBefore(descripcionRow, tituloRow.nextSibling);
    tableBody.insertBefore(fechaLanzamientoRow, descripcionRow.nextSibling);
    tableBody.insertBefore(generoRow, fechaLanzamientoRow.nextSibling);
    tableBody.insertBefore(directorRow, generoRow.nextSibling);
    tableBody.insertBefore(novedadRow, directorRow.nextSibling);

    let idRow = document.createElement("tr");
    idRow.classList.add("edit-row", `edit-row-${id}`, "IDrowEdit");
    let idCell = document.createElement("td");
    idCell.textContent = id;
    idCell.setAttribute("rowspan", "8"); 
    idRow.appendChild(idCell);

    tableBody.insertBefore(idRow, row.nextSibling);
}

let editMovie = async (id) => {
    let rowData = {
        "title": document.getElementById("inputTitulo").value,
        "description": document.getElementById("descripcionInput").value,
        "releaseDate": document.getElementById("inputFechaLanzamiento").value,
        "genre": document.getElementById("selectGenero").value,
        "director": document.getElementById("inputDirector").value,
        "newRelease": document.getElementById("selectNovedad").value === "true" ? true : false
    };

    let jsonData = JSON.stringify(rowData);

    const response = await fetch("http://localhost:8089/edit/" + id, {
    method: "PUT",
    headers: {
      "Accept": "application/json",
      "Content-Type": "application/json"
    },
    body: jsonData
});

if (response.ok) {
    console.log("Datos enviados con éxito y respuesta recibida del servidor");
    // Si la respuesta es exitosa, recarga la lista de películas
    fullCatalogue();
    } else {
    console.error("Error al enviar los datos al servidor");
    console.error(response.status, response.statusText);
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
    fullCatalogue();
}