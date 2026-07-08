#!/bin/bash

IMAGE=$1

echo "Déploiement de l'image : $IMAGE"

kubectl set image deployment/ecommerce \
ecommerce=$IMAGE

kubectl rollout status deployment ecommerce