# Music Poll Website

A website development project, created for the Per Scholas Full Stack Java Developer training program.

Users of this website may create a poll with a poll title and three song titles. Then users may vote on which is the best song in each poll. Users are limited to one vote per poll.

This website is currently live at [music-poll-website.herokuapp.com](https://music-poll-website.herokuapp.com). (Note that it may take up to 20 seconds for the website to load at first, if my free dyno has gone into sleep mode.)

## Technologies
#### Source Code
- Java 11
- Spring Boot
#### Database
- Postgres
#### Security
- Spring Security
#### Testing
- JUnit
#### Utility
- Lombok

## Installation
1. Download source code from the git repository using this command:

```
git clone https://github.com/ekand/music-poll-website-public.git
```

2. Start up an instance of Postgres with database name `music_poll_website_generated` and schema `public`

- Adjust the following default properties in `src/resources/application.properties` to suit your database configuration.
- The default port for the database is `5432`
- The default username is `postgres`
- The default password is empty


3. In the root directory of the project, run this command to build and launch the application:

```
./mvnw spring-boot:run
```
