package com.webServer.questbook;

import lombok.Getter;

import java.util.Optional;
import java.util.function.Function;

@Getter
public class Remove {

    private String name;
    private String lastName;

    public Remove(String name, String lastname) {
        this.name = name;
        this.lastName = lastname;
    }
    public Remove() {
    }

    public String mr(String newName) {
        return newName + " :nowe imie";
    }

    public static void main(String[] args) {
        Remove r = new Remove("asd", "dsa");
    }
}
