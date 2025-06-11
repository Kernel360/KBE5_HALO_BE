# Build Stage
FROM amazoncorretto:17-alpine AS build
WORKDIR /workspace

# Gradle 캐시를 활용하기 위해 Gradle Wrapper와 설정 파일만 먼저 복사
COPY gradle gradle
COPY gradlew .
COPY settings.gradle .
COPY build.gradle .

# 의존성 캐싱
RUN ./gradlew dependencies --no-daemon

# 전체 소스 복사 및 빌드
COPY . .
WORKDIR /workspace
RUN ./gradlew clean bootJar -x test --no-daemon

# Runtime Stage
FROM amazoncorretto:17-alpine
WORKDIR /app

# 빌드된 JAR 파일 복사
COPY --from=build /workspace/api/build/libs/*.jar app.jar

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]