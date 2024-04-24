window.onload = function(){
    showFullHistory();
}

function formatDate(dateString) {
    let fecha = new Date(dateString);
    let dia = fecha.getDate();
    let mes = fecha.getMonth() + 1;
    let anio = fecha.getFullYear();
    let hora = fecha.getHours();
    let minutos = fecha.getMinutes();
    
    if (dia < 10) {
        dia = "0" + dia;
    }
    if (mes < 10) {
        mes = "0" + mes;
    }
    if (hora < 10) {
        hora = "0" + hora;
    }
    if (minutos < 10) {
        minutos = "0" + minutos;
    }
    return dia + "-" + mes + "-" + anio + " " + hora + ":" + minutos;
}

let showFullHistory = async () => {
    const request = await fetch("http://localhost:8089/history", {
        method: "GET",
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json"
        }
    });

    const creditHistories = await request.json();
    console.log(creditHistories);
    let contentTable = "";

    for (let creditHistory of creditHistories) {
        creditHistory.date = formatDate(creditHistory.date);
        let contentRow = `<tr id="row-${creditHistory.creditId}">
            <td>${creditHistory.id}</td>
            <td class="allignTable">${creditHistory.userId}</td>
            <td class="allignTable">${creditHistory.userNickname}</td>
            <td class="allignTable">${creditHistory.date}</td>
            <td class="allignTable">${creditHistory.recharge}</td>
            <td class="allignTable">${creditHistory.amount}</td>
            <td class="allignTable">${creditHistory.totalCredit}</td>
            <td class="acciones allignTable">
                <i onClick="deleteCreditHistory(${creditHistory.creditId})" class="material-icons button delete">delete</i>
            </td>
        </tr>`;
        contentTable += contentRow;
    }
    document.querySelector("#table tbody").innerHTML = contentTable;
}