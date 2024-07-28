#!/bin/bash

echo "Waiting for database to setup..."
sleep 5
echo "Wait is over. Starting the application."

exec "$@"