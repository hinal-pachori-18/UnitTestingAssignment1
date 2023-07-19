package com.example.PayRollService.controller;

import com.example.PayRollService.entity.Employee;
import com.example.PayRollService.exception.EmployeeNotFoundException;
import com.example.PayRollService.service.EmployeeService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EmployeeService employeeService;

    Employee employee1;
    Employee employee2;
    List<Employee> employeeList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        employee1 = new Employee(1, "lavish", 50000);
        employee2 = new Employee(2, "Avish", 60000);
        employeeList.add(employee1);
        employeeList.add(employee2);
    }

    @Nested
    class GetAllEmployee {
        @Test
        void testGetAllEmployees_PositiveScenario() throws Exception {
            when(employeeService.getAllEmployee()).thenReturn(employeeList);
            mockMvc.perform(get("/api/v1/emp")).andDo(print()).andExpect(status().isFound());
        }

        @Test
        void testGetAllEmployees_NegativeScenario() throws Exception {
            when(employeeService.getAllEmployee()).thenThrow(new EmployeeNotFoundException());
            mockMvc.perform(get("/api/v1/emp")).andDo(print()).andExpect(status().isNotFound());
        }
    }

    @Nested
    class GetEmployeeById {
        @Test
        void testGetEmployeeById_PositiveScenario() throws Exception {
            when(employeeService.getEmployeeById(2)).thenReturn(employee2);
            mockMvc.perform(get("/api/v1/emp/id/2")).andDo(print()).andExpect(status().isFound());
        }

        @Test
        void testGetEmployeeById_NegativeScenario() throws Exception {
            when(employeeService.getEmployeeById(7)).thenThrow(new EmployeeNotFoundException(7));
            mockMvc.perform(get("/api/v1/emp/id/7")).andDo(print()).andExpect(status().isNotFound());
        }
    }

    @Nested
    class getEmployeeByName {
        @Test
        void getEmployeeByName_PositiveScenario() throws Exception {
            when(employeeService.getEmployeeByName("lavish")).thenReturn(employee1);
            mockMvc.perform(get("/api/v1/emp/name/lavish")).andDo(print()).andExpect(status().isFound());
        }

        @Test
        void getEmployeeByName_NegativeScenario() throws Exception {
            when(employeeService.getEmployeeByName("manish")).thenThrow(new EmployeeNotFoundException("manish"));
            mockMvc.perform(get("/api/v1/emp/name/manish")).andDo(print()).andExpect(status().isNotFound());
        }
    }

    @Nested
    class AddEmployee {
        @Test
        void testAddEmployee_Positive() throws Exception {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.WRAP_ROOT_VALUE,false);
            ObjectWriter ow =  mapper.writer().withDefaultPrettyPrinter();
            String requestJson = ow.writeValueAsString(employee1);

            when(employeeService.addEmployee(employee1)).thenReturn(employee1);
            mockMvc.perform(post("/api/v1/emp").contentType(MediaType.APPLICATION_JSON)
                            .content(requestJson)).
                    andDo(print()).andExpect(status().isCreated());

        }
        @Test
        void testAddEmployee_Negative() throws Exception {
            //Convert and object into Json
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.WRAP_ROOT_VALUE,false);
            ObjectWriter ow =  mapper.writer().withDefaultPrettyPrinter();
            String requestJson = ow.writeValueAsString(null);

            when(employeeService.addEmployee(employee1)).thenReturn(null);
            mockMvc.perform(post("/api/v1/emp").contentType(MediaType.APPLICATION_JSON)
                            .content(requestJson)).
                    andDo(print()).andExpect(status().isBadRequest());

        }
    }

    @Nested
    class UpdateEmployee {
        @Test
        void testUpdateEmployee_PositiveScenario() throws Exception{
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.WRAP_ROOT_VALUE,false);
            ObjectWriter ow =  mapper.writer().withDefaultPrettyPrinter();
            String requestJson = ow.writeValueAsString(employee1);

            when(employeeService.updateEmployee(1,employee1)).thenReturn(employee1);
            mockMvc.perform(put("/api/v1/emp/1").contentType(MediaType.APPLICATION_JSON)
                            .content(requestJson)).
                    andDo(print()).andExpect(status().isOk());
        }

         @Test
        void testUpdateEmployee_NegativeScenario() throws Exception{
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(SerializationFeature.WRAP_ROOT_VALUE,false);
            ObjectWriter ow =  mapper.writer().withDefaultPrettyPrinter();
            String requestJson = ow.writeValueAsString(null);

            when(employeeService.updateEmployee(1,employee2)).thenReturn(employee2);
            mockMvc.perform(put("/api/v1/emp/1").contentType(MediaType.APPLICATION_JSON)
                            .content(requestJson)).
                    andDo(print()).andExpect(status().isBadRequest());
        }
    }

    @Nested
    class DeleteEmployee {
        @Test
        void testDeleteEmployee_PositiveScenario() throws Exception {
            when(employeeService.deleteEmployee(1)).thenReturn("Employee Successfully deleted");
            mockMvc.perform(delete("/api/v1/emp/1")).andDo(print()).andExpect(status().isOk());
        }

        @Test
        void testDeleteEmployee_NegativeScenario() throws Exception {
            when(employeeService.deleteEmployee(20)).thenThrow(new EmployeeNotFoundException(20));
            mockMvc.perform(delete("/api/v1/emp/20")).andDo(print()).andExpect(status().isNotFound());
        }
    }
}