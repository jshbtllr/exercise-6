CREATE DATABASE ERS;

create table employee (
	employeeid BIGINT NOT NULL auto_increment,
	lastname VARCHAR(50) NOT NULL,
	firstname VARCHAR(50) NOT NULL,
	middlename VARCHAR(50) default NULL,
	suffix VARCHAR(30) default NULL,
	title VARCHAR(30) default NULL,
	street VARCHAR(100) default NULL,
	barangay VARCHAR(50) default NULL,
	city VARCHAR(30) default NULL,
	zipcode VARCHAR(30) default NULL,
	birthday DATE,
	gwa FLOAT(8),
	hiredate DATE,
	employed BIT(1),
	PRIMARY KEY (id)
);

create table employeerole (
	employeeid INT NOT NULL,
	roleid INT NOT NULL,
	PRIMARY KEY (EmployeeID, RoleID)
);

create table roles (
	roleid INT NOT NULL auto_increment,
	rolecode VARCHAR(10) NOT NULL,
	rolename VARCHAR(50) NOT NULL,
	PRIMARY KEY (RoleID)
);

create table contactinfo (
	contactid INT NOT NULL auto_increment,
	contactinfotype VARCHAR(30) NOT NULL,
	contactinformation VARCHAR(100) NOT NULL,
	EmployeeID INT NOT NULL REFERENCES employee,
	PRIMARY KEY (ContactID)
);

