package fr.goui.riskgameofthroneshelperv2.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Player POJO.
 */
public class Player {

    /**
     * Android color resource id.
     */
    private int color = -1;

    private List<Territory> territories;

    private int numberOfTroops;

    private int regionBonus;

    private int totalPoints;

    private int numberOfCastles;

    private int numberOfPorts;

    public Player() {
        territories = new ArrayList<>();
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public List<Territory> getTerritories() {
        return territories;
    }

    public void setTerritories(List<Territory> territories) {
        this.territories = territories;
    }

    public int getNumberOfTroops() {
        return numberOfTroops;
    }

    public void setNumberOfTroops(int numberOfTroops) {
        this.numberOfTroops = numberOfTroops;
    }

    public int getRegionBonus() {
        return regionBonus;
    }

    public void setRegionBonus(int regionBonus) {
        this.regionBonus = regionBonus;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    public int getNumberOfCastles() {
        return numberOfCastles;
    }

    public void setNumberOfCastles(int numberOfCastles) {
        this.numberOfCastles = numberOfCastles;
    }

    public int getNumberOfPorts() {
        return numberOfPorts;
    }

    public void setNumberOfPorts(int numberOfPorts) {
        this.numberOfPorts = numberOfPorts;
    }
}
