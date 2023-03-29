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
        
        if (types.contains(Map.Type.EMPTY) ||
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
        !myMap.move(myName, validMoves.get(choice), Map.Type.PACMAN))
      return false;

    this.myLoc = validMoves.get(choice);
    return true;
  }

  public boolean is_ghost_in_range() {
    int startX = myLoc.x - 1; 
    int startY = myLoc.y - 1;

    for(int row = startX; row < startX + 3; row++)
    {
      for(int col = startY; col < startY + 3; col++)
      {
        if(myMap.getLoc(new Location(row, col)).contains(Map.Type.GHOST))
          return true;
      }
    }
    return false;
  }

  public JComponent consume() {
    return myMap.getLoc(myLoc).contains(Map.Type.COOKIE)? myMap.eatCookie(myName): null;
  }
}
