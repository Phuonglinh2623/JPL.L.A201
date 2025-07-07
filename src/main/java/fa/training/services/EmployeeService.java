package fa.training.services;

import fa.training.entities.Employee;
import fa.training.entities.HourlyEmployee;
import fa.training.entities.SalariedEmployee;
import fa.training.utils.Validator;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EmployeeService {
    private List<Employee> employees;
    private static final String EMPLOYEE_FILE = "employees.txt";
    
    public EmployeeService() {
        this.employees = new ArrayList<>();
        loadEmployeesFromFile();
    }
    
    public Employee createEmployee(Scanner scanner) {
        System.out.println("=== ADD NEW EMPLOYEE ===");
        
        String ssn = inputValidString(scanner, "Enter SSN: ");
        String firstName = inputValidString(scanner, "Enter First Name: ");
        String lastName = inputValidString(scanner, "Enter Last Name: ");
        
        LocalDate birthDate = inputValidDate(scanner, "Enter Birth Date (dd/MM/yyyy): ");
        String phone = inputValidPhone(scanner, "Enter Phone: ");
        String email = inputValidEmail(scanner, "Enter Email: ");
        
        System.out.println("Select Employee Type:");
        System.out.println("1. Salaried Employee");
        System.out.println("2. Hourly Employee");
        System.out.print("Your choice: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline
        
        Employee employee = null;
        
        switch (choice) {
            case 1:
                employee = createSalariedEmployee(scanner, ssn, firstName, lastName, birthDate, phone, email);
                break;
            case 2:
                employee = createHourlyEmployee(scanner, ssn, firstName, lastName, birthDate, phone, email);
                break;
            default:
                System.out.println("Invalid choice. Creating as Hourly Employee.");
                employee = createHourlyEmployee(scanner, ssn, firstName, lastName, birthDate, phone, email);
        }
        
        if (employee != null) {
            employees.add(employee);
            saveEmployeesToFile();
            System.out.println("Employee added successfully!");
        }
        
        return employee;
    }
    
    private SalariedEmployee createSalariedEmployee(Scanner scanner, String ssn, String firstName, 
                                                   String lastName, LocalDate birthDate, String phone, String email) {
        double basicSalary = inputValidDouble(scanner, "Enter Basic Salary: ");
        double commissionRate = inputValidDouble(scanner, "Enter Commission Rate (0-1): ");
        double grossSales = inputValidDouble(scanner, "Enter Gross Sales: ");
        
        return new SalariedEmployee(ssn, firstName, lastName, birthDate, phone, email,
                                   commissionRate, grossSales, basicSalary);
    }
    
    private HourlyEmployee createHourlyEmployee(Scanner scanner, String ssn, String firstName,
                                               String lastName, LocalDate birthDate, String phone, String email) {
        double wage = inputValidDouble(scanner, "Enter Hourly Wage: ");
        double workingHours = inputValidDouble(scanner, "Enter Working Hours: ");
        
        return new HourlyEmployee(ssn, firstName, lastName, birthDate, phone, email,
                                 wage, workingHours);
    }
    
    public void displayAllEmployees() {
        System.out.println("\n=== ALL EMPLOYEES ===");
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }
        
        for (Employee emp : employees) {
            emp.display();
            System.out.println();
        }
    }
    
    public void classifyEmployees() {
        System.out.println("\n=== EMPLOYEE CLASSIFICATION ===");
        int salariedCount = 0, hourlyCount = 0;
        
        for (Employee emp : employees) {
            if (emp instanceof SalariedEmployee) {
                salariedCount++;
            } else if (emp instanceof HourlyEmployee) {
                hourlyCount++;
            }
        }
        
        System.out.println("Salaried Employees: " + salariedCount);
        System.out.println("Hourly Employees: " + hourlyCount);
        System.out.println("Total Employees: " + employees.size());
    }
    
    public List<Employee> searchByName(String name) {
        List<Employee> result = new ArrayList<>();
        String searchName = name.toLowerCase().trim();
        
        for (Employee emp : employees) {
            String fullName = (emp.getFirstName() + " " + emp.getLastName()).toLowerCase();
            if (fullName.contains(searchName) || 
                emp.getFirstName().toLowerCase().contains(searchName) ||
                emp.getLastName().toLowerCase().contains(searchName)) {
                result.add(emp);
            }
        }
        
        return result;
    }
    
    public List<Employee> getEmployees() {
        return new ArrayList<>(employees);
    }
    
    // Input validation methods
    private String inputValidString(Scanner scanner, String prompt) {
        String input;
        do {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (!Validator.validateString(input)) {
                System.out.println("Invalid input. Please enter a valid string.");
            }
        } while (!Validator.validateString(input));
        return input;
    }
    
    private LocalDate inputValidDate(Scanner scanner, String prompt) {
        LocalDate date;
        do {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            date = Validator.validateAndParseDate(input);
            if (date == null) {
                System.out.println("Invalid date format. Please use dd/MM/yyyy format.");
            }
        } while (date == null);
        return date;
    }
    
    private String inputValidPhone(Scanner scanner, String prompt) {
        String phone;
        do {
            System.out.print(prompt);
            phone = scanner.nextLine().trim();
            if (!Validator.validatePhone(phone)) {
                System.out.println("Invalid phone number. Please enter at least 7 digits.");
            }
        } while (!Validator.validatePhone(phone));
        return phone;
    }
    
    private String inputValidEmail(Scanner scanner, String prompt) {
        String email;
        do {
            System.out.print(prompt);
            email = scanner.nextLine().trim();
            if (!Validator.validateEmail(email)) {
                System.out.println("Invalid email format. Please enter a valid email address.");
            }
        } while (!Validator.validateEmail(email));
        return email;
    }
    
    private double inputValidDouble(Scanner scanner, String prompt) {
        double value;
        do {
            System.out.print(prompt);
            while (!scanner.hasNextDouble()) {
                System.out.println("Invalid input. Please enter a valid number.");
                System.out.print(prompt);
                scanner.nextLine();
            }
            value = scanner.nextDouble();
            scanner.nextLine(); // consume newline
            if (!Validator.validatePositiveDouble(value)) {
                System.out.println("Please enter a positive number.");
            }
        } while (!Validator.validatePositiveDouble(value));
        return value;
    }
    
    // File I/O methods
    private void saveEmployeesToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(EMPLOYEE_FILE))) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            
            for (Employee emp : employees) {
                if (emp instanceof SalariedEmployee) {
                    SalariedEmployee se = (SalariedEmployee) emp;
                    writer.printf("SALARIED|%s|%s|%s|%s|%s|%s|%.2f|%.2f|%.2f%n",
                        se.getSsn(), se.getFirstName(), se.getLastName(),
                        se.getBirthDate().format(formatter), se.getPhone(), se.getEmail(),
                        se.getCommissionRate(), se.getGrossSales(), se.getBasicSalary());
                } else if (emp instanceof HourlyEmployee) {
                    HourlyEmployee he = (HourlyEmployee) emp;
                    writer.printf("HOURLY|%s|%s|%s|%s|%s|%s|%.2f|%.2f%n",
                        he.getSsn(), he.getFirstName(), he.getLastName(),
                        he.getBirthDate().format(formatter), he.getPhone(), he.getEmail(),
                        he.getWage(), he.getWorkingHours());
                }
            }
        } catch (IOException e) {
            System.err.println("Error saving employees to file: " + e.getMessage());
        }
    }
    
    private void loadEmployeesFromFile() {
        File file = new File(EMPLOYEE_FILE);
        if (!file.exists()) {
            return;
        }
        
        try (Scanner fileScanner = new Scanner(file)) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split("\\|");
                
                if (parts.length < 8) continue;
                
                String type = parts[0];
                String ssn = parts[1];
                String firstName = parts[2];
                String lastName = parts[3];
                LocalDate birthDate = LocalDate.parse(parts[4], formatter);
                String phone = parts[5];
                String email = parts[6];
                
                if ("SALARIED".equals(type) && parts.length >= 10) {
                    double commissionRate = Double.parseDouble(parts[7]);
                    double grossSales = Double.parseDouble(parts[8]);
                    double basicSalary = Double.parseDouble(parts[9]);
                    
                    employees.add(new SalariedEmployee(ssn, firstName, lastName, birthDate,
                                                     phone, email, commissionRate, grossSales, basicSalary));
                } else if ("HOURLY".equals(type) && parts.length >= 9) {
                    double wage = Double.parseDouble(parts[7]);
                    double workingHours = Double.parseDouble(parts[8]);
                    
                    employees.add(new HourlyEmployee(ssn, firstName, lastName, birthDate,
                                                   phone, email, wage, workingHours));
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading employees from file: " + e.getMessage());
        }
    }
}
