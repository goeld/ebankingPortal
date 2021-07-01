# How to Build and Run

**Summary**
* Maven build
* Build docker image
* Run as Spring boot project with profile (local or k8s)
* Run as k8s cluster on minikube
* Verify applications are up and running
  * Verify via Browser
    * Verify when running as spring boot
    * Verify when running as K8s
* Application Functional Verification
  * Customer-Id & JWT token mappings
  * Customer and Accounts mappings.
  * Verification via Postman (Recommended)



**Prequisites**
* Java 11
* Docker engine with k8s or minikube 


## Maven build

For both Ebanking/Mock Module
```shell
$: cd <proejct_dir>
$: mvn clean install
```

**Build artifact checks (Only for eBanking Project)**
* target/cucumber-reports.html - Functional Test report
* target/site/jacoco/index.html - Code Coverage report
* Applcation jar file (Also available for mock services api's)


## Build docker image

If you are building and testing with minikube, kubernetes does not pick up from local docker registry. 
The solution is to 
* Point docker registry to minikube before creating docker image using below command. 
* Also, in pod definition file you need to mention the ***imagePullPolicy*** as ***Never***. 

```shell
$ eval $(minikube docker-env)              # Optional (Required for working with Minikube)
$ ./scripts/build-docker.sh
```

**Verify Image is built successfully**

```shell
$ docker image ls
REPOSITORY                                TAG                       IMAGE ID       CREATED          SIZE
ebank-api                                 latest                    71959cb10e8e   45 seconds ago   377MB
<none>                                    <none>                    a6eb2a334a9f   13 days ago      22.6MB
$
```
**Verify Image is runnable**
```shell
$ docker run -p 8080:8080 ebank-api
```
Open your browser and hit http://localhost:8080/ebanking/version


## Run as Spring boot project with profile (local or k8s).
**NOTE** For running on local ensure to have the profile set to local.
```shell
$ mvn spring-boot:run -Dspring.run.arguments=--spring.profiles.active=local
# or
$ java -jar target/ -Dspring.profiles.active=local
```
Open your browser and hit http://localhost:<<port_no>>/ebanking/version


## Run as k8s cluster on minikube

Assume you have admin access to cluster and have created the required persistent volumes. Below are the instructions to create PV using minikube
```shell
$ minikube ssh
$ mkdir -p /mnt/data/ebank-api
$ mkdir -p /mnt/data/mock-services

Exit from minikube terminal
```

**Running Kubernetes Cluster and exposing service**

When running the pods using minikube it will require to start the services manually, so the application can be acessible via browser as below
```shell
$ cd <ebanking-api>
$ kubectl apply -f k8s/
$ minikube service ebank-api-svc

$ cd <mock-service>
$ kubectl apply -f k8s/
$ minikube service mock-services-svc
```

## Verify applications are up and running

## Verify via Browser
The url in local are secure are same when run as spring boot jars or on kubernetes cluster.

### Verify when running as spring boot
* **eBanking-API service**
    * Verify eBanking (liveness-probe)/Health - https://localhost:8080/actuator/health
    * Verify eBanking (readiness-probe)Up and running - https://localhost:8080/ebanking/version
    * Verify eBanking Swagger - https://localhost:8080/swagger.html
* **Mock Service APi**
    * Verify mock-service (liveness-probe)/Health - http://localhost:8091/actuator/health
    * Verify mock-service (readiness-probe)/Up and running - http://localhost:8091/mock-app/version 
    * Verify mock-service Swagger - http://localhost:8091/swagger-ui/index.html


### Verify when running as k8s
  Same as above springboot, but use http instead of https.

## Application Functional Verification

Endpoint for testing the transaction <br>
Post https://localhost:8080/ebanking/customer/transactions



### Customer-Id & JWT token mappings
The token are generated with commands mentioned and are the only tokens accepted by SSO. Others will throw an exception

|Customer-Id|EmailId| Token |
|:-----|:-----|:--------|
|P-0123456789|employee1@sp.com|eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyAiY3VzdG9tZXJJZCI6ICJQLTAxMjM0NTY3ODkiICwgImVtYWlsIjoiZW1wbG95ZWUxQHNwLmNvbSJ9.XbgM2NJiTKhuSgzL6MUNU1uarkUoH0kIASs5_JLbSGg|
|P-0123456790|employee2@sp.com|eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyAiY3VzdG9tZXJJZCI6ICJQLTAxMjM0NTY3ODkiICwgImVtYWlsIjoiZW1wbG95ZWUyQHNwLmNvbSJ9.3mJ0aHF21El_bsfBvwIccxvOcg6_qa_KvlJmWC56XuA|
|P-0123456791|employee3@sp.com|eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyAiY3VzdG9tZXJJZCI6ICJQLTAxMjM0NTY3ODkiICwgImVtYWlsIjoiZW1wbG95ZWUzQHNwLmNvbSJ9.nLxacDiNrEOcvZ-qlGgJ1ugEGNBxTck2AwFBIwZBsS0|


### Customer and Accounts mappings.
|Customer-Id|Account Id| Currency|
|:-----|:-----|:--------|
|P-0123456789|CH93-0000-0000-93454|INR|
|P-0123456789|CH93-0000-0000-93451|SGD|
|P-0123456789|CH93-0000-0000-93452|USD|
|P-0123456790|CH93-0000-0000-93459|USD|
|P-0123456790|CH93-0000-0000-93458|SGD|
|P-0123456791|CH93-0000-0000-93457|USD|

Customer with id * **P-0123456789** has most of the data, specially for year-2020, Month-10   
While testing the API with different tokens, remember to change the "Customer ID" in the request body.

### Verification via Postman (Recommended)
Import postman collections. Open postman and import all the collection and environment from <ebanking_project_dir>/postman/*.json. The APIs are added per module. Modify the URL's/ports as needed to test the api's as per need.

The set up has been done for each API. This can also be done using swagger.
Import the collection from postman folder. These should be runnable without any modification. For valid tokens refer below,
For Data, refer to data after token section




## Other References
### Generating tokens reference
Below are the commands to generate the token. Change the payload if you need to have different customers. 
```shell
jwt_header=$(echo -n '{"alg":"HS256","typ":"JWT"}' | base64 | sed s/\+/-/g | sed 's/\//_/g' | sed -E s/=+$//)
payload=$(echo -n '{ "customerId": "P-0123456789" , "email":"employee3@sp.com"}' | base64 | sed s/\+/-/g |sed 's/\//_/g' |  sed -E s/=+$//)
hmac_signature=$(echo -n "${jwt_header}.${payload}" | openssl dgst -sha256 -hmac "${key}" -binary | openssl base64 -e -A | sed s/\+/-/g | sed 's/\//_/g' | sed -E s/=+$//)
jwt="${jwt_header}.${payload}.${hmac_signature}"
echo $jwt
```


### Certificates creation command reference
Refer to command to create certificates if not done
```shell
(base) admin@Admins-MacBook-Pro certs % keytool -genkeypair -alias tomcat -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore keystore.p12 -validity 365
Enter keystore password:  
Re-enter new password: 
What is your first and last name?
  [Unknown]:  deepak
What is the name of your organizational unit?
  [Unknown]:  sp
What is the name of your organization?
  [Unknown]:  sp
What is the name of your City or Locality?
  [Unknown]:  sg
What is the name of your State or Province?
  [Unknown]:  sg
What is the two-letter country code for this unit?
  [Unknown]:  sg
Is CN=deepak, OU=sp, O=sp, L=sg, ST=sg, C=sg correct?
  [no]:  y

```

















