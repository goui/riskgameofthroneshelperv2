package fr.goui.riskgameofthroneshelperv2.qrcode;

import com.android.colorpicker.ColorPickerSwatch;

import fr.goui.riskgameofthroneshelperv2.Utils;
import fr.goui.riskgameofthroneshelperv2.model.Player;
import fr.goui.riskgameofthroneshelperv2.model.PlayerModel;
import fr.goui.riskgameofthroneshelperv2.model.Territory;

/**
 * Presenter for the qrcode screen.
 */
public class QRCodePresenter implements IQRCodePresenter {

    private IQRCodeView mView;

    private PlayerModel mPlayerModel;

    private Player mSelectedPlayer;

    @Override
    public void attachView(IQRCodeView view) {
        mView = view;
        init();
    }

    /**
     * Gets needed objects and changing the background color.
     */
    private void init() {
        mPlayerModel = PlayerModel.getInstance();
        mSelectedPlayer = mPlayerModel.getPlayers().get(0);
        mView.changeBackgroundColor(Utils.getColorFromIndex(mView.getContext(), mSelectedPlayer.getColorIndex()));
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void onPlayerSelectClick() {
        // getting picked colors
        int[] colors = Utils.convertColorSet(mView.getContext(), mPlayerModel.getPickedColors());
        // getting the color of the current player
        int selectedColor = Utils.getColorFromIndex(mView.getContext(), mSelectedPlayer.getColorIndex());
        // opening the color picker
        mView.openColorPicker(colors, selectedColor, new ColorPickerSwatch.OnColorSelectedListener() {
            @Override
            public void onColorSelected(int color) {
                // changing the selected player
                int colorIndex = Utils.getIndexFromColor(mView.getContext(), color);
                mSelectedPlayer = mPlayerModel.findPlayerByColorIndex(colorIndex);
                // changing the background color and the list of territories
                mView.changeBackgroundColor(color);
                mView.updateDisplayedList(mSelectedPlayer.getTerritories());
            }
        });
    }

    @Override
    public void onScanSuccess(String scanResult) {
        // adding the territory to the player's list
        mSelectedPlayer.getTerritories().add(new Territory(scanResult));
        mView.updateDisplayedList(mSelectedPlayer.getTerritories());
    }
}
