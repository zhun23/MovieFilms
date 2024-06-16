let currentPage = 0;
let totalPages = 1;

document.addEventListener('DOMContentLoaded', function() {
    const nextBtn = document.getElementById("nextBtn");
    const prevBtn = document.getElementById("prevBtn");

    if (nextBtn && prevBtn) {
        nextBtn.addEventListener("click", nextPage);
        prevBtn.addEventListener("click", prevPage);
    } else {
        console.error("Los botones de paginación no se encontraron en el DOM.");
    }

    showFullCatalogue(currentPage);
});

async function showFullCatalogue(page) {
    togglePaginationButtons(false);
    const size = 24;
    const url = `http://localhost:8090/list?page=${page}&size=${size}`;

    try {
        const response = await fetch(url);
        if (!response.ok) {
            if (response.status === 404 && page > 0) {
                showFullCatalogue(page - 1);
                return;
            } else {
                throw new Error("Error al obtener los datos: " + response.statusText);
            }
        }

        const data = await response.json();
        console.log("Datos recibidos:", data);
        console.log("Total de páginas:", data.totalPages);

        if (data.movies.length === 0 && page > 0) {
            showFullCatalogue(page - 1);
            return;
        }

        updateTable(data.movies || []);
        totalPages = data.totalPages || 1;
        document.getElementById('page-info').textContent = `Página ${page + 1} de ${totalPages}`;
        currentPage = page;
    } catch (error) {
        console.error("Error en la solicitud:", error.message);
        alert("Error al cargar los datos. Por favor, inténtalo de nuevo más tarde.");
    } finally {
        togglePaginationButtons(true);
    }
}


function togglePaginationButtons(enable) {
    const nextBtn = document.getElementById("nextBtn");
    const prevBtn = document.getElementById("prevBtn");
    nextBtn.disabled = !enable;
    prevBtn.disabled = !enable;
}

function updateTable(movies) {
    const tableBody = document.querySelector("#table tbody");
    let tableHTML = "";
    movies.forEach(movie => {
        let newReleaseTrad = movie.newRelease ? "Sí" : "No";
        tableHTML += `<tr id="row-${movie.id}">
            <td id="valor0">${movie.id}</td>
            <td>${movie.title}</td>
            <td id="releaseDateAllign">${movie.releaseDate}</td>
            <td>${movie.genre}</td>
            <td>${movie.director}</td>
            <td id="newReleaseAllign">${newReleaseTrad}</td>
            <td id="allignprice">${movie.price}</td>
            <td id="allignStock">${movie.stock}</td>
            <td class="acciones">
                <i onClick="showFormEdit(${movie.id})" class="material-icons button edit">edit</i>
                <i onClick="delMovie(${movie.id})" class="material-icons button delete">delete</i>
            </td>
        </tr>`;
    });
    tableBody.innerHTML = tableHTML;
}

function nextPage() {
    let newPage = currentPage + 1;
    if (newPage < totalPages) {
        console.log("Cambiando a la página:", newPage + 1);
        showFullCatalogue(newPage);
    }
}

function prevPage() {
    let newPage = currentPage - 1;
    if (newPage >= 0) {
        console.log("Cambiando a la página:", newPage + 1);
        showFullCatalogue(newPage);
    }
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
        const response = await fetch(`http://localhost:8090/id/${id}`);
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

    let stockRow = document.createElement("tr");
    stockRow.classList.add("edit-row", `edit-row-${id}`);
    let stockCell = document.createElement("td");
    stockCell.classList.add("Labels");
    stockCell.textContent = "Stock";
    let stockInputCell = document.createElement("td");
    let stockInput = document.createElement("input");
    stockInput.classList.add("inputCustomStock");
    stockInput.type = "text";
    stockInput.id = "inputStock";
    stockInput.value = movie.stock;
    stockInput.required = true;
    stockInputCell.appendChild(stockInput);
    stockRow.appendChild(stockCell);
    stockRow.appendChild(stockInputCell);

    let priceRow = document.createElement("tr");
    priceRow.classList.add("edit-row", `edit-row-${id}`);
    let priceCell = document.createElement("td");
    priceCell.classList.add("Labels");
    priceCell.textContent = "Precio";
    let priceInputCell = document.createElement("td");
    let priceInput = document.createElement("input");
    priceInput.type = "text";
    priceInput.id = "inputPrice";
    priceInput.value = movie.price;
    priceInput.required = true;
    priceInputCell.appendChild(priceInput);
    priceRow.appendChild(priceCell);
    priceRow.appendChild(priceInputCell);

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
    tableBody.insertBefore(priceRow, newReleaseRow.nextSibling);
    tableBody.insertBefore(stockRow, priceRow.nextSibling);

    let idRow = document.createElement("tr");
    idRow.classList.add("edit-row", `edit-row-${id}`, "IDrowEdit");
    let idCell = document.createElement("td");
    idCell.textContent = id;
    idCell.setAttribute("rowspan", "10"); 
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
        "newRelease": document.getElementById("selectNewRelease").value === "true" ? true : false,
        "price": document.getElementById("inputPrice").value,
        "stock": parseInt(document.getElementById("inputStock").value)
    };

    let jsonData = JSON.stringify(rowData);

    const response = await fetch("http://localhost:8090/edit/" + id, {
        method: "PUT",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        body: jsonData
    });

    if (response.ok) {
        showFullCatalogue(currentPage);
    } else {
        const errorData = await response.json();
        alert(`Error: ${errorData.error}\nMensaje: ${errorData.message}`);
    }
}
        

let delMovie = async (id) => {
    let row = document.getElementById(`row-${id}`);
    let existingConfirmRows = document.querySelectorAll(`.confirm-row-${id}`);
    if (existingConfirmRows.length) {
        existingConfirmRows.forEach(row => row.remove());
        return;
    }

    let confirmRow = document.createElement("tr");
    confirmRow.classList.add("confirm-row", `confirm-row-${id}`);
    let confirmCell = document.createElement("td");
    confirmCell.colSpan = 9;
    confirmCell.innerHTML = `
        <div>Estás seguro de borrar la película: <strong>${row.cells[1].textContent}</strong>?</div>
        <button onclick="confirmDelete(${id})" class="confirm-button">Confirmar</button>
        <button onclick="cancelDelete(${id})" class="cancel-button">Cancelar</button>
    `;
    confirmRow.appendChild(confirmCell);

    row.parentNode.insertBefore(confirmRow, row.nextSibling);
}

let confirmDelete = async (id) => {
    const request = await fetch("http://localhost:8090/delete/" + id, {
        method: "DELETE",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        }
    });
    if (request.ok) {
        showFullCatalogue(currentPage);
    } else {
        alert("Error al intentar eliminar la película");
    }
}

let cancelDelete = (id) => {
    document.querySelectorAll(`.confirm-row-${id}`).forEach(row => row.remove());
}