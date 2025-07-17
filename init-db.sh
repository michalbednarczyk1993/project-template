#!/bin/bash
psql -U postgres <<EOSQL
CREATE DATABASE orders_db;
CREATE DATABASE products_db;
CREATE DATABASE users_db;
EOSQL