

* Code Organisation [Refer here](code-structure.md)
* For build and run instructions [Refer here](build-and-run.md)
* Git Repositry links
  The repositories are private, will provide the access as per requested.
  * Ebanking-API [Refer here](https://github.com/goeld/ebankingPortal)
  * mock-ebanking-services [Refer here](https://github.com/goeld/mock-ebanking-services)
  *   
* Architecture Diagram [Refer here](https://github.com/goeld/ebankingPortal/blob/main/documents/architecture_diagram.docx)
* C4 System Context Diagram [Refer here](https://github.com/goeld/ebankingPortal/blob/main/documents/System%20Context%20Diagram.docx)
* C4 Container Diagram [Refer here](https://github.com/goeld/ebankingPortal/blob/main/documents/Container%20Diagram.docx)
* **Sample artefacts**  
  * For Test coverage [Refer here](sample-artefacts/test_coverage.jpg)
  * For Functional testing scope as per last build [Referhere](sample-artefacts/cucumber-reports.html)
  * Circle CI report for ebankiong-service [Refer here](sample-artefacts/pipelines-ebanking-portal.pdf)  
  * Circle CI report for mock-services [Refer here](sample-artefacts/pipeline-mock-ebanking-services.pdf)
  * Swagger UI : list End points for mock service [Refer here](sample-artefacts/mock-service-swagger.pdf)   
  * Swagger UI : list End points for ebanking-portal service [Refer here](sample-artefacts/mock-ebanking-services-commit-history.pdf)   
* How the requirements are implemented
  At high level, the step were below for each of the services. 
  * **Created POC**
    * Create Git repository
    * Create spring boot project
    * Build & run docker image
    * Create kubernetes config and run on minikube
  * Architecture document - System design process
  * C4 Context diagram
  * Develop individual mock service, test via post man
  * Develop corresponding API consumption of mock servies from ebanking portal api.
  * For individual feature developed refer to commit history in the attached pdf file <br> 
    [Ebanking Commit History](sample-artefacts/ebankingPortal-solution-commit-history.pdf), 
    [Mock Service Commit History](sample-artefacts/mock-ebanking-services-commit-history.pdf)
    Each branch is named as feature_<sequence>/functionality_name [here]
* **Asumptions & decisions**
  * Focus is on ebanking-Portal application.
  * Focus is on how to craft softwares - from ideation to delivery.  
  * No testing will be implemented for any of the mock service.
  * *Kafka* 
    <br>Not familiar with kafka, so I was planning to do in last if I get time. But I will not be able to do it now.
  * *Authentication & Authorisation*<br> 
    Given I have more time, I could have enhanced the service with OAuth provider like Okta, Firebase along with JWT
  * *Data Security at rest*<br>
    Given I have more time, data can be encrypted, along with logic to decrypt by the consumer.
  * *Kubernetes configuration*<br> are not tested in openshift, if required, this can be done with additonal devops works with minimal changes to k8s configs done.
  * Solution is build and tested on developer machine using kubernetes.
  * API Contract: This can be enforced with tools like Pact and or open api.
  

# Deliverables Checklist

|Deliverable|Type|Link|Status|Remarks|
|:-----|:-----|:--------|:---------------|:-----|
|Code|**Docker**<br>Build 2 Dokcer images| [ebank-api-docker](https://github.com/goeld/ebankingPortal/blob/main/Dockerfile) |Completed| |
|Code|K8s | Except Services - <br />Pod, deployment, PV, PVC | Completed| K8s Services for interpod communication |
|Code|Git Repository| 2 Git Repos |Completed | To simulate Microservice, one of the repo is mock repo with  <br /> multi module springboot project|
|Code|Link Repo with pipeline | Git + Circle CI |Completed | Able to see CI on each commit|
|Code|Logging |  | Completed| Logging to files and on Persistent volumes on Pods|
|Code|Monitoring | |Completed |Pod monitoring has been added for liveness and readiness. These can be further read from APM applications like Filebeat, Datadog |
|Code|Solution evolution | How solution is eveolved |Completed | Refer to branches in the repo or git logs.|
|Code|Swagger/Open API Integration| |Completed| |
|Code|Springboot Webapps | | Completed| Business logic has been completely implemented with exception handling|
|Code|Mock Application | Mock SSO, Account, Exchange Rate, Transactions | Completed| Refer to assumption for further details|
|Code|Security | Authentication & Authorisation |Completed | Business logic to validate authentication is done|
|Code|Testing | Unit Testing | In Progress| Few test cases are pending|
|Code|Testing | Integration Testing| In Progress| Few test cases are pending|
|Code|Testing | Functional Testing| In Progress| Few test cases are pending|
|Code|Kafka Integration| |Not started| |
|Code|Security | Data Security - In Transit | Completed | Implemented TLS for both the apps, but minikube does not allow to have https |
|Code|Security | Data Security - At rest| Not started|  |
|Code|K8s | Services for inter pod comm | Not started| |
|Code|Testing | API contract test| Not started|  |
|Documentation|Architecture Diagram | High level arch diagram| Completed| Refer to eBanking Portal API and documents folder.|
|Documentation|C4 Diagram | Context, Container <br />and Component diagram| Completed| Pending Component diagram|
|Documentation|Read me | | Completed| |
|Documentation|Solution evolution  | |Completed | |
|Documentation|Code organisation | |Completed| |
|Documentation|Relevant Decisions, Assumptions made | |Completed | |




<br >
<br >

To do

* Diagram of Git progress either from IDE or browser
* Code
  * Add Application log statements
  * Complete unit tests coverage
  * Add Generic error handling when downstream system has error
  * Postman collection  
* Documentation 
  * How to build application
  * How to run application in local
  * How to run application kubernetes
  * Pre-requiste to run document
  * How the code is structured  
    


