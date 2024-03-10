FROM maven as build

COPY src src
COPY pom.xml pom.xml

RUN mvn clean package -Dmaven.test.skip

FROM amazoncorretto:21-alpine as app

RUN adduser --system proxyusr &&  \
    addgroup --system jsonplaceholderproxy &&  \
    adduser proxyusr jsonplaceholderproxy
USER proxyusr

WORKDIR /app

COPY --from=build target/JsonPlaceholderProxy.jar ./app.jar

ENTRYPOINT ["java", "-jar", "./app.jar"]