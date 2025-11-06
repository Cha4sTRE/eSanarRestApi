# ============================
# 1) BUILD STAGE
# ============================
FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /app

# Copiar primero pom.xml para cachear dependencias
COPY pom.xml .
RUN mvn -q -e dependency:go-offline

# Ahora copiar el código fuente
COPY src ./src

# Ejecutar el build con pruebas
RUN mvn -q clean package


# ============================
# 2) RUNTIME STAGE
# ============================
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 80

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
