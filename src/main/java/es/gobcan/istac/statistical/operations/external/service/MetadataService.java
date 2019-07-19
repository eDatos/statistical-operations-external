package es.gobcan.istac.statistical.operations.external.service;

public interface MetadataService {

    String getOperationsApi();
    String getNavbarUrl();
    String getFooterUrl();

    String getPropertyById(String propertyId);
}
