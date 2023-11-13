package sdf.day09.zork;

import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Game {
    public static Map<String,Room> rooms = new HashMap<>();
    public static Room currentRoom;
    public static List<String> inventory = new LinkedList<>();

    public static void loadGame (String fileName) throws IOException {
        File file = new File(fileName);

        try (FileReader fr = new FileReader(fileName)) {
            BufferedReader br = new BufferedReader(fr);

            String line;
            String roomToMakeName = null;
            Room roomToMake = null;

            while((line = br.readLine()) != null) {
                if (line.startsWith("room: ")) {
                    roomToMakeName = line.trim().substring(6);
                    roomToMake = new Room();
                    roomToMake.setId(roomToMakeName);
                }

                if (line.startsWith("name: ")) {
                    roomToMake.setName(line.trim().substring(6));
                }

                if (line.startsWith("description: ")) {
                    roomToMake.setDescription(line.trim().substring(13).replaceAll("<break>", "\n\n"));
                }

                if (line.startsWith("direction: ")) {
                    roomToMake.addDirection(line.trim().substring(11));
                }

                if (line.startsWith("items: ")) {
                    roomToMake.addItems(line.trim().substring(7));
                }

                if ("".equals(line.trim())) {
                    if (!roomToMakeName.equals(null)) {
                        rooms.put(roomToMakeName, roomToMake);
                        roomToMakeName = null;
                        roomToMake = null;
                    }
                }

                if (line.startsWith("start: ")) {
                    currentRoom = rooms.get(line.trim().substring(7));
                }

            
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found");
            System.exit(1);
        }


    }

    public static void playGame() {
        Console cons = System.console();

        boolean stop = false;
        String[] inputs;

        System.out.println(currentRoom.getDescription());
        System.out.print(currentRoom.describeItems());

        while(!stop) {
            inputs = cons.readLine(">").trim().split("\\s+");

            switch(inputs[0].toLowerCase()) {
                case Commands.COMMAND_GO:
                    if (inputs.length <= 1) {
                        System.out.println("Go where?");
                        break;
                    }
                    if (currentRoom.isAccessible(inputs[1])) {
                        currentRoom = rooms.get(currentRoom.getRoom(inputs[1]));
                        System.out.println(currentRoom.getDescription());
                        if (currentRoom.hasItem()) {
                            System.out.print(currentRoom.describeItems());
                        }
                    } else {
                        System.out.println("You cannot go in that direction.");
                    }
                    break;
                case Commands.COMMAND_TAKE:
                    if (inputs.length <= 1) {
                        System.out.println("Take what?");
                        break;
                    }

                    //can try making removeItem method return boolean for Room 
                    if (currentRoom.hasItem(inputs[1])) {
                        inventory.add(currentRoom.removeItem(inputs[1]));
                        System.out.printf("Taking %s%n", inputs[1]);
                    } else {
                        System.out.printf("I do not see any %s here.", inputs[1]);
                    }
                    break;
                case Commands.COMMAND_DROP:
                    if (inputs.length <= 1) {
                        System.out.println("Drop what?");
                        break;
                    }
                    if (inventory.contains(inputs[1])) {
                        currentRoom.putItem(inputs[1]);
                        System.out.printf("Dropped %s%n", inputs[1]);
                    } else {
                        System.out.printf("You cannot drop %s, you do not have that item.");
                    }
                    break;
                case Commands.COMMAND_INVENTORY:
                    System.out.println("You currently have:");
                    if (inventory.size() == 0) {
                        System.out.println("\tNothing");
                    } else {
                        inventory.forEach(item -> System.out.printf("\t%s%n", item));
                    }
                    break;
                case Commands.COMMAND_QUIT:
                    System.out.println("Quitting game");
                    stop = true;
                    break;
                default:
                    System.out.println("Invalid command.");
                    break;
            }

        }
    }
    
}
