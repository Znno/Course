package byow.lab12;

import org.junit.Test;

import static org.junit.Assert.*;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    public static void addHexagon(int i, int j, int size, TETile type, TETile[][] world) {
        int idx = i;
        int cnt = size;
        for (int x = j; x > j - size; x--) {
            int left = (cnt) / 2;
            int right = left;
            if (size % 2 == 0)
                left--;
            world[idx][x] = type;
            int pos = i + 1;
            for (int y = 0; y < right; y++)
                world[pos++][x] = type;
            pos = i - 1;
            for (int y = 0; y < left; y++)
                world[pos--][x] = type;
            cnt += 2;
        }
        cnt -= 2;
        for (int x = j - size; x > j - 2 * size; x--) {
            int left = (cnt) / 2;
            int right = left;
            if (size % 2 == 0)
                left--;
            world[idx][x] = type;
            int pos = i + 1;
            for (int y = 0; y < right; y++)
                world[pos++][x] = type;
            pos = i - 1;
            for (int y = 0; y < left; y++)
                world[pos--][x] = type;
            cnt -= 2;
        }
    }

    public static void main(String[] args) {
        TERenderer renderer = new TERenderer();
        renderer.initialize(50, 50);
        TETile[][] world = new TETile[50][50];
        int wid = 2;
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                world[i][j] = Tileset.NOTHING;
            }
        }
        int pos = 45 - 2 * wid;
        for (int i = 0; i < 3; i++) {
            addHexagon(24 - 4 * wid + 2, pos, wid, Tileset.SAND, world);
            pos -= 2 * wid;
        }
        pos = 45 - wid;
        for (int i = 0; i < 4; i++) {
            addHexagon(24 - 2 * wid + 1, pos, wid, Tileset.SAND, world);
            pos -= 2 * wid;
        }
        pos = 45;
        for (int i = 0; i < 5; i++) {
            addHexagon(24, pos, wid, Tileset.SAND, world);
            pos -= 2 * wid;
        }
        pos = 45 - wid;
        for (int i = 0; i < 4; i++) {
            addHexagon(24 + 2 * wid - 1, pos, wid, Tileset.SAND, world);
            pos -= 2 * wid;
        }
        pos = 45 - 2 * wid;
        for (int i = 0; i < 3; i++) {
            addHexagon(24 + 4 * wid - 2, pos, wid, Tileset.SAND, world);
            pos -= 2 * wid;
        }


        renderer.renderFrame(world);
    }
}
