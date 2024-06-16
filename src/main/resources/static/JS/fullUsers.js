let currentPage = 0;  // Página actual inicializada en 0
let totalPages = 1;   // Total de páginas inicializada en 1

window.onload = function() {
    showFullUsers(currentPage);
}

let showFullUsers = async (page) => {
    const url = `http://localhost:8090/api/user/listuser?page=${page}&size=24`;
    try {
        const request = await fetch(url, {
            method: "GET",
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            }
        });

        // Primero chequeamos el estado de la respuesta
        if (!request.ok) {
            if (request.status === 404 && page > 0) {
                // Intenta cargar la página anterior si la respuesta es 404 y no estamos en la primera página
                showFullUsers(page - 1);
                return;
            } else {
                throw new Error("Error al obtener los datos: " + request.statusText);
            }
        }

        const data = await request.json();

        if (data.users.length === 0 && page > 0) {
            // Si no hay usuarios en la página actual y no es la primera página, carga la página anterior
            showFullUsers(page - 1);
            return;
        }

        totalPages = data.totalPages;  // Actualiza el número total de páginas
        currentPage = page;  // Asegúrate de actualizar la página actual correctamente
        updatePageInfo(currentPage + 1, totalPages);  // Actualiza la información de la página

        let contentTable = "";
        for (let user of data.users) {
            let contentRow = `<tr id="row-${user.userid}">
                <td>${user.userid}</td>
                <td>${user.nickname}</td>
                <td>${user.firstname}</td>
                <td>${user.lastname}</td>
                <td>${user.mail}</td>
                <td id="showCredit">${user.credit}</td>
                <td class="acciones">
                    <i onClick="showUserEdit(${user.userid})" class="material-icons button edit">edit</i>
                    <i onClick="delUser(${user.userid})" class="material-icons button delete">delete</i>
                </td>
            </tr>`;

            contentTable += contentRow;
        }

        document.querySelector("#table tbody").innerHTML = contentTable;
    } catch (error) {
        console.error(error);
    }
}




function nextPage() {
    if (currentPage < totalPages - 1) {
        currentPage++;
        showFullUsers(currentPage);
    }
}

function prevPage() {
    if (currentPage > 0) {
        currentPage--;
        showFullUsers(currentPage);
    }
}

function updatePageInfo(current, total) {
    document.getElementById('page-info').textContent = `Página ${current} de ${total}`;
}



let delUser = async (userid) => {
    console.log("Valor de userid:", userid);
    let row = document.getElementById(`row-${userid}`);
    let existingConfirmRows = document.querySelectorAll(`.confirm-row-${userid}`);
    if (existingConfirmRows.length) {
        existingConfirmRows.forEach(row => row.remove());
        return;
    }

    let confirmRow = document.createElement("tr");
    confirmRow.classList.add("confirm-row", `confirm-row-${userid}`);
    let confirmCell = document.createElement("td");
    confirmCell.colSpan = 8; // Asumiendo que tienes 8 columnas en tu tabla
    confirmCell.innerHTML = `
        <div>Estás seguro de borrar el usuario: <strong>${row.cells[1].textContent}</strong>?</div>
        <button onclick="confirmDelete(${userid})" class="confirm-button">Confirmar</button>
        <button onclick="cancelDelete(${userid})" class="cancel-button">Cancelar</button>
    `;
    confirmRow.appendChild(confirmCell);

    row.parentNode.insertBefore(confirmRow, row.nextSibling);
}

let confirmDelete = async (userid) => {
    const request = await fetch("http://localhost:8090/api/user/deleteUser/" + userid, {
        method: "DELETE",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        }
    });
    
    if (request.ok) {
        showFullUsers(currentPage);
    } else {
        alert("Error al intentar eliminar el usuario");
    }
}

let cancelDelete = (userid) => {
    document.querySelectorAll(`.confirm-row-${userid}`).forEach(row => row.remove());
}

