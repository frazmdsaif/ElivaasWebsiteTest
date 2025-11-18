 # ElivaasWebsiteTest

This repository contains automated test cases and supporting files for validating core functionalities of the Elivaas website. It focuses on booking flows, inventory management, OTA booking validations, and regression scenarios across all major user journeys.

## Features Covered
- Property search and listing validations  
- Booking flow (direct booking + OTA booking)  
- Inventory and unit-inventory sync verification  
- Duplicate booking prevention tests  
- Date-change and cancellation impact checks  
- UI & functional regression scenarios  

## Tech Stack
- Java  
- Selenium WebDriver  
- TestNG  
- Maven  
- Allure Reports  

## How to Run Tests
1. Clone the repository  
2. Configure environment variables (UAT/Stage URLs, credentials)  
3. Run using:
   ```
   mvn clean test
   ```
4. Generate report:
   ```
   allure serve target/allure-results
   ```

## Folder Structure
- `/src/test/java` – Test cases  
- `/src/main/java` – Utilities & drivers  
- `/resources` – Config files & test data  
- `/screenshots` – Failure screenshots  

## Author
**Md Saif Fraz**  
QA Engineer at Elivaas
