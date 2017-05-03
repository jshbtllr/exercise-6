<?xml version="1.0" encoding="utf-8"?>

<hibernate-mapping>
	<class name="com.exercise6.model.Employee" table="EMPLOYEE">
		<meta attribute="class-description">
			Class-Table mapping contains all employee details.
		</meta>
		<id name="id" type="int" column="id">
			<generator class="native"/>
		</id>
		<property name="lastName" column="Last_Name" type="string"/>
		<property name="firstName" column="First_Name" type="string"/>
		<property name="middleName" column="Middle_Name" type="string"/>
		<property name="suffix" column="Suffix" type="string"/>
		<property name="title" column="Title" type="string"/>
		<component name="address" class="Address">
			<property name="streetNumber" column="Street" type="string"/>
			<property name="barangay" column="Barangay" type="string"/>
			<property name="city" column="City" type="string"/>
			<property name="zipcode" column="Zipcode" type="string"/>
		</component>
		<property name="birthday" column="Birthday" type="date"/>
		<property name="gradeWeightAverage" column="GWA" type="float"/>
		<property name="hireDate" column="Hire_Date" type="date"/>
		<property name="employed" column="Employed" type="boolean"/>
		<set name="contactInfo" table="CONTACTINFO" cascade="ALL">
			<key column="EmployeeID"/>
			<one-to-many class="com.exercise6.model.Address"/>
		</set>
		<set name="role" cascade="save-update" table="EMPROLE">
			<key column="EmployeeID"/>
			<many-to-many column="RoleID" class="com.exercise6.model.Roles"/>
		</set>
	</class>
</hibernate-mapping>