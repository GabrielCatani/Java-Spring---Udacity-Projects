package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ScheduleService {

    @Autowired
    ScheduleRepository scheduleRepository;


    @Autowired
    PetRepository petRepository;

    public Schedule saveSchedule(Schedule schedule) {
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> getScheduleByPetId(Long petId) {
        return scheduleRepository.findByPets_Id(petId);
    }

    public List<Schedule> getScheduleByEmployeeId(Long employeeId) {
        return scheduleRepository.findByEmployees_Id(employeeId);
    }

    public List<Schedule> getScheduleByCustomerId(Long customerId) {
        List<Pet> pets = petRepository.findByCustomer_Id(customerId);
        List<Schedule> schedules = new ArrayList<>();
        pets.forEach(pet -> {
            List<Schedule> petsSchedules = getScheduleByPetId(pet.getId());
            schedules.addAll(petsSchedules);
        });
        return schedules;
    }
}
