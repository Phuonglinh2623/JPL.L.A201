package fa.training.services;

import fa.training.entities.Department;
import fa.training.entities.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DepartmentService {
    private List<Department> departments;
    
    public DepartmentService() {
        this.departments = new ArrayList<>();
        initializeDepartments();
    }
    
    private void initializeDepartments() {
        departments.add(new Department("IT"));
        departments.add(new Department("HR"));
        departments.add(new Department("Finance"));
        departments.add(new Department("Marketing"));
    }
    
    public void assignEmployeeToDepartment(Scanner scanner, List<Employee> employees) {
        if (employees.isEmpty()) {
            System.out.println("No employees available to assign.");
            return;
        }
        
        System.out.println("=== ASSIGN EMPLOYEE TO DEPARTMENT ===");
        
        // Display employees
        System.out.println("Available employees:");
        for (int i = 0; i < employees.size(); i++) {
            System.out.printf("%d. %s %s (SSN: %s)%n", 
                            i + 1, employees.get(i).getFirstName(), 
                            employees.get(i).getLastName(), employees.get(i).getSsn());
        }
        
        System.out.print("Select employee (1-" + employees.size() + "): ");
        int empChoice = scanner.nextInt() - 1;
        scanner.nextLine(); // consume newline
        
        if (empChoice < 0 || empChoice >= employees.size()) {
            System.out.println("Invalid employee selection.");
            return;
        }
        
        Employee selectedEmployee = employees.get(empChoice);
        
        // Display departments
        System.out.println("Available departments:");
        for (int i = 0; i < departments.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, departments.get(i).getDepartmentName());
        }
        
        System.out.print("Select department (1-" + departments.size() + "): ");
        int deptChoice = scanner.nextInt() - 1;
        scanner.nextLine(); // consume newline
        
        if (deptChoice < 0 || deptChoice >= departments.size()) {
            System.out.println("Invalid department selection.");
            return;
        }
        
        Department selectedDepartment = departments.get(deptChoice);
        selectedDepartment.addEmployee(selectedEmployee);
        
        System.out.printf("Employee %s %s assigned to %s department successfully!%n",
                         selectedEmployee.getFirstName(), selectedEmployee.getLastName(),
                         selectedDepartment.getDepartmentName());
    }
    
    public List<Employee> searchEmployeesByDepartment(String departmentName) {
        for (Department dept : departments) {
            if (dept.getDepartmentName().equalsIgnoreCase(departmentName)) {
                return dept.getListOfEmployee();
            }
        }
        return new ArrayList<>();
    }
    
    public void displayDepartmentReport() {
        System.out.println("\n=== DEPARTMENT REPORT ===");
        for (Department dept : departments) {
            System.out.printf("Department: %s - Employees: %d%n", 
                            dept.getDepartmentName(), dept.getListOfEmployee().size());
        }
    }
    
    public void displayAllDepartments() {
        System.out.println("\n=== ALL DEPARTMENTS ===");
        for (Department dept : departments) {
            dept.display();
            System.out.println();
        }
    }
    
    public List<Department> getDepartments() {
        return new ArrayList<>(departments);
    }
}
