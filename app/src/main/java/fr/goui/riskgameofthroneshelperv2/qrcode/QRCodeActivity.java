package fr.goui.riskgameofthroneshelperv2.qrcode;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.goui.riskgameofthroneshelperv2.R;
import fr.goui.riskgameofthroneshelperv2.Utils;
import fr.goui.riskgameofthroneshelperv2.adapter.TerritoryAdapter;
import fr.goui.riskgameofthroneshelperv2.model.PlayerModel;
import fr.goui.riskgameofthroneshelperv2.model.Territory;

public class QRCodeActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.fab)
    FloatingActionButton mFab;

    @BindView(R.id.content_qrcode)
    LinearLayout mLayout;

    @BindView(R.id.territory_recycler_view)
    RecyclerView mRecyclerView;

    private PlayerModel mPlayerModel;

    private TerritoryAdapter mTerritoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mTerritoryAdapter = new TerritoryAdapter(this);
        mRecyclerView.setAdapter(mTerritoryAdapter);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new IntentIntegrator(QRCodeActivity.this).initiateScan();
            }
        });

        mPlayerModel = PlayerModel.getInstance();
        mLayout.setBackgroundColor(Utils.getColor(this, mPlayerModel.getPlayers().get(0).getColorIndex()));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null) {
            mTerritoryAdapter.addTerritory(new Territory(scanResult.getContents()));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_qrcode, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_start) {
            new AlertDialog.Builder(QRCodeActivity.this)
                    .setTitle(R.string.Start_game_qm)
                    .setMessage(R.string.Start_game_caution)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int wich) {
                            // TODO start map activity
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        }
        if (id == R.id.action_select_player) {
            // TODO open color picker with available colors
        }
        return super.onOptionsItemSelected(item);
    }

    public static Intent getStartingIntent(Context callingContext) {
        return new Intent(callingContext, QRCodeActivity.class);
    }
}
