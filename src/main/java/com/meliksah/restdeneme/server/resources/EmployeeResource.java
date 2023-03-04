package com.meliksah.restdeneme.server.resources;

import com.meliksah.restdeneme.server.domain.Employee;
import com.meliksah.restdeneme.server.repository.EmployeeRepository;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

@Path("employees")
@Produces(MediaType.APPLICATION_JSON)
public class EmployeeResource {


    @GET
    public Map<String, Employee> getAllEmployees() {
        EmployeeRepository.getAllEmployees();
        return EmployeeRepository.getAllEmployees();
    }

    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Path("{tcKimlikNo}")
    public Response findEmployeeByTcKimlikNo(@PathParam("tcKimlikNo") String tcKimlikNo) {
        Employee employeeByTcKimlikNo = EmployeeRepository.findEmployeeByTcKimlikNo(tcKimlikNo);

        if (employeeByTcKimlikNo != null) {
            return Response.ok(employeeByTcKimlikNo).build();
        } else {
            return Response.noContent().build();
        }
    }

    @POST
    @Path("{tcKimlikNo}")
    public Response createEmployeeByEntity(@PathParam("tcKimlikNo") String tcKimlikNo, Employee employee, @Context UriInfo uriInfo) {

        boolean addEmployeeIsSuccess = EmployeeRepository.addEmployee(tcKimlikNo, employee);

        if (addEmployeeIsSuccess) {
            String requestUriString = uriInfo.getAbsolutePath().getPath();
            int index = requestUriString.lastIndexOf('/');
            String newUriString = requestUriString.substring(0, index);
            URI uri = null;
            try {
                uri = new URI(newUriString + "/findEmployee/" + tcKimlikNo);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            return Response.created(uri).entity(employee).build();
        }
        return Response.status(409).entity("New resource hasn't been created because it already exists!").build();
    }

    @PUT
    @Path("{tcKimlikNo}")
    public Response changeEmployeeByEntity(@PathParam("tcKimlikNo") String tcKimlikNo, Employee employee, @Context UriInfo uriInfo) {
        boolean isExist = EmployeeRepository.isExistEmployeeByTcKimlikNo(tcKimlikNo);
        if (isExist) {

            if (EmployeeRepository.isSameEmployee(tcKimlikNo, employee)) {
                return Response.status(409).entity("Exact resource entity already exist!").build();
            } else {

                boolean changeEmployeeIsSuccess = EmployeeRepository.changeEmployee(tcKimlikNo, employee);
                if (changeEmployeeIsSuccess) {
                    return Response.ok().build();
                } else {
                    return Response.status(409).entity("A problem occurred during replacement!").build();
                }
            }

        } else {//new resource

            boolean addEmployeeIsSuccess = EmployeeRepository.addEmployee(tcKimlikNo, employee);

            if (addEmployeeIsSuccess) {
                String requestUriString = uriInfo.getAbsolutePath().getPath();
                int index = requestUriString.lastIndexOf('/');
                String newUriString = requestUriString.substring(0, index);
                URI uri = null;
                try {
                    uri = new URI(newUriString + "/findEmployee/" + tcKimlikNo);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                return Response.created(uri).entity(employee).build();
            } else {
                return Response.status(409).entity("A problem occurred during creating!").build();
            }
        }
    }


    @GET//PATCH
    @Path("{tcKimlikNo}/{age}")
    public Response updateEmployeeAge(@PathParam("tcKimlikNo") String tcKimlikNo, @PathParam("age") Long age) {
        boolean isExist = EmployeeRepository.isExistEmployeeByTcKimlikNo(tcKimlikNo);
        if (isExist) {

            if (EmployeeRepository.isSameAgesByTcKimlikNo(tcKimlikNo, age)) {
                return Response.status(409).entity("Exact resource age already exist!").build();
            } else {
                EmployeeRepository.updateEmployeeAge(tcKimlikNo, age);
                return Response.ok().build();
            }
        } else {
            return Response.status(409).entity("No such entity exists!").build();
        }
    }

    @DELETE
    @Consumes(MediaType.TEXT_PLAIN)
    @Path("{tcKimlikNo}")
    public Response deleteEmployeeByTcKimlikNo(@PathParam("tcKimlikNo") String tcKimlikNo) {

        boolean deleteEmployeeIsSuccess = EmployeeRepository.deleteEmployeeByTcKimlikNo(tcKimlikNo);

        if (deleteEmployeeIsSuccess) {
            return Response.status(200).entity("Employee has been deleted").build();
        } else {
            return Response.status(409).entity("No such Employee exists!").build();
        }
    }

    @OPTIONS
    @Produces(MediaType.TEXT_PLAIN)
    public Response getOptions() {
        return Response.ok("GET,HEAD,POST,PUT,DELETE,OPTIONS").build();
    }
}
