# modelSpec
## Description
Спецификация моделей проекта **NeoLoan** реализована по спецификации **OAS openapi**. 

Покрытие моделей тестами на основе **jupiter-api** и **junit-platform**.

Статистика покрытия тестами при помощи проекта **JaCoCo**, включающего плагин для __maven__.
## Project Structure
- src/main/resources:
    > Спецификация на основе **openapi 3.1.0**
    >
    > - `models.yaml`: Схемы моделей
    > - `components.yaml`: Схемы типов данных
- src/test/java/com/igorpetrovcm/neoloan/modelSpec:
    > Тест-кейсы моделей
    >
    > - `LoanDTOsTest.java`: Тесты моделей заема