# cash-flow-api

1. Start up application:
```sh
    mvn spring-boot:run
```

2. API Documentation: 
    - http://localhost:8080/swagger-ui/#/

3. Operaciones:
- Crear cuenta:
  ```sh
    curl --location --request POST 'http://localhost:8080/accounts' \
         --header 'Content-Type: application/json' \
         --data-raw '{
              "accountNumber": "10-1200-10",
              "clientName": "Juan Perez"
           }'
   ```
        
- Depositar dinero:
  ```sh
    curl --location --request POST 'http://localhost:8080/transactions/10-1200-10' \
         --header 'Content-Type: application/json' \
         --data-raw '{
              "amount": 100,
              "description": "first transaction",
              "type": "DEPOSIT"
           }'
   ```           
           
- Retirar Dinero:
  ```sh
    curl --location --request POST 'http://localhost:8080/transactions/10-1200-10' \
         --header 'Content-Type: application/json' \
         --data-raw '{
              "amount": 200,
              "description": "first transaction",
              "type": "WITHDRAW"
           }'
  ```
           
- Consultar saldo:
  ```sh
    curl --location --request GET 'http://localhost:8080/accounts/10-1200-10'
  ```
  
- Historico Transacciones:
  ```sh
    curl --location --request GET 'http://localhost:8080/transactions/10-1200-10'
  ```
    
4. Notas
- Base de datos: La base de datos es H2 (base de datos en memoria)
- Test Cases: Los test cases que se implementaron estan en las clases:
    - AccountServiceTest
    - TransactionServiceTest
