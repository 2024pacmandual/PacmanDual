package mp.project.pacmandual;

import android.os.Handler;

public class PacmanTimer {
    private int TotalTime;
    private int remainingTime;
    private Handler timerHandler;
    private Runnable timerRunnable;

    private int timerFlag; // 종료 : -1, 일시정지 : 0, run : 1

    public int getTimerFlag(){
        return timerFlag;
    }

    public PacmanTimer(int time){
        timerHandler = new Handler();
        remainingTime = TotalTime = time;
        timerFlag = 0;
        this.setupTimer();
    }
    private void setupTimer() {
        timerRunnable = new Runnable() {
            @Override
            public void run() {
                if (remainingTime > 0) {
                    remainingTime--;
                    timerHandler.postDelayed(this, 1000); // 1초마다 실행
                } else {
                    stopTimer(1); // 타이머 중지
                }
            }
        };
    }

    public int getRemainingTime(){
        return remainingTime;
    }

    public void startTimer() {
        if (timerFlag == 1) return;
        timerFlag = 1;
        timerHandler.post(timerRunnable);
    }

//    public void resetTimer(){
//        remainingTime = TotalTime;
//    }

    public void setTime(int time){
        remainingTime = time;
    }

    public void stopTimer(int isEnd) {
        if (isEnd == 1) {
            timerHandler.removeCallbacks(timerRunnable);
            timerFlag = -1;
            return;
        } else {
            timerHandler.removeCallbacks(timerRunnable);
            timerFlag = 0;
        }
    }

}