async function showUserEdit(userid) {
    let tableBody = document.querySelector("#table tbody");


    let existingEditRows = document.querySelectorAll(`.edit-row-${userid}`);
    if (existingEditRows.length) {
        existingEditRows.forEach(row => row.remove());
        return;
    }

    let row = document.getElementById(`row-${userid}`);

    try {
        const response = await fetch(`http://localhost:8090/api/user/user/userid/${userid}`);
        if (!response.ok) {
            throw new Error(`Error al obtener los datos: ${response.statusText}`);
        }
        const user = await response.json();

    let buttonsRow = document.createElement("tr");
    buttonsRow.classList.add("edit-row", `edit-row-${userid}`);

    let buttonsCell = document.createElement("td");
    buttonsCell.className = "ButtonLabelsTable";
    buttonsCell.colSpan = 6;
    
    let buttonRow = document.createElement("td");

    let acceptButton = document.createElement("button");
    acceptButton.classList.add("aceptButton");
    acceptButton.textContent = "Modificar cambios";
    acceptButton.addEventListener("click", async function() {
        try {
            await editUser(userid);
            document.querySelectorAll('.edit-row').forEach(row => row.remove());
        } catch (error) {
            console.error("Failed to edit user", error);
            alert("Failed to edit user: " + error.message);
        }
    });

    let cancelButton = document.createElement("button");
    cancelButton.classList.add("cancelButton");
    cancelButton.textContent = "Cancelar";
    cancelButton.addEventListener("click", function() {
        document.querySelectorAll('.edit-row').forEach(row => row.remove());
    });

    buttonsCell.appendChild(acceptButton);
    buttonsCell.appendChild(cancelButton);

    buttonsRow.appendChild(buttonsCell);

    tableBody.insertBefore(buttonsRow, row.nextSibling);

    let creditRow = document.createElement("tr");
    creditRow.classList.add("edit-row", `edit-row-${userid}`);
    let creditCell = document.createElement("td");
    creditCell.classList.add("Labels");
    creditCell.textContent = "Saldo";
    let creditInputCell = document.createElement("td");
    let creditInput = document.createElement("input");
    creditInput.classList.add("inputCustomNickname");
    creditInput.type = "text";
    creditInput.id = "inputCredit";
    creditInput.value = user.credit;
    creditInputCell.appendChild(creditInput);
    creditRow.appendChild(creditCell);
    creditRow.appendChild(creditInputCell);

    let mailRow = document.createElement("tr");
    mailRow.classList.add("edit-row", `edit-row-${userid}`);
    let mailCell = document.createElement("td");
    mailCell.classList.add("Labels");
    mailCell.textContent = "Correo electrónico";
    let mailInputCell = document.createElement("td");
    let mailInput = document.createElement("input");
    mailInput.classList.add("inputCustomMail");
    mailInput.type = "text";
    mailInput.id = "inputMail";
    mailInput.value = user.mail;
    mailInputCell.appendChild(mailInput);
    mailRow.appendChild(mailCell);
    mailRow.appendChild(mailInputCell);

    let lastNameRow = document.createElement("tr");
    lastNameRow.classList.add("edit-row", `edit-row-${userid}`);
    let lastNameCell = document.createElement("td");
    lastNameCell.classList.add("Labels");
    lastNameCell.textContent = "Apellidos";
    let lastNameInputCell = document.createElement("td");
    let lastNameInput = document.createElement("input");
    lastNameInput.classList.add("inputCustomLastName");
    lastNameInput.type = "text";
    lastNameInput.id = "inputLastName";
    lastNameInput.value = user.lastname;
    lastNameInputCell.appendChild(lastNameInput);
    lastNameRow.appendChild(lastNameCell);
    lastNameRow.appendChild(lastNameInputCell);

    let firstNameRow = document.createElement("tr");
    firstNameRow.classList.add("edit-row", `edit-row-${userid}`);
    let firstNameCell = document.createElement("td");
    firstNameCell.classList.add("Labels");
    firstNameCell.textContent = "Nombre";
    let firstNameInputCell = document.createElement("td");
    let firstNameInput = document.createElement("input");
    firstNameInput.classList.add("inputCustomFirstName");
    firstNameInput.type = "text";
    firstNameInput.id = "inputFirstName";
    firstNameInput.value = user.firstname;
    firstNameInputCell.appendChild(firstNameInput);
    firstNameRow.appendChild(firstNameCell);
    firstNameRow.appendChild(firstNameInputCell);


    let nicknameRow = document.createElement("tr");
    nicknameRow.classList.add("edit-row", `edit-row-${userid}`);
    let nicknameCell = document.createElement("td");
    nicknameCell.classList.add("Labels");
    nicknameCell.textContent = "Apodo";
    let nicknameInputCell = document.createElement("td");
    let nicknameInput = document.createElement("input");
    nicknameInput.classList.add("inputCustomNickname");
    nicknameInput.type = "text";
    nicknameInput.id = "inputNickname";
    nicknameInput.value = user.nickname;
    nicknameInputCell.appendChild(nicknameInput);
    nicknameRow.appendChild(nicknameCell);
    nicknameRow.appendChild(nicknameInputCell);

    tableBody.insertBefore(nicknameRow, row.nextSibling);
    tableBody.insertBefore(firstNameRow, nicknameRow.nextSibling);
    tableBody.insertBefore(lastNameRow, firstNameRow.nextSibling);
    tableBody.insertBefore(mailRow, lastNameRow.nextSibling);
    tableBody.insertBefore(creditRow, mailRow.nextSibling);

    let idRow = document.createElement("tr");
    idRow.classList.add("edit-row", `edit-row-${userid}`, "IDrowEdit");
    let idCell = document.createElement("td");
    idCell.textContent = userid;
    idCell.setAttribute("rowspan", "7"); 
    idRow.appendChild(idCell);

    tableBody.insertBefore(idRow, row.nextSibling);

    } catch (error) {
    console.error("No se pudo cargar la información del usuario", error);
    }
}

let editUser = async (userid) => {
    let rowData = {
        "nickname": document.getElementById("inputNickname").value,
        "firstname": document.getElementById("inputFirstName").value,
        "lastname": document.getElementById("inputLastName").value,
        "mail": document.getElementById("inputMail").value,
        "credit": document.getElementById("inputCredit").value
    };

    console.log("JSON data sent to server:", rowData);

    let jsonData = JSON.stringify(rowData);

    const response = await fetch("http://localhost:8090/api/user/editUser/" + userid, {
        method: "PUT",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        },
        body: jsonData
    });

    if (!response.ok) {
        const data = await response.json();
        console.error("Error sending data to server: ", data.message);
        throw new Error(data.message);
    } else {
        showFullUsers(currentPage);
    }

    return await response.json();
};
