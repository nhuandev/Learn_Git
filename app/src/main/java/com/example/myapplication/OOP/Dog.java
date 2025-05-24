package com.example.myapplication.OOP;

public class Dog extends Animal implements InterfaceTest, InterfaceTest2 {
    @Override
    public void animalSound() {
        System.out.println("gâu gâu");
    }

    @Override
    public double cong2so(double a, double b) {
        return a + b;
    }

    @Override
    public void sound() {
        System.out.println("TEST TEST");
    }

    @Override
    public double cong3so(double a, double b, double c) {
        return a + b + c;
    }
}
