package fr.goui.riskgameofthroneshelperv2.model;

import java.util.List;

/**
 * Territory POJO.
 */
public class Territory {

    private String name;

    private int castle;

    private int port;

    private int color;

    private List<Coordinates> coordinates;

    public Territory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCastle() {
        return castle;
    }

    public void setCastle(int castle) {
        this.castle = castle;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public List<Coordinates> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Coordinates> coordinates) {
        this.coordinates = coordinates;
    }
}
