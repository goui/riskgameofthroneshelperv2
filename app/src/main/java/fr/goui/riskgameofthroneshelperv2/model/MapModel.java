package fr.goui.riskgameofthroneshelperv2.model;

/**
 * Singleton class managing the maps.
 */
public class MapModel {

    public enum MapId {
        ESSOS, WESTEROS, WESTEROS_ESSOS
    }

    private static MapModel instance;

    private Map westeros;

    private Map currentMap;

    private MapModel() {
    }

    public static MapModel getInstance() {
        if (instance == null) {
            instance = new MapModel();
        }
        return instance;
    }

    public Map getWesteros() {
        return westeros;
    }

    public void setWesteros(Map westeros) {
        this.westeros = westeros;
    }

    public Map getCurrentMap() {
        return currentMap;
    }

    public void setCurrentMap(MapId mapId) {
        switch (mapId) {
            case ESSOS:
//                currentMap = essos; TODO
                break;
            case WESTEROS:
                currentMap = westeros;
                break;
            case WESTEROS_ESSOS:
//                currentMap = westeros + essos; TODO
                break;
        }
    }

    public Territory getTerritoryByName(String name) {
        Territory territory = null;
        for (Region region : currentMap.getRegions()) {
            for (Territory currentTerritory : region.getTerritories()) {
                if (currentTerritory.getName().equals(name)) {
                    territory = currentTerritory;
                }
            }
        }
        return territory;
    }
}
