package academy.pocu.comp3500.lab9;

import java.util.Arrays;

public class PyramidBuilder {
    public static int findMaxHeight(final int[] widths, int statue) {
        int level = 0;
        int prevLevelCount = 1;
        int prevLevelWidthSum = statue;
        int curLevelCount = 0;
        int curLevelWidthSum = 0;

        Arrays.sort(widths);
        for (int width : widths) {
            curLevelCount++;
            curLevelWidthSum += width;

            if (curLevelCount > prevLevelCount && curLevelWidthSum > prevLevelWidthSum) {
                level++;
                prevLevelCount = curLevelCount;
                prevLevelWidthSum = curLevelWidthSum;
                curLevelCount = 0;
                curLevelWidthSum = 0;
            }
        }
        return level;
    }
}