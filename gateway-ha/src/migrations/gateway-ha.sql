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
name VARCHAR(256)  PRIMARY KEY,
description VARCHAR(256),
priority INT NOT NULL,
status BOOLEAN DEFAULT TRUE
)

CREATE TABLE IF NOT EXISTS routingRule (
type_name VARCHAR(256) NOT NULL,
subject VARCHAR(256) NOT NULL,
priority INT DEFAULT 10000,
action_type ENUM ('REDIRECT','BLACKLIST','DEFAULT') DEFAULT 'DEFAULT' NOT NULL,
action_value VARCHAR(256) DEFAULT 'adhoc',
status BOOLEAN DEFAULT TRUE,
PRIMARY KEY (type_name, subject),
FOREIGN KEY (type_name) REFERENCES rule_type(name) ON  DELETE CASCADE
)

INSERT INTO rule_type(name, description, priority, status)
VALUES
('user','User who submitted the query', 1, 1),
('tool','Tool used to submit the query', 2, 1),
('hint','Hint provided with the query', 3, 1);

INSERT INTO routingRule(type_name, subject, priority, action_type, action_value, status)
VALUES
('user', 'pganeshan@ea.com', 1,'BLACKLIST','',1),
('tool', 'superset', 1,'REDIRECT','tool',1),
('tool', 'tableau', 1,'REDIRECT','tool',1),
