package fr.goui.riskgameofthroneshelperv2.player;

import fr.goui.riskgameofthroneshelperv2.Utils;
import fr.goui.riskgameofthroneshelperv2.model.PlayerModel;

import static fr.goui.riskgameofthroneshelperv2.Utils.MAX_PLAYERS;
import static fr.goui.riskgameofthroneshelperv2.Utils.MIN_PLAYERS;

/**
 * Presenter for a player.
 */
class PlayerPresenter implements IPlayerPresenter {

    private IPlayerView mView;

    private PlayerModel mPlayerModel;

    private int mNumberOfPlayers;

    @Override
    public void attachView(IPlayerView view) {
        mView = view;
        init();
    }

    private void init() {
        Utils.getInstance().initColorsArray(mView.getContext());
        mPlayerModel = PlayerModel.getInstance();
        mNumberOfPlayers = MIN_PLAYERS;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void onMinusClick() {
        if (mNumberOfPlayers > MIN_PLAYERS) {
            mNumberOfPlayers--;
            mPlayerModel.removePlayer();
            mView.setNumberOfPlayers(String.valueOf(mNumberOfPlayers));
        }
    }

    @Override
    public void onPlusClick() {
        if (mNumberOfPlayers < MAX_PLAYERS) {
            mNumberOfPlayers++;
            mPlayerModel.addPlayer();
            mView.setNumberOfPlayers(String.valueOf(mNumberOfPlayers));
        }
    }
}
