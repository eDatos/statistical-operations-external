package es.gobcan.istac.statistical.operations.external.service;

import es.gobcan.istac.statistical.operations.external.domain.TipoEleccionesDatasetUrlEntity;

public interface TipoEleccionesDatasetUrlService {
    
    TipoEleccionesDatasetUrlEntity findOne(String tipoElecciones);
}