package pacman;

import java.util.ArrayList;
import java.util.HashSet;

public class Ghost {
  String myName;
  Location myLoc;
  Map myMap;

  public Ghost(String name, Location loc, Map map) {
    this.myLoc = loc;
    this.myName = name;
    this.myMap = map;
  }

  public ArrayList<Location> get_valid_moves() {
    ArrayList<Location> validMoves = new ArrayList<Location>();

    for (int dx = -1; dx <= 1; dx++) {
      for (int dy = -1; dy <= 1; dy++) {
        Location newLocation = myLoc.shift(dx, dy);

        HashSet<Map.Type> types = myMap.getLoc(newLocation);
        if (types.contains(Map.Type.EMPTY) ||
            (types.size() == 1 && types.contains(Map.Type.PACMAN)))
          validMoves.add(newLocation);
      }
    }

    return validMoves;
  }

  public boolean move() {
    ArrayList<Location> validMoves = get_valid_moves();
    int choice = (int) (Math.random() * validMoves.size());

    if (validMoves.size() == 0 ||
        myMap.move(myName, validMoves.get(choice), Map.Type.PACMAN))
      return false;

    this.myLoc = validMoves.get(choice);
    return false;
  }

  public boolean is_pacman_in_range() {
    for (int dx = -1; dx <= 1; dx++) {
      for (int dy = -1; dy <= 1; dy++) {
        Location newLocation = myLoc.shift(dy, dx);

        if (myMap.getLoc(newLocation).contains(Map.Type.COOKIE))
          return true;
      }
    }

    return false;
  }

  public boolean attack() {
    return is_pacman_in_range() || myMap.attack(myName);
  }
}
