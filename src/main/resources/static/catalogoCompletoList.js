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
            <td id="idallign">${movie.id}</td>
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
    console.log("showFormEdit() called");

    let tableBody = document.querySelector("#table tbody");
    console.log("tableBody: ", tableBody);

    // Obtener la fila correspondiente al ID
    let row = document.getElementById(`row-${id}`);
    console.log("Fila correspondiente: ", row);

    let newRow = document.createElement("tr");
    console.log("newRow: ", newRow);

    for (let i = 0; i < 7; i++) {
        let newCell = document.createElement("td");
        let inputField = document.createElement("input");
        inputField.type = "text";
        newCell.appendChild(inputField);
        newRow.appendChild(newCell);
    }

    tableBody.insertBefore(newRow, row.nextSibling);

    // Crear una nueva fila para los botones "Aceptar" y "Cancelar"
    let buttonsRow = document.createElement("tr");

    // Crear celdas para los botones
    let buttonsCell = document.createElement("td");
    buttonsCell.colSpan = "7"; // Colspan para ocupar todas las columnas

    // Crear botón "Aceptar"
    let acceptButton = document.createElement("button");
    acceptButton.textContent = "Aceptar";
    acceptButton.addEventListener("click", function() {
        console.log("Aceptar button clicked");
    });
    buttonsCell.appendChild(acceptButton);

    // Crear botón "Cancelar"
    let cancelButton = document.createElement("button");
    cancelButton.textContent = "Cancelar";
    cancelButton.addEventListener("click", function() {
        console.log("Cancelar button clicked");
        tableBody.removeChild(newRow);
        tableBody.removeChild(buttonsRow); // Eliminar fila de botones
    });
    buttonsCell.appendChild(cancelButton);

    // Agregar celda de botones a la fila de botones
    buttonsRow.appendChild(buttonsCell);

    // Insertar fila de botones después del formulario
    tableBody.insertBefore(buttonsRow, newRow.nextSibling);
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
