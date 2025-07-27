# syntax=docker/dockerfile:1

FROM eclipse-temurin:21-jdk-jammy as build

WORKDIR /build

# Copy gradle wrapper
COPY --chmod=0755 gradlew gradlew
COPY gradle/ gradle/

# Copy build files
COPY build.gradle.kts settings.gradle.kts ./

# Download dependencies
RUN --mount=type=cache,target=/root/.gradle \
    ./gradlew dependencies --no-daemon

# Copy source code and build
COPY src/ src/
RUN --mount=type=cache,target=/root/.gradle \
    ./gradlew bootJar --no-daemon

# Runtime stage
FROM eclipse-temurin:21-jre-jammy

ARG UID=10001
RUN adduser \
    --disabled-password \
    --gecos "" \
    --home "/nonexistent" \
    --shell "/sbin/nologin" \
    --no-create-home \
    --uid "${UID}" \
    appuser

USER appuser

COPY --from=build /build/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]