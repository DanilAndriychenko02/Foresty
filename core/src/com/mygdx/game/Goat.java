package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;

public class Goat extends Animal {

    private final static int RADIUS_OF_CELLS_EATEN = 2;
    HashMap<Point, Texture> contentPoints;
    private boolean exploded = false;

    public Goat(char[][] grid, SpriteBatch spriteBatch, HashSet<Point> borderPoints, LinkedHashSet<Point> tracePoints, HashMap<Point, Texture> contentPoints) {
        super(Animal.TYPES.GOAT.getStringAnimationHashMap(), Animal.TYPES.GOAT.getAnimalXVel(), Animal.TYPES.GOAT.getAnimalYVel(), grid, spriteBatch, borderPoints, tracePoints);
        this.contentPoints = contentPoints;
        caughtMusic = Gdx.audio.newMusic(Gdx.files.internal("AnimalSounds\\sheep.mp3"));
    }


    @Override
    public void moveAndDrawAnimal() {
        if (this.animalCaught() && !exploded) {
            exploded = true;
            explode();
        }
        {
            super.moveAndDrawAnimal();
        }
    }

    private void explode() {
        int animalCurrGridX = (int) (getAnimalX() / RECT_SIZE);
        int animalCurrGridY = (int) (getAnimalY() / RECT_SIZE);
        for (int y = animalCurrGridY - RADIUS_OF_CELLS_EATEN; y <= animalCurrGridY + RADIUS_OF_CELLS_EATEN; y++) {
            for (int x = animalCurrGridX - RADIUS_OF_CELLS_EATEN; x <= animalCurrGridX + RADIUS_OF_CELLS_EATEN; x++) {
                if (x < grid[0].length && x >= 0 && y < grid.length && y >= 0) {
                    contentPoints.remove(new Point(x * RECT_SIZE, y * RECT_SIZE));
                    System.out.println(x + ", " + y);
                    grid[y][x] = '.';
                }
            }
        }
        for (int y = animalCurrGridY - RADIUS_OF_CELLS_EATEN - 1; y <= animalCurrGridY + RADIUS_OF_CELLS_EATEN + 1; y++) {
            if (y < grid.length && y >= 0) {
                borderPoints.add(new Point((animalCurrGridX - RADIUS_OF_CELLS_EATEN - 1) * RECT_SIZE, y * RECT_SIZE));
                borderPoints.add(new Point((animalCurrGridX + RADIUS_OF_CELLS_EATEN + 1) * RECT_SIZE, y * RECT_SIZE));
                grid[y][animalCurrGridX - RADIUS_OF_CELLS_EATEN - 1] = 'B';
                grid[y][animalCurrGridX + RADIUS_OF_CELLS_EATEN + 1] = 'B';
            }
        }
        for (int x = animalCurrGridX - RADIUS_OF_CELLS_EATEN - 1; x <= animalCurrGridX + RADIUS_OF_CELLS_EATEN + 1; x++) {
            if (x < grid[0].length && x >= 0) {
                borderPoints.add(new Point(x * RECT_SIZE, (animalCurrGridY - RADIUS_OF_CELLS_EATEN - 1) * RECT_SIZE));
                borderPoints.add(new Point(x * RECT_SIZE, (animalCurrGridY + RADIUS_OF_CELLS_EATEN + 1) * RECT_SIZE));
                grid[animalCurrGridY - RADIUS_OF_CELLS_EATEN - 1][x] = 'B';
                grid[animalCurrGridY + RADIUS_OF_CELLS_EATEN + 1][x] = 'B';
            }
        }
    }
}