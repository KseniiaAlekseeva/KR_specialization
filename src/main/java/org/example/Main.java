package org.example;

import org.example.Controller.ConsoleMenu;
import org.example.Controller.Controller;
import org.example.Model.PetType;
import org.example.Service.PetDataBase;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        PetDataBase my = new PetDataBase();
        Controller controller = new Controller(my);
        ConsoleMenu cm = new ConsoleMenu(controller);
        cm.start();

//        controller.printPetList();
//        System.out.println("______________");
//        controller.printPetList();
//        controller.addPet(PetType.DOG);
//        System.out.println("______________");
//        controller.printPetList();
//        System.out.println("______________");
//        controller.getPetById(3);
//        System.out.println("______________");
//        controller.addCommand(3, "sleep");
//        controller.getPetById(45);
//        controller.showCommands(3);
        }
}