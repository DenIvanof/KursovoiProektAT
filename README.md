# Процедура запуска автотестов

### Предусловие

* Необходимо изучить перечень используемых инструментов, описаных в [плане автоматизации тестирования](./Other/Plan.md)
  в папке Other файле Plan.md и подготовить их для дальнейшего использования в проекте.
* Клонировать репозиторий командой в терминале `git@github.com:DenIvanof/KursovoiProektAT.git`.
* Запустить программу  **Docker Desktop**.
* Запустить программу  **IntelliJ IDEA**.
* Открыть клонированный проект в **IntelliJ IDEA**.

### Запуск приложения

Для запуска приложения поочередно ввести команды в терминале IntelliJ IDEA

* Для запуска контейнера ввесети команду `docker-compose up`.
* Для запуска Java-приложения ввесети команду`java -jar artifacts/aqa-shop.jar`.

### Запуск тестов

Для запуска тестов поочередно ввести команды в терминале IntelliJ IDEA

* Для очистки истории прошлых запусков тестов и повторного запуска тестов необходимо ввести
  команду `./gradlew clean test`.
* Для генерация отчёта Allure Report необходимо ввести команду `./gradlew allureServe`.
           
