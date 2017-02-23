package fr.goui.riskgameofthroneshelperv2.player;

import fr.goui.riskgameofthroneshelperv2.IPresenter;

/**
 * Presenter interface for a player.
 */
interface IPlayerPresenter extends IPresenter<IPlayerView> {

    void onMinusClick();

    void onPlusClick();

    void loadMaps();
}
