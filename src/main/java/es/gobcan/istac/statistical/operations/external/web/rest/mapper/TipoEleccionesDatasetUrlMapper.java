package es.gobcan.istac.statistical.operations.external.web.rest.mapper;

import org.mapstruct.Mapper;

import es.gobcan.istac.statistical.operations.external.domain.TipoEleccionesDatasetUrlEntity;
import es.gobcan.istac.statistical.operations.external.web.rest.dto.TipoEleccionesDatasetUrlDTO;

@Mapper(componentModel = "spring", uses = {})
public interface TipoEleccionesDatasetUrlMapper extends EntityMapper<TipoEleccionesDatasetUrlDTO, TipoEleccionesDatasetUrlEntity> {
}