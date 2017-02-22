package fr.goui.riskgameofthroneshelperv2.qrcode;

import fr.goui.riskgameofthroneshelperv2.IPresenter;

/**
 * Presenter interface for the qrcode screen.
 */
interface IQRCodePresenter extends IPresenter<IQRCodeView> {

    /**
     * Triggered when the action bar button is clicked.
     * Will open the color picker to change the current player.
     */
    void onPlayerSelectClick();

    /**
     * If the scan is successful, returns the scan result.
     *
     * @param scanResult the qrcode scan result
     */
    void onScanSuccess(String scanResult);
}
