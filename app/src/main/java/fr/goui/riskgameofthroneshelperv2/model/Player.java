package fr.goui.riskgameofthroneshelperv2.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Player POJO.
 */
public class Player {

    private int colorIndex;

    private List<Territory> territories;

    public Player() {
        territories = new ArrayList<>();
    }

    public int getColorIndex() {
        return colorIndex;
    }

    public void setColorIndex(int colorIndex) {
        this.colorIndex = colorIndex;
    }

    public List<Territory> getTerritories() {
        return territories;
    }

    public void setTerritories(List<Territory> territories) {
        this.territories = territories;
    }
}
