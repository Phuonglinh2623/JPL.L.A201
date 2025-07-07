package fa.training.main;

import fa.training.entities.Employee;
import fa.training.services.DepartmentService;
import fa.training.services.EmployeeService;

import java.util.List;
import java.util.Scanner;

public class EmployeeManagement {
    private EmployeeService employeeService;
    private DepartmentService departmentService;
    private Scanner scanner;
    
    public EmployeeManagement() {
        this.employeeService = new EmployeeService();
        this.departmentService = new DepartmentService();
        this.scanner = new Scanner(System.in);
    }
    
    public void run() {
        System.out.println("Welcome to HR Management System!");
        
        while (true) {
            displayMenu();
            int choice = getChoice();
            
            switch (choice) {
                case 1:
                    addEmployee();
                    break;
                case 2:
                    displayEmployees();
                    break;
                case 3:
                    classifyEmployees();
                    break;
                case 4:
                    searchEmployees();
                    break;
                case 0:
                    System.out.println("Thank you for using HR Management System!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            
            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
        }
    }
    
    private void displayMenu() {
        System.out.println("\n====== EMPLOYEE MANAGEMENT SYSTEM ======");
        System.out.println("1. Add an employee");
        System.out.println("2. Display employees");
        System.out.println("3. Classify employees");
        System.out.println("4. Search book by (department, emp's name)");
        System.out.println("0. Exit");
        System.out.println("Please choose function you'd like to do:");
    }
    
    private int getChoice() {
        System.out.print("Your choice: ");
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            System.out.print("Your choice: ");
            scanner.nextLine();
        }
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline
        return choice;
    }
    
    private void addEmployee() {
        employeeService.createEmployee(scanner);
    }
    
    private void displayEmployees() {
        employeeService.displayAllEmployees();
    }
    
    private void classifyEmployees() {
        employeeService.classifyEmployees();
    }
    
    private void searchEmployees() {
        System.out.println("\n=== SEARCH EMPLOYEES ===");
        System.out.println("1. Search by department name");
        System.out.println("2. Search by employee name");
        System.out.print("Your choice: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline
        
        switch (choice) {
            case 1:
                searchByDepartment();
                break;
            case 2:
                searchByEmployeeName();
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }
    
    private void searchByDepartment() {
        System.out.print("Enter department name: ");
        String departmentName = scanner.nextLine().trim();
        
        List<Employee> employees = departmentService.searchEmployeesByDepartment(departmentName);
        
        if (employees.isEmpty()) {
            System.out.println("No employees found in department: " + departmentName);
        } else {
            System.out.println("\nEmployees in " + departmentName + " department:");
            for (Employee emp : employees) {
                emp.display();
            }
        }
    }
    
    private void searchByEmployeeName() {
        System.out.print("Enter employee name: ");
        String employeeName = scanner.nextLine().trim();
        
        List<Employee> employees = employeeService.searchByName(employeeName);
        
        if (employees.isEmpty()) {
            System.out.println("No employees found with name: " + employeeName);
        } else {
            System.out.println("\nEmployees with name containing '" + employeeName + "':");
            for (Employee emp : employees) {
                emp.display();
            }
        }
    }
    
    public static void main(String[] args) {
        EmployeeManagement management = new EmployeeManagement();
        management.run();
    }
}