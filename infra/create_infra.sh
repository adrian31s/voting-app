#!/bin/sh


# kubectl
if ! command -v kubectl >/dev/null 2>&1; then
  echo "Kubectl is not installed."
  echo "Install guide: https://kubernetes.io/docs/tasks/tools/"
  exit 1
fi

# helm
if ! command -v helm >/dev/null 2>&1; then
  echo "Helm is not installed."
  echo "Install guide: https://helm.sh/docs/intro/install/"
  exit 1
fi

# kind
if ! command -v kind >/dev/null 2>&1; then
  echo "Kind is not installed."
  echo "Install guide: https://kind.sigs.k8s.io/docs/user/quick-start/"
  exit 1
fi

# docker
if ! command -v docker >/dev/null 2>&1; then
  echo "Docker is not installed."
  echo "Install guide: https://docs.docker.com/get-docker/"
  exit 1
fi


kind delete cluster --name one-node-cluster
cat <<EOF | kind create cluster --name one-node-cluster --config -
kind: Cluster
apiVersion: kind.x-k8s.io/v1alpha4
nodes:
- role: control-plane
  extraPortMappings:
  - containerPort: 30200
    hostPort: 81
    protocol: TCP
EOF
while [ "$(kubectl get nodes | awk 'NR>1{print $2}')" != "Ready" ]; do
    echo "waiting for nodes to be ready..."
    sleep 1
done


kubectl delete namespace voting
kubectl create namespace voting

# install traefik
helm uninstall traefik -n voting
helm install traefik ./traefik -n voting
while [ "$(kubectl get po -l app.kubernetes.io/instance=traefik-voting -n voting | awk 'NR>1 {print $2}')" != "1/1" ]; do
    echo "waiting for traefik pod to be ready..."
    sleep 1
done    

### install postgresql
kubectl delete -f postgresql/templates/init-db-scripts-secret.yaml -n voting
kubectl apply -f postgresql/templates/init-db-scripts-secret.yaml -n voting
helm uninstall postgresql -n voting
helm install postgresql ./postgresql -n voting
while [ "$(kubectl get po -l app.kubernetes.io/instance=postgresql -n voting | awk 'NR>1 {print $2}')" != "1/1" ]; do
    echo "waiting for postgresql pod to be ready..."
    sleep 1
done


## install keycloak
kubectl delete configmap keycloak-realm-json -n voting
kubectl create configmap keycloak-realm-json \
  --from-file=voting-app-realm.json=./keycloak/voting-app-realm.json \
  -n voting

helm uninstall keycloak -n voting
helm install keycloak ./keycloak -n voting 
while [ "$(kubectl get po -l app.kubernetes.io/instance=keycloak -n voting | awk 'NR>1 {print $2}')" != "1/1" ]; do
    echo "waiting for keycloak pod to be ready..."
    sleep 1
done    

kubectl apply -f traefik/keycloak-traefik-ingress.yaml


# install voting service
helm uninstall voting -n voting
helm install voting ./voting -n voting
while [ "$(kubectl get po -l app.kubernetes.io/instance=voting -n voting | awk 'NR>1 {print $2}')" != "1/1" ]; do
    echo "waiting for voting pod to be ready..."
    sleep 1
done    

kubectl apply -f traefik/voting-traefik-ingress.yaml



#http://localhost:81/auth/realms/voting-app/protocol/openid-connect/auth?client_id=voting-app&response_type=code&scope=openid&redirect_uri=http%3A%2F%2Flocalhost%3A81%2Fvoting-service%2Fswagger-ui%2Findex.html
