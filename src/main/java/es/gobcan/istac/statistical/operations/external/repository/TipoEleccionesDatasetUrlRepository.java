package es.gobcan.istac.statistical.operations.external.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.gobcan.istac.statistical.operations.external.domain.TipoEleccionesDatasetUrlEntity;

@Repository
public interface TipoEleccionesDatasetUrlRepository extends JpaRepository<TipoEleccionesDatasetUrlEntity, String> {
}