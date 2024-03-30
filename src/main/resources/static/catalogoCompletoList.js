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

    let tableBody = document.querySelector("#table tbody");

    // Verificar si ya existe una fila de edición para este ID
    let existingEditRow = document.querySelector(`#edit-row-${id}`);
    if(existingEditRow) {
        tableBody.removeChild(existingEditRow);
        return; // No necesitamos agregar una nueva fila de edición
    }

    // Obtener la fila correspondiente al ID
    let row = document.getElementById(`row-${id}`);
    console.log("Fila correspondiente: ", row);

    let newRow = document.createElement("tr");
    newRow.id = `edit-row-${id}`; // Agregar un ID único a la fila de edición
    console.log("newRow: ", newRow);

    for (let i = 0; i < 7; i++) {
        let newCell = document.createElement("td");

        // Si es la primera celda, crear un desplegable (select)
        if (i === 3) {
            let selectField = document.createElement("select");

            // Agregar opciones al desplegable
            let option1 = document.createElement("option");
            option1.value = "value1";
            option1.text = "Opción 1";
            selectField.appendChild(option1);

            let option2 = document.createElement("option");
            option2.value = "value2";
            option2.text = "Opción 2";
            selectField.appendChild(option2);

            // Agregar más opciones si es necesario

            newCell.appendChild(selectField);
        } else if (i === 5) {
                let selectField = document.createElement("select");
    
                // Agregar opciones al desplegable
                let option1 = document.createElement("option");
                option1.value = "value1";
                option1.text = "Sí";
                selectField.appendChild(option1);
    
                let option2 = document.createElement("option");
                option2.value = "value2";
                option2.text = "No";
                selectField.appendChild(option2);
    
                // Agregar más opciones si es necesario
    
                newCell.appendChild(selectField);
        } else {
            // Para las otras celdas, crear campos de texto
            let inputField = document.createElement("input");
            inputField.type = "text";
            newCell.appendChild(inputField);
        }

        newRow.appendChild(newCell);
    }

    // Crear celda para los botones en la misma fila
    let buttonsCell = document.createElement("td");
    buttonsCell.colSpan = "7"; // Colspan para ocupar todas las columnas

    // Crear botón "Aceptar"
    let acceptButton = document.createElement("button");
    acceptButton.textContent = "Aceptar";
    acceptButton.addEventListener("click", function() {
    });
    buttonsCell.appendChild(acceptButton);

    // Crear botón "Cancelar"
    let cancelButton = document.createElement("button");
    cancelButton.textContent = "Cancelar";
    cancelButton.addEventListener("click", function() {
        tableBody.removeChild(newRow);
    });

    // Agregar botones a la celda
    buttonsCell.appendChild(acceptButton);
    buttonsCell.appendChild(cancelButton);

    // Agregar celda de botones a la fila de edición
    newRow.appendChild(buttonsCell);

    tableBody.insertBefore(newRow, row.nextSibling);
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
