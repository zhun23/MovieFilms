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
                <i onClick="showFormEdit(${user.id})" class="material-icons button edit">edit</i>
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