package fr.goui.riskgameofthroneshelperv2;

import android.content.Context;
import android.content.res.TypedArray;

/**
 *
 */
public class Utils {

    /**
     * Gets a color from the player_color xml resource.
     *
     * @param position the color position
     * @return the color's resource id
     */
    public static int getColor(Context context, int position) {
        TypedArray ta = context.getResources().obtainTypedArray(R.array.player_color);
        int color = ta.getColor(position, -1);
        ta.recycle();
        return color;
    }
}
