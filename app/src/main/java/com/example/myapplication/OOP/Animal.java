package com.example.myapplication.OOP;

abstract class Animal {
    public abstract void animalSound(); // trừu tượng

    // Không trừu tượng
    public void sleep() {
        System.out.println("Zzz");
    }
}
