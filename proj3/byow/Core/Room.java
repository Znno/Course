package byow.Core;

import byow.TileEngine.Tileset;

import java.util.Random;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Room {
    private final int x, y, width, height, midX, midY;

    public Room() {
        width = Engine.RANDOM.nextInt(5) + 2;
        height = Engine.RANDOM.nextInt(6) + 2;
        x = Engine.RANDOM.nextInt(Engine.WIDTH - width - 1);
        y = Engine.RANDOM.nextInt(Engine.HEIGHT - height - 1);
        midX = x + width / 2;
        midY = y + height / 2;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getMidX() {
        return midX;
    }

    public int getMidY() {
        return midY;
    }

    public void createRoom() {
        for (int i = x; i <= x + width + 1; i++) {
            for (int j = y; j <= y + height + 1; j++) {
                Engine.finalWorldFrame[i][j] = Tileset.WALL;
            }
        }
        for (int i = x + 1; i <= x + width; i++) {
            for (int j = y + 1; j <= y + height; j++) {
                Engine.finalWorldFrame[i][j] = Tileset.FLOOR;
            }
        }
    }

    public boolean checkRoom() {
        for (int i = x; i <= x + width + 1; i++) {
            for (int j = y; j <= y + height + 1; j++) {
                if (!(Engine.finalWorldFrame[i][j].equals(Tileset.NOTHING))) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void connect(Room fir, Room sec) {
        for (int i = fir.getMidX(); i <= sec.getMidX(); i++) {
            if (Engine.finalWorldFrame[i][fir.getMidY() - 1].equals(Tileset.NOTHING)) {
                Engine.finalWorldFrame[i][fir.getMidY() - 1] = Tileset.WALL;
            }
            Engine.finalWorldFrame[i][fir.getMidY()] = Tileset.FLOOR;
            if (Engine.finalWorldFrame[i][fir.getMidY() + 1].equals(Tileset.NOTHING)) {
                Engine.finalWorldFrame[i][fir.getMidY() + 1] = Tileset.WALL;
            }
        }
        int start = min(fir.getMidY(), sec.getMidY());
        int end = max(fir.getMidY(), sec.getMidY());
        for (int i = start; i <= end; i++) {
            if (Engine.finalWorldFrame[sec.getMidX() - 1][i].equals(Tileset.NOTHING)) {
                Engine.finalWorldFrame[sec.getMidX() - 1][i] = Tileset.WALL;
            }
            Engine.finalWorldFrame[sec.getMidX()][i] = Tileset.FLOOR;
            if (Engine.finalWorldFrame[sec.getMidX() + 1][i].equals(Tileset.NOTHING)) {
                Engine.finalWorldFrame[sec.getMidX() + 1][i] = Tileset.WALL;
            }
        }
    }

}
