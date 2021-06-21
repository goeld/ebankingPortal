# How to Build and Run

**Summary**
* Complete maven build.
* Run as standar spring boot project
* Build docker image
* Run as kubernetes on minikube


**Prequisites**
* Java 11
* Docker engine with k8s or minikube 


## Build

### For both Ebanking/Mock Module
**maven build**
```shell
$: cd <ebanking_proejct_dir>
$: mvn clean install
```

**Build artifact checks**
For mock module only jar artifact will be available.
* target/cucumber-reports.html - Functional Test report
* target/site/jacoco/index.html - Code Coverage report
* Applcation jar file


**Build docker image**
To build docker image, if you are building and testing with minikube, kubernetes does not pick up from local docker registery. 
The solution is to point docker registery to minikube before docker image is created with below command. Also, in **note** in the pod definition file you need to mention the ***imagePullPolicy*** as ***Never***. 
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


## Running as spring boot project
```shell
$ mvn spring-boot:run -Dspring.run.arguments=--spring.profiles.active=local
# or
$ java -jar target/ -Dspring.profiles.active=local
```
Open your browser and hit http://localhost:<<port_no>>/ebanking/version


## Running on minikube
Assume you have admin access to cluster and have created the required persistent volumes. Below are the instructions to create PV using minikube
```shell
$ minikube ssh
$ mkdir -p /mnt/data/ebank-api
$ mkdir -p /mnt/data/mock-services

Exit from minikube terminal
```

**Running Kubernetes**
When running the pods using minikube it will require to start the services manually, so the application can be acessible via browser as below
```shell
$ kubectl apply -f k8s/
$ minikube service ebank-api-svc
$ minikube service mock-services-svc
```

## Verify using postman
Open postman and import all the collection and environment from <ebanking_project_dir>/postman/*.json. The APIs are added per module. Modify the URL's/ports as needed to test the api's as per need.

## Verify on browser
The url in local are secure are same when run as spring boot jars or on kubernetes cluster.

***As spring boot***
* Verify eBanking liveness/Health - https://localhost:8080/actuator/health
* Verify eBanking Up and running - https://localhost:8080/ebanking/version
* Verify eBanking Swagger - https://localhost:8080/swagger.html


***As Kubernetes cluster***
* Verify eBanking liveness/Health - http://localhost:8080/actuator/health
* Verify eBanking Up and running - http://localhost:8080/ebanking/version
* Verify eBanking Swagger - http://localhost:8080/swagger.html



### Certificates are created.
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

















