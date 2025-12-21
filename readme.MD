# YClone
🇬🇧
---
Yclone, is a YouTube API clone attempt for study purposes
for my college subject (Corporative Systems). In this API
I want to explore `Java Spring boot`, and some popular `IaaS`, 
increasing my pool of knowledge.
I'll want to update this repository often, so with your interest
stay on!
---

## How to Run
After cloning the repository, create a `.env` file for the `docker-compose.yml` with the following environment variables:

```env
# PostgreSQL Config
POSTGRES_DB=
POSTGRES_USER=
POSTGRES_PASSWORD=
POSTGRES_PORT=

# MinIO Config
MINIO_URL=
MINIO_ACCESS_KEY=
MINIO_SECRET_KEY=
MINIO_BUCKET=
```
Once configured, simply run the following commands:
```bash
docker compose build
```
---
```bash
docker compose up -d
```
---
## Useful Links
### **Swagger UI**
```url
http://localhost:8080/swagger-ui/index.html#/
```
### **MinIo Admin**
```url
http://localhost:9001/
```

🇧🇷
---
Yclone é uma tentativa de clonar a API do YouTube para fins de estudo
para a minha disciplina na faculdade (Sistemas Corporativos). Nesta API,
pretendo explorar o `Java Spring Boot` e alguns `IaaS` populares,
aumentando assim meu conhecimento.

Pretendo atualizar este repositório frequentemente, então, se você tiver interesse,
fique ligado!

## Para rodar
Após clonar o repositório
Crie uma `.env` para o `docker-compose.yml`, com as seguintes 
variáveis de ambiente:
```.env
# PostgreSQL Config
POSTGRES_DB=
POSTGRES_USER=
POSTGRES_PASSWORD=
POSTGRES_PORT=

# MinIO Config
MINIO_URL=
MINIO_ACCESS_KEY=
MINIO_SECRET_KEY=
MINIO_BUCKET=
```
---
Após isso apenas rode os seguintes comandos:
```bash
docker compose build
```
---
```bash
docker compose up -d
```
---
## Links úteis
### **Swagger UI**
```url
http://localhost:8080/swagger-ui/index.html#/
```
### **MinIo Admin**
```url
http://localhost:9001/
```