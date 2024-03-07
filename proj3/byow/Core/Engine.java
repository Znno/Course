package byow.Core;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    public static TETile[][] finalWorldFrame = new TETile[WIDTH][HEIGHT];
    public static Random RANDOM;

    private static class cmp implements Comparator<Room> {
        public int compare(Room r1, Room r2) {
            if (r1.getMidX() < r2.getMidX()) {
                return -1;
            } else if (r1.getMidX() > r2.getMidX()) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     * <p>
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     * <p>
     * In other words, both of these calls:
     * - interactWithInputString("n123sss:q")
     * - interactWithInputString("lww")
     * <p>
     * should yield the exact same world state as:
     * - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] interactWithInputString(String input) {
        input = input.toLowerCase();
        int pos = input.indexOf('s');
        ArrayList<Room> rooms = new ArrayList<Room>();
        //n1234s
        String seedString = input.substring(1, pos);
        int seed = Integer.parseInt(seedString);
        RANDOM = new Random(seed);
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                finalWorldFrame[i][j] = Tileset.NOTHING;
            }
        }
        int roomCount = RANDOM.nextInt(15) + 10;
        int counter = 0;
        int bound = 0;
        while (counter < roomCount && bound < 100) {
            Room tempRoom = new Room();
            if (tempRoom.checkRoom()) {
                tempRoom.createRoom();
                rooms.add(tempRoom);
                counter++;
            }
            bound++;
        }
        rooms.sort(new cmp());
        for (int i = 0; i < rooms.size() - 1; i++) {
            Room.connect(rooms.get(i), rooms.get(i + 1));
        }
        ter.initialize(WIDTH, HEIGHT);
        ter.renderFrame(finalWorldFrame);
        return finalWorldFrame;
    }
}
