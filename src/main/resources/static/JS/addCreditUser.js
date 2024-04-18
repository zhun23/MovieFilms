document.getElementById('search').addEventListener('click', function(event) {
    event.preventDefault();
    const query = document.getElementById('findGlobalUser').value;

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
            // Procesar la respuesta exitosa
            const tableContainer = document.getElementById('tableContainer');
            tableContainer.innerHTML = ''; // Limpiamos cualquier tabla previa

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

            addClickEventToRows();
        })
        .catch(error => {
            console.error('Error:', error);
            const errorContainer = document.getElementById('searchResult');
            errorContainer.innerHTML = `<p>${error.message}</p>`;
            setTimeout(() => {
                errorContainer.style.opacity = '0';
            }, 3000);
        });
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
                <div id="creditRow" style="display: flex; align-items: center;">
                    <p style="margin-right: 10px;">Crédito: </p>
                    <input type="number" id="creditInput" value="${cells[5].textContent}" readonly style="margin-right: 10px;">
                    <button id="decrement">-</button>
                    <button id="increment">+</button>
                </div>
            `;
            
            // Botón para decrementar
            document.getElementById('decrement').addEventListener('click', function() {
                const creditInput = document.getElementById('creditInput');
                const newValue = parseInt(creditInput.value) - 1;
                creditInput.value = newValue >= 0 ? newValue : 0;
            });
            
            // Botón para incrementar
            document.getElementById('increment').addEventListener('click', function() {
                const creditInput = document.getElementById('creditInput');
                creditInput.value = parseInt(creditInput.value) + 1;
            });
        });
    }
}

