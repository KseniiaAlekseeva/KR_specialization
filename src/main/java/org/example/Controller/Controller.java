package org.example.Controller;

import org.example.Model.Pet;
import org.example.Service.IDataBase;

import java.util.List;

public class Controller {
    private final IDataBase<Pet> petDB;

    public Controller(IDataBase<Pet> petDB) {
        this.petDB = petDB;
    }

    public void getPetList() {
        //try {
            List<Pet> list = petDB.getList();
            for (Pet pet : list) {
                System.out.println(pet.toString());
            }
            //view.printAll(petRepository.getAll(), Pet.class);
       // } catch (RuntimeException e) {
            //view.showMessage(e.getMessage());
       // }
    }
}
