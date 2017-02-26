package fr.goui.riskgameofthroneshelperv2.map;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.colorpicker.ColorPickerDialog;
import com.android.colorpicker.ColorPickerSwatch;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.goui.riskgameofthroneshelperv2.R;
import fr.goui.riskgameofthroneshelperv2.model.Player;
import fr.goui.riskgameofthroneshelperv2.model.PlayerModel;

public class MapActivity extends AppCompatActivity implements IMapView {

    @BindView(R.id.players_layout)
    LinearLayout mPlayersLayout;

    @BindView(R.id.map_view)
    MapView mMapView;

    private MapPresenter mPresenter;

    private PlayerModel mPlayerModel;

    private TextView[] mTroopsCounters;

    int mNumberOfPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);
        mPlayerModel = PlayerModel.getInstance();
        // adding the troops count bottom view
        mNumberOfPlayers = mPlayerModel.getPlayers().size();
        mTroopsCounters = new TextView[mNumberOfPlayers];
        for (int i = 0; i < mNumberOfPlayers; i++) {
            Player player = mPlayerModel.getPlayers().get(i);
            TextView playerTroopsTextView = (TextView) getLayoutInflater().inflate(R.layout.player_troops_text_view, null);
            playerTroopsTextView.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));
            playerTroopsTextView.setText(String.valueOf(player.getNumberOfTroops()));
            playerTroopsTextView.setBackgroundColor(player.getColor());
            mPlayersLayout.addView(playerTroopsTextView);
            mTroopsCounters[i] = playerTroopsTextView;
        }
        // setting up the presenter
        mPresenter = new MapPresenter();
        mPresenter.attachView(this);
        // providing listener to the map view
        mMapView.setOnChangePlayerListener(mPresenter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_end) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.End_game_qm)
                    .setMessage(R.string.End_game_caution)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int wich) {
                            // hiding the end game action button
                            item.setVisible(false);
                            // ending game
                            mPresenter.endGame();
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void openColorPicker(int[] colors, int selectedColor, ColorPickerSwatch.OnColorSelectedListener colorSelectListener) {
        ColorPickerDialog colorPickerDialog = new ColorPickerDialog();
        colorPickerDialog.initialize(
                R.string.Select_player,
                colors,
                selectedColor,
                colors.length / 2,
                colors.length);
        colorPickerDialog.setOnColorSelectedListener(colorSelectListener);
        colorPickerDialog.show(getFragmentManager(), null);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showProgressBar() {
        // do nothing
    }

    @Override
    public void hideProgressBar() {
        // do nothing
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void refreshMapView() {
        mMapView.invalidate();
    }

    @Override
    public void refreshTroopsCounters() {
        for (int i = 0; i < mNumberOfPlayers; i++) {
            mTroopsCounters[i].setText(String.valueOf(mPlayerModel.getPlayers().get(i).getNumberOfTroops()));
        }
    }

    @Override
    public void disableMapView() {
        mMapView.disable();
    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        mPresenter = null;
        super.onDestroy();
    }

    /**
     * Gets the intent needed to navigate to this activity.
     *
     * @param callingContext the calling context
     * @return the intent needed
     */
    public static Intent getStartingIntent(Context callingContext) {
        return new Intent(callingContext, MapActivity.class);
    }
}
