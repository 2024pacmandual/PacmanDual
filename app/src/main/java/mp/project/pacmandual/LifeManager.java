package mp.project.pacmandual;

public class LifeManager {
    private int lives;

    // 생성자: 생명 3개로 시작
    public LifeManager() {
        this.lives = 3;
    }

    // 생명을 1개 감소시키고, 0 미만으로 떨어지지 않도록 처리
    public void decreaseLife() {
        if (lives > 0) {
            lives--;
        }
    }

    // 현재 생명 수를 반환
    public int getLives() {
        return lives;
    }


    public void resetLife() {
        if (lives < 3) {
            lives = 3;
        }
    }
}
