package org.example.Model;

public enum PetType {
    CAT,
    DOG,
    HAMSTER;

    public static PetType getType(String type){
        switch(type.toLowerCase()){
            case "cat":
                return CAT;
            case "dog":
                return DOG;
            case "hamster":
                return HAMSTER;
            default:
                return null;
        }
    }
}
