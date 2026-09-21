# Attendance Management System Setup

## 1. Install the Java toolchain

Install a JDK supported by your NetBeans version. JDK 17 or newer is suitable for this source code. During installation, enable the option to add Java to `PATH`, or configure `JAVA_HOME` manually.

Verify in PowerShell:

```powershell
java -version
javac -version
```

## 2. Open the project

Open the `JavaApplication1` folder in Apache NetBeans. It is an Ant project because it contains `build.xml` and `nbproject/`.

If NetBeans asks for a Java platform, select the installed JDK.

## 3. Add the MySQL JDBC driver

Create a `lib` folder in the project root and place this file inside it:

```text
lib/mysql-connector-j-9.3.0.jar
```

The Ant project is already configured to use this portable path in `nbproject/project.properties`.

## 4. Configure MySQL

Install and start MySQL Server. Create the application database by running the schema script from:

```text
src/MySQL/attendance_CheckingWithData.sql
```

The application currently connects with:

```text
Database: attendance_checking
User: root
Password: empty
Host: localhost
Port: 3306
```

If your MySQL credentials differ, update the constants in `src/attendance_Checking_Main_Project1/DatabaseConnection.java`.

## 5. Build and run

From NetBeans, use **Clean and Build Project**, then run the project. From a terminal with Ant installed, run:

```powershell
ant clean jar
```

The project run class is configured as `attendance_Checking_Main_Project1.login_admin_employee`.
