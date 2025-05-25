Application PRICES:

Hexagonal Spring Boot Application for Dynamic Pricing

Spring Boot 3.1.5; 
Java 17

Overview:

A REST API that retrieves product pricing based on:
Brand ID;
Product ID;
Application date;

Built with Hexagonal Architecture for clean separation of concerns.

Quick Start
Prerequisites:
JDK 17+
Maven 3.9+

Run Locally:
  bash;
  git clone https://github.com/AndriusPazukasTestProgram/prices.git;
  cd prices;
  mvn spring-boot:run

API Documentation:
GET /api/prices;   
  Retrieve pricing for a product at a specific date.


Example Request:
   bash;
   curl "http://localhost:8080/api/prices?date=2020-06-14T15:00:00&productId=35455&brandId=1";
   Success Response (200 OK):
json
{
  "productId": 35455,
  "brandId": 1,
  "priceList": 2,
  "startDate": "2020-06-14T15:00:00",
  "endDate": "2020-06-14T18:30:00",
  "price": 25.45,
  "currency": "EUR"
}
Error Cases:
400 Bad Request: Invalid date format
404 Not Found: Price not found

Hexagonal Architecture:
  Key Components:
  Domain:   Price, PriceQuery (business models)
  Application:   GetPriceUseCase (ports), PriceService (use cases)
  Adapters:   PriceController (REST adapter), JpaPriceRepository (DB adapter)

Database Setup:
Schema:
sql
CREATE TABLE prices (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  brand_id BIGINT NOT NULL,
  product_id BIGINT NOT NULL,
  start_date TIMESTAMP NOT NULL,
  end_date TIMESTAMP NOT NULL,
  price_list INT NOT NULL,
  priority INT NOT NULL,
  price DECIMAL(10,2) NOT NULL,
  curr VARCHAR(3) NOT NULL
);

Sample Data:
    sql
    INSERT INTO prices VALUES 
(1, 1, 35455, '2020-06-14 00:00:00', '2020-12-31 23:59:59', 1, 0, 35.50, 'EUR'),
(2, 1, 35455, '2020-06-14 15:00:00', '2020-06-14 18:30:00', 2, 1, 25.45, 'EUR');

Testing:
   Test Coverage:
   Integration Tests: API endpoints (PriceControllerTest)


Contact:
  For questions or contributions:
  Email: www.andriuspazukas1981@gmail.com
