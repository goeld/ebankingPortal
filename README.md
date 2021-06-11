# ebankingPortal


## Assumptions
When running the code with minikube it will require to start the services manually so the application can be acessible via browser as below

```shell
$ minikube service <name of the service>
$ minikube service ebank-api-svc
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
|:-----|:-----:|:--------|:---------------|:-----|
|Code|Docker  | Build Dokcer image | In progress| |
|Code|K8s | Except Services | In Progress| |
|Code|K8s | Services for Microservice comm | Not started| |
|Code|Swagger/Open API Integration| |In Progress| |
|Code|Logging | | In progress| |
|Code|Monitoring | |In progress | |
|Code|Springboot Webapp | | In progress| |
|Code|Git Repository | |In progress | |
|Code|Solution evolution  | |In Progress | Refer to branches in the repo. They almost (not exactly) in the sequence. |
|Code|Kafka Integration| |Not started| |
|Code|Mock Application | | Not started | To mock the data for customer, accoount <b> exchange rate and SSO|
|Code|Security | Authentication & Authorisation |Not Started | |
|Code|Security | Data Security - In Transit | Not started | |
|Code|Security | Data Security - At rest| Not started| |
|Code|Testing | Unit Testing | Not started| |
|Code|Testing | Integration Testing| Not started| |
|Code|Testing | Functional Testing| Not started| |
|Code|Testing | API contract test| Not started| |
|Code|Link Repo with pipeline | |Not started | |
|Code|Working Pipeline | |Not started | |
|Documentation|C4 Diagram | | In progress| Refer to eBanking Portal API and documents folder.|
|Documentation|Architecture Diagram | | In progress| Refer to eBanking Portal API and documents folder.|
|Documentation|Read me | | In progress| |
|Documentation|Solution evolution  | |Not started | |
|Documentation|Code organisation | | | |
|Documentation|Relevant Decisions, Assumptions made | | | |
|Documentation|Swagger Documentation | | Not started| |

