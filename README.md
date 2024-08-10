# Kubernetes example with Grpc Protobuf

I have the two application setup as client and server
- Greeter Client for Rest API and Protobuf client for sending the Protobuf request
- Greeter Client for accepting the request and sending the response

## Deployment

### Pods

I have two kubernetes pods 

- First pod has the Greeter Client
- Second pod for Greeter Server

### Services

Two services

- First service has ports 8080 
- Second service has port 9090 open for handling protobuf communication

### Local setup

If you want to build locally update the application.yaml in greeter-service to the following
```      address: "static://localhost:9090"```

## Commands

Build the Project
```
./gradlew  build  
cd greeter-client
docker build -t greeter-client:1.0 .  

cd ../greeter-server
docker build -t greeter-server:1.0 .  
```
Deploy in Kubernetes , I am using Minikube 
```
minikube start --container-runtime=containerd
minikube image load greeter-client:1.0
minikube image load greeter-server:1.0
minikube tunnel ( Run in a new window and keep it open)
```
Kubernetes deployment
```
cd ../deployment

kubectl apply -f greeter-server-deployment.yaml
kubectl apply -f greeter-client-deployment.yaml

```

### Check every thing is up

```
kubectl get pods
kubectl get services 
```

### Test

Protobuf test
```
curl http://localhost:8080/hello?name=Biker
curl http://localhost:8080/hello?name=MountainBiker
```
![k8s-server-log](https://github.com/user-attachments/assets/f192a49a-f3db-4def-aef6-dc70141ef1b5)

Clean up 
```
kubectl delete service greeter-client
kubectl delete service  greeter-server
kubectl delete deployment greeter-client-deployment
kubectl delete deployment greeter-server-deployment
```
