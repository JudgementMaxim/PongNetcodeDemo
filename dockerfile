FROM openjdk:17-alpine
WORKDIR /app

COPY out/production/PongServer/ ./server/

CMD ["java", "-cp", "./server", "GameServerKt"]
