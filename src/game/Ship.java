package game;

import java.util.List;

public class Ship {
    String name;
    int length;
    List <List <int[]>> segments;

    public Ship (String name, int length) {
        this.name = name;
        this.length = length;
    }

    public int getLength() {
        return length;
    }
    public String getName() {
        return name;
    }
    public void setLength(int length) {
        this.length = length;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSegments(List<List<int[]>> segments) {
        this.segments = segments;
    }
}
