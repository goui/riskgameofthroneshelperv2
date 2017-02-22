package fr.goui.riskgameofthroneshelperv2.qrcode;

import com.android.colorpicker.ColorPickerSwatch;

import java.util.List;

import fr.goui.riskgameofthroneshelperv2.IView;
import fr.goui.riskgameofthroneshelperv2.model.Territory;

/**
 * View interface for the qrcode screen.
 */
interface IQRCodeView extends IView {

    /**
     * Changes the background color of the screen.
     *
     * @param colorId the color resource id
     */
    void changeBackgroundColor(int colorId);

    /**
     * Opens the zxing color picker.
     *
     * @param colors              the list of colors
     * @param selectedColor       the selected color
     * @param colorSelectListener the color click listener
     */
    void openColorPicker(int[] colors, int selectedColor, ColorPickerSwatch.OnColorSelectedListener colorSelectListener);

    /**
     * Changes the displayed list of territories.
     *
     * @param territories the list of territories
     */
    void updateDisplayedList(List<Territory> territories);
}
