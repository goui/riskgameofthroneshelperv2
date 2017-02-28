package fr.goui.riskgameofthroneshelperv2.chart;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.goui.riskgameofthroneshelperv2.R;
import fr.goui.riskgameofthroneshelperv2.Utils;
import fr.goui.riskgameofthroneshelperv2.model.Player;
import fr.goui.riskgameofthroneshelperv2.model.PlayerModel;

public class ChartActivity extends AppCompatActivity {

    @BindView(R.id.chart)
    BarChart mChart;

    private List<BarEntry> mTotalPointsEntries;
    private List<BarEntry> mCastlesEntries;
    private List<BarEntry> mPortsEntries;
    private BarDataSet mBarDataSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        ButterKnife.bind(this);
        // setting up the chart
        mChart.setDrawBarShadow(false);
        mChart.setDrawValueAboveBar(true);
        mChart.setMaxVisibleValueCount(8);
        mChart.setScaleEnabled(false);
        mChart.setDrawGridBackground(false);
        mChart.getLegend().setEnabled(false);
        mChart.getDescription().setText(getString(R.string.Total_points));
        // setting up the x axis
        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(7);
        xAxis.setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                return "";
            }
        });
        // setting up the y axis
        YAxis yAxis = mChart.getAxisLeft();
        yAxis.setLabelCount(10, false);
        yAxis.setGranularity(1f);
        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        yAxis.setSpaceTop(15f);
        yAxis.setAxisMinimum(0f);
        // disabling right axis
        mChart.getAxisRight().setEnabled(false);
        // setting data in the chart
        setData();
        // disabling bars touch
        mChart.getData().setHighlightEnabled(false);
    }

    /**
     * Sets all the data for all the charts.
     */
    private void setData() {
        PlayerModel playerModel = PlayerModel.getInstance();
        int[] colors = new int[playerModel.getPlayers().size()];
        // filling entries
        mTotalPointsEntries = new ArrayList<>();
        mCastlesEntries = new ArrayList<>();
        mPortsEntries = new ArrayList<>();
        for (int i = 0; i < playerModel.getPlayers().size(); i++) {
            Player player = playerModel.getPlayers().get(i);
            colors[i] = player.getColor();
            mTotalPointsEntries.add(new BarEntry(i, player.getTotalPoints(), ""));
            mCastlesEntries.add(new BarEntry(i, player.getNumberOfCastles(), ""));
            mPortsEntries.add(new BarEntry(i, player.getNumberOfPorts(), ""));
        }
        // chaining up the entries with the data set and its data
        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            mBarDataSet = (BarDataSet) mChart.getData().getDataSetByIndex(0);
            mBarDataSet.setValues(mTotalPointsEntries);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            mBarDataSet = new BarDataSet(mTotalPointsEntries, "");
            mBarDataSet.setColors(colors);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(mBarDataSet);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10);
            data.setValueFormatter(new IValueFormatter() {
                @Override
                public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                    return String.valueOf((int) value);
                }
            });

            mChart.setData(data);
        }
    }

    @OnClick(R.id.chart_total_points_button)
    public void onTotalPointsClick() {
        mChart.getDescription().setText(getString(R.string.Total_points));
        mBarDataSet.setValues(mTotalPointsEntries);
        mChart.getData().notifyDataChanged();
        mChart.notifyDataSetChanged();
        mChart.invalidate();
    }

    @OnClick(R.id.chart_castles_button)
    public void onCastlesClick() {
        mChart.getDescription().setText(getString(R.string.Castles));
        mBarDataSet.setValues(mCastlesEntries);
        mChart.getData().notifyDataChanged();
        mChart.notifyDataSetChanged();
        mChart.invalidate();
    }

    @OnClick(R.id.chart_ports_button)
    public void onPortsClick() {
        mChart.getDescription().setText(getString(R.string.Ports));
        mBarDataSet.setValues(mPortsEntries);
        mChart.getData().notifyDataChanged();
        mChart.notifyDataSetChanged();
        mChart.invalidate();
    }

    /**
     * Gets the intent needed to navigate to this activity.
     *
     * @param callingContext the calling context
     * @return the intent needed
     */
    public static Intent getStartingIntent(Context callingContext) {
        return new Intent(callingContext, ChartActivity.class);
    }
}
