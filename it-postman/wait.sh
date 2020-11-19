#!/bin/sh
url="http://localhost:8762/auth/login"
echo "Esperando a los contenedores.";
sleep 60;
until curl -sf --header "Content-Type: application/json" --request POST --data '{"username":"admin","password":"admin"}' "$url"; do echo -n "."; sleep 1; done
