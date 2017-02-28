package fr.goui.riskgameofthroneshelperv2.map;

import com.android.colorpicker.ColorPickerSwatch;

import fr.goui.riskgameofthroneshelperv2.Utils;
import fr.goui.riskgameofthroneshelperv2.listener.OnChangePlayerListener;
import fr.goui.riskgameofthroneshelperv2.model.Player;
import fr.goui.riskgameofthroneshelperv2.model.PlayerModel;
import fr.goui.riskgameofthroneshelperv2.model.Territory;

/**
 * Presenter for the map screen.
 */
class MapPresenter implements IMapPresenter, OnChangePlayerListener {

    private IMapView mView;

    private PlayerModel mPlayerModel;

    @Override
    public void attachView(IMapView view) {
        mView = view;
        mPlayerModel = PlayerModel.getInstance();
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void onChangePlayer(final Territory territory) {
        // getting picked colors
        int[] colors = Utils.convertColorSet(mPlayerModel.getPickedColors());
        // getting the old color
        final int oldColor = territory.getColor();
        // opening the color picker
        mView.openColorPicker(colors, oldColor, new ColorPickerSwatch.OnColorSelectedListener() {
            @Override
            public void onColorSelected(int color) {
                // changing the color of the territory
                territory.setColor(color);
                // removing this territory from the old player
                Player oldPlayer = mPlayerModel.findPlayerByColor(oldColor);
                if (oldPlayer != null) {
                    oldPlayer.getTerritories().remove(territory);
                }
                // adding this territory to the new player
                mPlayerModel.findPlayerByColor(color).getTerritories().add(territory);
                // recomputing the troops
                mPlayerModel.compute();
                // refreshing the map and the troops counters
                mView.refreshMapView();
                mView.refreshTroopsCounters();
            }
        });
    }
}
