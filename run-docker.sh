#!/bin/bash

docker run -p 5432:5432 -e POSTGRES_USER=user -e POSTGRES_PASSWORD=password -e POSTGRES_DB=test -d postgres

docker run -p 6379:6379 -d redis
