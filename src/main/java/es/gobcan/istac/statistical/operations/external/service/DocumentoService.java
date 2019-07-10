package es.gobcan.istac.statistical.operations.external.service;

import es.gobcan.istac.statistical.operations.external.web.rest.dto.EvolucionElectoralDTO;

public interface DocumentoService {

    byte[] generarPdfEvolucionElectoral(EvolucionElectoralDTO evolucionElectoral, byte[] grafica);
}