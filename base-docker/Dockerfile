FROM openjdk:8-jdk-slim-buster
WORKDIR /app
ADD lobby-home.tar.xz .
WORKDIR /app/lobby-home
CMD ["java", "-Xmx1g", "-jar", "spigot-1.8.8.jar", "nogui", "--noconsole"]
