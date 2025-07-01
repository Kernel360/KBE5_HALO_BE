# Build Stage
FROM amazoncorretto:17-alpine AS build
WORKDIR /workspace

COPY build.gradle settings.gradle gradlew gradle/ ./
RUN ./gradlew dependencies

COPY src ./src
RUN ./gradlew bootJar -x test

# Runtime Stage
FROM amazoncorretto:17-alpine
WORKDIR /app

COPY --from=build /workspace/build/libs/*.jar app.jar

# 실행 시 외부 설정(application.yml) 지정
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.config.location=file:/config/application.yml"]
