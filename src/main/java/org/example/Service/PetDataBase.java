package org.example.Service;

import org.example.Model.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PetDataBase implements IDataBase<Pet> {
    private List<Pet> petList = new ArrayList<>();
    private Statement state;
    private String query;
    private ResultSet response;

    public static Connection getConnection() throws SQLException, IOException {

        Properties props = new Properties();
        try (FileInputStream file = new FileInputStream("./src/main/java/org/example/Resource/DataBase.props")) {
            props.load(file);
            String url = props.getProperty("url");
            String username = props.getProperty("username");
            String password = props.getProperty("password");
            return DriverManager.getConnection(url, username, password);
        }
    }

    private Pet createPet(PetType type) {
        switch (type) {
            case CAT:
                return new Cat();
            case DOG:
                return new Dog();
            case HAMSTER:
                return new Hamster();
            default:
                return null;
        }
    }

    public List<Pet> getPetList() {
        updateList();
        return petList;
    }

    @Override
    public void updateList() {
        this.petList.clear();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection dbConnection = getConnection()) {
                state = dbConnection.createStatement();
                query = "SELECT id, name, birthday, commands, subtype FROM all_pets ORDER BY id";
                response = state.executeQuery(query);
                while (response.next()) {
                    int id = response.getInt(1);
                    String name = response.getString(2);
                    Date birthday = response.getDate(3);
                    String commands = response.getString(4);
                    PetType type = PetType.getType(response.getString(5));
                    Pet pet = createPet(type);
                    pet.setId(id);
                    pet.setName(name);
                    pet.setBirthday(birthday);
                    pet.setCommands(commands);
                    this.petList.add(pet);
                }
            }
        } catch (ClassNotFoundException | IOException | SQLException ex) {
            Logger.getLogger(PetDataBase.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex.getMessage());
        }
    }

    public void add(PetType type, String name, Date birthday, String commands) {
        Pet pet = createPet(type);
        pet.setName(name);
        pet.setBirthday(birthday);
        pet.setCommands(commands);
        int rows;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection dbConnection = getConnection()) {
                query = "INSERT INTO all_pets (name, birthday, commands, subtype) VALUES (?, ?, ?, ?)";
                PreparedStatement pState = dbConnection.prepareStatement(query);
                pState.setString(1, pet.getName());
                pState.setDate(2, (Date) pet.getBirthday());
                pState.setString(3, pet.getCommands());
                pState.setString(4, pet.getType().toString().toLowerCase());
                rows = pState.executeUpdate();
                this.updateList();
            }
        } catch (ClassNotFoundException | IOException | SQLException ex) {
            Logger.getLogger(PetDataBase.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex.getMessage());
        }
    }

    @Override
    public Pet getById(Integer id) {
        updateList();
        for (Pet pet : petList) {
            if (pet.getId() == id)
                return pet;
        }
        return null;
    }

    public void update(Integer id, PetType type, String name, Date birthday, String commands) {
        Pet pet = createPet(type);
        pet.setId(id);
        pet.setName(name);
        pet.setBirthday(birthday);
        pet.setCommands(commands);
        int rows;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection dbConnection = getConnection()) {
                query = "UPDATE all_pets SET name = ?, birthday = ?, commands = ?, subtype = ? WHERE id = ?";
                PreparedStatement pState = dbConnection.prepareStatement(query);
                pState.setString(1, pet.getName());
                pState.setDate(2, (Date) pet.getBirthday());
                pState.setString(3, pet.getCommands());
                pState.setString(4, pet.getType().toString().toLowerCase());
                pState.setInt(5, pet.getId());
                rows = pState.executeUpdate();
                this.updateList();
            }
        } catch (ClassNotFoundException | IOException | SQLException ex) {
            Logger.getLogger(PetDataBase.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex.getMessage());
        }
    }
//    public void addCommand (int id, String command){
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            try (Connection dbConnection = getConnection()) {
//                String SQLstr = "INSERT INTO pet_command (PetId, CommandId) SELECT ?, (SELECT Id FROM commands WHERE Command_name = ?)";
//                PreparedStatement prepSt = dbConnection.prepareStatement(SQLstr);
//                prepSt.setInt(1, id);
//                prepSt.setString(2, command);
//
//                prepSt.executeUpdate();
//            }
//        } catch (ClassNotFoundException | IOException | SQLException ex) {
//            Logger.getLogger(PetRepository.class.getName()).log(Level.SEVERE, null, ex);
//            throw new RuntimeException(ex.getMessage());
//        }
//    }


//    @Override
//    public Pet getById(Integer id) {
//        return null;
//    }
//
//    @Override
//    public void create() {
//
//    }
//
//    @Override
//    public void delete() {
//
//    }
}
