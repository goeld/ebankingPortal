# ebankingPortal





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