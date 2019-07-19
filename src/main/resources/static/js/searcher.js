'use strict';

function initSearch($searcher) {
    var $searchResultFocused;
    var $searchInput = $searcher.find('.search-input');
    var $searchResultsPanel = $searcher.find('.search-results-panel');

    var totalSearchResult = 0;
    var isfocusing = false;

    var searchResultsTemplate = Handlebars.compile($("#search-results-template").html());
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

    $searcher.on('click', '.search-results-item', function(e) {
        hideSearchResults();
    });

    $searcher.on('click', function(e) {
        if (!isfocusing) {
            e.stopPropagation();
        }
    })

    $searchInput.on('input', function() {
        $.ajax({
            method: 'GET',
            url: CONFIGURATION.OPERATIONS_API_URL + '.json',
            data: {
                query: this.value? 'TITLE ILIKE "' + this.value + '"': ''
            },
            success: function(response) {
                $searchResultFocused = null;
                totalSearchResult = response.total;
                var results;
                
                if (totalSearchResult > 0) {
                    results =  response.operation.map(function(operation, index) {
                        return {
                            iResult: index,
                            name: getTranslatedText(operation.name),
                            id: operation.id
                        }
                    });
                    
                }

                var htmlContent = searchResultsTemplate({results: results});
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