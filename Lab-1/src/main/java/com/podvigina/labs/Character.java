package com.podvigina.labs;

public class Character {

    private final int id;
    private final String name;

    public Character(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Character{id=" + id + ", name='" + name + "'}";
    }
}