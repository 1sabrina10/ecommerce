# E-Commerce API

## Présentation

Ce projet est une API REST développée avec **Spring Boot** dans le but de mettre en pratique le développement d'une application backend moderne ainsi que les bonnes pratiques de développement et de DevOps.

L'application permet la gestion d'une plateforme e-commerce avec une authentification sécurisée par JWT, la gestion des utilisateurs, des catégories, des produits et des commandes. Le projet est conteneurisé avec Docker, déployé sur Kubernetes (Minikube) et intègre un pipeline CI avec GitHub Actions.

---

## Fonctionnalités

- Authentification sécurisée avec JWT
- Gestion des rôles (ADMIN / USER)
- Gestion des utilisateurs
- Gestion des catégories
- Gestion des produits
- Gestion des commandes
- API REST documentée avec Swagger/OpenAPI (en environnement de développement)
- Validation des données
- Gestion centralisée des exceptions
- Tests unitaires avec JUnit et Mockito

---

## Technologies utilisées

| Technologie | Description |
|-------------|-------------|
| Java 21 | Langage principal |
| Spring Boot 3 | Framework backend |
| Spring Security | Authentification et autorisation |
| Spring Data JPA | Accès aux données |
| Hibernate | ORM |
| MySQL | Base de données |
| JWT | Authentification |
| Maven | Gestion des dépendances |
| Docker | Conteneurisation |
| Docker Hub | Registre des images Docker |
| Kubernetes (Minikube) | Orchestration des conteneurs |
| GitHub Actions | Intégration Continue (CI) |
| JUnit 5 | Tests unitaires |
| Mockito | Mocking pour les tests |
| H2 Database | Base de données utilisée pour les tests |

---

## Architecture du projet

```text
src
├── controller
├── service
├── repository
├── entity
├── dto
├── mapper
├── security
├── config
├── exception

k8s
├── app-deployment.yaml
├── app-service.yaml
├── mysql-deployment.yaml
├── mysql-service.yaml
├── configmap.yaml
└── secret.yaml
```

---

## Lancer le projet

### Cloner le dépôt

```bash
git clone https://github.com/<votre-utilisateur>/ecommerce.git
cd ecommerce
```

### Compiler le projet

```bash
mvn clean package
```

### Lancer l'application

```bash
mvn spring-boot:run
```

L'API sera accessible à l'adresse :

```
http://localhost:8080
```

---

## Docker

Construire l'image Docker :

```bash
docker build -t ecommerce-api .
```

Exécuter le conteneur :

```bash
docker run -p 8080:8080 ecommerce-api
```

---

## Déploiement Kubernetes

Déployer MySQL :

```bash
kubectl apply -f k8s/mysql-deployment.yaml
kubectl apply -f k8s/mysql-service.yaml
```

Déployer l'application :

```bash
kubectl apply -f k8s/app-deployment.yaml
kubectl apply -f k8s/app-service.yaml
```

Déployer une nouvelle version :

```bash
./deploy.sh <image:tag>
```

Exemple :

```bash
./deploy.sh 1sabrina10/ecommerce-api:ed1eb671ffd10439bf1025b7e5b8dabab1037ce9
```

---

## Pipeline CI

Le projet intègre un pipeline **GitHub Actions** qui s'exécute automatiquement à chaque **push** ou **pull request** sur la branche `main`.

Le pipeline réalise les étapes suivantes :

1. Exécution des tests unitaires.
2. Compilation du projet avec Maven.
3. Construction de l'image Docker.
4. Publication de l'image sur Docker Hub.

Le déploiement sur Kubernetes est réalisé localement sur **Minikube** via le script `deploy.sh`, permettant d'effectuer un **Rolling Update** de l'application.

---

## Architecture de déploiement

```text
                    GitHub
                       │
                       ▼
               GitHub Actions
                       │
             Tests → Build → Docker
                       │
                       ▼
                  Docker Hub
                       │
                       ▼
            Kubernetes (Minikube)
                       │
               Rolling Update
                       │
                       ▼
               Spring Boot API
                       │
                       ▼
                     MySQL
```

---

## Auteur

**Sabrina Moufok**

Étudiante en Master Génie Logiciel.
