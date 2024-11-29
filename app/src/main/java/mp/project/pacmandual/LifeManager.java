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

    // 생명 수를 초기화 (게임 시작 시)
    public void resetLives() {
        lives = 3;
    }

    // 생명 수를 증가시킬 수 있는 경우 (게임 아이템 등)
    public void increaseLife() {
        if (lives < 3) {
            lives++;
        }
    }
}
