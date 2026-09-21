# Attendance Management System Setup

This is a Java Swing attendance application built as an Apache NetBeans Ant project. It provides employee check-in/check-out, shift scheduling, employee management, attendance records, and administrator shift monitoring.

## 1. Install the Java toolchain

Install a JDK supported by your NetBeans version. The project is configured with Java source and target level 23, so use JDK 23 or newer. During installation, enable the option to add Java to `PATH`, or configure `JAVA_HOME` manually.

Verify in PowerShell:

```powershell
java -version
javac -version
```

## 2. Open the project

Open the `JavaApplication1` folder in Apache NetBeans, not only the repository parent folder. It is an Ant project because it contains `build.xml` and `nbproject/`.

If NetBeans asks for a Java platform, select the installed JDK.

## 3. Add the MySQL JDBC driver

Create a `lib` folder in the project root and place this file inside it:

```text
lib/mysql-connector-j-9.3.0.jar
```

The Ant project is already configured to use this portable path in `nbproject/project.properties`. Do not rely on the absolute ZIP reference in that file; the JAR in `lib/` is the intended runtime dependency.

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

The current connection configuration uses an empty MySQL password for local development. Use a dedicated database user and move credentials out of source code before deploying this application.

## 5. UI architecture

The application uses Swing with NetBeans-generated forms and runtime layout composition. No external Look and Feel library is required.

Shared visual styling is defined in:

```text
src/attendance_Checking_Main_Project1/ThemeManager.java
```

The main screens are:

| Screen                       | Purpose                                    |
| ---------------------------- | ------------------------------------------ |
| `login_admin_employee`       | Authenticates administrators and employees |
| `employee_info`              | Admin home panel and employee directory    |
| `attendance_record_employee` | Employee attendance check-in/check-out     |
| `employee_shifts_schedule`   | Employee shift selection and attendance    |
| `attendance_record_admin`    | Administrator attendance records           |
| `admin_shift_view`           | Administrator employee-shift monitor       |

The admin home panel uses a resizable split layout: the employee directory occupies the main area and employee editing controls appear in a separate details panel. Existing `.form` files remain compatible with NetBeans; the redesigned presentation is assembled after `initComponents()` so generated code does not need manual edits.

## 6. Build and run

From NetBeans, use **Clean and Build Project**, then run the project. From a terminal with Ant installed, run:

```powershell
ant clean jar
```

The project run class is configured as `attendance_Checking_Main_Project1.login_admin_employee`.

If Ant is not installed, compile the sources directly from the `JavaApplication1` directory in PowerShell:

```powershell
Remove-Item -Recurse -Force .\build\verify -ErrorAction SilentlyContinue
New-Item -ItemType Directory .\build\verify | Out-Null
javac -d .\build\verify -cp .\lib\mysql-connector-j-9.3.0.jar (Get-ChildItem -Recurse .\src -Filter *.java | Select-Object -ExpandProperty FullName)
```

For normal execution, prefer NetBeans or Ant because they configure the complete runtime classpath and application entry point.

## 7. Project conventions

- Keep database access in service/repository code as the application grows.
- Keep generated `initComponents()` sections under NetBeans control.
- Add custom Swing styling after `initComponents()`.
- Create Swing windows on the Event Dispatch Thread.
- Run database work outside the Event Dispatch Thread for production-sized datasets.
- Use `ThemeManager` for new colors, controls, tables, and typography instead of adding screen-specific styling.
