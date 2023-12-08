package org.example.Controller;

public class Counter implements AutoCloseable {

    static int sum = 0;


    public void add() {
        sum++;
    }

    @Override
    public void close() {
        System.out.println("New added animals - " + sum + ". Counter closed");
    }
}
