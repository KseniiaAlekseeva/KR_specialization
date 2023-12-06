package org.example;

import org.example.Controller.Controller;
import org.example.Model.Pet;
import org.example.Service.IDataBase;
import org.example.Service.PetDatabase;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        IDataBase<Pet> my = new PetDatabase();
        Controller controller = new Controller(my);
        controller.getPetList();
        }
}