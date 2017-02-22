package fr.goui.riskgameofthroneshelperv2;

import android.content.Context;
import android.content.res.TypedArray;

import java.util.Set;

/**
 *
 */
public class Utils {

    /**
     * Gets a color resource id from the color index in the player_color xml.
     *
     * @param context    the context
     * @param colorIndex the color index
     * @return the color's resource id
     */
    public static int getColorFromIndex(Context context, int colorIndex) {
        TypedArray ta = context.getResources().obtainTypedArray(R.array.player_color);
        int color = ta.getColor(colorIndex, -1);
        ta.recycle();
        return color;
    }

    /**
     * Gets a color index from the color resource id in the player_color xml.
     *
     * @param context the context
     * @param colorId the color resource id
     * @return the color index
     */
    public static int getIndexFromColor(Context context, int colorId) {
        TypedArray ta = context.getResources().obtainTypedArray(R.array.player_color);
        int colorIndex = 0;
        for (int i = 0; i < ta.length(); i++) {
            int currentColorId = ta.getColor(i, -1);
            if (currentColorId == colorId) {
                colorIndex = i;
            }
        }
        ta.recycle();
        return colorIndex;
    }

    /**
     * Converts a set of integers to a int array.
     *
     * @param context  the context
     * @param colorSet the color set
     * @return the integer array
     */
    public static int[] convertColorSet(Context context, Set<Integer> colorSet) {
        int[] colors = new int[colorSet.size()];
        int counter = 0;
        for (int color : colorSet) {
            colors[counter] = getColorFromIndex(context, color);
            counter++;
        }
        return colors;
    }
}
