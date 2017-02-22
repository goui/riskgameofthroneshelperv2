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
    private int color;

    private List<Territory> territories;

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
}
