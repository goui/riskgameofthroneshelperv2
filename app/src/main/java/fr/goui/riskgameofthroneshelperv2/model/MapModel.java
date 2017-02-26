package fr.goui.riskgameofthroneshelperv2.model;

/**
 * Singleton class managing the maps.
 */
public class MapModel {

    public enum MapId {
        ESSOS, WESTEROS, WESTEROS_ESSOS
    }

    private static MapModel instance;

    private Map[] maps;

    private int numberOfMaps;

    private Map westeros;

    private Map essos;

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

    public Map getEssos() {
        return essos;
    }

    public void setEssos(Map essos) {
        this.essos = essos;
    }

    public Map[] getMaps() {
        return maps;
    }

    /**
     * Sets which map players are on.
     *
     * @param mapId the map id
     */
    public void setMaps(MapId mapId) {
        switch (mapId) {
            case ESSOS:
                numberOfMaps = 1;
                maps = new Map[numberOfMaps];
                maps[0] = essos;
                break;
            case WESTEROS:
                numberOfMaps = 1;
                maps = new Map[numberOfMaps];
                maps[0] = westeros;
                break;
            case WESTEROS_ESSOS:
                numberOfMaps = 2;
                maps = new Map[numberOfMaps];
                maps[0] = westeros;
                maps[1] = essos;
                break;
        }
    }

    /**
     * Returns how many maps there are.
     *
     * @return the number of maps.
     */
    public int getNumberOfMaps() {
        return numberOfMaps;
    }

    /**
     * Gets the territory that has the specified name.
     *
     * @param name the name of the searched territory
     * @return the territory with the right name, null if non existent
     */
    public Territory getTerritoryByName(String name) {
        Territory territory = null;
        for (Map map : maps) {
            for (Region region : map.getRegions()) {
                for (Territory currentTerritory : region.getTerritories()) {
                    if (currentTerritory.getName().equals(name)) {
                        territory = currentTerritory;
                    }
                }
            }
        }
        return territory;
    }
}
