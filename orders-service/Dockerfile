FROM eclipse-temurin:21-jre-alpine

# Tworzenie użytkownika i grupy
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

WORKDIR /app

# Kopiowanie i budowanie aplikacji
COPY target/orders-service-*.jar app.jar

# Ustawienia JVM
ENV JAVA_OPTS="-Xms128m -Xmx256m"

USER appuser:appgroup

EXPOSE 8081

HEALTHCHECK --interval=30s --timeout=5s --start-period=30s --retries=3 \
  CMD wget --spider --quiet http://localhost:8081/actuator/health || exit 1

ENTRYPOINT exec java $JAVA_OPTS -jar app.jar
