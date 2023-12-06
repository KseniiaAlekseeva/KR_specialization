package org.example.Service;

import org.example.Model.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PetDatabase implements IDataBase<Pet> {
    private List<Pet> petList = new ArrayList<>();
    private Statement state;
    private String query;
    private ResultSet response;

    public static Connection getConnection() throws SQLException, IOException {

        Properties props = new Properties();
        try (FileInputStream file = new FileInputStream("./src/main/java/org/example/Resource/DataBase.props")) {
            //props.load(file);
            //String url = props.getProperty("url");
            //String username = props.getProperty("username");
            //String password = props.getProperty("password");
            String url = "jdbc:mysql://localhost:3306/human_friends";
            String username = "root";
            String password = "root1234";
            return DriverManager.getConnection(url, username, password);
        }
    }

    @Override
    public List<Pet> getList() {
        List<Pet> list = new ArrayList<Pet>();
        Pet pet;
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
                    pet = createPet(type);
                    pet.setId(id);
                    pet.setName(name);
                    pet.setBirthday(birthday);
                    pet.setCommands(commands);
                    list.add(pet);
                }
                return list;
            }
        } catch (ClassNotFoundException | IOException | SQLException ex) {
            Logger.getLogger(PetDatabase.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex.getMessage());
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
