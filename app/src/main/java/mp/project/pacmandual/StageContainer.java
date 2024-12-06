package mp.project.pacmandual;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

//******************************************************************************************
//******************************************************************************************
// 각각의 stage들은 -1, 0, 1을 모두 포함해야함.
//******************************************************************************************
//******************************************************************************************
public class StageContainer {
    private List<int[][]> maps;

    public StageContainer(){
        this.maps = new ArrayList<>();
        init();
    }
    public int[][] getMapArray(int level){
        if (level <= maps.size()){
            return maps.get(level-1);
        }
        else
        {
            Log.d("ERR", "getMapArray: " + maps.size());
            return null;
        }
    }
    private void init(){
        int[][] array;
        array = new int[8][20];
        int[][] array2;
        array2 = new int[17][20];
        int[][] array3;
        array3 = new int[24][20];

        array[0] = new int[] {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
        array[1] = new int[] {-1, 1, 1, 1, 1, 1, 1, -1, 0, 0, 0, 0, -1, 1, 1, 1, 1, 1, 1, -1};
        array[2] = new int[] {-1, 1, 1, 1, -1, 1, 1, -1, 0, 0, 0, 0, -1, 1, 1, -1, 1, 1, 1, -1};
        array[3] = new int[] {-1, 1, 1, 1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, -1, 1, 1, 1, -1};
        array[4] = new int[] {-1, 1, 1, 1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, -1, 1, 1, 1, -1};
        array[5] = new int[] {-1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, -1};
        array[6] = new int[] {-1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, -1};
        array[7] = new int[]  {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};

        array2[0] = new int[] {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
        array2[1] = new int[] {-1,  0,  0,  1,  1,  1,  1,  1,  1, -1,  1,  1,  1,  1,  1,  1,  1,  0,  0, -1};
        array2[2] = new int[] {-1, 0, 1, 1, -1, -1, 1, 1, 1, 1, 1, -1, 1, 1, 1, -1, -1, 1, 0, -1};
        array2[3] = new int[] {-1,  1,  1,  1,  1, -1,  1,  1,  1,  1,  1,  1,  1, 1,  1,  -1,  1,  1,  1, -1};
        array2[4] = new int[] {-1, 1, 1, 1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, -1, -1, 1, 1, -1};
        array2[5] = new int[] {-1,  1,  1,  1,  1,  1,  1, -1,  1, -1,  1, -1,  1,  1,  1,  1,  1, 1,  1, -1};
        array2[6] = new int[] {-1,  1,  1,  1,  1,  1,  1, -1,  1, -1,  1, -1,  1,  1,  1,  1,  1, 1,  1, -1};
        array2[7] = new int[] {-1,  1,  1,  1,  1,  1,  1, -1,  1,  1,  1, -1,  1,  1,  1,  1,  1,  1,  1, -1};
        array2[8] = new int[] {-1, 1, 1, 1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, -1, 1, 1, 1, -1};
        array2[9] = new int[] {-1,  1,  1,  1,  1,  1,  1, -1,  1, -1,  1, -1,  1,  1,  1,  1,  1, 1,  1, -1};
        array2[10] = new int[] {-1,  1, -1, -1, -1, -1, -1, -1,  1, -1,  1, -1, -1, -1, -1, 1, -1, -1,  1, -1};
        array2[11] = new int[] {-1,  1,  1,  1,  1,  1,  1, -1,  1, -1,  1, -1,  1,  1,  1,  1,  1, 1,  1, -1};
        array2[12] = new int[] {-1, 1, 1, 1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, 1, -1, -1, 1, 1, -1};
        array2[13] = new int[] {-1,  1,  1,  1,  1, -1,  1,  1,  1,  1,  1,  1,  1, 1,  1,  -1,  1,  1,  1, -1};
        array2[14] = new int[] {-1, 0, 1, 1, -1, -1, 1, 1, 1, 1, 1, -1, 1, 1, 1, -1, -1, 1, 0, -1};
        array2[15] = new int[] {-1,  0,  0,  1,  1,  1,  1,  -1,  1, 1,  1,  1,  1,  1,  1,  1,  1,  0,  0, -1};
        array2[16] = new int[] {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};


        array3[0] = new int[] {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
        array3[1] = new int[] {-1,  0,  1,  1,  1,  1,  1, 1,  1,  1,  1,  1,  1,  1, 1,  1,  1,  1,  0, -1};
        array3[2] = new int[] {-1,  1, -1, -1, -1, -1,  1, -1, -1, -1, -1, -1, -1, -1, -1, 1, -1, -1,  -1, -1};
        array3[3] = new int[] {-1,  1, -1,  1,  1, -1,  1,  1,  1, -1,  1,  1,  1,  1,  1,  1, -1,  1,  1, -1};
        array3[4] = new int[] {-1,  1, -1,  1, -1, -1, -1, -1,  1, -1, -1, 1, -1, 1, 1, 1, 1, 1,  1, -1};
        array3[5] = new int[] {-1,  1, -1,  1,  1,  1,  1, -1,  1,  1,  1,  1, -1,  1,  1,  1,  1, -1,  1, -1};
        array3[6] = new int[] {-1,  1, 1, 1, 1, -1, -1, -1, 1, -1, -1, -1, -1, -1, 1, 1, 1, 1,  1, -1};
        array3[7] = new int[] {-1,  1,  1,  1,  1,  1,  1,  1,  1, -1,  1,  1,  1,  1,  1,  1,  1, -1,  1,  -1};
        array3[8] = new int[] {-1, 1, -1, -1, -1, -1, -1, -1,  1, -1, -1, -1, -1, -1, -1, -1, 1, -1,  1, -1};
        array3[9] = new int[] {-1,  1,  1,  1,  1, -1,  1,  1,  1, -1,  1,  1,  1, -1,  1,  1,  1, -1,  1, -1};
        array3[10] = new int[] {-1,  1, -1, -1,  1, 1, 1, -1,  1, -1, 1, 1, -1, -1, 1, 1, 1, -1,  1, -1};
        array3[11] = new int[] {-1,  1, -1,  1,  1,  1,  1, -1,  1,  0,  1, -1,  1,  1,  1,  1,  1, 1,  1, -1};
        array3[12] = new int[] {-1, 1, -1, -1, -1, -1,  1, -1, -1, 1, 1, 1, -1, -1, -1, -1,  1, 1, -1, -1};
        array3[13] = new int[] {-1,  1,  1,  1,  1, -1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1,  1, 1,  1, -1};
        array3[14] = new int[] {-1,  1, -1, -1,  1, -1, 1, -1, 1, -1, -1, -1, -1, -1, -1, -1, 1, 1, 1, -1};
        array3[15] = new int[] {-1,  1,  1,  1,  1,  1,  1,  1,  1, -1,  1,  1,  1, -1,  1,  1,  1,  1,  1, -1};
        array3[16] = new int[] {-1,  1,  1,  1,  1,  1,  1,  1,  1, -1,  1,  1,  1, -1,  1,  1,  1,  1,  1, -1};
        array3[17] = new int[] {-1,  1,  1,  1,  1,  1,  1,  1,  1, -1,  1,  1,  1, -1,  1,  1,  1,  1,  1, -1};
        array3[18] = new int[] {-1,  1,  1,  1,  -1,  1,  1,  1,  1, -1,  1,  1,  1, -1,  1,  1,  1,  1,  1, -1};
        array3[19] = new int[] {-1,  1,  1,  -1,  -1,  -1,  1,  1,  1, 1,  1,  1,  1, 1,  1,  1,  1,  1,  1, -1};
        array3[20] = new int[] {-1,  1,  1,  1,  -1,  1,  1,  1,  1, 1,  1,  1,  1, 1,  1,  1,  1,  1,  1, -1};
        array3[21] = new int[] {-1,  1,  1,  1,  1,  1,  1,  1,  1, -1,  1,  1,  1, -1,  1,  1,  1,  1,  1, -1};
        array3[22] = new int[] {-1,  0,  1,  1,  1,  1,  1,  1,  1, -1,  1,  1,  1, -1,  1,  1,  1,  1,  0, -1};
        array3[23] = new int[] {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};


        Log.d("StageContainer", "array size: " + array.length + "x" + array[0].length);
        Log.d("StageContainer", "array2 size: " + array2.length + "x" + array2[0].length);
        Log.d("StageContainer", "array3 size: " + array3.length + "x" + array3[0].length);
        maps.add(array);
        maps.add(array2);
        maps.add(array3);
    }
}
