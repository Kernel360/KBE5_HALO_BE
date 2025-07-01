# Build Stage
FROM amazoncorretto:17-alpine AS build
WORKDIR /workspace

COPY gradle gradle
COPY gradlew .
COPY settings.gradle .
COPY build.gradle .
RUN ./gradlew dependencies --no-daemon

COPY . .
RUN ./gradlew clean bootJar -x test --no-daemon

# Runtime Stage
FROM amazoncorretto:17-alpine
WORKDIR /app

COPY --from=build /workspace/api/build/libs/*.jar app.jar

# 실행 시 외부 설정(application.yml) 지정
ENTRYPOINT ["java", "-jar", "app.jar", "--spring.config.location=file:/config/application.yml"]
