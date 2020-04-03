'use strict';

var NORMALIZED_REGEXPS = {
    a: /[àáâãäå]/g,
    e: /[èéêë]/g,
    i: /[ìíîï]/g,
    o: /[òóôõö]/g,
    u: /[ùúûü]/g
}

function parseString(rawString) {
    return rawString.toLowerCase()
            .replace(new RegExp(NORMALIZED_REGEXPS.a),"a")
            .replace(new RegExp(NORMALIZED_REGEXPS.e),"e")
            .replace(new RegExp(NORMALIZED_REGEXPS.i),"i")
            .replace(new RegExp(NORMALIZED_REGEXPS.o),"o")
            .replace(new RegExp(NORMALIZED_REGEXPS.u),"u");
}

function initSearch($searcher, operations) {
    var $searchResultFocused;
    var $searchInput = $searcher.find('.search-input');
    var $searchResultsContainer = $searcher.find('.search-results-container');

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
        else hideSearchResultsContainer();
    }

    function addEventClickOutsideSearch() {
        $(document).off('click', clickOutsideSearchHandler);
        $(document).on('click', clickOutsideSearchHandler);
    }

    function hideSearchResultsContainer() {
        $searchResultsContainer.hide();
        $(document).off('click', clickOutsideSearchHandler);
        document.removeEventListener('keydown', arrowKeyPressHandler);
    }

    function showSearchResultsContainer() {
        $searchResultsContainer.show();
        addEventClickOutsideSearch();
        document.addEventListener('keydown', arrowKeyPressHandler);
    }

    function select($result) {
        var $searchResults = $searcher.find('.search-results');

        var elHeight = $result.height();
        var scrollTop = $searchResults.scrollTop();
        var viewport = scrollTop + $searchResults.height();
        var elOffset = $result[0].offsetTop;

        if (elOffset < scrollTop || (elOffset + elHeight) > viewport)
            $searchResults.scrollTop(elOffset);

        focusSearchResult($result);
    }

    function filterResults(value) {
        var results;
        $searchResultFocused = null;
        if (!value) {
            results = operations.map(function(operation, index) {
                return {
                    iResult: index,
                    name: getTranslatedText(operation.name),
                    id: operation.id
                }
            });
        }
        else {
            results = operations.reduce(function(filteredOperations, operation) {
                var operationName = getTranslatedText(operation.name);

                if (parseString(operationName).indexOf(value) != -1) {
                    filteredOperations.push({
                        iResult: filteredOperations.length,
                        name: operationName,
                        id: operation.id
                    });
                }

                return filteredOperations;
            }, []);
        }

        totalSearchResult = results.length;

        var htmlContent = searchResultsTemplate({results: results});
        $searchResultsContainer.html(htmlContent);
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
        select($searcher.find('.search-results-item[data-iresult="'+ iFocused + '"]'));

        e.preventDefault();
    }

    $searcher.find('.btn-show-results').on('click', function(e) {
        $searchInput.focus();
    });

    $searchInput.on('focus', function() {
        isfocusing = true;
        showSearchResultsContainer();
        if (!$searchInput.val() && totalSearchResult == 0) {
            filterResults();
        }
    });

    $searcher.on('mouseover', '.search-results-item', function(e) {
        focusSearchResult($(this));
    });

    $searcher.on('click', '.search-results-item', function(e) {
        hideSearchResultsContainer();
    });

    $searcher.on('click', function(e) {
        if (!isfocusing) {
            e.stopPropagation();
        }
    })

    $searchInput.on('input', function() {
        var value = this.value;
        filterResults(parseString(value));
    });
}
