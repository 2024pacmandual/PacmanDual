package mp.project.pacmandual;

public class Tile {
    private int type;  // -1: 벽, 1: 도트, 0: 빈 공간

    public Tile(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public boolean isWall() {
        return type == -1;
    }

    public boolean hasDot() {
        return type == 1;
    }

    public boolean isPowerPellet() {
        return type == 2;
    }

    public boolean isEmpty() {
        return type == 0;
    }

    public void consumeDot() {
        if (hasDot()) {
            type = 0;  // 도트를 먹었으므로 빈 공간으로 변경
        }
    }
}
