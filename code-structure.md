# Code Organisation

The design uses micro-services principle such that each service is independently build and deployable. Each service is individual spring boot project, however, this is an assignment, I have clubbed them to two repository due to time challenge. Also, the focus is to build the e-Banking API, hence I have build the solution keeping a real production in mind.

* **e-Banking portal API**
  - Spring boot project
  - /script/ - Folder contains script to do docker build 
  - /k8s/ - Contains configuration files Kubernetes, Deployment, Service, Persistent Volume, 
  - /src/main/ - Application logic
  - /src/test/ - Unit test, Integration tests, Cucumber test cases, 
  - /postman/ - Contains rest calls collection for services.  
* **mock-ebanking-services** Multi Module spring boot Project
    > Manages common dependencies for all the projects. <br> To enable build all the module using one command. <br> Contains DockerFile, Kubernetest configurations, Script file for building
  
  * **Single-Sign-on Service**
    - Receives and validate the token, that containing customer id and returns customer details, This can include roles etc.
    - Validates only 3 customers id from pre-populated java hash-map.  
    - This can be deployed and run as individual service.   
  * **Customer Service**
    - Contains logic to return Customer accounts for each customer by given currency.
    - Additional API to return all the customer accounts.
    - This can be deployed and run as individual service.
  * **Exchange Rate Service**
    - Returns the hash map of currency pair and exchange rate. 
    - **NOTE** I have logic to consume this service, but could not think of a use case, unless the requirement is to show customer
      total balance across all currencies.
    - This can be deployed and run as individual service.
  * **Transaction Service**
    - Returns the transactions for a account filtered by currency, calendar month and year
    - **Note** No Kafka integration or not even a DB integration. Just a plain Java collection.
    - This can be deployed and run as individual service.
  * **App Module**
    - Spring-boot app that having maven dependency for each of the child modules above

