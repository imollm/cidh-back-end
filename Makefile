### Manage PostgresSQL server
POSTGRES_USER=postgres
POSTGRES_PASSWORD=Pass2021!
POSTGRES_DB=postgres
SQL_SCRIPT_FOLDER=/Users/Ivan/Sites/pdp/cidh-back-end/src/main/resources/db/migration
SQL_SCRIPT_NAME=V1__initial_data_model.sql
CONTAINER_POSTGRES_DATA_FOLDER=/var/lib/postgresql/dump
OUTGOING_PORT=6432
INCOMING_PORT=5432
CONTAINER_NAME=cidh-postgres
DOCKER_IMAGE=postgres:alpine

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
	@echo "init       	- Create database from script file in src/main/resources/db/migration."

run:
	docker run --name="$(CONTAINER_NAME)" \
	-e POSTGRES_USER=$(POSTGRES_USER) \
	-e POSTGRES_PASSWORD=$(POSTGRES_PASSWORD) \
	-e POSTGRES_DB=$(POSTGRES_DB) \
	-v "$(SQL_SCRIPT_FOLDER):$(CONTAINER_POSTGRES_DATA_FOLDER)" \
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

refresh:
	make rm
	make run

bash:
	docker exec -it $$(docker ps -aqf name=$(CONTAINER_NAME)) bash

psql:
	docker exec -it $$(docker ps -aqf name=$(CONTAINER_NAME)) psql -U $(POSTGRES_USER) -d $(POSTGRES_DB)

init:
	docker exec -it $$(docker ps -aqf name=$(CONTAINER_NAME)) \
	psql -U $(POSTGRES_USER) -h localhost -d $(POSTGRES_DB) -f $(CONTAINER_POSTGRES_DATA_FOLDER)/$(SQL_SCRIPT_NAME)

#tables:
#	docker exec -it $$(docker ps -aqf name=$(CONTAINER_NAME)) psql -U $(POSTGRES_USER) -d $(POSTGRES_DB)
