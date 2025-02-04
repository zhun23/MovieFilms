document.addEventListener('DOMContentLoaded', () => {
    const tableBody = document.getElementById('tableBody');
    const pageInfo = document.getElementById('page-info');
    const lowStockThresholdSelect = document.getElementById('lowStockThreshold');
    let currentPage = 0;
    let totalPages = 1;

    lowStockThresholdSelect.addEventListener('change', () => {
        fetchPage(currentPage);
    });

    function fetchPage(pageNumber) {
        fetch(`http://localhost:8090/api/catalogue/catalogueStock?page=${pageNumber}&size=24`)
            .then(response => response.json())
            .then(data => {
                totalPages = data.totalPages;
                currentPage = data.number;
                renderTable(data.content);
                updatePageInfo();
            })
            .catch(error => console.error('Error fetching data:', error));
    }

    function renderTable(movies) {
        const lowStockThreshold = parseInt(lowStockThresholdSelect.value, 10);
        tableBody.innerHTML = '';
        movies.forEach((movie, index) => {
            const row = document.createElement('tr');
            const stockClass = movie.stock < lowStockThreshold ? 'lowStock' : '';
            row.innerHTML = `
                <td>${movie.id}</td>
                <td class="RowOfMovie ${stockClass}">${movie.title}</td>
                <td class="RowOfMovie ${stockClass}">${movie.stock}</td>
            `;
            tableBody.appendChild(row);
        });
    }

    function updatePageInfo() {
        pageInfo.textContent = `Página ${currentPage + 1} de ${totalPages}`;
        document.getElementById('prevBtn').disabled = currentPage === 0;
        document.getElementById('nextBtn').disabled = currentPage === totalPages - 1;
    }

    function prevPage() {
        if (currentPage > 0) {
            fetchPage(currentPage - 1);
        }
    }

    function nextPage() {
        if (currentPage < totalPages - 1) {
            fetchPage(currentPage + 1);
        }
    }

    document.getElementById('prevBtn').addEventListener('click', prevPage);
    document.getElementById('nextBtn').addEventListener('click', nextPage);

    // Initial fetch
    fetchPage(currentPage);
});
