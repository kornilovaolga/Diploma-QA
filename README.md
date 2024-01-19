## Дипломный проект профессии «Тестировщик ПО»   

### О проекте   

#### Веб-сервис "Путешествие дня"
Дипломный проект представляет собой автоматизацию тестирования комплексного сервиса, взаимодействующего с СУБД и API Банка.

Приложение предлагает купить тур по определённой цене с помощью двух способов:

1. Оплата по дебетовой карте
2. Оплата по кредитной карте

Приложение в собственной СУБД сохраняет информацию о том, каким способом был совершён платёж и успешно ли он был совершён.

### Документация
* [План автоматизации](https://github.com/kornilovaolga/Diploma-QA/blob/main/documents/Plan.md)
* [Отчет по итогам тестирования](https://github.com/kornilovaolga/Diploma-QA/blob/main/documents/Report.md)
* [Итоговый отчёт по автоматизации](https://github.com/kornilovaolga/Diploma-QA/blob/main/documents/Summary.md)
### Для начала работы
Необходимо загрузить на свой ПК репозиторий с проектом. Для этого либо воспользуйтесь командой git clone и вставьте ссылку https://github.com/kornilovaolga/Diploma-QA в поле URL, нажмите "Clone".

Для запуска тестов на вашем ПК должно быть установлено следующее ПО:

1. IntelliJ IDEA
2. Git
3. Docker Desktop
4. Любой браузер

### Установка и запуск
1. Запускаем контейнеры из файла docker-compose.yml командой в терминале:   
`docker-compose up -d`

2. Запускаем SUT командой в терминале:   
для MySQL:   
`java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar artifacts/aqa-shop.jar`   
для PostgreSQL:   
`java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar artifacts/aqa-shop.jar`

3. Запускаем авто-тесты командой в терминале:   
для MySQL:   
`./gradlew clean test "-Ddb.url=jdbc:mysql://localhost:3306/app"`   
для PostgreSQL:   
`./gradlew clean test "-Ddb.url=jdbc:postgresql://localhost:5432/app"`   

Приложение запускается в браузере по адресу: http://localhost:8080/

4. Генерируем отчёт по итогам тестирования с помощью Allure. Отчёт автоматически откроется в браузере с помощью команды в терминале:   
`./gradlew allureServe`   
После генерации и работы с отчётом, останавливаем работу allureServe в терминале сочетанием клавиш CTRL + C и подтверждаем действие в терминале вводом Y.
