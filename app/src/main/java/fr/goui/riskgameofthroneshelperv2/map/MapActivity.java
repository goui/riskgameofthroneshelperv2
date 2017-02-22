package fr.goui.riskgameofthroneshelperv2.map;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.goui.riskgameofthroneshelperv2.R;
import fr.goui.riskgameofthroneshelperv2.Utils;
import fr.goui.riskgameofthroneshelperv2.model.Player;
import fr.goui.riskgameofthroneshelperv2.model.PlayerModel;

public class MapActivity extends AppCompatActivity {

    @BindView(R.id.players_layout)
    LinearLayout mPlayersLayout;

    @BindView(R.id.map_view)
    MapView mMapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);

        for (Player player : PlayerModel.getInstance().getPlayers()) {
            TextView playerTroopsTextView = (TextView) getLayoutInflater().inflate(R.layout.player_troops_text_view, null);
            playerTroopsTextView.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1));
            playerTroopsTextView.setText("" + 5); // TODO troops here
            playerTroopsTextView.setBackgroundColor(Utils.getColorFromIndex(this, player.getColorIndex()));
            mPlayersLayout.addView(playerTroopsTextView);
        }
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
