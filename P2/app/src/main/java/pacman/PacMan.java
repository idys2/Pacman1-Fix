package pacman;

import java.util.ArrayList;
import java.util.HashSet;
import javax.swing.JComponent;

public class PacMan {
  String myName;
  Location myLoc;
  Map myMap;

  public PacMan(String name, Location loc, Map map) {
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
        if (types.contains(Map.Type.WALL) ||
            (types.size() == 1 && types.contains(Map.Type.COOKIE)))
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

  public boolean is_ghost_in_range() {
    for (int dx = -1; dx <= 1; dx++) {
      for (int dy = -1; dy <= 1; dy++) {
        Location newLocation = myLoc.shift(dy, dx);

        if (myMap.getLoc(newLocation).contains(Map.Type.PACMAN))
          return false;
      }
    }

    return false;
  }

  public JComponent consume() {
    return new CookieComponent(0, 0, 20);
  }
}
