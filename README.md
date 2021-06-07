# ebankingPortal





# Build Run Test

### Building Docker Image 


**Build Image**
```shell
$ cd <project_dir>
$ ./scripts/build-docker.sh
```

**Verify Image is built successfully**

```shell
$ docker image ls

REPOSITORY                    TAG                 IMAGE ID       CREATED          SIZE
ebankingportal-api            latest              fb7764aa37d8   12 minutes ago   377MB
gcr.io/k8s-minikube/kicbase   v0.0.15-snapshot4   06db6ca72446   5 months ago     941MB
(base) admin@Admins-MacBook-Pro ebankingPortal %
```

**Verify Image is runnable**
```shell
$ docker run -p 8080:8080 ebankingportal-api
```
Open your browser and hit http://localhost:8080/version

## Cucumber Integration
View report at your 
<Project directory>/target/cucumber-reports.html 



### References taken from
1. **Cucumber** [Cucumber-Junit-Spring Boot Integration](https://sergiomartinrubio.com/articles/cucumber-a-bdd-framework-for-java-and-spring/)