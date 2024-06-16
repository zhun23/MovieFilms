//let currentPage = 0;
//let totalPages = 1;

let currentPageHistory = 0;
let totalPagesHistory = 1;
let currentPageUser = 0;
let totalPagesUser = 1;
let currentUserId = null;

window.onload = function() {
    showFullHistory(currentPageHistory);
}

function showFullHistory(page = 0) {
    togglePaginationButtons(false, 'history');
    const url = `http://localhost:8090/history?page=${page}&size=24`;

    fetch(url).then(response => {
        if (!response.ok) {
            throw new Error("Error al obtener los datos");
        }
        return response.json();
    }).then(data => {
        totalPagesHistory = data.totalPages || 1;
        currentPageHistory = data.currentPage || 0;
        document.getElementById('page-info').textContent = `Página ${currentPageHistory + 1} de ${totalPagesHistory}`;
        updateTable(data.creditHistories || [], 'history');
    }).catch(error => {
        console.error("Error en la solicitud:", error.message);
        alert("Error al cargar los datos. Por favor, inténtalo de nuevo más tarde.");
    }).finally(() => {
        togglePaginationButtons(true, 'history');
    });
}

function loadUserCreditHistory(userId, page = 0) {
    currentUserId = userId;
    togglePaginationButtons(false, 'user');
    const url = `/creditHistory/id/${userId}?page=${page}&size=24`;

    fetch(url).then(response => {
        if (!response.ok) {
            throw new Error('No se encontró un historial de crédito para este ID de usuario');
        }
        return response.json();
    }).then(data => {
        totalPagesUser = data.totalPages || 1;
        currentPageUser = data.currentPage || 0;
        document.getElementById('page-info').textContent = `Página ${currentPageUser + 1} de ${totalPagesUser}`;
        updateTable(data.creditHistories || [], 'user');
    }).catch(error => {
        console.error('Error:', error);
        alert(error.message);
    }).finally(() => {
        togglePaginationButtons(true, 'user');
    });
}

function updateTable(creditHistories, type) {
    const tableBody = document.querySelector("#table tbody");
    let tableHTML = "";
    creditHistories.forEach(history => {
        const buyTranslate = history.buy ? 'Si' : 'No';

        let rowClass = history.amount > 0 && !history.buy ? 'greenRow' :
               history.amount < 0 && !history.buy ? 'redRow' :
               history.amount < 0 && history.buy ? 'purpleRow' : '';
        
        let formattedDate = formatDate(history.date);

        if (history.amount > 0 && !history.buy) {
            history.action = "Recarga";
        } else if (history.amount < 0 && !history.buy) {
            history.action = "Ajuste";
        } else if (history.amount < 0 && history.buy) {
            history.action = "Compra";
        }

        let contentRow = `<tr class="${rowClass}">
            <td>${history.historyid}</td>
            <td class="allignTable">${history.userid}</td>
            <td class="allignTable">${history.userNickname}</td>
            <td class="allignTable">${formattedDate}</td>
            <td class="allignTable">${history.action}</td>
            <td class="allignTable">${buyTranslate}</td>
            <td class="allignTable">${history.amount}</td>
            <td class="allignTable">${history.totalCredit}</td>
        </tr>`;
        tableHTML += contentRow;
    });
    tableBody.innerHTML = tableHTML;
}

function nextPage() {
    if (currentUserId && currentPageUser < totalPagesUser - 1) {
        currentPageUser += 1;
        loadUserCreditHistory(currentUserId, currentPageUser);
    } else if (currentPageHistory < totalPagesHistory - 1) {
        currentPageHistory += 1;
        showFullHistory(currentPageHistory);
    }
}

function prevPage() {
    if (currentUserId && currentPageUser > 0) {
        currentPageUser -= 1;
        loadUserCreditHistory(currentUserId, currentPageUser);
    } else if (currentPageHistory > 0) {
        currentPageHistory -= 1;
        showFullHistory(currentPageHistory);
    }
}

document.addEventListener('DOMContentLoaded', function() {
    const searchForm = document.getElementById('searchForm');
    const findGlobalUser = document.getElementById('findGlobalUser');

    searchForm.addEventListener('submit', function(event) {
        event.preventDefault();
        const userId = findGlobalUser.value;

        fetch(`/api/user/user/userid/${userId}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('No se pudo obtener la información del usuario');
                }
                return response.json();
            })
            .then(userData => {
                const userName = userData.nickname.toUpperCase();
                document.getElementById('thTitle').textContent = `HISTORIAL CRÉDITO DE @${userName}`;

                loadUserCreditHistory(userId, 0);
            })
            .catch(error => {
                console.error('Error:', error);
                alert('Error al cargar la información del usuario. Por favor, inténtalo de nuevo más tarde.');
            });
    });

    document.getElementById('clean').addEventListener('click', function() {
        findGlobalUser.value = '';
        currentUserId = null;
        document.getElementById('thTitle').textContent = "HISTORIAL GLOBAL DE CRÉDITO DE USUARIOS";
        showFullHistory(0);
    });
});

function togglePaginationButtons(enable, type) {
    if (type === 'user') {
        document.getElementById("nextBtn").disabled = currentPageUser >= totalPagesUser - 1 || !enable;
        document.getElementById("prevBtn").disabled = currentPageUser <= 0 || !enable;
    } else {
        document.getElementById("nextBtn").disabled = currentPageHistory >= totalPagesHistory - 1 || !enable;
        document.getElementById("prevBtn").disabled = currentPageHistory <= 0 || !enable;
    }
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
