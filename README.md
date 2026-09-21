# Attendance Management System

A desktop-based Attendance Management System designed to streamline time tracking, manage attendance records, and simplify user logs using a modern Java Swing GUI built in Apache NetBeans.

---

## Features

* **User Authentication:** Secure login for administrators and authorized personnel.
* **Attendance Tracking:** Real-time check-in and check-out recording.
* **Record Management:** View, search, and manage attendance logs easily.
* **Data Export:** Export attendance summaries for reporting and auditing.
* **Modern Desktop UI:** Built using NetBeans Swing/GUI Builder components for intuitive navigation.

---

## Tech Stack & Requirements

* **Programming Language:** Java (JDK 8 or higher)
* **Development Environment:** Apache NetBeans IDE (v12.0 or higher recommended)
* **GUI Framework:** Java Swing / Abstract Window Toolkit (AWT)
* **Database (Optional/Configurable):** MySQL (via JDBC) / XAMPP / WAMP for local deployment

---

## Prerequisites(important)

Before running the application, ensure you have the following installed on your system:

1. **Java Development Kit (JDK 8+):** [Download JDK](https://www.oracle.com/java/technologies/downloads/?utm_source=gemini)
2. **Apache NetBeans IDE:** [Download NetBeans](https://netbeans.apache.org/download/index.html?utm_source=gemini)
3. **Database Server (if applicable):** [XAMPP](https://www.apachefriends.org/?utm_source=gemini) or MySQL Workbench.

---

## Installation & Setup

1. **Clone the Repository:**
```bash
git clone https://github.com/hashleycuachin03-code/AttendanceManagementSystem.git

```


2. **Open Project in NetBeans:**
* Launch **Apache NetBeans IDE**.
* Go to `File` > `Open Project...` (or press `Ctrl + Shift + O`).
* Navigate to the cloned repository directory and select the `JavaApplication1` folder.
* Click **Open Project**.


3. **Configure Database (If Applicable):**
* Start your local database server (e.g., MySQL via XAMPP).
* Import the provided `.sql` database schema file into your database manager.
* Ensure the database configuration settings in your Java source code match your MySQL credentials:
* **URL:** `jdbc:mysql://localhost:3306/your_database_name`
* **Username:** `root`
* **Password:** *(leave empty or enter your MySQL password)*





---

## Running the Application

1. Open the project inside Apache NetBeans.
2. Locate the main file (e.g., `Main.java` or `LoginForm.java`) inside the `src` folder.
3. Clean and build the project:
* Right-click the project in the left sidebar and select **Clean and Build** (or press `Shift + F11`).


4. Run the project:
* Press **`F6`** (Run Main Project) or **`Shift + F6`** (Run Selected File).



---

## Project Structure

```text
AttendanceManagementSystem/
│
├── JavaApplication1/
│   ├── src/
│   │   ├── ui/               # Swing GUI Forms / Panels (.java and .form files)
│   │   ├── database/         # Database Connection Class (JDBC Helpers)
│   │   └── models/           # Data Models (User, Attendance, Logs)
│   ├── build/                # Compiled Bytecode (.class files)
│   ├── nbproject/            # NetBeans Project Configuration
│   └── build.xml             # Ant Build Script
│
└── README.md

```

---

## Default Credentials (Development)

| Role | Username | Password |
| --- | --- | --- |
| **Administrator** | `admin` | `admin123` |
| **User** | `user` | `user123` |

*(Note: Change these default credentials after initial setup for security.)*

---

## Contributing

Contributions, issues, and feature requests are welcome!

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/NewFeature`)
3. Commit your Changes (`git commit -m 'Add NewFeature'`)
4. Push to the Branch (`git push origin feature/NewFeature`)
5. Open a Pull Request

---

## License

This project is open-source and available under the [MIT License](https://www.google.com/search?q=LICENSE&utm_source=gemini).
