package mp.project.pacmandual;

public class Ghost {
    private int ghostX;
    private int ghostY;
    private int GHOST_SPEED = 1; //default = 1

    public Ghost(Map map, int speed, int ghostX, int ghostY){
        this.GHOST_SPEED = speed;
        this.ghostX = ghostX;
        this.ghostY = ghostY;
    }

    public boolean checkout_val_move(int dy, int dx){

        return false;
    }

}
