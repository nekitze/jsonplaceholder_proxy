# JsonPlaceholder Proxy

<!-- TOC -->
* [JsonPlaceholder Proxy](#jsonplaceholder-proxy)
  * [О проекте](#о-проекте)
  * [Сборка и запуск](#сборка-и-запуск)
    * [С помощью Docker (рекомендуется)](#с-помощью-docker-рекомендуется)
    * [Доступ к базе данных внутри контейнера](#доступ-к-базе-данных-внутри-контейнера)
    * [С помощью Maven](#с-помощью-maven)
  * [Тестирование API](#тестирование-api)
  * [Список пользователей по умолчанию](#список-пользователей-по-умолчанию-создаются-в-бд-при-запуске)
<!-- TOC -->
<br><br>
## О проекте
Проект релизует REST API для перенаправления запросов на https://jsonplaceholder.typicode.com/

Поддерживаются обработчики GET, POST, PUT, DELETE, проксирующие следующие запросы:
- /api/posts/**
- /api/users/**
- /api/albums/**

Реализована базовая авторизация и ролевая модель.<br>

Реализован эндпоинт для добавления пользователей:<br>
- /api/proxy/add_user/

Для хранения пользователей и ведения аудита используется база данных Postgres.<br>

Используется кэширование для уменьшения числа запросов к jsonplaceholder.<br><br><br>

## Сборка и запуск

После запуска проект будет доступен по http://localhost:3333/jsonplaceholder_proxy/ <br><br>

### С помощью Docker (рекомендуется):
В Dockerfile уже описаны все инструкции для сборки проекта.
С помощью docker compose поднимается контейнер с базой данных и контейнер с приложением.

**Всё можно запустить одной командой:**
```shell
docker compose up
```
При первом запуске будут подкачаны необходимые образы для работы с Postgres и JDK, поэтому процесс может занять пару минут.<br><br>

### Доступ к базе данных внутри контейнера

Чтобы посмотреть, что лежит в БД контейнера, можно использовать следующие команды:

- Для вывода в терминал записей из таблицы аудита:

```shell
docker exec -it jsonplaceholderproxy-db-1 psql -U postgres -d postgres -c "select * from jsonplaceholder_proxy.audit"
```

- Для вывода сохраненных пользователей:

```shell
docker exec -it jsonplaceholderproxy-db-1 psql -U postgres -d postgres -c "select * from jsonplaceholder_proxy.users"
```

<br><br>

### С помощью Maven:
1. Устанавливаем и запускаем Postgres на локальной машине или где-нибудь еще.
2. Настраиваем ```application.properties``` (нужно указать url, username, password от вашей БД)<br>
   
Пример:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/postgres
spring.datasource.username=postgres
spring.datasource.password=1234
spring.datasource.driver-class-name=org.postgresql.Driver
 ```
3. Собираем проект:
```shell
mvn package
```
4. Запускаем:
```shell
java -jar target/JsonPlaceholderProxy.jar
```
<br><br>
## Тестирование API

Для тестирования api можно использовать [Postman](https://www.postman.com/winter-comet-601186/workspace/jsonplaceholderproxy-api/request/25055749-b1bae944-cc57-403f-a7b9-c851e31eb19c) с заготовленными запросами.
Для того, чтобы рабоать с Postman в браузере и он мог отправлять запросы на ваш localhost, установите Postman Agent.

Также для взаимодействия предусмотрена интеграция со Swagger UI, доступ по http://localhost:3333/jsonplaceholder_proxy/swagger-ui/index.html
<br><br>
## Список пользователей по умолчанию (создаются в БД при запуске):

| Name     | Password | Roles                                                                                |
|:---------|:---------|:-------------------------------------------------------------------------------------|
| admin    | admin    | ROLE\_ADMIN                                                                          |
| users    | users    | ROLE\_USERS                                                                          |
| albums   | albums   | ROLE\_ALBUMS                                                                         |
| posts    | posts    | ROLE\_POSTS                                                                          |
| visitor  | visitor  | ROLE\_POSTS\_VIEWER, ROLE\_ALBUMS\_VIEWER                                            |
| redactor | redactor | ROLE\_POSTS\_EDITOR, ROLE\_ALBUMS\_EDITOR |

Для добавления новых пользователей используйте эндпоинт ```/api/proxy/add_user/```

Пример тела запроса:
```json
{
  "name": "new_user",
  "password": "new_user",
  "roles": "ROLE_POSTS_VIEWER, ROLE_POSTS_EDITOR"
}
```
