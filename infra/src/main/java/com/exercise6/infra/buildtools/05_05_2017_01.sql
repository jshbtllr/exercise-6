create table IF NOT EXISTS EMPLOYEE (
	id SERIAL,
	Last_Name VARCHAR(50) NOT NULL,
	First_Name VARCHAR(50) NOT NULL,
	Middle_Name VARCHAR(50) default NULL,
	Suffix VARCHAR(10) default NULL,
	Title VARCHAR(20) default NULL,
	Street VARCHAR(100) default NULL,
	Barangay VARCHAR(50) default NULL,
	City VARCHAR(30) default NULL,
	Zipcode VARCHAR(10) default NULL,
	Birthday DATE,
	GWA FLOAT(8),
	Hire_Date DATE,
	Employed BIT(1),
	PRIMARY KEY (id)
);

create table IF NOT EXISTS EMPROLE (
	EmployeeID INT NOT NULL,
	RoleID INT NOT NULL,
	PRIMARY KEY (EmployeeID, RoleID)
);

create table IF NOT EXISTS ROLES (
	RoleID SERIAL,
	RoleCode VARCHAR(10) NOT NULL,
	RoleName VARCHAR(50) NOT NULL,
	PRIMARY KEY (RoleID)
);

create table IF NOT EXISTS CONTACTINFO (
	ContactID SERIAL,
	ContactInfoType VARCHAR(10) NOT NULL,
	ContactInformation VARCHAR(100) NOT NULL,
	EmployeeID INT NOT NULL,
	PRIMARY KEY (ContactID)
);

INSERT INTO ROLES (RoleCode, RoleName) VALUES ('Dev', 'Developer');
INSERT INTO ROLES (RoleCode, RoleName) VALUES ('Test', 'Tester');
INSERT INTO ROLES (RoleCode, RoleName) VALUES ('QA', 'Quality Assurance');