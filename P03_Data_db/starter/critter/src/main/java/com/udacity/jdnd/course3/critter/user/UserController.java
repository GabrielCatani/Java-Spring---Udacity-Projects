package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.Pet;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.beans.Beans;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    CustomerService customerService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        return customerToDTO(customerService.save(DTOToCustomer(customerDTO)));
    }

    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<Customer> customers = customerService.getAll();
        List<CustomerDTO> customerDTOs = new ArrayList<>();
        customers.forEach(customer -> {
            customerDTOs.add(customerToDTO(customer));
        });
        return customerDTOs;
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        return customerToDTO(customerService.getOwnerByPetId(petId));
    }

    private CustomerDTO customerToDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        List<Long> petIds = new ArrayList<>();
        BeanUtils.copyProperties(customer, customerDTO);
        if(customer.getPets() != null) {
            customer.getPets().forEach(pet -> {
                petIds.add(pet.getId());
            });
        }
        customerDTO.setPetIds(petIds);
        return customerDTO;
    }

    private Customer DTOToCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerDTO, customer);
        customer.setPets(new ArrayList<>());
        if(customerDTO.getPetIds() != null && customerDTO.getPetIds().size() > 0) {
            customerDTO.getPetIds().forEach(id -> {
                Pet pet = new Pet();
                pet.setId(id);
                customer.getPets().add(pet);
            });
        }
        return customer;
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        return employeeToDTO(employeeService.save(DTOToEmployee(employeeDTO)));
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        return employeeToDTO(employeeService.getEmployeeById(employeeId));

    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        employeeService.setEmployeeAvailability(daysAvailable, employeeId);
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        List <Employee> employees = employeeService.findEmployeesByRequest(employeeDTO);
        List <EmployeeDTO> employeeDTOS = new ArrayList<>();
        employees.forEach(employee -> {
            employeeDTOS.add(employeeToDTO(employee));
        });

        return employeeDTOS;
    }

    private EmployeeDTO employeeToDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        BeanUtils.copyProperties(employee, employeeDTO);

        if(employee.getSkills() != null && employee.getSkills().size() > 0) {
            Set<EmployeeSkill> skills = new HashSet<>(employee.getSkills());
            employeeDTO.setSkills(skills);
        }

        if(employee.getDaysAvailable() != null && employee.getDaysAvailable().size() > 0) {
            Set<DayOfWeek> availability = new HashSet<>(employee.getDaysAvailable());
            employeeDTO.setDaysAvailable(availability);
        }

        return employeeDTO;
    }

    private Employee DTOToEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);

        if(employeeDTO.getSkills() != null && employeeDTO.getSkills().size() > 0) {
            Set<EmployeeSkill> skills = new HashSet<>(employeeDTO.getSkills());
            employee.setSkills(skills);
        }

        if(employeeDTO.getDaysAvailable() != null && employeeDTO.getDaysAvailable().size() > 0) {
            Set<DayOfWeek> availability = new HashSet<>(employeeDTO.getDaysAvailable());
            employee.setDaysAvailable(availability);
        }

        return employee;
    }
}
