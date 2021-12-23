### PSQL CREDENTIALS ###
POSTGRES_USER=postgres
POSTGRES_PASSWORD=Pass2021!
POSTGRES_DB=postgres

### SQL SCRIPTS ###
SQL_MIGRATION=src/main/resources/db/migration
SQL_INSERTS=src/main/resources/db/customScripts
SQL_SCRIPT_FOLDER=src/main/resources/db/customScripts
SQL_SCRIPT_MOCKDATA=initial_data.sql
CONTAINER_SQL_SCRIPT_FOLDER=/var/lib/postgresql/dump

### PSQL PERSIST VOLUME ###
CONTAINER_DATA_VOLUME=/var/lib/postgresql/data
DATA_FOLDER_VOLUME=docker/psql/data

### PORTS ###
OUTGOING_PORT=6432
INCOMING_PORT=5432

### CONTAINER ID & DOCKER IMAGE ###
CONTAINER_NAME=cidh-postgres
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
	@echo ""
	@echo "init       	- Run migrate and seed."
	@echo "create       - Create the schema."
	@echo "drop       	- Drop the schema."
	@echo "migrate      - Migrate all tables of database."
	@echo "seed       	- Insert into DB all mock data, the file is in src/main/resources/db/customScripts."
	@echo "clean       	- Clean all data of tables."

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
	make migrate
	make seed

create:
	docker exec -it $$(docker ps -aqf name=$(CONTAINER_NAME)) \
	psql -U $(POSTGRES_USER) -h localhost -c 'CREATE DATABASE ${POSTGRES_DB};'

drop:
	docker exec -it $$(docker ps -aqf name=$(CONTAINER_NAME)) \
	psql -U $(POSTGRES_USER) -h localhost -c 'DROP DATABASE IF EXISTS ${POSTGRES_DB};'

migrate:
	flyway migrate -user=${POSTGRES_USER} -password=${POSTGRES_PASSWORD} -url=jdbc:postgresql://localhost:${OUTGOING_PORT}/${POSTGRES_DB} -locations=filesystem:$(shell pwd)/${SQL_MIGRATION}

seed:
	docker exec -it $$(docker ps -aqf name=$(CONTAINER_NAME)) \
	psql -U $(POSTGRES_USER) -h localhost -d $(POSTGRES_DB) -f $(CONTAINER_SQL_SCRIPT_FOLDER)/$(SQL_SCRIPT_MOCKDATA)

clean:
	flyway clean -user=${POSTGRES_USER} -password=${POSTGRES_PASSWORD} -url=jdbc:postgresql://localhost:${OUTGOING_PORT}/${POSTGRES_DB}
