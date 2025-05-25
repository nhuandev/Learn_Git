package com.example.myapplication.OOP;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;

public class Dog extends Animal implements InterfaceTest, InterfaceTest2 {
    public Dog(String name, int age) {
        super(name, age);
    }

    public Dog() {
        super();
    }

    @Override
    public void animalSound() {
        System.out.println("gâu gâu");
    }

//    @Override
//    public void sleep() {
//        System.out.println("S SSS ");
//    }

    @Override
    public double cong2so(double a, double b) {
        return a + b;
    }

    @Override
    public void sound() {
        System.out.println("TEST TEST");
    }

    @Override
    public void sound222() {
        System.out.println("TEST TEST 2");
    }

    @Override
    public void sound3() {
        System.out.println("TEST TEST 3");
    }

    @Override
    public double cong3so(double a, double b, double c) {
        return a + b + c;
    }

    public static void main(String[] args) {

        //Set
        Set<String> hashSet = new HashSet<>();
        hashSet.add("Hello1");
        hashSet.add("Hello");
        System.out.println(hashSet);

        Set<String> linkedHashSet = new LinkedHashSet<>();
        linkedHashSet.add("Hello1");
        linkedHashSet.add("Hello");
        System.out.println(linkedHashSet);

        Set<Dog> dogSet = new HashSet<>();
        dogSet.add(new Dog("Dog2", 20));
        dogSet.add(new Dog("Dog2", 20));
        dogSet.add(new Dog("Dog3", 30));
        System.out.println(dogSet);

        // Map
        // Không cho key trùng lặp
        Map<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key12", "value12");
        map.put("key3", "value3");
        System.out.println(map);

        Map<String, Dog> dogMap = new HashMap<>();
        dogMap.put("Dog1", new Dog("Dog1", 10));
        dogMap.put("Dog2", new Dog("Dog2", 20));
        dogMap.put("Dog3", new Dog("Dog3", 30));
        System.out.println(dogMap);

        // Treemap
        // Sắp xếp theo thứ tự key tăng dần
        Map<Integer, Dog> dogTreeMap = new TreeMap<>();
        dogTreeMap.put(2, new Dog("Dog2", 20));
        dogTreeMap.put(1, new Dog("Dog1", 10));
        dogTreeMap.put(3, new Dog("Dog3", 30));
        System.out.println(dogTreeMap);

        // List
        List<Dog> dogList = new ArrayList<>();
        dogList.add(new Dog("Dog1", 10));
        dogList.add(new Dog("Dog2", 20));
        dogList.add(new Dog("Dog3", 30));
        System.out.println(dogList);

        List<Dog> dogList2 = new LinkedList<>();
        dogList2.add(new Dog("Dog1", 10));
        dogList2.add(new Dog("Dog2", 20));
        dogList2.add(new Dog("Dog3", 30));
        System.out.println(dogList2);

        List<Dog> dogList3 = new Vector<>();
        dogList3.add(new Dog("Dog1", 10));
        dogList3.add(new Dog("Dog2", 20));
        dogList3.add(new Dog("Dog3", 30));
        System.out.println(dogList3);
    }
}
