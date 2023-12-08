package org.example.Model;

import java.sql.Date;

public abstract class Pet implements Animal {
    protected Integer id;
    protected String name;
    protected Date birthday;
    protected String commands;
    PetType type;

    public Pet(PetType type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getCommands() {
        return commands;
    }

    public void setCommands(String commands) {
        this.commands = commands;
    }

    public PetType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                ", commands='" + commands + '\'' +
                ", type=" + type +
                '}';
    }

}
