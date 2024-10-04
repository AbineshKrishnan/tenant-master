CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE tenant (
	id uuid DEFAULT uuid_generate_v4() NOT NULL PRIMARY KEY,
	name varchar(255) NULL,
	email_id varchar(255) NULL,
	mobile_number varchar(255) NULL,
	driver varchar(255) NULL,
	port varchar(255) NULL,
	db_url varchar(255) NULL,
	db_name varchar(255) NULL,
	db_username varchar(255) NULL,
	db_password varchar(255) NULL,
	created_by varchar(255) NULL,
	updated_by varchar(255) NULL,
	created_at timestamptz DEFAULT (now() AT TIME ZONE 'UTC'::text) NULL,
	updated_at timestamptz DEFAULT (now() AT TIME ZONE 'UTC'::text) NULL,
	is_deleted bool NULL
);

CREATE TABLE IF NOT EXISTS master_configuration (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    key VARCHAR(50),
    value TEXT,
    static_configuration BOOLEAN,
    s3_url BOOLEAN,
    list_configuration BOOLEAN,
    created_by VARCHAR(30),
    updated_by VARCHAR(30),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT (now() AT TIME ZONE 'UTC'),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT (now() AT TIME ZONE 'UTC'),
    is_deleted BOOLEAN
);

CREATE TABLE IF NOT EXISTS master_configuration_activity_history (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    tenant_id UUID,
    master_configuration_id UUID,
    action VARCHAR(255),
    description TEXT,
    status VARCHAR(20),
    time_of_action VARCHAR(255),
    created_by VARCHAR(30),
    updated_by VARCHAR(30),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT (now() AT TIME ZONE 'UTC'),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT (now() AT TIME ZONE 'UTC'),
    is_deleted BOOLEAN
);