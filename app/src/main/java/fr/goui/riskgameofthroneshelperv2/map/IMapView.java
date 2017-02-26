package fr.goui.riskgameofthroneshelperv2.map;

import com.android.colorpicker.ColorPickerSwatch;

import fr.goui.riskgameofthroneshelperv2.IView;

/**
 * View interface for the map screen.
 */
interface IMapView extends IView {

    /**
     * Opens the zxing color picker.
     *
     * @param colors              the list of colors
     * @param selectedColor       the selected color
     * @param colorSelectListener the color click listener
     */
    void openColorPicker(int[] colors, int selectedColor, ColorPickerSwatch.OnColorSelectedListener colorSelectListener);

    /**
     * Refreshes the map view.
     */
    void refreshMapView();

    /**
     * Refreshes the troops counters.
     */
    void refreshTroopsCounters();
}
