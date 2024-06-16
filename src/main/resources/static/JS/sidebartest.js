$("#menu-toggle").click(function(e) {
    e.preventDefault();
    $("#wrapper").toggleClass("toggled");
});

$("#menu-toggle-2").click(function(e) {
    e.preventDefault();
    $("#wrapper").toggleClass("toggled-2");
    $('#menu ul').hide();
});

function initMenu() {
    $('#menu ul').hide();
    $('#menu ul').children('.current').parent().show();
    // $('#menu ul:first').show();
    $('#menu li a').click(function() {
        var checkElement = $(this).next();
        if ((checkElement.is('ul')) && (checkElement.is(':visible'))) {
            return false;
        }
        if ((checkElement.is('ul')) && (!checkElement.is(':visible'))) {
            $('#menu ul:visible').slideUp('normal');
            checkElement.slideDown('normal');
            return false;
        }
    });
}


$(document).ready(function() {
    initMenu();

    // Title search form submission
    document.getElementById('titleSearchForm').addEventListener('submit', function(event) {
        event.preventDefault(); // Prevent the default form submission

        const titleQuery = document.getElementById('inputTitleSearch').value;
        const url = `http://localhost:8090/api/catalogue/titleSearch?title=${encodeURIComponent(titleQuery)}`;

        fetch(url)
            .then(response => response.json())
            .then(data => {
                // Store the search results in localStorage
                localStorage.setItem('searchResults', JSON.stringify(data));
                localStorage.setItem('titleQuery', titleQuery);
                // Redirect to the results page
                window.location.href = 'titleResults.html';
            });
    });

    // Director search form submission
    document.getElementById('directorSearchForm').addEventListener('submit', function(event) {
        event.preventDefault(); // Prevent the default form submission

        const directorQuery = document.getElementById('inputDirectorSearch').value;
        const url = `http://localhost:8090/api/catalogue/directorSearch?director=${encodeURIComponent(directorQuery)}`;

        fetch(url)
            .then(response => response.json())
            .then(data => {
                // Store the search results in localStorage
                localStorage.setItem('searchResults', JSON.stringify(data));
                localStorage.setItem('directorQuery', directorQuery);
                // Redirect to the results page
                window.location.href = 'directorResults.html';
            });
    });

    // Year search form submission
    document.getElementById('yearSearchForm').addEventListener('submit', function(event) {
        event.preventDefault(); // Prevent the default form submission

        const yearQuery = document.getElementById('inputYearSearch').value;
        const url = `http://localhost:8090/api/catalogue/yearSearch?year=${encodeURIComponent(yearQuery)}`;

        fetch(url)
            .then(response => response.json())
            .then(data => {
                // Store the search results in localStorage
                localStorage.setItem('searchResults', JSON.stringify(data));
                localStorage.setItem('yearQuery', yearQuery);
                // Redirect to the results page
                window.location.href = 'yearResult.html';
            });
    });
});
