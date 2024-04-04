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
    const request = await fetch("http://localhost:8089/deleteUser/" + id, {
        method: "DELETE",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        }
    });
    showFullUsers();
}

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

    let acceptButton = document.createElement("button");
    acceptButton.classList.add("ButtonLabels");
    acceptButton.textContent = "Modificar cambios";
    acceptButton.addEventListener("click", function() {
        editUser(id);
        document.querySelectorAll('.edit-row').forEach(row => row.remove());
    });

    let cancelButton = document.createElement("button");
    cancelButton.classList.add("ButtonLabels");
    cancelButton.textContent = "Cancelar";
    cancelButton.addEventListener("click", function() {
        document.querySelectorAll('.edit-row').forEach(row => row.remove());
    });

    buttonsCell.appendChild(acceptButton);
    buttonsCell.appendChild(cancelButton);

    buttonsRow.appendChild(buttonsCell);

    tableBody.insertBefore(buttonsRow, row.nextSibling);

    let mailRow = document.createElement("tr");
    mailRow.classList.add("edit-row", `edit-row-${id}`);
    let mailCell = document.createElement("td");
    mailCell.classList.add("Labels");
    mailCell.textContent = "Correo";
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
    lastNameCell.textContent = "Apellido";
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

    let idRow = document.createElement("tr");
    idRow.classList.add("edit-row", `edit-row-${id}`, "IDrowEdit");
    let idCell = document.createElement("td");
    idCell.textContent = id;
    idCell.setAttribute("rowspan", "6"); 
    idRow.appendChild(idCell);

    tableBody.insertBefore(idRow, row.nextSibling);

    } catch (error) {
    console.error("No se pudo cargar la información del usuario", error);
    }
}