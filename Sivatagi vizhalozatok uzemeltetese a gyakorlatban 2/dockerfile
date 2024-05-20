# Build with maven
FROM maven:3.8.6-openjdk-11 AS builder

WORKDIR /app
COPY pom.xml .
COPY src ./src

RUN mvn clean package

# Create the runtime image
FROM openjdk:11

RUN apt-get update && apt-get install -y \
    xauth \
    libxrender1 \
    libxtst6 \
    libxi6

# X11 env variable
ENV DISPLAY=host.docker.internal:0.0

WORKDIR /app
COPY --from=builder /app/target/*.jar /app/app.jar
COPY --from=builder /app/src/palya.txt /app/src/palya.txt

# Expose VNC port
EXPOSE 5800 5900
CMD ["java", "-jar", "/app/app.jar"]