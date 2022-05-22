#!/bin/bash

sudo apt-get update

sudo apt-get install \
    ca-certificates \
    curl \
    gnupg \
    lsb-release
    
sudo apt-get update

function pause(){
   read -p "$*"
}

pause 'Please log into docker with your credentials and click Enter to continue...'


sudo apt-get install docker

curl -LO https://storage.googleapis.com/minikube/releases/latest/minikube-linux-amd64
sudo install minikube-linux-amd64 /usr/local/bin/minikube


docker run -d -p 5000:5000 --restart=always --name registry registry:2

docker pull selenium/hub:4.1.4-20220427
docker pull selenium/node-chrome:4.1.4-20220427
docker pull selenium/node-edge:4.1.4-20220427
docker pull selenium/node-firefox:4.1.4-20220427

docker network create grid
docker run -d -p 4442-4444:4442-4444 --net grid --name selenium-hub selenium/hub:4.1.4-20220427
docker run -d --net grid -e SE_EVENT_BUS_HOST=selenium-hub \
    --shm-size="2g" \
    -e SE_EVENT_BUS_PUBLISH_PORT=4442 \
    -e SE_EVENT_BUS_SUBSCRIBE_PORT=4443 \
    selenium/node-chrome:4.1.4-20220427
docker run -d --net grid -e SE_EVENT_BUS_HOST=selenium-hub \
    --shm-size="2g" \
    -e SE_EVENT_BUS_PUBLISH_PORT=4442 \
    -e SE_EVENT_BUS_SUBSCRIBE_PORT=4443 \
    selenium/node-edge:4.1.4-20220427
docker run -d --net grid -e SE_EVENT_BUS_HOST=selenium-hub \
    --shm-size="2g" \
    -e SE_EVENT_BUS_PUBLISH_PORT=4442 \
    -e SE_EVENT_BUS_SUBSCRIBE_PORT=4443 \
    selenium/node-firefox:4.1.4-20220427


cd springboot-automation-selenium


mvn clean install test

./mvnw package && java -jar target/gs-spring-boot-docker-0.1.0.jar

docker build -t springio/gs-spring-boot-docker .

cd ..


docker network rm grid
docker stop selenium-hub
docker rm selenium-hub


minikube start --driver=docker --cpus 4 --memory 4096

kubectl create deployment balanced --image=k8s.gcr.io/echoserver:1.4  
kubectl expose deployment balanced --type=LoadBalancer --port=8081

kubectl run selenium-hub --image selenium/hub:3.10.0 --port 4444 --containerPort: 4444
kubectl run selenium-node-chrome --image selenium/node-chrome:3.10.0 --env="HUB_PORT_4444_TCP_ADDR=selenium-hub" --env="HUB_PORT_4444_TCP_PORT=4444" --container-port = 
kubectl run selenium-node-firefox --image selenium/node-firefox:3.10.0 --env="HUB_PORT_4444_TCP_ADDR=selenium-hub" --env="HUB_PORT_4444_TCP_PORT=4444"
kubectl run selenium-node-edge --image selenium/node-edge:3.10.0 --env="HUB_PORT_4444_TCP_ADDR=selenium-hub" --env="HUB_PORT_4444_TCP_PORT=4444"
kubectl scale deployment selenium-node-chrome --replicas=4 deployment "selenium-node-chrome" scaled
kubectl scale deployment selenium-node-firefox --replicas=4 deployment "selenium-node-firefox" scaled
kubectl scale deployment selenium-node-edge --replicas=4 deployment "selenium-node-firefox" scaled
kubectl expose deployment selenium-hub --type=NodePort service "selenium-hub" exposed

export PODNAME=`kubectl get pods --selector="app=selenium-hub" --output=template --template="{{with index .items 0}}{{.metadata.name}}{{end}}"`
kubectl port-forward $PODNAME 4444:4444


#minikube tunnel to get a routable ip
#kubectl get services balanced get the routable ip
echo $(kubectl get pods)
echo $(kubectl get services | grep selenium-hub)
echo $(minikube service selenium-hub --url)


cd springboot-automation-selenium 

docker run -p 8080:8080 springio/gs-spring-boot-dock ./mvnw test

cd ..


docker stop springio/gs-spring-boot-dock
docker rm springio/gs-spring-boot-dock
kubectl delete deployment selenium/hub:4.1.4-20220427
kubectl delete deployment selenium/node-chrome:4.1.4-20220427
kubectl delete deployment selenium/node-firefox:4.1.4-20220427
kubectl delete deployment selenium/node-edge:4.1.4-20220427
kubectl delete svc selenium-hub
docker container stop registry
docker container stop registry && docker container rm -v registry




#Jenkins Setup For Future Development
#Logging of output for future development

docker pull jenkins/jenkins:lts-jdk11
docker pull jenkins/jenkins:jdk11

docker network create jenkins

touch Dockerfile
echo "FROM jenkins/jenkins:jdk11" >> Dockerfile
echo "RUN apt-get update && apt-get install -y sudo && rm -rf /var/lib/apt/lists/*" >> Dockerfile
echo "RUN echo \"jenkins ALL=NOPASSWD: ALL\" >> /etc/sudoers" >> Dockerfile
echo "USER jenkins" >> Dockerfile
echo "COPY plugins.txt /usr/share/jenkins/plugins.txt" >> Dockerfile
echo "RUN /usr/local/bin/plugins.sh /usr/share/jenkins/plugins.txt" >> Dockerfile

docker build -t newjenkins .

docker run -d -v /var/run/docker.sock:/var/run/docker.sock -v $(which docker):/usr/bin/docker -p 8080:8080 newjenkins

echo $(docker logs newjenkins) 

