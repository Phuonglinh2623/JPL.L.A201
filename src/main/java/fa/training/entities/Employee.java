package fa.training.entities;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public abstract class Employee implements Payable {
    private String ssn;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String phone;
    private String email;
    
    protected  Employee() {}
    
    protected  Employee(String ssn, String firstName, String lastName, 
                   LocalDate birthDate, String phone, String email) {
        this.ssn = ssn;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.phone = phone;
        this.email = email;
    }
    
    // Getters and Setters
    public String getSsn() { return ssn; }
    public void setSsn(String ssn) { this.ssn = ssn; }
    
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    
    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public void display() {
        System.out.println(toString());
    }
    
    @Override
    public String toString() {
        return String.format("Employee [SSN: %s, Name: %s %s, Birth: %s, Phone: %s, Email: %s]",
                ssn, firstName, lastName, 
                birthDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                phone, email);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Employee employee = (Employee) obj;
        return Objects.equals(ssn, employee.ssn);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(ssn);
    }
}