# Use uma imagem do Maven para construir o projeto
FROM maven:3.8.4-jdk-11 AS build

# Crie um diretório de trabalho para a aplicação
WORKDIR /app

# Copie o arquivo pom.xml e o diretório src para o contêiner
COPY pom.xml .
COPY src ./src

# Rode o comando de build do Maven para compilar a aplicação
RUN mvn clean package

# Use uma imagem do JDK para rodar a aplicação
FROM openjdk:11-jre-slim

# Defina o diretório de trabalho para o contêiner
WORKDIR /app

# Copie o JAR gerado do estágio de build para o estágio de runtime
COPY --from=build /app/target/api-live2.jar /app/api-live2.jar

# Exponha a porta em que a aplicação será executada
EXPOSE 10000

# Defina o comando de inicialização da aplicação
CMD ["java", "-jar", "api-live2.jar"]
