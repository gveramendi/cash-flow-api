# cash-flow-api

1. Start up application
    mvn spring-boot:run

2. API Documentation: 
    http://localhost:8080/swagger-ui/#/

3. Operaciones:
  Crear cuenta:
    curl --location --request POST 'http://localhost:8080/accounts' \
         --header 'Content-Type: application/json' \
         --data-raw '{
              "accountNumber": "10-1200-10",
              "clientName": "Juan Perez"
           }'
        
  Depositar dinero:
    curl --location --request POST 'http://localhost:8080/transactions/1' \
         --header 'Content-Type: application/json' \
         --data-raw '{
              "amount": 100,
              "description": "first transaction",
              "type": "DEPOSIT"
           }'
           
  Retirar Dinero:
    curl --location --request POST 'http://localhost:8080/transactions/1' \
         --header 'Content-Type: application/json' \
         --data-raw '{
              "amount": 200,
              "description": "first transaction",
              "type": "WITHDRAW"
           }'
           
  Consultar saldo:
    curl --location --request GET 'http://localhost:8080/accounts/10-1200-10'
    
    
