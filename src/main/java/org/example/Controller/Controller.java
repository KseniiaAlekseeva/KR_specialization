package org.example.Controller;

import org.example.Model.Pet;
import org.example.Model.PetType;
import org.example.Service.PetDataBase;

import java.sql.Date;
import java.util.List;

public class Controller {
    private final PetDataBase petDB;

    public Controller(PetDataBase petDB) {
        this.petDB = petDB;
    }

    public void addPet(PetType type, String[] params) {
        try (Counter count = new Counter()) {
            String[] date = params[1].split("-");
            Date birthday = new Date(Integer.parseInt(date[0]) - 1900, Integer.parseInt(date[1]) - 1, Integer.parseInt(date[2]));
                petDB.add(type, params[0], birthday, params[2]);
                count.add();
        }
    }

    public boolean checkPetById(Integer id) {
        Pet pet = petDB.getById(id);
        if (pet == null) {
            System.out.println("No pet with that ID.");
            return false;
        }
        return true;
    }

    public boolean addCommand(Integer id, String com) {
        Pet pet = petDB.getById(id);
        if (pet.getCommands().contains(com))
            return false;
        petDB.update(id, pet.getType(), pet.getName(), pet.getBirthday(), pet.getCommands() + ", " + com);
        return true;
    }

    public Pet getPetById(Integer id){
        return petDB.getById(id);
    }

    public List<Pet> getPetList(){
        return petDB.getPetList();
    }
}
