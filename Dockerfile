# Selecciona la imagen base
FROM openjdk:17-jdk-slim
# Define el directorio de trabajo para el comando
WORKDIR /usr/src/app/
# Copia de la aplicación compilada
COPY target/bajagym-1.0.jar /usr/src/app/
# Indica el puerto que expone el contenedor
EXPOSE 8443
# Comando que se ejecuta al hacer docker run
CMD [ "java", "-jar", "bajagym-1.0.jar" ]