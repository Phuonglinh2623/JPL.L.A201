package fa.training.entities;

import java.util.ArrayList;
import java.util.List;

public class Department {
    private String departmentName;
    private List<Employee> listOfEmployee;
    
    public Department() {
        this.listOfEmployee = new ArrayList<>();
    }
    
    public Department(String departmentName) {
        this.departmentName = departmentName;
        this.listOfEmployee = new ArrayList<>();
    }
    
    // Getters and Setters
    public String getDepartmentName() { return departmentName; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }
    
    public List<Employee> getListOfEmployee() { return listOfEmployee; }
    public void setListOfEmployee(List<Employee> listOfEmployee) { this.listOfEmployee = listOfEmployee; }
    
    public void addEmployee(Employee employee) {
        if (!listOfEmployee.contains(employee)) {
            listOfEmployee.add(employee);
        }
    }
    
    public boolean removeEmployee(Employee employee) {
        return listOfEmployee.remove(employee);
    }
    
    public void display() {
        System.out.println("Department: " + departmentName);
        System.out.println("Number of employees: " + listOfEmployee.size());
        for (Employee emp : listOfEmployee) {
            emp.display();
        }
    }
    
    @Override
    public String toString() {
        return String.format("Department [Name: %s, Employees: %d]", 
                           departmentName, listOfEmployee.size());
    }
}