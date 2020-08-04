package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.Customer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    PetService petService;

    @PostMapping
    public PetDTO save(@RequestBody PetDTO petDTO) {
        return petToDTO(petService.save(DTOToPet(petDTO)));
    }

    @GetMapping("/{petId}")
    public PetDTO getPetById(@PathVariable long petId) {
        return petToDTO(petService.getPetById(petId));
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<Pet> pets = petService.getAllPets();
        List<PetDTO> petDTOs = new ArrayList<>();
        pets.forEach(pet -> {
            petDTOs.add(petToDTO(pet));
        });
        return petDTOs;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwnerId(@PathVariable long ownerId) {
        List<Pet> pets = petService.getPetsByOwner(ownerId);
        List<PetDTO> petDTOs = new ArrayList<>();
        pets.forEach(pet -> {
            petDTOs.add(petToDTO(pet));
        });
        return petDTOs;
    }

    private PetDTO petToDTO(Pet pet) {
        PetDTO petDTO = new PetDTO();
        BeanUtils.copyProperties(pet, petDTO);
        petDTO.setOwnerId(pet.getCustomer().getId());
        petDTO.setType(pet.getType());
        return petDTO;
    }

    private Pet DTOToPet(PetDTO petDTO) {
        Pet pet = new Pet();
        BeanUtils.copyProperties(petDTO, pet);
        pet.setCustomer(new Customer());
        pet.getCustomer().setId(petDTO.getOwnerId());
        pet.setType(petDTO.getType());
        return pet;
    }
}
