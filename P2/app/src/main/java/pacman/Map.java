package pacman;

import java.util.HashMap;
import java.util.HashSet;
import javax.swing.JComponent;

public class Map {

  public enum Type {
    EMPTY,
    PACMAN,
    GHOST,
    WALL,
    COOKIE
  }

  private HashMap<Location, HashSet<Type>> field;
  private boolean gameOver;
  private int dim;

  private HashMap<String, Location> locations;
  private HashMap<String, JComponent> components;
  private HashSet<Type> emptySet;
  private HashSet<Type> wallSet;

  private int cookies = 0;

  public Map(int dim) {
    gameOver = false;
    locations = new HashMap<String, Location>();
    components = new HashMap<String, JComponent>();
    field = new HashMap<Location, HashSet<Type>>();

    emptySet = new HashSet<Type>();
    wallSet = new HashSet<Type>();
    emptySet.add(Type.EMPTY);
    wallSet.add(Type.WALL);
    this.dim = dim;
  }

  public void add(String name, Location loc, JComponent comp, Type type) {
    locations.put(name, loc);
    components.put(name, comp);
    if (!field.containsKey(loc))
      field.put(loc, new HashSet<Type>());
    field.get(loc).add(type);
  }

  public int getCookies() {
    return cookies;
  }

  public boolean isGameOver() {
    return gameOver;
  }

  public boolean move(String name, Location loc, Type type) {
    Location oldLocation = locations.get(name);
    JComponent component = components.get(name);

    field.get(oldLocation).remove(type);

    locations.put(name, loc);
    component.setLocation(loc.x, loc.y);
    if (!field.containsKey(loc))
      field.put(loc, new HashSet<Type>());
    field.get(loc).add(type);
    return true;
  }

  public HashSet<Type> getLoc(Location loc) {
    // boundary check
    if (loc.x < 0 || loc.x >= dim || loc.y < 0 || loc.y >= dim)
      return wallSet;
    if (!field.containsKey(loc) || field.get(loc).size() == 0)
      return emptySet;

    return field.get(loc);
  }

  public boolean attack(String Name) {
    gameOver = true;
    return true;
  }

  public JComponent eatCookie(String name) {
    Location pacman = locations.get(name);
    HashSet<Type> items = getLoc(pacman);
    if(items.contains(Map.Type.COOKIE)){
	String cookie = "tok_x"+pacman.x+"_y"+pacman.y;
	items.remove(Map.Type.COOKIE);
	cookies++;
	field.put(pacman, items);
	locations.remove(cookie);
	return components.remove(cookie);
    } else {
	return null;
    }
  }
}
