'use strict';

function initSearch($searcher) {
    var $searchResultFocused;
    var $searchInput = $searcher.find('.search-input');
    var $searchResultsPanel = $searcher.find('.search-results-panel');

    var totalSearchResult = 0;
    var isfocusing = false; 

    function focusSearchResult($newFocused) {
        $searchResultFocused && $searchResultFocused.removeClass('focused');
        $newFocused.addClass('focused');
        $searchResultFocused = $newFocused;
    }

    function clickOutsideSearchHandler() {
        if (isfocusing) isfocusing = false;
        else hideSearchResults();
    }

    function addEventClickOutsideSearch() {
        $(document).off('click', clickOutsideSearchHandler);
        $(document).on('click', clickOutsideSearchHandler);
    }

    function hideSearchResults() {
        $searchResultsPanel.hide();
        $(document).off('click', clickOutsideSearchHandler);
        document.removeEventListener('keydown', arrowKeyPressHandler);
    }

    function showSearchresults() {
        $searchResultsPanel.show();
        addEventClickOutsideSearch();
        document.addEventListener('keydown', arrowKeyPressHandler);
    }

    function select($result, index) {
        var selected = index;
        var $searchResults = $searcher.find('.search-results');
        
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
        select($searcher.find('.search-results-item[data-iresult="'+ iFocused + '"]'), iFocused);

        e.preventDefault();
    }
    
    $searcher.find('.btn-show-results').on('click', function(e) {
        $searchInput.focus();
    });

    $searchInput.on('focus', function() {
        isfocusing = true;
        showSearchresults();
    });

    $searcher.on('mouseover', '.search-results-item', function(e) {
        focusSearchResult($(this));
    });

    $searcher.on('click', function(e) {
        if (!isfocusing && $(e.target).closest('a').length == 0) {
            e.preventDefault();
            e.stopPropagation();
        }
    })

    $searchInput.on('input', function() {
        var operationsApiUrl = 'https://www3.gobiernodecanarias.org/istac/api/operations/v1.0/operations.json';

        $.ajax({
            method: 'GET',
            url: operationsApiUrl + '?query=' + (this.value? 'TITLE ILIKE "' + this.value + '"': '') ,
            success: function(response) {
                $searchResultFocused = null;
                totalSearchResult = response.total;
                var htmlContent = '<ul class="search-results">';
                if (totalSearchResult > 0) {
                    for (var i=0; i < totalSearchResult; i++) {
                        var operation = response.operation[i];
                        htmlContent += '<li class="search-results-item" data-iresult="' + i + '">';
                            htmlContent += '<a class="link" href="/operations/'+ operation.id + '">' + getTranslatedText(operation.name) + '</a>';
                        htmlContent += '</li>'
                    }
                    
                }
                else {
                    htmlContent += '<li>' + 'No se han encontrado resultados' + '</li>'; 
                }

                htmlContent += '</ul>';

                $searchResultsPanel.html(htmlContent);
            },
            error: function(e) {
                totalSearchResult = 0;
                console.log('Error al consultar las operaciones');
                console.error(e);
            }
        })
    });
}

(function() {
    $('.searcher').each(function(index) {
        initSearch($(this));
    });
})()