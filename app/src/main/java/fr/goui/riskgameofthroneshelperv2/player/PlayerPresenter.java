package fr.goui.riskgameofthroneshelperv2.player;

import fr.goui.riskgameofthroneshelperv2.model.PlayerModel;

/**
 * Presenter for a player.
 */
class PlayerPresenter implements IPlayerPresenter {

    private static final int MIN_PLAYERS = 2;
    private static final int MAX_PLAYERS = 7;

    private IPlayerView mView;

    private PlayerModel mPlayerModel = PlayerModel.getInstance();

    private int mNumberOfPlayers = MIN_PLAYERS;

    @Override
    public void attachView(IPlayerView view) {
        mView = view;
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
