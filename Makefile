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

### PORTS ###
OUTGOING_PORT=6432
INCOMING_PORT=5432

### CONTAINER ID & DOCKER IMAGE ###
CONTAINER_NAME=macadamia-nut-db
DOCKER_IMAGE=postgres:13-alpine

help:
	@echo "run        	- Run database container for local development with name cidh-postgres."
	@echo "id			- Get the container ID."
	@echo "start      	- Start the database container."
	@echo "stop       	- Stop the database container (data is not removed)."
	@echo "restart		- Restart the database container (data is not removed)."
	@echo ""
	@echo "rm     		- Remove the database container."
	@echo "refresh    	- Stop, remove and rerun the database container."
	@echo ""
	@echo "bash       	- Run a bash terminal in the database container."
	@echo "psql       	- Run a postgresql shell against a running container."
	@echo "init       	- Create database from script files in src/main/resources/db/migration."

run:
	docker run --name="$(CONTAINER_NAME)" \
	-e POSTGRES_USER=$(POSTGRES_USER) \
	-e POSTGRES_PASSWORD=$(POSTGRES_PASSWORD) \
	-e POSTGRES_DB=$(POSTGRES_DB) \
	-v "$(shell pwd)/$(SQL_SCRIPT_FOLDER):$(CONTAINER_SQL_SCRIPT_FOLDER)" \
	-v "$(shell pwd)/$(DATA_FOLDER_VOLUME):$(CONTAINER_DATA_VOLUME)" \
	-p $(OUTGOING_PORT):$(INCOMING_PORT) \
	-d \
	$(DOCKER_IMAGE)

id:
	docker ps -aqf name=$(CONTAINER_NAME)

stop:
	docker stop $$(docker ps -aqf name=$(CONTAINER_NAME))

start:
	docker start $$(docker ps -aqf name=$(CONTAINER_NAME))

restart:
	make stop
	make start

rm:
	make stop
	docker rm $$(docker ps -aqf name=$(CONTAINER_NAME))
	$(shell rm -rf $(shell pwd)/$(DATA_FOLDER_VOLUME)/*)

refresh:
	make rm
	make run

bash:
	docker exec -it $$(docker ps -aqf name=$(CONTAINER_NAME)) bash

psql:
	docker exec -it $$(docker ps -aqf name=$(CONTAINER_NAME)) psql -U $(POSTGRES_USER) -d $(POSTGRES_DB)

init:
	@for f in $(shell ls $(SQL_SCRIPT_FOLDER)); do \
		docker exec -it $$(docker ps -aqf name=$(CONTAINER_NAME)) \
		psql -U $(POSTGRES_USER) -h localhost -d $(POSTGRES_DB) -f $(shell pwd)/$(SQL_SCRIPT_FOLDER)/$${f}; done
