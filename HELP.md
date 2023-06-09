# Quick Start
* Clonar el repositorio
* Situarse con una consola en la raíz del proyecto.
* Ejecutar: mvn clean install
* Ejecutar: docker compose -f docker/docker-compose.yaml -f docker/docker-compose-apps.yaml up --build -d
* Acceder a: http://localhost:3000/
* Detener: docker compose -f docker/docker-compose.yaml -f docker/docker-compose-apps.yaml down
