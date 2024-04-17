window.onload = function(){
    showFullUsers();
}

let showFullUsers = async () => {
    const request = await fetch("http://localhost:8089/user/", {
        method: "GET",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        }
    });

    const users = await request.json();

    let contentTable = "";

    for (let user of users) {

        let contentRow = `<tr id="row-${user.id}">
            <td>${user.id}</td>
            <td>${user.nickname}</td>
            <td>${user.firstName}</td>
            <td>${user.lastName}</td>
            <td>${user.mail}</td>
            <td id="showCredit">${user.credit}</td>
            <td class="acciones">
                <i onClick="showUserEdit(${user.id})" class="material-icons button edit">edit</i>
                <i onClick="delUser(${user.id})" class="material-icons button delete">delete</i>
            </td>
        </tr>`;

        contentTable += contentRow;
    }

    document.querySelector("#table tbody").innerHTML = contentTable;
}



let delUser = async (id) => {
    let row = document.getElementById(`row-${id}`);
    let existingConfirmRows = document.querySelectorAll(`.confirm-row-${id}`);
    if (existingConfirmRows.length) {
        existingConfirmRows.forEach(row => row.remove());
        return;
    }

    let confirmRow = document.createElement("tr");
    confirmRow.classList.add("confirm-row", `confirm-row-${id}`);
    let confirmCell = document.createElement("td");
    confirmCell.colSpan = 8; // Asumiendo que tienes 8 columnas en tu tabla
    confirmCell.innerHTML = `
        <div>Estás seguro de borrar el usuario: <strong>${row.cells[1].textContent}</strong>?</div>
        <button onclick="confirmDelete(${id})" class="confirm-button">Confirmar</button>
        <button onclick="cancelDelete(${id})" class="cancel-button">Cancelar</button>
    `;
    confirmRow.appendChild(confirmCell);

    row.parentNode.insertBefore(confirmRow, row.nextSibling);
}

let confirmDelete = async (id) => {
    const request = await fetch("http://localhost:8089/deleteUser/" + id, {
        method: "DELETE",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        }
    });
    
    if (request.ok) {
        showFullUsers();
    } else {
        alert("Error al intentar eliminar el usuario");
    }
}

let cancelDelete = (id) => {
    document.querySelectorAll(`.confirm-row-${id}`).forEach(row => row.remove());
}







/*
let delUser = async (id) => {
    const request = await fetch("http://localhost:8089/deleteUser/" + id, {
        method: "DELETE",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        }
    });
    showFullUsers();
}
*/
async function showUserEdit(id) {
    let tableBody = document.querySelector("#table tbody");


    let existingEditRows = document.querySelectorAll(`.edit-row-${id}`);
    if (existingEditRows.length) {
        existingEditRows.forEach(row => row.remove());
        return;
    }

    let row = document.getElementById(`row-${id}`);

    try {
        const response = await fetch(`http://localhost:8089/user/id/${id}`);
        if (!response.ok) {
            throw new Error(`Error al obtener los datos: ${response.statusText}`);
        }
        const user = await response.json();

    let buttonsRow = document.createElement("tr");
    buttonsRow.classList.add("edit-row", `edit-row-${id}`);

    let buttonsCell = document.createElement("td");
    buttonsCell.className = "ButtonLabelsTable";
    buttonsCell.colSpan = 6;
    
    let buttonRow = document.createElement("td");

    let acceptButton = document.createElement("button");
    acceptButton.classList.add("aceptButton");
    acceptButton.textContent = "Modificar cambios";
    acceptButton.addEventListener("click", function() {
        editUser(id);
        document.querySelectorAll('.edit-row').forEach(row => row.remove());
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
    creditRow.classList.add("edit-row", `edit-row-${id}`);
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
    mailRow.classList.add("edit-row", `edit-row-${id}`);
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
    lastNameRow.classList.add("edit-row", `edit-row-${id}`);
    let lastNameCell = document.createElement("td");
    lastNameCell.classList.add("Labels");
    lastNameCell.textContent = "Apellidos";
    let lastNameInputCell = document.createElement("td");
    let lastNameInput = document.createElement("input");
    lastNameInput.classList.add("inputCustomLastName");
    lastNameInput.type = "text";
    lastNameInput.id = "inputLastName";
    lastNameInput.value = user.lastName;
    lastNameInputCell.appendChild(lastNameInput);
    lastNameRow.appendChild(lastNameCell);
    lastNameRow.appendChild(lastNameInputCell);

    let firstNameRow = document.createElement("tr");
    firstNameRow.classList.add("edit-row", `edit-row-${id}`);
    let firstNameCell = document.createElement("td");
    firstNameCell.classList.add("Labels");
    firstNameCell.textContent = "Nombre";
    let firstNameInputCell = document.createElement("td");
    let firstNameInput = document.createElement("input");
    firstNameInput.classList.add("inputCustomFirstName");
    firstNameInput.type = "text";
    firstNameInput.id = "inputFirstName";
    firstNameInput.value = user.firstName;
    firstNameInputCell.appendChild(firstNameInput);
    firstNameRow.appendChild(firstNameCell);
    firstNameRow.appendChild(firstNameInputCell);


    let nicknameRow = document.createElement("tr");
    nicknameRow.classList.add("edit-row", `edit-row-${id}`);
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
    idRow.classList.add("edit-row", `edit-row-${id}`, "IDrowEdit");
    let idCell = document.createElement("td");
    idCell.textContent = id;
    idCell.setAttribute("rowspan", "7"); 
    idRow.appendChild(idCell);

    tableBody.insertBefore(idRow, row.nextSibling);

    } catch (error) {
    console.error("No se pudo cargar la información del usuario", error);
    }
}

let editUser = async (id) => {
    let rowData = {
        "nickname": document.getElementById("inputNickname").value,
        "firstName": document.getElementById("inputFirstName").value,
        "lastName": document.getElementById("inputLastName").value,
        "mail": document.getElementById("inputMail").value,
        "credit": document.getElementById("inputCredit").value
    };

    let jsonData = JSON.stringify(rowData);

    const response = await fetch("http://localhost:8089/editUser/" + id, {
        method: "PUT",
        headers: {
          "Accept": "application/json",
          "Content-Type": "application/json"
        },
        body: jsonData
    });

    if (response.ok) {
        console.log("Data sent successfully and response received from the server");
        showFullUsers();
    } else {
        console.error("Error sending data to server");
        console.error(response.status, response.statusText);
    }
}