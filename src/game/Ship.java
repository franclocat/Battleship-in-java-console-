package game;

import java.util.List;

public class Ship {
    String name;
    int length;
    List<List<int[]>> segments;

    public Ship(String name, List<List<int[]>> segments) {
        this.name = name;
        this.segments = segments;
    }
}
