### PSQL CREDENTIALS ###
POSTGRES_USER=postgres
POSTGRES_PASSWORD=Pass2021!
POSTGRES_DB=postgres

### SQL SCRIPTS ###
SQL_SCRIPT_FOLDER=src/main/resources/db/migration
CONTAINER_SQL_SCRIPT_FOLDER=/var/lib/postgresql/dump

### PSQL PERSIST VOLUME ###
CONTAINER_DATA_VOLUME=/var/lib/postgresql/data
DATA_FOLDER_VOLUME=docker/psql/data

### DB PORTS ###
DB_OUTGOING_PORT=6432
DB_INCOMING_PORT=5432

### API PORTS ###
API_OUTGOING_PORT=8080
API_INCOMING_PORT=8080

### DOCKER DB ###
DB_CONTAINER_NAME=macadamia-nut-db
DB_DOCKER_IMAGE=postgres:13-alpine

### DOCKER API ###
API_CONTAINER_NAME=macadamia-nut-api

help:
	@echo "DATABASE COMMANDS"
	@echo "db-run        	- Run database container for local development with name cidh-postgres."
	@echo "db-id			- Get the container ID."
	@echo "db-start      	- Start the database container."
	@echo "db-stop       	- Stop the database container (data is not removed)."
	@echo "db-restart		- Restart the database container (data is not removed)."
	@echo "db-rm     		- Remove the database container."
	@echo "db-refresh    	- Stop, remove and rerun the database container."
	@echo "db-bash       	- Run a bash terminal in the database container."
	@echo "db-psql       	- Run a postgresql shell against a running container."
	@echo "db-init       	- Create database from script files in src/main/resources/db/migration."
	@echo ""
	@echo "API COMMANDS"
	@echo "api-build        - Build spring boot api container."
	@echo "api-run        	- Run spring boot api container."
	@echo "api-id			- Get the container ID."
	@echo "api-start      	- Start spring boot api container."
	@echo "api-stop       	- Stop spring boot api container."
	@echo "api-rm     		- Remove the database container."


db-run:
	docker run --name="$(DB_CONTAINER_NAME)" \
	-e POSTGRES_USER=$(POSTGRES_USER) \
	-e POSTGRES_PASSWORD=$(POSTGRES_PASSWORD) \
	-e POSTGRES_DB=$(POSTGRES_DB) \
	-v "$(shell pwd)/$(SQL_SCRIPT_FOLDER):$(CONTAINER_SQL_SCRIPT_FOLDER)" \
	-v "$(shell pwd)/$(DATA_FOLDER_VOLUME):$(CONTAINER_DATA_VOLUME)" \
	-p $(DB_OUTGOING_PORT):$(DB_INCOMING_PORT) \
	-d \
	$(DB_DOCKER_IMAGE)

db-id:
	docker ps -aqf name=$(DB_CONTAINER_NAME)

db-stop:
	docker stop $$(docker ps -aqf name=$(DB_CONTAINER_NAME))

db-start:
	docker start $$(docker ps -aqf name=$(DB_CONTAINER_NAME))

db-restart:
	make db-stop
	make db-start

db-rm:
	make db-stop
	docker rm $$(docker ps -aqf name=$(DB_CONTAINER_NAME))
	$(shell rm -rf $(shell pwd)/$(DATA_FOLDER_VOLUME)/*)

db-refresh:
	make db-rm
	make db-run

db-bash:
	docker exec -it $$(docker ps -aqf name=$(DB_CONTAINER_NAME)) bash

db-psql:
	docker exec -it $$(docker ps -aqf name=$(DB_CONTAINER_NAME)) psql -U $(POSTGRES_USER) -d $(POSTGRES_DB)

db-init:
	@for f in $(shell ls $(SQL_SCRIPT_FOLDER)); do \
		docker exec -it $$(docker ps -aqf name=$(DB_CONTAINER_NAME)) \
		psql -U $(POSTGRES_USER) -h localhost -d $(POSTGRES_DB) -f $(shell pwd)/$(SQL_SCRIPT_FOLDER)/$${f}; done

api-build:
	docker build -t $(API_CONTAINER_NAME):v1.0 .

api-run:
	docker run -d \
	--name $(API_CONTAINER_NAME) \
	-p $(API_OUTGOING_PORT):$(API_INCOMING_PORT) \
	$(API_CONTAINER_NAME)

api-id:
	docker ps -aqf name=$(API_CONTAINER_NAME)

api-start:
	docker start $$(docker ps -aqf name=$(API_CONTAINER_NAME))

api-stop:
	docker stop $$(docker ps -aqf name=$(API_CONTAINER_NAME))

api-rm:
	make db-stop
	docker rm $$(docker ps -aqf name=$(API_CONTAINER_NAME))
