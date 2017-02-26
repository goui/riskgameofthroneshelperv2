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
import android.widget.Toast;

import com.android.colorpicker.ColorPickerDialog;
import com.android.colorpicker.ColorPickerSwatch;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.goui.riskgameofthroneshelperv2.R;
import fr.goui.riskgameofthroneshelperv2.adapter.TerritoryAdapter;
import fr.goui.riskgameofthroneshelperv2.map.MapActivity;
import fr.goui.riskgameofthroneshelperv2.model.Player;

/**
 * View for the qrcode scanning and territories adding.
 */
public class QRCodeActivity extends AppCompatActivity implements IQRCodeView {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.fab)
    FloatingActionButton mFab;

    @BindView(R.id.content_qrcode)
    LinearLayout mLayout;

    @BindView(R.id.territory_recycler_view)
    RecyclerView mRecyclerView;

    /**
     * Adapter for the list of territories.
     */
    private TerritoryAdapter mTerritoryAdapter;

    /**
     * Presenter associated with this view.
     */
    private IQRCodePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);
        ButterKnife.bind(this);
        // setting custom tool bars
        setSupportActionBar(mToolbar);
        // setting up the recycler view
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
        mTerritoryAdapter = new TerritoryAdapter(this);
        mRecyclerView.setAdapter(mTerritoryAdapter);
        // qrcode scan floating action button click listener
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new IntentIntegrator(QRCodeActivity.this).initiateScan();
            }
        });
        // creating the presenter
        mPresenter = new QRCodePresenter();
        mPresenter.attachView(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        // getting scan result
        IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (scanResult != null) {
            mPresenter.onScanSuccess(scanResult.getContents());
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
            // opening a dialog to ask if the user wants to continue
            new AlertDialog.Builder(QRCodeActivity.this)
                    .setTitle(R.string.Start_game_qm)
                    .setMessage(R.string.Start_game_caution)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            // TODO count troops
                            startActivity(MapActivity.getStartingIntent(QRCodeActivity.this));
                        }
                    })
                    .setNegativeButton("Cancel", null)
                    .show();
        }
        if (id == R.id.action_select_player) {
            mPresenter.onPlayerSelectClick();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showProgressBar() {
        // nothing to do
    }

    @Override
    public void hideProgressBar() {
        // nothing to do
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void changeBackgroundColor(int colorId) {
        mLayout.setBackgroundColor(colorId);
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
    public void updateDisplayedList() {
        mTerritoryAdapter.notifyItemInserted(mTerritoryAdapter.getItemCount());
    }

    @Override
    public void changePlayer(Player player) {
        mTerritoryAdapter.setPlayer(player);
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
        return new Intent(callingContext, QRCodeActivity.class);
    }
}
