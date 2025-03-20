#!/bin/bash
#
# Create a new migration file generated from the 
# current state of entities beans.
#

if [ -z "$1" ]; then
  echo "Usage: $0 <migration-name>"
  exit 1
fi

MIGRATION_NAME=$1
TIMESTAMP=$(date +%Y%m%d%H%M%S)
FILE_PATH="src/main/resources/db/migrations/${TIMESTAMP}_${MIGRATION_NAME}.sql"

# ensure the database is up-to-date
mvn liquibase:update

mvn liquibase:diff -Dliquibase.diffChangeLogFile=${FILE_PATH}

if [ -f "$FILE_PATH" ]; then
	sed -i "s/-- changeset .*/-- changeset ${MIGRATION_NAME}:${TIMESTAMP}-1/g" "$FILE_PATH"
	awk '/-- changeset/ {sub(/-[0-9]+/, "-" ++i)} 1' i=0 "$FILE_PATH" > temp && mv temp "$FILE_PATH"
	echo "Migration file generated: $FILE_PATH"
else
    echo "Error: Migration file was not created!"
    exit 1
fi
