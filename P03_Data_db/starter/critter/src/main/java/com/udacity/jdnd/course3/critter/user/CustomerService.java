package com.udacity.jdnd.course3.critter.user;

import com.udacity.jdnd.course3.critter.pet.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public List<Customer> getAll() {
        return customerRepository.findAll();
    }


    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer getOwnerByPetId(Long petId) {
        return customerRepository.findByPets_Id(petId);
    }

    public void addPetToCustomer(Pet pet, Customer customer) {
        List<Pet> pets = customer.getPets();
        if(pets == null){
            pets = new ArrayList<>();
        }
        pets.add(pet);
        customer.setPets(pets);
        customerRepository.save(customer);
    }
}
