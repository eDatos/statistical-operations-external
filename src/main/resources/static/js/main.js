'use strict';
const ISTAC_HEADER_CUSTOM_CONTENT_ID = '#istac-app-header-content';
const CUSTOM_CONTENT_ELEMENTS_ID = '#istac-app-header-content-elements';

(function() {
    const headerCustomContent = Handlebars.compile($(CUSTOM_CONTENT_ELEMENTS_ID).html());
    $(ISTAC_HEADER_CUSTOM_CONTENT_ID).html(headerCustomContent());

    initDropdown('#dropdown-language');

    getOperations(
        function(response) {
            if (response.total > 0) {
                var operations = response.operation;
                $('.searcher').each(function() {
                    initSearch($(this), operations);
                });
            }
            else {
                console.warn('No hay operaciones estadísticas para mostrar');
            }
        },
        function(err) {
            console.log('Error al obtener las operaciones estadísticas');
            console.error(err);
        }
    );
})();
