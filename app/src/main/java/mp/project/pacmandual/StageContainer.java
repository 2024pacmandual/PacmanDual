package mp.project.pacmandual;

import java.util.ArrayList;
import java.util.List;

public class StageContainer {
    //private int level;
    private List<int[][]> maps;

    public StageContainer(){
        this.maps = new ArrayList<>();
        init();
    }
    public int[][] getMapArray(int level){
        return maps.get(0);
    }
    private void init(){
        int[][] array, array2, array3;
        array = new int[8][19];
//        array2 = new int[][];
//        array3 = new int[][];
        array[0] = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
        array[1] = new int[]{-1, 1, 1, 1, 1, 1, 1, -1, 0, 0, 0, 0, -1, 1, 1, 1, 1, 1, -1};
        array[2] = new int[]{-1, 1, 1, 1, -1, 1, 1, -1, 0, 0, 0, 0, -1, 1, 1, -1, 1, 1, -1};
        array[3] = new int[]{-1, 1, 1, 1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, -1, 1, 1, -1};
        array[4] = new int[]{-1, 1, 1, 1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, -1, 1, 1, -1};
        array[5] = new int[]{-1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, -1};
        array[6] = new int[]{-1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, -1};
        array[7] = new int[]{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};

        maps.add(array);
    }
}
