package org.example.Service;

import org.example.Model.Pet;
import org.example.Model.PetType;

import java.sql.Connection;
import java.sql.*;

import java.util.List;

public interface IDataBase <E> {
    List<E> getPetList();
    void updateList();
    E getById(Integer id);

}
