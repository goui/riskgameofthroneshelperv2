package fr.goui.riskgameofthroneshelperv2.player;

import android.util.Log;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import fr.goui.riskgameofthroneshelperv2.R;
import fr.goui.riskgameofthroneshelperv2.Utils;
import fr.goui.riskgameofthroneshelperv2.model.Map;
import fr.goui.riskgameofthroneshelperv2.model.MapModel;
import fr.goui.riskgameofthroneshelperv2.model.PlayerModel;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static fr.goui.riskgameofthroneshelperv2.Utils.MAX_PLAYERS;
import static fr.goui.riskgameofthroneshelperv2.Utils.MIN_PLAYERS;

/**
 * Presenter for a player.
 */
class PlayerPresenter implements IPlayerPresenter {

    private static final String TAG = PlayerPresenter.class.getSimpleName();

    private IPlayerView mView;

    private PlayerModel mPlayerModel;

    private int mNumberOfPlayers;

    private Subscription mGettingMapSubscription;

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

    @Override
    public void loadMaps() {
        mView.showProgressBar();
        // loading maps
        if (mGettingMapSubscription != null) {
            mGettingMapSubscription.unsubscribe();
        }
        mGettingMapSubscription = getMapsObservable().subscribe(new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {
                setCurrentMap();
                // hiding progress bar and go to qrcode
                mView.startQRCodeActivity();
                mView.hideProgressBar();
            }

            @Override
            public void onError(Throwable e) {
                mView.hideProgressBar();
                mView.showMessage(e.getMessage());
            }

            @Override
            public void onNext(Boolean success) {
                // do nothing
            }
        });
    }

    private void setCurrentMap() {
        if (mNumberOfPlayers == MIN_PLAYERS) {
            MapModel.getInstance().setCurrentMap(MapModel.MapId.ESSOS);
        } else if (mNumberOfPlayers > MIN_PLAYERS && mNumberOfPlayers <= (MAX_PLAYERS - MIN_PLAYERS)) {
            MapModel.getInstance().setCurrentMap(MapModel.MapId.WESTEROS);
        } else {
            MapModel.getInstance().setCurrentMap(MapModel.MapId.WESTEROS_ESSOS);
        }
    }

    /**
     * Observable to get all the maps.
     *
     * @return observable to subscribe to
     */
    private Observable<Boolean> getMapsObservable() {
        Observable observable = Observable.create(new Observable.OnSubscribe<Subscriber<Boolean>>() {
            @Override
            public void call(Subscriber subscriber) {
                subscriber.onNext(getWesterosMap());
                subscriber.onNext(getEssosMap());
                subscriber.onCompleted();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        return observable;
    }

    /**
     * Gets the westeros map from a local json file.
     *
     * @return the success boolean
     */
    private boolean getWesterosMap() {
        InputStream inputStream = mView.getContext().getResources().openRawResource(R.raw.westeros_with_coordinates);
        String jsonString = readJsonFile(inputStream);
        Gson gson = new Gson();
        MapModel.getInstance().setWesteros(gson.fromJson(jsonString, Map.class));
        return true;
    }

    /**
     * Gets the essos map from a local json file.
     *
     * @return the success boolean
     */
    private boolean getEssosMap() {
        InputStream inputStream = mView.getContext().getResources().openRawResource(R.raw.essos_with_coordinates);
        String jsonString = readJsonFile(inputStream);
        Gson gson = new Gson();
        MapModel.getInstance().setEssos(gson.fromJson(jsonString, Map.class));
        return true;
    }

    /**
     * Reads json file.
     *
     * @param inputStream the input stream
     * @return the json string
     */
    private String readJsonFile(InputStream inputStream) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        byte bufferByte[] = new byte[1024];
        int length;
        try {
            while ((length = inputStream.read(bufferByte)) != -1) {
                outputStream.write(bufferByte, 0, length);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
        return outputStream.toString();
    }
}
