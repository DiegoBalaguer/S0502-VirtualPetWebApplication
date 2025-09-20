FROM maven:3.9.10-eclipse-temurin-24 AS builder

# Establecer el directorio de trabajo en el contenedor
WORKDIR /pet-app

# Copiar el archivo pom.xml y las dependencias de Maven primero
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar el resto de la aplicación
COPY src ./src

# Construir el proyecto
RUN mvn clean package -DskipTests

FROM eclipse-temurin:24-jre-alpine

WORKDIR /pet-app

COPY --from=builder /pet-app/target/*.jar pet-app.jar

EXPOSE 8080

# Usuario no root por seguridad
RUN addgroup -S spring && adduser -S spring -G spring
USER spring

ENTRYPOINT ["java", "-jar", "pet-app.jar"]
