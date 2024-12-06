package mp.project.pacmandual;

public class LifeManager {
    private int lives;

    public LifeManager() {
        this.lives = 3;
    }

    public void decreaseLife() {
        if (lives > 0) {
            lives--;
        }
    }

    public int getLives() {
        return lives;
    }

    public void resetLife() {
        if (lives < 3) {
            lives = 3;
        }
    }
}
