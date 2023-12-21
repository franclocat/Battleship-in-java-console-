package game;

import java.util.List;

public class Ship {
    String name;
    int length;
    String direction;
    List<int[]> shipParts;

    public Ship(String name, int length) {
        this.name = name;
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public List<int[]> getShipParts() {
        return shipParts;
    }

    public void setShipParts(List<int[]> shipParts) {
        this.shipParts = shipParts;
    }
}
