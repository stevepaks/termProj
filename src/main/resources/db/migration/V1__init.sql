
CREATE TABLE employee (
    `id` IDENTITY PRIMARY KEY,
    employee_id VARCHAR(20) NOT NULL,
    name VARCHAR(30) NOT NULL,
    department VARCHAR(50),
    role VARCHAR(50),
    join_date DATE
);

CREATE TABLE leave (
    `id` IDENTITY PRIMARY KEY,
    employee_id VARCHAR(20) NOT NULL,
    `start` DATE NOT NULL,
    `end` DATE NOT NULL,
    days DOUBLE NOT NULL,
    leave_type VARCHAR(30) NOT NULL,
    leave_status VARCHAR(50) NOT NULL,
    created_at DATETIME
);

INSERT INTO employee(employee_id, name, department, role, join_date)
VALUES ('K0001', '홍길동', '인트라플랫폼팀', '팀원', '2021-01-01');