package com.meliksah.restdeneme.client;

import com.google.gson.Gson;
import com.meliksah.restdeneme.server.domain.Employee;
import com.meliksah.restdeneme.server.enums.Title;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.Map;
import java.util.logging.Logger;

public class EmployeeClient {

    private static WebResource webResource;
    private static Client client;

    private static final String BASE_URI = "http://localhost:8080/RestDeneme/resources/employees";
    private static final Logger logger = Logger.getLogger(EmployeeClient.class.getName());

    static {
        DefaultClientConfig defaultClientConfig = new DefaultClientConfig();
        defaultClientConfig.getClasses().add(JacksonJsonProvider.class);
        client = Client.create(defaultClientConfig);
        webResource = client.resource(BASE_URI);
    }

    public static void main(String[] args) throws IOException {

//        System.out.println("Update Employee");
//        updateEmployeeAge();
//        System.out.println("=========================");

//        System.out.println("Delete Employee");
//        deleteEmployee();
//        System.out.println("=========================");

        System.out.println("All Employees");
        Map<String, Employee> allEmployees = getAllEmployees();
        Gson gson = new Gson();
        String json = gson.toJson(allEmployees);
        System.out.println(json);
        System.out.println("=========================");
//        allEmployees.forEach((tcKimlikNo, employee) -> System.out.println(tcKimlikNo + " " + employee));//todo burasında hata alıyorum.

//        System.out.println("Find Employee");
//        findEmployee();
//        System.out.println("=========================");
//
//        System.out.println("Create Employee By Entity");
//        createEmployee();
//        System.out.println("=========================");

//        System.out.println("Change Employee");
//        changeEmployee();
//        System.out.println("=========================");
//


//        System.out.println("Options Method");
//        getOptions();
//        System.out.println("=========================");

    }

    public static Map<String, Employee> getAllEmployees() {
        ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        int status = response.getStatus();
        if (status == 200) {
            return response.getEntity(Map.class);
        } else {
            return Collections.emptyMap();
        }
    }

    public static void findEmployee() {
        Long tcKimlikNo = 0L;
        ClientResponse response = webResource
                .path(MessageFormat.format("{0}", new Long[]{tcKimlikNo}))
                .accept(MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);

        Employee responseEntity = response.getEntity(Employee.class);
        System.out.println(" Find Searching Result -> " + responseEntity);
    }

    public static void createEmployee() {
        String tcKimlikNo = "3";
        Employee newEmployee = new Employee("Osman", "Ekeroğlu", 26L, Title.JUNIOR_DEVELOPER);

        ClientResponse response = webResource
                .path(MessageFormat.format("{0}", new String[]{tcKimlikNo}))
                .entity(newEmployee, MediaType.APPLICATION_JSON)
                .post(ClientResponse.class);
        int status = response.getStatus();
        System.out.println("status -> " + status);
        if (status == 201) {
            System.out.println("URL -> " + response.getLocation());
            Employee employee = response.getEntity(Employee.class);
            System.out.println("New Resource ->" + employee.getName() + "," + employee.getSurName());
        } else {
            String entityAsString = response.getEntity(String.class);
            System.out.println(entityAsString);
        }
    }

    public static void changeEmployee() {
        Employee employee = new Employee("Melikşah", "Selvi", 25L, Title.JUNIOR_DEVELOPER);
        String tcKimlikNo = "0";
        ClientResponse response = webResource
                .path(MessageFormat.format("{0}", new String[]{tcKimlikNo}))
                .entity(employee, MediaType.APPLICATION_JSON)
                .put(ClientResponse.class);
        int status = response.getStatus();
        System.out.println("Status -> " + status);
        if (status == 200) {
            System.out.println("Change Is Successfull");
        } else if (status == 201) {
            System.out.println("URL -> " + response.getLocation());
            Employee responseEntity = response.getEntity(Employee.class);
            System.out.println("Newly created resource" + responseEntity);
        } else {
            String entityAsString = response.getEntity(String.class);
            System.out.println("Resource is not replaced : " + entityAsString);
        }

    }

    public static void updateEmployeeAge() {
        String tcKimlikNo = "0";
        Long age = 25L;
        ClientResponse response = webResource
                .path(MessageFormat.format("{0}/{1}", new Object[]{tcKimlikNo, age}))
                .accept(MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);

        System.out.println(response.getLocation());
        int status = response.getStatus();
        if (status == 200) {
            System.out.println("Update Is Successfull");
        } else if (status == 409) {
            String entityAsString = response.getEntity(String.class);
            System.out.println("Resource is not updated : " + entityAsString);
        }
    }

    public static void deleteEmployee() {
        String tcKimlikNo = "0";
        ClientResponse response = webResource
                .path(MessageFormat.format("{0}", new String[]{tcKimlikNo}))
                .accept(MediaType.APPLICATION_JSON)
                .delete(ClientResponse.class);
        System.out.println(response.getLocation());

        int status = response.getStatus();
        String entityAsString = response.getEntity(String.class);
        if (status == 200) {
            System.out.println("Resource is deleted" + entityAsString);
        } else if (status == 409) {
            System.out.println("Error : " + entityAsString);
        }
    }

    public static void getOptions() {
        ClientResponse response = webResource
                .accept(MediaType.TEXT_PLAIN)
                .options(ClientResponse.class);
        int status = response.getStatus();
        System.out.println("Status -> " + status);
        String entityAsString = response.getEntity(String.class);
        if (status == 200) {
            System.out.println("Allow Header : " + entityAsString);
        } else {
            System.out.println("OPTIONS method is not allowed this server");
        }
    }
}
