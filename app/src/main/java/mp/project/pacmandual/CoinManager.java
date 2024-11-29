package mp.project.pacmandual;

public class CoinManager {
    private int coinCount;

    // 생성자: 코인은 0개로 시작
    public CoinManager() {
        this.coinCount = 0;
    }

    // 코인을 먹을 때마다 호출 (코인 수 증가)
    public void collectCoin() {
        coinCount++;
    }

    // 현재 코인 수를 반환
    public int getCoinCount() {
        return coinCount;
    }

    // 코인 수를 초기화 (게임 시작 시)
    public void resetCoins() {
        coinCount = 0;
    }
}
