CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    full_name VARCHAR(255) NOT NULL,
    phone VARCHAR(30),
    email VARCHAR(255),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT chk_users_role
        CHECK (role IN ('OWNER', 'OPERATOR'))
);


CREATE TABLE buildings (
    id BIGSERIAL PRIMARY KEY,
    address VARCHAR(500) NOT NULL
);


CREATE TABLE apartments (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    building_id BIGINT NOT NULL,
    apartment_number VARCHAR(20) NOT NULL,

    CONSTRAINT fk_apartments_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_apartments_building
        FOREIGN KEY (building_id)
        REFERENCES buildings(id)
        ON DELETE RESTRICT,

    CONSTRAINT uk_apartment_building_number
        UNIQUE (building_id, apartment_number)
);


CREATE TABLE meters (
    id BIGSERIAL PRIMARY KEY,
    apartment_id BIGINT,
    building_id BIGINT,
    serial_number VARCHAR(100) NOT NULL UNIQUE,
    meter_type VARCHAR(30) NOT NULL,
    placement_type VARCHAR(20) NOT NULL,
    installation_date DATE NOT NULL,
    next_verification_date DATE NOT NULL,

    CONSTRAINT fk_meters_apartment
        FOREIGN KEY (apartment_id)
        REFERENCES apartments(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_meters_building
        FOREIGN KEY (building_id)
        REFERENCES buildings(id)
        ON DELETE CASCADE,

    CONSTRAINT chk_meters_type
        CHECK (
            meter_type IN (
                'COLD_WATER',
                'HOT_WATER',
                'ELECTRICITY',
                'GAS'
            )
        ),

    CONSTRAINT chk_meters_placement
        CHECK (
            placement_type IN (
                'INDIVIDUAL',
                'COMMON'
            )
        ),

    CONSTRAINT chk_meters_owner
        CHECK (
            (
                placement_type = 'INDIVIDUAL'
                AND apartment_id IS NOT NULL
                AND building_id IS NULL
            )
            OR
            (
                placement_type = 'COMMON'
                AND apartment_id IS NULL
                AND building_id IS NOT NULL
            )
        )
);


CREATE TABLE meter_readings (
    id BIGSERIAL PRIMARY KEY,
    meter_id BIGINT NOT NULL,
    value DECIMAL(15, 3) NOT NULL,
    recorded_at TIMESTAMP WITH TIME ZONE NOT NULL,
    submitted_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    reading_source VARCHAR(20) NOT NULL,
    validation_status VARCHAR(20) NOT NULL,
    validation_comment VARCHAR(500),
    created_by BIGINT,

    CONSTRAINT fk_readings_meter
        FOREIGN KEY (meter_id)
        REFERENCES meters(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_readings_user
        FOREIGN KEY (created_by)
        REFERENCES users(id)
        ON DELETE SET NULL,

    CONSTRAINT chk_readings_source
        CHECK (
            reading_source IN (
                'OWNER',
                'OPERATOR',
                'AVERAGE'
            )
        ),

    CONSTRAINT chk_readings_validation
        CHECK (
            validation_status IN (
                'PENDING',
                'VALID',
                'INVALID',
                'AVERAGE'
            )
        ),

    CONSTRAINT chk_readings_value
        CHECK (value >= 0)
);


CREATE TABLE meter_verifications (
    id BIGSERIAL PRIMARY KEY,
    meter_id BIGINT NOT NULL,
    verification_date DATE NOT NULL,
    document_number VARCHAR(100) NOT NULL,
    result VARCHAR(20) NOT NULL,
    next_verification_date DATE NOT NULL,
    comment VARCHAR(1000),

    CONSTRAINT fk_verifications_meter
        FOREIGN KEY (meter_id)
        REFERENCES meters(id)
        ON DELETE CASCADE,

    CONSTRAINT chk_verifications_result
        CHECK (
            result IN (
                'PASSED',
                'FAILED'
            )
        )
);


CREATE TABLE notifications (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    text VARCHAR(1000) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    read_at TIMESTAMP WITH TIME ZONE,
    type VARCHAR(50) NOT NULL,

    CONSTRAINT fk_notifications_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
);


CREATE TABLE reading_periods (
    id BIGSERIAL PRIMARY KEY,
    year INTEGER NOT NULL,
    month INTEGER NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,

    CONSTRAINT chk_reading_period_month
        CHECK (month BETWEEN 1 AND 12),

    CONSTRAINT chk_reading_period_dates
        CHECK (start_date <= end_date),

    CONSTRAINT uq_reading_period
        UNIQUE (year, month)
);


CREATE INDEX idx_apartments_user_id
    ON apartments(user_id);

CREATE INDEX idx_apartments_building_id
    ON apartments(building_id);

CREATE INDEX idx_meters_apartment_id
    ON meters(apartment_id);

CREATE INDEX idx_meters_building_id
    ON meters(building_id);

CREATE INDEX idx_meters_next_verification_date
    ON meters(next_verification_date);

CREATE INDEX idx_meter_readings_meter_id
    ON meter_readings(meter_id);

CREATE INDEX idx_meter_readings_recorded_at
    ON meter_readings(recorded_at);

CREATE INDEX idx_meter_readings_meter_recorded
    ON meter_readings(meter_id, recorded_at);

CREATE INDEX idx_meter_verifications_meter_id
    ON meter_verifications(meter_id);

CREATE INDEX idx_notifications_user_id
    ON notifications(user_id);

CREATE INDEX idx_notifications_unread
    ON notifications(user_id, read_at);