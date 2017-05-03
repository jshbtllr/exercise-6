create table EMPLOYEE (
	id INT NOT NULL auto_increment,
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

create table EMPROLE (
	EmployeeID INT NOT NULL,
	RoleID INT NOT NULL,
	PRIMARY KEY (EmployeeID, RoleID)
);

create table ROLES (
	RoleID INT NOT NULL auto_increment,
	RoleCode VARCHAR(10) NOT NULL,
	RoleName VARCHAR(50) NOT NULL,
	PRIMARY KEY (RoleID)
);

create table CONTACTINFO (
	ContactID INT NOT NULL auto_increment,
	ContactInfoType VARCHAR(10) NOT NULL,
	ContactInformation VARCHAR(100) NOT NULL,
	EmployeeID INT NOT NULL,
	PRIMARY KEY (ContactID)
);

