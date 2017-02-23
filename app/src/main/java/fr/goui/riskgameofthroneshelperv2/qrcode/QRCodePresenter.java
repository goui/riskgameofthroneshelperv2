package fr.goui.riskgameofthroneshelperv2.qrcode;

import com.android.colorpicker.ColorPickerSwatch;

import fr.goui.riskgameofthroneshelperv2.R;
import fr.goui.riskgameofthroneshelperv2.Utils;
import fr.goui.riskgameofthroneshelperv2.model.MapModel;
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
        mView.changePlayer(mSelectedPlayer);
        mView.changeBackgroundColor(mSelectedPlayer.getColor());
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void onPlayerSelectClick() {
        // getting picked colors
        int[] colors = Utils.convertColorSet(mPlayerModel.getPickedColors());
        // getting the color of the current player
        int selectedColor = mSelectedPlayer.getColor();
        // opening the color picker
        mView.openColorPicker(colors, selectedColor, new ColorPickerSwatch.OnColorSelectedListener() {
            @Override
            public void onColorSelected(int color) {
                // changing the selected player
                mSelectedPlayer = mPlayerModel.findPlayerByColor(color);
                // changing the background color and the list of territories
                mView.changePlayer(mSelectedPlayer);
                mView.changeBackgroundColor(color);
            }
        });
    }

    @Override
    public void onScanSuccess(String scanResult) {
        // adding the territory to the player's list
        Territory territory = MapModel.getInstance().getTerritoryByName(scanResult);
        if (territory != null) {
            mSelectedPlayer.getTerritories().add(territory);
            territory.setColor(mSelectedPlayer.getColor());
            mView.updateDisplayedList();
        } else {
            mView.showMessage(mView.getContext().getString(R.string.Territory_not_found));
        }
    }
}
