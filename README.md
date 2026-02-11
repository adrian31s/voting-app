## Voting App — szybkie środowisko lokalne (Kind + Helm)

Skrypt tworzy lokalny klaster Kubernetes (kind), instaluje Traefik, PostgreSQL, Keycloak oraz aplikację przy użyciu lokalnych chartów Helm znajdujących się w katalogu `infra/` oraz w głównym katalogu projektu.

## Wymagania (prerequisites)

- Docker
- kind
- kubectl
- Helm

kind load docker-image voting-app:latest --name one-node-cluster

## Uruchamianie (z katalogu infra)

```bash
./reate_infra.sh
```

Skrypt wykonuje kolejno wszystkie kroki instalacyjne i czeka na to, aż główne pody będą uruchomione. W zaleznosci od szybkosci internetu, może to potrwać kilka minut. (dla przyspieszenia wyłączone są probes dla k8s)

aplikacja jest dosępna na porcie localhost:81 logowanie/rejestracja do aplikacji odbywa sie przez url:

http://localhost:81/auth/realms/voting-app/protocol/openid-connect/auth?client_id=voting-app&response_type=code&scope=openid&redirect_uri=http%3A%2F%2Flocalhost%3A81%2Fvoting-service%2Fswagger-ui%2Findex.html

domyslne login haslo admina
login: admin
haslo: pass

zostaniemy przekierowani na strone swaggera gdzie mozemy uzywac aplikacji

token uwierzytelniajacy dostaniemy uzupelniajac odpowiedni username oraz password, przyklad dla admina: 
(zmien na raw przy copy-paste)

curl -X POST "http://localhost:81/auth/realms/voting-app/protocol/openid-connect/token" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "grant_type=password" \
  -d "client_id=voting-app" \
  -d "username=admin@gmail.com" \
  -d "password=pass"


## Czyszczenie

Jeśli chcesz usunąć klaster i zasoby lokalne:

```bash

# usuń klaster kind
kind delete cluster --name one-node-cluster



## Debug / Troubleshooting
- Sprawdź status podów:

```bash
kubectl get pods -n voting
kubectl describe pod <pod-name> -n voting
kubectl logs <pod-name> -n voting
```

- Jeżeli skrypt utknie w pętli "waiting for ... pod to be ready", sprawdź logi powyżej i upewnij się, że obrazy zostały poprawnie załadowane do klastra (polecenie 
`docker pull adrian31s/test-voting-app-repo:1.0.0 && kind load docker-image adrian31s/test-voting-app-repo:1.0.0 --name one-node-cluster`).

## Pliki istotne dla tego procesu

- `infra/create_infra.sh` — główny skrypt automatyzujący tworzenie i instalację
- `traefik/` — chart i pliki ingress dla Traefik
- `postgresql/` — chart PostgreSQL (w tym init scripts)
- `keycloak/` — chart Keycloak oraz `voting-app-realm.json`
- `voting/` — chart aplikacji voting


