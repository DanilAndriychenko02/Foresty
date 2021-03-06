package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;

public class GoatBaby extends Animal {

    private GameScreen gameScreen;
    private ArrayList<Animal> uncaughtAnimals;
    private boolean uncaught;

    public GoatBaby(char[][] grid, SpriteBatch spriteBatch, HashSet<Point> borderPoints, LinkedHashSet<Point> tracePoints, GameScreen gameScreen) {
        super(Animal.TYPES.GOAT_BABY.getStringAnimationHashMap(), Animal.TYPES.GOAT_BABY.getAnimalXVel(), Animal.TYPES.GOAT_BABY.getAnimalYVel(), grid, spriteBatch, borderPoints, tracePoints);
        this.gameScreen = gameScreen;
        uncaughtAnimals = new ArrayList<>();
        uncaught = true;
        caughtMusic = Gdx.audio.newMusic(Gdx.files.internal("AnimalSounds\\goatBaby.mp3"));
    }

    @Override
    public void moveAndDrawAnimal() {
        if (this.animalCaught()) {
            uncaught = false;
            for (Animal animal : uncaughtAnimals) {
                if (animal.animalCaught() && animal != this){
                    super.moveAndDrawAnimal();
                    return;
                }
            }
            gameScreen.setLose(true);
        }
        super.moveAndDrawAnimal();
        if (uncaught) {
            uncaughtAnimals.clear();
            for (Animal animal : gameScreen.animals) {
                if (!animal.animalCaught()) {
                    uncaughtAnimals.add(animal);
                }
            }
        }
    }

}