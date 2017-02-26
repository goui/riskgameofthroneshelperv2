package fr.goui.riskgameofthroneshelperv2.listener;

import fr.goui.riskgameofthroneshelperv2.model.Territory;

/**
 * When user tries to change the player owning a territory.
 */
public interface OnChangePlayerListener {

    /**
     * Triggered when the user tries to change player for a specific territory.
     */
    void onChangePlayer(Territory territory);
}
