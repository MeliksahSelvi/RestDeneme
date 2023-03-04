package com.meliksah.restdeneme.server.repository;

import com.meliksah.restdeneme.server.domain.Employee;
import com.meliksah.restdeneme.server.enums.Title;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class EmployeeRepository {

    private static Map<String, Employee> employeeMap = new ConcurrentHashMap<>();

    static {
        employeeMap.put("0", new Employee("Melikşah", "Selvi", 25L, Title.JUNIOR_DEVELOPER));
        employeeMap.put("1", new Employee("Eray", "Gürsoy", 0L, Title.TEAM_LEAD));
        employeeMap.put("2", new Employee("Fettah", "Fettah", 0L, Title.CLEANER));
    }

    public static Map<String, Employee> getAllEmployees() {
        return employeeMap;
    }

    public static Set<String> getAllTcKimlikNo() {
        return employeeMap.keySet();
    }

    public static boolean addEmployee(String tckimlikNo, Employee employee) {
        if (isExistEmployeeByTcKimlikNo(tckimlikNo)) {
            return false;
        }
        employeeMap.put(tckimlikNo, employee);
        return true;
    }

    public static Employee findEmployeeByTcKimlikNo(String tcKimlikNo) {
        if (isExistEmployeeByTcKimlikNo(tcKimlikNo)) {
            return employeeMap.get(tcKimlikNo);
        }
        return null;
    }

    public static boolean isExistEmployeeByTcKimlikNo(String tcKimlikNo) {
        return employeeMap.containsKey(tcKimlikNo);
    }

    public static boolean isSameAgesByTcKimlikNo(String tcKimlikNo, Long age) {
        Employee employee = employeeMap.get(tcKimlikNo);
        if (employee.getAge().equals(age)) {
            return true;
        }
        return false;
    }

    public static boolean changeEmployee(String tcKimlikNo, Employee newEmployee) {
        employeeMap.replace(tcKimlikNo, newEmployee);
        return true;
    }

    public static boolean isSameEmployee(String tcKimlikNo, Employee newEmployee) {
        Employee oldEmployee = employeeMap.get(tcKimlikNo);
        if (oldEmployee.getName().equals(newEmployee.getName()) && oldEmployee.getSurName().equals(newEmployee.getSurName())) {
            return true;
        }
        return false;
    }

    public static void updateEmployeeAge(String tckimlikNo, Long age) {
        Employee employee = employeeMap.get(tckimlikNo);
        employee.setAge(age);
        employeeMap.replace(tckimlikNo, employee);
    }

    public static boolean deleteEmployeeByTcKimlikNo(String tcKimlikNo) {
        if (isExistEmployeeByTcKimlikNo(tcKimlikNo)) {
            employeeMap.remove(tcKimlikNo);
            return true;
        }
        return false;
    }
}
