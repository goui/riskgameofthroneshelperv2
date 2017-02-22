package fr.goui.riskgameofthroneshelperv2.player;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.goui.riskgameofthroneshelperv2.R;
import fr.goui.riskgameofthroneshelperv2.adapter.PlayerAdapter;
import fr.goui.riskgameofthroneshelperv2.model.Player;

public class PlayersActivity extends AppCompatActivity implements IPlayerView {

    @BindView(R.id.player_number_text_view)
    TextView mNumberOfPlayersTextView;

    @BindView(R.id.player_progress_bar)
    ProgressBar mProgressBar;

    @BindView(R.id.player_recycler_view)
    RecyclerView mRecyclerView;

    private PlayerAdapter mPlayerAdapter;

    private IPlayerPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_players);
        ButterKnife.bind(this);
        // setting up the recycler view
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mPlayerAdapter = new PlayerAdapter(this);
        mRecyclerView.setAdapter(mPlayerAdapter);
        // creating the presenter
        mPresenter = new PlayerPresenter();
        mPresenter.attachView(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_players, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_next) {
            new AlertDialog.Builder(PlayersActivity.this)
                    .setTitle(R.string.Start_game_qm)
                    .setMessage(R.string.Start_game_caution)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int wich) {
                            // TODO get map
                            // TODO start qrcode reader activity
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.player_minus_button)
    public void onMinusClick() {
        mPresenter.onMinusClick();
    }

    @OnClick(R.id.player_plus_button)
    public void onPlusClick() {
        mPresenter.onPlusClick();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setNumberOfPlayers(String numberOfPlayers) {
        mNumberOfPlayersTextView.setText(numberOfPlayers);
    }
}
