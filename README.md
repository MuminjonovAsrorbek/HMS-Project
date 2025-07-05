# 🏥 Hospital Management System (HMS)

<div align="center">

![Java](https://img.shields.io/badge/Java-17-orange?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.0-brightgreen?style=for-the-badge&logo=spring)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-blue?style=for-the-badge&logo=postgresql)
![License](https://img.shields.io/badge/License-MIT-yellow?style=for-the-badge)

**A comprehensive Hospital Management System designed for internal hospital operations with role-based access control and real-time appointment management.**

[Features](#-key-features) • [Setup](#-setup-instructions) • [API Docs](#-api-overview) • [Contributing](#-contributing)

</div>

---

## 📋 Project Overview

The Hospital Management System (HMS) is a full-stack web application built to streamline hospital operations and improve patient care efficiency. The system provides a secure, role-based platform where different hospital staff members can perform their specific duties with appropriate access controls.

### 🎯 Purpose
- **Internal Hospital Use**: Designed specifically for hospital staff operations
- **Role-Based Access**: Four distinct user roles with specific permissions
- **Real-Time Operations**: Live appointment scheduling and status tracking
- **Automated Notifications**: Email and SMS alerts for appointments
- **Comprehensive Reporting**: Excel export capabilities for management

---

## ✨ Key Features

### 🔐 **Security & Access Control**
- JWT-based authentication and authorization
- Role-based permission system
- Secure password handling with encryption
- Session management and timeout controls

### 📅 **Appointment Management**
- Real-time available time slot selection
- Color-coded appointment status indicators
- Automatic conflict detection and prevention
- Bulk appointment operations

### 📊 **Reporting & Analytics**
- Daily appointment statistics
- Excel report generation
- User activity tracking
- System usage analytics

### 🔔 **Notification System**
- Automated email notifications
- SMS appointment reminders
- Real-time status updates
- Customizable notification templates

### 📝 **Activity Logging**
- Complete audit trail using Spring Listeners
- User action tracking
- System event logging
- Security incident monitoring

### 🎨 **User Interface**
- Responsive design for all devices
- Intuitive navigation
- Modern UI/UX principles
- Accessibility compliance

---

## 🛠️ Technologies Used

### **Backend**
- **Framework**: Spring Boot 3.5.0
- **Language**: Java 17
- **Security**: Spring Security 6 + JWT
- **Database**: PostgreSQL (Production) / H2 (Development)
- **ORM**: Spring Data JPA
- **Documentation**: OpenAPI 3 (Swagger)
- **Build Tool**: Maven
- **Additional Libraries**:
  - MapStruct (Object Mapping)
  - Lombok (Code Generation)
  - Apache POI (Excel Generation)
  - Thymeleaf (Template Engine)

### **Frontend**
- **Languages**: HTML5, CSS3, JavaScript (ES6+)
- **Styling**: Custom CSS with responsive design
- **UI Components**: Modern web components
- **AJAX**: Fetch API for asynchronous operations

### **Database**
- **Production**: PostgreSQL
- **Development**: H2 Database
- **Migration**: Spring Boot Auto-configuration

### **DevOps & Tools**
- **Version Control**: Git
- **IDE**: IntelliJ IDEA
- **Testing**: JUnit 5, Spring Boot Test
- **API Testing**: Swagger UI

---

## 👥 System Roles

### 🔑 **Admin**
**Full System Control**
- ✅ Complete user management (CRUD operations)
- ✅ System configuration and settings
- ✅ Comprehensive audit log access
- ✅ System-wide statistics and reports
- ✅ Role and permission management
- ✅ Database maintenance operations

### 👔 **Manager**
**Operational Management**
- ✅ User creation and role assignment
- ✅ Doctor and staff scheduling
- ✅ Room and resource management
- ✅ Daily appointment statistics
- ✅ Excel report generation
- ✅ Department oversight

### 👨‍⚕️ **Doctor**
**Medical Practice Management**
- ✅ Personal appointment calendar
- ✅ Patient appointment history
- ✅ Appointment status updates
- ✅ Personal schedule management
- ✅ Patient medical records access
- ✅ Treatment notes and prescriptions

### 👩‍💼 **Receptionist**
**Patient Services**
- ✅ Patient registration and management
- ✅ Appointment scheduling
- ✅ Daily appointment overview
- ✅ Patient check-in/check-out
- ✅ Appointment status tracking
- ✅ Patient communication

---

## 🚀 API Overview

### **Authentication Endpoints**
```http
POST /api/auth/login          # User authentication
POST /api/auth/logout         # User logout
POST /api/auth/refresh        # Token refresh
```

### **User Management**
```http
GET    /api/users             # Get all users (Admin/Manager)
POST   /api/users             # Create new user (Admin/Manager)
PUT    /api/users/{id}        # Update user (Admin/Manager)
DELETE /api/users/{id}        # Delete user (Admin only)
```

### **Appointment Management**
```http
GET    /api/appointments      # Get appointments
POST   /api/appointments      # Create appointment
PUT    /api/appointments/{id} # Update appointment
DELETE /api/appointments/{id} # Cancel appointment
GET    /api/appointments/available-slots # Get available time slots
```

### **Patient Management**
```http
GET    /api/patients          # Get patients
POST   /api/patients          # Register patient
PUT    /api/patients/{id}     # Update patient
GET    /api/patients/search   # Search patients
```

### **Reporting**
```http
GET    /api/reports/daily-appointments    # Daily appointment stats
GET    /api/reports/export-excel         # Export to Excel
GET    /api/statistics/overview          # System statistics
```

### **📚 API Documentation**
Access the complete API documentation at: `http://localhost:8080/swagger-ui.html`

---

## 🔧 Setup Instructions

### **Prerequisites**
- ☑️ Java 17 or higher
- ☑️ Maven 3.6+
- ☑️ PostgreSQL 12+ (for production)
- ☑️ Git

### **1. Clone the Repository**
```bash
git clone https://github.com/yourusername/HMS-Project.git
cd HMS-Project
```

### **2. Database Setup**

#### **For Development (H2 Database)**
No additional setup required. H2 will auto-configure.

#### **For Production (PostgreSQL)**
```sql
-- Create database
CREATE DATABASE hms_db;

-- Create user (optional)
CREATE USER hms_user WITH PASSWORD 'your_password';
GRANT ALL PRIVILEGES ON DATABASE hms_db TO hms_user;
```

### **3. Configuration**

#### **Development Environment**
```yaml
# src/main/resources/application-dev.yaml
spring:
  datasource:
    url: jdbc:h2:mem:testdb
    driver-class-name: org.h2.Driver
  h2:
    console:
      enabled: true
```

#### **Production Environment**
```yaml
# src/main/resources/application-product.yml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/hms_db
    username: hms_user
    password: your_password
    driver-class-name: org.postgresql.Driver
```

### **4. Build and Run**

#### **Development Mode**
```bash
# Build the project
mvn clean compile

# Run with development profile
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

#### **Production Mode**
```bash
# Build the project
mvn clean package

# Run with production profile
java -jar target/HMS-Project-0.0.1-SNAPSHOT.jar --spring.profiles.active=product
```

### **5. Access the Application**
- **Application**: http://localhost:8080
- **API Documentation**: http://localhost:8080/swagger-ui.html
- **H2 Console** (dev only): http://localhost:8080/h2-console

### **6. Default Login Credentials**
```
Admin User:
Username: admin
Password: admin123

Manager User:
Username: manager
Password: manager123

Doctor User:
Username: doctor
Password: doctor123

Receptionist User:
Username: receptionist
Password: receptionist123
```

---

## 📁 Project Structure

```
HMS-Project/
├── 📁 src/
│   ├── 📁 main/
│   │   ├── 📁 java/uz/dev/hmsproject/
│   │   │   ├── 📁 config/           # Configuration classes
│   │   │   ├── 📁 controller/       # REST Controllers
│   │   │   ├── 📁 dto/              # Data Transfer Objects
│   │   │   ├── 📁 entity/           # JPA Entities
│   │   │   ├── 📁 enums/            # Enumerations
│   │   │   ├── 📁 exception/        # Custom Exceptions
│   │   │   ├── 📁 filter/           # Security Filters
│   │   │   ├── 📁 handler/          # Exception Handlers
│   │   │   ├── 📁 listener/         # Event Listeners
│   │   │   ├── 📁 mapper/           # MapStruct Mappers
│   │   │   ├── 📁 projection/       # JPA Projections
│   │   │   ├── 📁 repository/       # Data Repositories
│   │   │   ├── 📁 service/          # Business Logic
│   │   │   ├── 📁 specification/    # JPA Specifications
│   │   │   └── 📁 utils/            # Utility Classes
│   │   └── 📁 resources/
│   │       ├── 📁 static/           # Static web assets
│   │       ├── 📁 templates/        # Thymeleaf templates
│   │       └── 📄 application.yaml # Configuration files
│   └── 📁 test/                     # Test classes
├── 📄 pom.xml                       # Maven configuration
├── 📄 README.md                     # Project documentation
└── 📄 LICENSE                       # License file
```


## 🤝 Contributing

We welcome contributions to improve the Hospital Management System! Here's how you can help:

### **Getting Started**
1. 🍴 Fork the repository
2. 🌿 Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. 💻 Make your changes
4. ✅ Add tests for new functionality
5. 📝 Commit your changes (`git commit -m 'Add some AmazingFeature'`)
6. 📤 Push to the branch (`git push origin feature/AmazingFeature`)
7. 🔄 Open a Pull Request

### **Development Guidelines**
- Follow Java coding standards and best practices
- Write comprehensive unit tests
- Update documentation for new features
- Ensure backward compatibility
- Use meaningful commit messages

### **Code Style**
- Use consistent indentation (4 spaces)
- Follow Spring Boot conventions
- Document public APIs with JavaDoc
- Use descriptive variable and method names

### **Reporting Issues**
- Use the GitHub issue tracker
- Provide detailed reproduction steps
- Include system information and logs
- Label issues appropriately

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

```
MIT License

Copyright (c) 2024 Hospital Management System

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

---

## 📞 Support & Contact

### **Technical Support**
- 📧 Email: support@hms-project.com
- 🐛 Issues: [GitHub Issues](https://github.com/yourusername/HMS-Project/issues)
- 📖 Documentation: [Wiki](https://github.com/yourusername/HMS-Project/wiki)

### **Community**
- 💬 Discussions: [GitHub Discussions](https://github.com/yourusername/HMS-Project/discussions)
- 🌟 Star the project if you find it useful!
- 🔄 Share with your network

---

<div align="center">

**Made with ❤️ for better healthcare management**

⭐ **Star this repository if it helped you!** ⭐

</div>