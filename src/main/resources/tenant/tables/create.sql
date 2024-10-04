CREATE EXTENSION IF NOT EXISTS "uuid-ossp";


CREATE TABLE IF NOT EXISTS sample_table (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    tenant_id UUID,
    otp INTEGER,
    otp_expiry_time TIMESTAMP,
    otp_type VARCHAR(20),
    active BOOLEAN,
    created_by VARCHAR(30),
    updated_by VARCHAR(30),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT (now() AT TIME ZONE 'UTC'),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT (now() AT TIME ZONE 'UTC'),
    is_deleted BOOLEAN
);
