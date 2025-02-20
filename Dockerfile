FROM eclipse-temurin:17-jdk-jammy
# Usa un contenedor base con Java 17 (Eclipse Temurin)

WORKDIR /app
# Define el directorio de trabajo como /app

COPY .mvn/ .mvn
# Copia la carpeta de configuración del wrapper de Maven

COPY mvnw pom.xml ./
# Copia el script mvnw y el archivo pom.xml al directorio de trabajo

RUN chmod +x mvnw
# 

RUN ./mvnw dependency:resolve
# Descarga las dependencias del proyecto para aprovechar la caché de Docker

COPY src ./src
# Copia el código fuente de la aplicación

CMD ["./mvnw", "spring-boot:run"]
# Inicia la aplicación Spring Boot usando Maven Wrapper