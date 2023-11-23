FROM eclipse-temurin:20
ARG JAR_FILE=target/*.jar
ENV BOT_NAME=test_javarush_tutorial_bot
ENV BOT_TOKEN=6956421218:AAEX0XxaO5tmHGqaLNmg_c_FAOAWXMXvw7s
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Dbot.username=${BOT_NAME}", "-Dbot.token=${BOT_TOKEN}","-jar", "/app.jar"]