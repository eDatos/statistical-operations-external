'use strict';

(function() {

    var $searchResultFocused;
    var totalSearchResult = 0;

    function focusSearchResult($newFocused) {
        $searchResultFocused && $searchResultFocused.removeClass('focused');
        $newFocused.addClass('focused');
        $searchResultFocused = $newFocused;
    }

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
        document.removeEventListener('keydown', arrowKeyPressHandler);
    }

    function select($result, index) {
        var selected = index;
        var $searchResults = $('.ac-search-results');
        
        var elHeight = $result.height();
        var scrollTop = $searchResults.scrollTop();
        var viewport = scrollTop + $searchResults.height();
        var elOffset = elHeight * selected;
        
        if (elOffset < scrollTop || (elOffset + elHeight) > viewport)
            $searchResults.scrollTop(elOffset);

        focusSearchResult($result);
    }

    function arrowKeyPressHandler(e) {
        if (!totalSearchResult || [38 /*UP*/, 40 /*DOWN*/, 13 /*ENTER*/].indexOf(e.keyCode) == -1) return;

        
        if (e.keyCode === 13) {
            $searchResultFocused && $searchResultFocused.find('a')[0].click();
            return;
        }
        
        var iFocused = $searchResultFocused && $searchResultFocused.data('iresult');
        if (e.keyCode === 38) { // up
            iFocused = iFocused != null & iFocused != 0? iFocused : totalSearchResult;
            iFocused--;
        }
        else if (e.keyCode === 40) { // down
            iFocused = iFocused != null && iFocused < (totalSearchResult -1)? iFocused : - 1;
            iFocused++;
            
        }
        select($('.search-results-item[data-iresult="'+ iFocused + '"]'), iFocused);

        e.preventDefault();
    }

    function showSearchresults() {
        $('.ac-search-results-panel').show();
        addEventClickOutsideSearch();
        document.addEventListener('keydown', arrowKeyPressHandler);
    }

    

    $('.searcher').on('click', function(e) {
        if ($(e.target).closest('a').length == 0) {
            
            e.preventDefault();
            e.stopPropagation();
        }
    })

    $('.btn-show-results').on('click', function(e) {
        $('.ac-search').focus();
    });

    $('.ac-search').on('focus', function() {
        showSearchresults();
    });

    $('.searcher').on('mouseover', '.search-results-item', function(e) {
        focusSearchResult($(this));
    });

    $('.ac-search').on('input', function() {
        var operationsApiUrl = 'https://www3.gobiernodecanarias.org/istac/api/operations/v1.0/operations.json';

        $.ajax({
            method: 'GET',
            url: operationsApiUrl + '?query=' + (this.value? 'TITLE ILIKE "' + this.value + '"': '') ,
            success: function(response) {
                $searchResultFocused = null;
                totalSearchResult = response.total;
                var htmlContent = '<ul class="ac-search-results">';
                if (totalSearchResult > 0) {
                    for (var i=0; i < totalSearchResult; i++) {
                        var operation = response.operation[i];
                        htmlContent += '<li class="search-results-item" data-iresult="' + i + '">';
                            htmlContent += '<a class="link" href="./operations/'+ operation.id + '">' + getTranslatedText(operation.name) + '</a>';
                        htmlContent += '</li>'
                    }
                    
                }
                else {
                    htmlContent += '<li>' + 'No se han encontrado resultados' + '</li>'; 
                }

                htmlContent += '</ul>';

                $('.ac-search-results-panel').html(htmlContent);
            },
            error: function(e) {
                totalSearchResult = 0;
                console.log('Error al consultar las operaciones');
                console.error(e);
            }
        })
    });
})();
