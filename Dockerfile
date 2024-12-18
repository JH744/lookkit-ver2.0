# Base image 설정 (JDK 17 사용)
FROM openjdk:17

# 작업 디렉토리 설정
WORKDIR /app

# Gradle로 빌드된 JAR 파일 복사
COPY build/libs/lookkit2nd-0.0.1-SNAPSHOT.jar app.jar

# 환경 변수 설정 (선택 사항)
ENV DB_URL=jdbc:mysql://lookkit-db-server.mysql.database.azure.com/lookkitDB?characterEncoding=UTF-8&serverTimezone=UTC
ENV DB_USERNAME=manager
ENV DB_PASSWORD=qwe123!@

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
