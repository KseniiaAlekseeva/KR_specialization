package org.example.Controller;

import org.example.Model.PetType;
import org.example.View.ConsoleView;

import java.util.Scanner;

public class ConsoleMenu {
    Controller controller;
    ConsoleView view;

    public ConsoleMenu(Controller controller) {
        this.controller = controller;
        this.view = new ConsoleView();
    }

    public void start() {

        try (Scanner in = new Scanner(System.in); Counter count = new Counter()) {
            boolean flag = true;
            Integer id;

            while (flag) {
                System.out.println("\n1 - Show animal list\n2 - Add new animal\n3 - Show animal\n4 - Show animal commands\n5 - Add animal command\n0 - Exit\nEnter: ");
                String str = in.next();
                switch (str) {
                    case "1":
                        view.printPetList(controller.getPetList());
                        break;
                    case "2":
                        PetType type = choosePetType(in);
                        if (controller.addPet(type, enterPet()))
                            view.showMessage("Writing to DB - successful...");
                        break;
                    case "3":
                        while (true) {
                            id = choosePetById(in);
                            if (id == 0)
                                break;
                            if (controller.checkPetById(id)) {
                                view.printPet(controller.getPetById(id));
                                break;
                            }
                        }
                        break;
                    case "4":
                        while (true) {
                            id = choosePetById(in);
                            if (id == 0)
                                break;
                            if (controller.checkPetById(id)) {
                                view.printCommands(controller.getPetById(id));
                                break;
                            }
                        }
                        break;
                    case "5":
                        while (true) {
                            id = choosePetById(in);
                            if (id == 0)
                                break;
                            if (controller.checkPetById(id)) {
                                while (true) {
                                    String com = enterCommand(in);
                                    if (com.equals("0"))
                                        break;
                                    if (controller.addCommand(id, com)) {
                                        view.showMessage("Writing to DB - successful...");
                                        break;
                                    } else
                                        view.showMessage("This command already exists.");
                                }
                                break;
                            }
                        }
                        break;
                    case "0":
                        flag = false;
                        break;
                    default:
                        view.showMessage("There is no such option.");
                        break;
                }
            }
        }

    }

    private PetType choosePetType(Scanner in) {
        while (true) {
            System.out.println("\nEnter pet type: \n1 - Cat\n2 - Dog\n3 - Hamster\n0 - Exit");
            String str = in.next();
            switch (str) {
                case "1":
                    return PetType.CAT;
                case "2":
                    return PetType.DOG;
                case "3":
                    return PetType.HAMSTER;
                case "0":
                    return null;
                default:
                    view.showMessage("There is no such option.");
                    break;
            }
        }
    }

    private String[] enterPet() {
        Scanner in = new Scanner(System.in);
        System.out.println("\nEnter animal name: ");
        String name = in.nextLine();
        System.out.println("\nEnter animal birthday yyyy-dd-mm: ");
        String birthday = in.nextLine();
        System.out.println("\nEnter animal commands: ");
        String commands = in.nextLine();
        String[] params = {name, birthday, commands};
        return params;
    }

    private int choosePetById(Scanner in) {
        while (true) {
            System.out.println("\nEnter pet ID or 0 - exit to main menu: ");
            Integer id = in.nextInt();
            in.nextLine();
            return id;
        }
    }

    private String enterCommand(Scanner in) {
        System.out.println("\nEnter new command, or 0 - exit to main menu: ");
        String str = in.nextLine();
        return str;
    }
}