package by.rest.petstore.service;

import by.rest.petstore.model.Pet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class PetService {
    private Map<Long, Pet> petsMap;

    public PetService(Map<Long, Pet> petsMap) {
        this.petsMap = petsMap;
    }

    public Map<Long, Pet> getPetsMap() {
        return petsMap;
    }

    public void petUpdateById(Long id, String name, Pet.Status status) {
        petsMap.get(id).setName(name);
        petsMap.get(id).setStatus(status);
    }

    public void addPet(Pet pet) {
        petsMap.put(pet.getId(), pet);
    }

    public boolean updatePet(Pet pet) {
        if (!petsMap.containsKey(pet.getId())) return false;
        petsMap.put(pet.getId(), pet);
        return true;
    }

    public List<Pet> findByStatus(Pet.Status status) {
        List<Pet> foundPets = new ArrayList<>();
        for (Pet pet: petsMap.values()) {
            if (pet.getStatus().equals(status)) {
                foundPets.add(pet);
            }
        }
        return foundPets;
    }
}
