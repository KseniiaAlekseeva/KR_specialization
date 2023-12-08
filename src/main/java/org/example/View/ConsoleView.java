package org.example.View;

import org.example.Model.Pet;

import java.util.List;

public class ConsoleView {

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void printPetList(List<Pet> list) {
        for (Pet pet : list) {
            System.out.println(pet.toString());
        }
    }

    public void printPet(Pet pet) {
        System.out.println(pet.toString());
    }

    public void printCommands(Pet pet) {
        System.out.println(pet.getCommands());
    }
}
