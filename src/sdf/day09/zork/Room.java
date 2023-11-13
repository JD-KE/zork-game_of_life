package sdf.day09.zork;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Room {
    // When returning attribute that is reference variable, better to return new copy or unmodifiable version like for collections
    // When returning objects that may return null, can force the method to return optional to get consistent results
    //  and check for null results
    private String id;
    private String name;
    private String description;
    private Map<String, String> directions = new HashMap<>();
    private List<String> items = new LinkedList<>();
    
    public void addDirection (String input) {
        String[] inputs = input.trim().split(" ");
        directions.put(inputs[0], inputs[1]);
    }

    public void addItems (String input) {
        String[] inputs = input.trim().split(" ");
        items.addAll(Arrays.asList(inputs));
    }

    public boolean isAccessible(String direction) {
        return directions.containsKey(direction);
    }

    public String getRoom (String direction) {
        return directions.get(direction);
    }

    public boolean hasItem (String item) {
        return items.contains(item);
    }

    public boolean hasItem () {
        return !items.isEmpty();
    }

    public String describeItems () {
        StringBuilder sb = new StringBuilder("\nYou see the following items:\n");
        if (hasItem()) {

            for (String item : items) {
                sb.append(String.format("\t%s%n", item));
            }

            return sb.toString();

        } else {
            sb.append("\tNothing");
            return sb.toString();
        }
    }

    public void putItem (String item) {
        items.add(item);
    }

    public String removeItem (String item) {
        items.remove(item);
        return item;
    }
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    
    
}
