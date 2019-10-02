CREATE DATABASE prestogateway;

CREATE TABLE IF NOT EXISTS gateway_backend (
name VARCHAR(256) PRIMARY KEY,
routing_group VARCHAR (256),
backend_url VARCHAR (256),
active BOOLEAN
);

CREATE TABLE IF NOT EXISTS query_history (
query_id VARCHAR(256) PRIMARY KEY,
query_text VARCHAR (256),
created bigint,
backend_url VARCHAR (256),
user_name VARCHAR(256),
source VARCHAR(256)
);

CREATE INDEX query_history_created_idx ON query_history(created);

CREATE TABLE IF NOT EXISTS rule_type (
type_id INT PRIMARY KEY,
type_name VARCHAR(256) NOT NULL,
description VARCHAR(256),
priority INT DEFAULT 10000 ,
status BOOLEAN
)

CREATE TABLE IF NOT EXISTS rule (
rule_id VARCHAR(256) PRIMARY KEY,
type_id  INT NOT NULL,
priority INT DEFAULT 10000,
condition_type INT NOT NULL,
condition_value VARCHAR(256) NOT NULL,
action_type ENUM ('REDIRECT','BLACKLIST','DEFAULT') DEFAULT 'DEFAULT' NOT NULL,
action_value VARCHAR(256) DEFAULT 'adhoc',
status BOOLEAN,
FOREIGN KEY (type_id) REFERENCES rule_type(type_id) ON  DELETE CASCADE,
FOREIGN KEY (condition_type) REFERENCES condition_type(id) ON DELETE CASCADE
)

CREATE TABLE IF NOT EXISTS condition_type (
id INT PRIMARY KEY,
name VARCHAR(256)
)


INSERT INTO condition_type(id, name)VALUES
(1, 'equals')
(2, 'startsWith');

INSERT INTO rule_type(type_id, type_name, description, priority, status) VALUES
(1,'user','',3,1),
(2,'source','',2,1),
(3,'hint','',1,1);


INSERT INTO rule(rule_id, type_id, priority, condition_type, condition_value, action_type, action_value, status)
VALUES
(1,1,10000, 1, 'pganeshan@ea.com','BLACKLIST','', 1),
(2,1,1, 2, 'svc','REDIRECT','etl', 1),
(3,2,10000, 1, 'superset','REDIRECT','superset', 1),
(4,2,10000, 1, 'tableau','REDIRECT','tableau', 1);