package es.gobcan.istac.statistical.operations.external.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.gobcan.istac.statistical.operations.external.domain.TipoEleccionesDatasetUrlEntity;
import es.gobcan.istac.statistical.operations.external.repository.TipoEleccionesDatasetUrlRepository;
import es.gobcan.istac.statistical.operations.external.service.TipoEleccionesDatasetUrlService;

@Service
public class TipoEleccionesDatasetUrlServiceImpl implements TipoEleccionesDatasetUrlService {
    
    @Autowired
    private TipoEleccionesDatasetUrlRepository tipoEleccionesDatasetUrlRepository;
    
    public TipoEleccionesDatasetUrlEntity findOne(String tipoElecciones) {
        return this.tipoEleccionesDatasetUrlRepository.findOne(tipoElecciones);
    }
}