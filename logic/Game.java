package logic;

import logic.Entity;

import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Game {

    private ScheduledExecutorService scheduler;
    private Entity player = new Entity();
    private ArrayList<Entity> champions = new ArrayList<>();

    public Game() {
        champions.add(player);
    }

    public void startGame() {
        scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(this::updateEntities, 0, 10, TimeUnit.MILLISECONDS);
    }

    private void updateEntities() {
        champions.forEach(Entity::update);
    }

    public ArrayList<Entity> getEntities() { // Will also return other entity types at some point
        return champions;
    }

    public Entity getPlayer() {
        return player;
    }
}
