# Inventario de operaciones estadísticas

---
## Introducción

---
### Descripción de la aplicación
La aplicación statistical-operations-external es un inventario de las operaciones estadísticas del ISTAC.

### Requisitos previos
En este apartado se especifican los requisitos necesarios, referidos al entorno, para que la aplicación funcione adecuadamente:
- Java. 1.8.x

### Establecer las propiedades de configuación
Para configurar la aplicación con el perfil de desarrollo será necesario copiar el fichero [applicacion-dev.yml](src/main/resources/config/applicacion-dev.yml) en la ruta `/W:/data/istac/statistical-operations-external/config/` de nuestro sistema de directorio. Podemos modificar dicha ruta modificando la propiedad correspondiente dentro de [data-location.properties](src/main/resources/config/data-location.properties).

Adicionalmente, en el fichero del proyecto `/etc/data/gobcan/application-env.yml` se puede ver un ejemplo de las propiedades de configuración en ambiente de producción.

#### Descripción de las propiedades de configuración

Propiedades de `application-dev.yml`:
```yml
application:
    metadata:
        endpoint: # API de Metadatos Comunes
    categoriesSchemes:
        schemePrefix: # Prefijo de esquema de categoría. Necesario para formar la URN de las categorías y así evitar consultar la API para obtener esta información.
        categories: # Categorías listadas en la página de inicio de la aplicación.
            -
                key: # Usado para la traducción del nombre de la categoria (message.properties) y para asignarle un ícono mediante una clase CSS (subject-area-icons.css).
                nestedId: # Corresponde con el nestedId de la categoría. Si este valor empieza por un carácter numérico se debe indicar con dobles comillas. Ejemplo "020.020_010".
                color: # Color, en hexadecimal, con el que se pintará la categoría. Ejemplo "#8C5C1D".
```
Propiedades de `application.yml`:
```yml
application:
    metadata: # Claves de metadatos
        navbarPathKey: # Corresponde a la URL del header de la aplicación.
        footerPathKey: # Corresponde a la URL del footer de la aplicación.
        operationsApiKey: # Corresponde a la URL de la API para consultar las operaciones estadísticas.
```

### Arrancar la aplicación
Pasos a realizar:
1. Ejecutar desde Eclipse la correspondiente configuración de `clean install` de Maven con el perfil de desarrollo (`dev`).
2. Ejecutar como aplicación Java (`Java application`) el fichero [StatisticalOperationsExternalApp.java](src/main/java/es/gobcan/istac/statistical/operations/external/StatisticalOperationsExternalApp.java).

Una vez se realice el último paso, la aplicación estará disponible en http://localhost:8080. Ante cualquier modificación será necesario refrescar el navegador para ver los cambios. Si dichos cambios se realizan fuera de Eclipse será necesario refrescar el proyecto previamente.
