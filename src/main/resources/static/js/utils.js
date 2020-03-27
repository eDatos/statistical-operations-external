'use strict';

function initDropdown(selector) {
    const dropdown = $(selector);
    dropdown.find('.dropdown-toggle').on('click', function() {
        dropdown.toggleClass('active');
    })
}

function getOperations(succesCallback, errorCallback) {
    $.ajax({
        method: 'GET',
        url: CONFIGURATION.OPERATIONS_API_URL + '.json',
        success: function(response) {
            succesCallback(response);
        },
        error: function(err) {
            errorCallback(err);
        }
    });
}
