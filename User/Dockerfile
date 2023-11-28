# Folosind o imagine Maven pentru a construi aplicația
FROM maven:3.8.3-openjdk-17 AS builder

WORKDIR /app

# Copiază fișierele sursă și POM.xml în container
COPY ./src ./src
COPY pom.xml ./

# Construiește aplicația folosind Maven și generează fișierul JAR
RUN mvn package

# Extrage stratul de aplicație generat
RUN java -Djarmode=layertools -jar target/ds-2020-0.0.1-SNAPSHOT.jar extract

# Imaginea finală va conține stratul de aplicație și dependențele
FROM openjdk:17-jdk-slim-buster

WORKDIR /app

# Copiază stratul de aplicație și dependențele din containerul de builder
COPY --from=builder /app/spring-boot-loader/ ./
COPY --from=builder /app/dependencies/ ./
COPY --from=builder /app/snapshot-dependencies/ ./
COPY --from=builder /app/application/ ./

# Setează variabile de mediu dacă este nevoie
# ENV TZ=UTC
# ENV DB_IP=172.19.0.6
# ...

# Expose portul 8080
EXPOSE 8080

# Pornirea aplicației
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
