package fr.goui.riskgameofthroneshelperv2;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;

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

    /**
     * Checks if provided point is in provided polygon.
     *
     * @param p       the specific point
     * @param polygon the specific polygon
     * @return true if point in is polygon, false otherwise
     */
    public static boolean isPointInPolygon(Point p, Point[] polygon) {
        double minX = polygon[0].x;
        double maxX = polygon[0].x;
        double minY = polygon[0].y;
        double maxY = polygon[0].y;
        for (int i = 1; i < polygon.length; i++) {
            Point q = polygon[i];
            minX = Math.min(q.x, minX);
            maxX = Math.max(q.x, maxX);
            minY = Math.min(q.y, minY);
            maxY = Math.max(q.y, maxY);
        }

        if (p.x < minX || p.x > maxX || p.y < minY || p.y > maxY) {
            return false;
        }

        // http://www.ecse.rpi.edu/Homepages/wrf/Research/Short_Notes/pnpoly.html
        boolean inside = false;
        for (int i = 0, j = polygon.length - 1; i < polygon.length; j = i++) {
            if ((polygon[i].y > p.y) != (polygon[j].y > p.y) &&
                    p.x < (polygon[j].x - polygon[i].x) * (p.y - polygon[i].y) / (polygon[j].y - polygon[i].y) + polygon[i].x) {
                inside = !inside;
            }
        }

        return inside;
    }
}
