document.addEventListener('DOMContentLoaded', function() {
    const cleanButton = document.getElementById('clean');
    const searchButton = document.getElementById('search');
    const inputField = document.getElementById('findGlobalUser');
    const tableContainer = document.getElementById('tableContainer');
    const userDetails = document.getElementById('userDetails');
    const searchResult = document.getElementById('searchResult');

    searchButton.addEventListener('click', function(event) {
        event.preventDefault();
        const query = inputField.value.trim();

        if (!query) {
            console.log("Please enter a search value.");
            return;
        }

        fetch(`/user/search/${query}`)
            .then(response => {
                if (response.ok) {
                    return response.json();
                } else if (response.status === 404) {
                    throw new Error("Usuario no encontrado");
                } else {
                    throw new Error(`Error ${response.status}`);
                }
            })
            .then(data => {
                tableContainer.innerHTML = ''; 
                createTable(data);
                addClickEventToRows();
            })
            .catch(error => {
                console.error('Error:', error);
                searchResult.innerHTML = `<p>${error.message}</p>`;
            });
    });

    cleanButton.addEventListener('click', function() {
        inputField.value = '';
        tableContainer.innerHTML = '';
        userDetails.innerHTML = '';
        searchResult.innerHTML = '';
    });

    function createTable(data) {
        const table = document.createElement('table');
        table.setAttribute('id', 'userInfoTable');
        const tableHead = document.createElement('thead');
        const headRow = document.createElement('tr');
        ['#ID', 'Nombre', 'Apellidos', 'Apodo', 'Email', 'Crédito'].forEach(cell => {
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
    }

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
        const rows = document.querySelectorAll('#userInfoTable tbody tr');

        rows.forEach(row => {
            row.addEventListener('click', function() {
                const cells = this.cells;
                userDetails.innerHTML = `
                    <h2>ID: ${cells[0].textContent}</h2>
                    <h3>Nombre: ${cells[1].textContent}</h3>
                    <h3>Apellidos: ${cells[2].textContent}</h3>
                    <h3>Apodo: ${cells[3].textContent}</h3>
                    <h3>Email: ${cells[4].textContent}</h3>
                    <div>
                        <h1 style="margin-right: 10px;">Crédito actual (€): ${cells[5].textContent}</h1>
                        <button id="decrement">-</button>
                        <input type="number" id="creditInput" value="10" data-user-id="${cells[0].textContent}">
                        <button id="increment">+</button>
                        <button id="submit">Añadir</button>
                    </div>
                `;
                tableContainer.style.display = 'none';
                attachEventHandlers(cells[0].textContent, parseInt(cells[5].textContent));
            });
        });
    }

    function attachEventHandlers(userId, currentCredit) {
        const decrementButton = document.getElementById('decrement');
        const incrementButton = document.getElementById('increment');
        const submitButton = document.getElementById('submit');
        const creditInput = document.getElementById('creditInput');

        decrementButton.addEventListener('click', () => {
            let newValue = parseInt(creditInput.value) - 1;
            creditInput.value = newValue >= 0 ? newValue : 0;
        });

        incrementButton.addEventListener('click', () => {
            creditInput.value = parseInt(creditInput.value) + 1;
        });

        submitButton.addEventListener('click', () => {
            const additionalCredit = parseInt(creditInput.value, 10);
            if (!isNaN(additionalCredit)) {
                updateCredit(userId, currentCredit + additionalCredit);
            } else {
                console.error('Invalid input for credit');
                alert('Por favor, introduce un número válido para el crédito.');
            }
        });
    }

    function updateCredit(userId, newCredit) {
        const updatedInfo = { credit: newCredit };
        console.log('Sending this JSON to server:', JSON.stringify(updatedInfo));

        fetch(`/creditUser/${userId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(updatedInfo)
        })
        .then(response => {
            if (!response.ok) throw new Error(`Failed to update user credit, status code: ${response.status}`);
            return response.json();
        })
        .then(updatedUser => {
            console.log('Credit updated', updatedUser);
            alert('Crédito actualizado exitosamente');
            userDetails.innerHTML = `<div>
                <h1>Crédito actualizado a: ${updatedUser.credit}</h1>
            </div>`;
        })
        .catch(error => {
            console.error('Error updating credit:', error);
            alert('Error actualizando el crédito: ' + error.message);
        });
    }
});
