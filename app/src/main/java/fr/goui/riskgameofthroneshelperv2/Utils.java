package fr.goui.riskgameofthroneshelperv2;

import android.content.Context;
import android.content.res.TypedArray;

import java.util.Set;

/**
 *
 */
public class Utils {

    public static final int MIN_PLAYERS = 2;
    public static final int MAX_PLAYERS = 7;

    private int[] colors;

    private static Utils instance;

    private Utils() {
        colors = new int[7];
    }

    public static Utils getInstance() {
        if (instance == null) {
            instance = new Utils();
        }
        return instance;
    }

    /**
     * Initializes the colors array with values in the player_color xml resource.
     *
     * @param context the context
     */
    public void initColorsArray(Context context) {
        TypedArray ta = context.getResources().obtainTypedArray(R.array.player_color);
        for (int i = 0; i < colors.length; i++) {
            colors[i] = ta.getColor(i, -1);
        }
        ta.recycle();
    }

    /**
     * Gets the colors array.
     *
     * @return the colors array
     */
    public int[] getColorsArray() {
        return colors;
    }

    /**
     * Converts a set of integers to a int array.
     *
     * @param colorSet the color set
     * @return the integer array
     */
    public static int[] convertColorSet(Set<Integer> colorSet) {
        int[] colors = new int[colorSet.size()];
        int counter = 0;
        for (int color : colorSet) {
            colors[counter] = color;
            counter++;
        }
        return colors;
    }
}
