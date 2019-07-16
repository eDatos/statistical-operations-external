'use strict';

(function() {

    function clickOutsideSearchHandler() {
        hideSearchResults();
    }

    function addEventClickOutsideSearch() {
        $(document).off('click', clickOutsideSearchHandler);
        $(document).one('click', clickOutsideSearchHandler);
    }

    function hideSearchResults() {
        $('.ac-search-results-panel').hide();
        $(document).off('click', clickOutsideSearchHandler);
    }

    $('.search-container').on('click', function(e) {
        if($(e.target).closest('a').length == 0) {
            e.preventDefault();
            e.stopPropagation();
        }
        
    })

    $('.btn-show-results').on('click', function(e) {
        $('.ac-search').focus();
    });

    $('.ac-search').on('focus', function() {
        $('.ac-search-results-panel').show();
        addEventClickOutsideSearch();
    })

    $('.ac-search').on('input', function() {
        var operationsApiUrl = 'https://www3.gobiernodecanarias.org/istac/api/operations/v1.0/operations.json';

        $.ajax({
            method: 'GET',
            url: operationsApiUrl + '?query=' + (this.value? 'TITLE ILIKE "' + this.value + '"': '') ,
            success: function(response) {
                var total = response.total;
                var htmlContent = '<ul class="ac-search-results">';
                if (total > 0) {
                    for (var i=0; i < total; i++) {
                        var operation = response.operation[i];
                        htmlContent += '<li class="search-results-item" data-iresult="' + i + '">';
                            htmlContent += '<a href="./operations/'+ operation.id + '">' + getTranslatedText(operation.name) + '</a>';
                        htmlContent += '</li>'
                    }
                    
                }
                else {
                    htmlContent += '<li>' + 'No se han encontrado resultados' + '</li>'; 
                }

                htmlContent += '</ul>';

                $('.ac-search-results-panel').show().html(htmlContent);
            },
            error: function(e) {
                console.log('Error al consultar las operaciones');
                console.error(e);
            }
        })
    });
})();
