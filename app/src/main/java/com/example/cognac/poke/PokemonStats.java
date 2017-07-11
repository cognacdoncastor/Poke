package com.example.cognac.poke;

/**
 * Created by Cognac on 7/11/2017.
 */

public class PokemonStats {
    String name;
    int number;
    int hp;
    int defense;
    int attack;
    int speed;
    int specialAttack;
    int specialDefense;
    int height,  weight;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getHp() {
        return hp;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setHp(int hp) {
        this.hp = hp;

    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpecialAttack() {
        return specialAttack;
    }

    public void setSpecialAttack(int specialAttack) {
        this.specialAttack = specialAttack;
    }

    public int getSpecialDefense() {
        return specialDefense;
    }

    public void setSpecialDefense(int specialDefense) {
        this.specialDefense = specialDefense;
    }

    public PokemonStats(String name, int number, int hp, int defense, int attack, int speed, int specialAttack, int specialDefense, int height, int weight) {

        this.name = name;
        this.number = number;
        this.hp = hp;
        this.defense = defense;
        this.attack = attack;
        this.speed = speed;
        this.specialAttack = specialAttack;
        this.specialDefense = specialDefense;
        this.height = height;
        this.weight = weight;
    }
}
