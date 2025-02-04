document.addEventListener("DOMContentLoaded", function() {
    const apiUrl = 'http://localhost:8090/api/allPurchases';
    const tableBody = document.getElementById('tableBody');
    const pageInfo = document.getElementById('page-info');
    let currentPage = 0;
    let totalPages = 0;

    if (!tableBody) {
        console.error('Element with ID "table-body" not found.');
        return;
    }

    if (!pageInfo) {
        console.error('Element with ID "page-info" not found.');
        return;
    }

    function fetchPurchases(page = 0) {
        fetch(`${apiUrl}?page=${page}`)
            .then(response => response.json())
            .then(data => {
                totalPages = data.totalPages;
                currentPage = data.currentPage;
                updateTable(data.purchases);
                updatePagination();
            })
            .catch(error => console.error('Error fetching data:', error));
    }

    function updateTable(purchases) {
        tableBody.innerHTML = ''; // Limpiar la tabla antes de agregar nuevas filas

        purchases.forEach((purchase) => {
            const row = document.createElement('tr');
            row.dataset.purchaseId = purchase.id; // Agrega el ID de la compra como atributo de datos
            row.innerHTML = `
                <td>${purchase.id}</td>
                <td>${new Date(purchase.date).toLocaleDateString()}</td>
                <td>${purchase.nickname}</td>
                <td>${purchase.address1}</td>
                <td>${purchase.location}</td>
                <td>${purchase.zipcode}</td>
                <td>${purchase.province}</td>
                <td id="allignprice">${purchase.shippingCost.toFixed(2)}</td>
                <td id="allignStock">${purchase.totalCost.toFixed(2)}</td>
            `;
            row.addEventListener('click', function() {
                var nextRow = row.nextSibling;
                if (nextRow && nextRow.classList.contains('details-row')) {
                    nextRow.remove();
                } else {
                    loadPurchaseItems(purchase.id, row);
                }
            });
            tableBody.appendChild(row);
        });
    }

    function updatePagination() {
        pageInfo.textContent = `Página ${currentPage + 1} de ${totalPages}`;
        document.getElementById('prevBtn').disabled = currentPage === 0;
        document.getElementById('nextBtn').disabled = currentPage === totalPages - 1;
    }

    document.getElementById('prevBtn').addEventListener('click', function() {
        if (currentPage > 0) {
            fetchPurchases(currentPage - 1);
        }
    });

    document.getElementById('nextBtn').addEventListener('click', function() {
        if (currentPage < totalPages - 1) {
            fetchPurchases(currentPage + 1);
        }
    });

    // Inicializa la tabla con la primera página de datos
    fetchPurchases();

    // Método separado para cargar los detalles de la compra
    function loadPurchaseItems(purchaseId, row) {
        fetch(`http://localhost:8090/api/purchaseItems/${purchaseId}`)
            .then(response => response.json())
            .then(data => {
                // Elimina filas de detalles existentes si las hay
                removeExistingDetailRows(row);

                // Crear una nueva fila de detalles
                const detailsRow = document.createElement('tr');
                detailsRow.classList.add('details-row');
                detailsRow.innerHTML = `
                    <td colspan="9">
                        <table class="details-table">
                            <thead>
                                <tr>
                                    <th>Artículo</th>
                                    <th>Cantidad</th>
                                </tr>
                            </thead>
                            <tbody>
                                ${data.map(item => `
                                    <tr>
                                        <td>${item.title}</td>
                                        <td>${item.quantity}</td>
                                    </tr>
                                `).join('')}
                            </tbody>
                        </table>
                    </td>
                `;
                row.parentNode.insertBefore(detailsRow, row.nextSibling);
            })
            .catch(error => console.error('Error fetching purchase details:', error));
    }

    // Método para eliminar filas de detalles existentes
    function removeExistingDetailRows(row) {
        let nextRow = row.nextSibling;
        while (nextRow && nextRow.classList.contains('details-row')) {
            const rowToRemove = nextRow;
            nextRow = nextRow.nextSibling;
            rowToRemove.parentNode.removeChild(rowToRemove);
        }
    }
});
