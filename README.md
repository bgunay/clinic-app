# Read Me First

Clinic APP

* You can access the application at [http://localhost:8080](http://localhost:8080)
* You can access swagger at [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
* Sample data is loaded on startup
* MySQL is used as the database, you can change the configuration in the application.properties file
* POSTMAN collection is available in the project root directory
* For Build and Run all tests run <code>mvn clean install </code> 
* For coverage report run <code>mvn jacoco:report</code>, reports are available in target/site/jacoco
* Jacoco test report generated with intellij editor also available in project root directory --> jacoco_report.png
* Run the application with 'mvn spring-boot:run' or with the IDE
* For simplicity, the application is not secured, and not all cases are covered in tests.
* Steps:
     * in the root folder, run <code>docker-compose up</code>
     * in the root folder, run <code>mvn clean install</code>
     * Run the application
     * Create a new Doctor
     * Create a new Patient
     * Create a new appointment
     * See OPEN appointment with appointments with getUserAppointmentList operation (patientId required)
     * Cancel appointment cancelAppointment operation (appointmentId required)
     * See CANCEL appointment with appointments with getUserAppointmentList operation (patientId required), see refund amount
