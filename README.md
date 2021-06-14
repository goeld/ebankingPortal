# ebankingPortal


## Assumptions

### Persistent Volumes are created 
Assume you have admin access to cluster and have created the required persistent volumes. Below are the instructions to create PV using minikube
```shell
$ minikube ssh
$ mkdir -p /mnt/data/ebank-api
$ mkdir -p /mnt/data/mock-services
```

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


### Minikube services are executed manually
When running the pods using minikube it will require to start the services manually, so the application can be acessible via browser as below

```shell
$ minikube service <name of the service>
$ minikube service ebank-api-svc
$ minikube service mock-services-svc
```


# Build Run Test

### Building Docker Image 

Set Docker registry to local minikube
```shell
eval $(minikube docker-env)
```

**Build Image**
```shell
$ cd <project_dir>
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
Open your browser and hit http://localhost:8080/version

## Cucumber Integration
View report at your 
<Project directory>/target/cucumber-reports.html 



### References taken from
1. **Cucumber** [Cucumber-Junit-Spring Boot Integration](https://sergiomartinrubio.com/articles/cucumber-a-bdd-framework-for-java-and-spring/)
2. **Running Local Docker Images in Kubernetes**  [Local Docker + Minikube](https://dzone.com/articles/running-local-docker-images-in-kubernetes-1)
3. **C4 Modelling** [C4 Diagram explained](https://youtu.be/x2-rSnhpw0g), [C4 Official website](https://c4model.com)

# Deliverables Checklist

|Deliverable|Type|Item|Status|Remarks|
|:-----|:-----|:--------|:---------------|:-----|
|Code|Docker  | Build 2 Dokcer images | Completed| |
|Code|K8s | Except Services - <br />Pod, deployment, PV, PVC | Completed| K8s Services for interpod communication |
|Code|Git Repository| 2 Git Repos |Completed | To simulate Microservice, one of the repo is mock repo with  <br /> multi module springboot project|
|Code|Link Repo with pipeline | Git + Circle CI |Completed | Able to see CI on each commit|
|Code|Working Pipeline | Circle CI |Completed | emails for failed builds|
|Documentation|Architecture Diagram | High level arch diagram| Completed| Refer to eBanking Portal API and documents folder.|
|Code|Logging |  | Completed| Logging to files and on Persistent volumes on Pods|
|Code|Monitoring | |Completed |Pod monitoring has been added for liveness and readiness |
|Code|Solution evolution  | |Completed | Refer to branches in the repo or git logs.|
|Code|Swagger/Open API Integration| |In Progress|Pending business logic |
|Documentation|C4 Diagram | Context, Container <br />and Component diagram| In Progress| Pending Component diagram|
|Code|Springboot Webapps | | In Progess| Pending business logic. <br />Application is able to access outside K8s <br />via browser etc.  |
|Code|Mock Application | | In Progress | To mock the data for customer, account, transactions and <br /> exchange rate and authentication|
|Code|Security | Authentication & Authorisation |In Progress | |
|Code|Testing | Unit Testing | In Progress|Pending business logic |
|Code|Testing | Integration Testing| In Progress| Pending business logic|
|Code|Testing | Functional Testing| In Progress| |
|Documentation|Read me | | In progress| |
|Documentation|Solution evolution  | |In Progress | |
|Documentation|Code organisation | |In Progress| |
|Documentation|Relevant Decisions, Assumptions made | |In Progress | |
|Code|Kafka Integration| |Not started|Pending business logic |
|Code|Security | Data Security - In Transit | Not started | |
|Code|Security | Data Security - At rest| Not started| |
|Code|K8s | Services for inter pod comm | Not started| |
|Code|Testing | API contract test| Not started| s |




<br >
<br >
s

