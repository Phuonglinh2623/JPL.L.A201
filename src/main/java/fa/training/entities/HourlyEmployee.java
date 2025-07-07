package fa.training.entities;

import java.time.LocalDate;

public class HourlyEmployee extends Employee {
    private double wage;
    private double workingHours;
    
    public HourlyEmployee() {}
    
    public HourlyEmployee(String ssn, String firstName, String lastName,
                         LocalDate birthDate, String phone, String email,
                         double wage, double workingHours) {
        super(ssn, firstName, lastName, birthDate, phone, email);
        this.wage = wage;
        this.workingHours = workingHours;
    }
    
    // Getters and Setters
    public double getWage() { return wage; }
    public void setWage(double wage) { this.wage = wage; }
    
    public double getWorkingHours() { return workingHours; }
    public void setWorkingHours(double workingHours) { this.workingHours = workingHours; }
    
    @Override
    public double getPaymentAmount() {
        if (workingHours <= 40) {
            return wage * workingHours;
        } else {
            return wage * 40 + (workingHours - 40) * wage * 1.5;
        }
    }
    
    @Override
    public String toString() {
        return super.toString() + 
               String.format(" [Hourly - Wage: %.2f, Hours: %.2f, Total: %.2f]",
                      wage, workingHours, getPaymentAmount());
    }
}
