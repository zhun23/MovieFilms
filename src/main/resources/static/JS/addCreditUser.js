document.getElementById('search').addEventListener('click', function(event) {
    event.preventDefault();
    const query = document.getElementById('findGlobalUser').value;

    if (!query) {
        console.log("Please enter a search value.");
        return;
    }

    fetch(`/user/search/${query}`)
        .then(response => response.ok ? response.json() : Promise.reject(`Error ${response.status}`))
        .then(data => {
            const tableContainer = document.getElementById('tableContainer');
            tableContainer.innerHTML = ''; // Limpiamos cualquier tabla previa

            const table = document.createElement('table');
            table.setAttribute('id', 'userInfoTable');

            const tableHead = document.createElement('thead');
            const headRow = document.createElement('tr');
            ['ID', 'Nombre', 'Apellidos', 'Apodo', 'Email', 'Crédito'].forEach(cell => {
                const th = document.createElement('th');
                th.textContent = cell;
                headRow.appendChild(th);
            });
            tableHead.appendChild(headRow);
            table.appendChild(tableHead);

            const tableBody = document.createElement('tbody');
            data.forEach(item => appendRow(tableBody, item));
            table.appendChild(tableBody);

            tableContainer.appendChild(table);

            addClickEventToRows();
        })
        .catch(error => console.error('Error fetching data:', error));
});

function appendRow(tableBody, item) {
    const newRow = tableBody.insertRow(-1);
    newRow.insertCell(0).textContent = item.id;
    newRow.insertCell(1).textContent = item.firstName;
    newRow.insertCell(2).textContent = item.lastName;
    newRow.insertCell(3).textContent = item.nickname;
    newRow.insertCell(4).textContent = item.mail;
    newRow.insertCell(5).textContent = item.credit || '0';
}

function addClickEventToRows() {
    const rows = document.getElementById('userInfoTable').getElementsByTagName('tbody')[0].getElementsByTagName('tr');
    for (let i = 0; i < rows.length; i++) {
        rows[i].addEventListener('click', function() {
            const cells = this.getElementsByTagName('td');
            const userDetails = document.getElementById('userDetails');
            userDetails.innerHTML = `
                <h3>ID: ${cells[0].textContent}</h3>
                <p>Nombre: ${cells[1].textContent}</p>
                <p>Apellidos: ${cells[2].textContent}</p>
                <p>Apodo: ${cells[3].textContent}</p>
                <p>Email: ${cells[4].textContent}</p>
                <p>Crédito: ${cells[5].textContent}</p>
            `;
            // Eliminar la tabla
            const tableContainer = document.getElementById('tableContainer');
            const table = document.getElementById('userInfoTable');
            if (table) {
                tableContainer.removeChild(table);
            }
        });
    }
}
