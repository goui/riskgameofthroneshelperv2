package fr.goui.riskgameofthroneshelperv2.map;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.goui.riskgameofthroneshelperv2.R;
import fr.goui.riskgameofthroneshelperv2.listener.OnChangePlayerListener;
import fr.goui.riskgameofthroneshelperv2.model.MapModel;

/**
 * The pager adapter to swap between maps when there are more than one.
 */
class MapViewPagerAdapter extends PagerAdapter {

    private LayoutInflater mLayoutInflater;

    private OnChangePlayerListener mOnChangePlayerListener;

    private MapView[] mMapViews;

    private int mNumberOfMaps;

    MapViewPagerAdapter(Context context, OnChangePlayerListener onChangePlayerListener) {
        mLayoutInflater = LayoutInflater.from(context);
        mOnChangePlayerListener = onChangePlayerListener;
        mNumberOfMaps = MapModel.getInstance().getNumberOfMaps();
        mMapViews = new MapView[mNumberOfMaps];
    }

    @Override
    public int getCount() {
        return mNumberOfMaps;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        MapView mapView = (MapView) mLayoutInflater.inflate(R.layout.map_view, container, false);
        mapView.setPosition(position);
        mapView.setOnChangePlayerListener(mOnChangePlayerListener);
        container.addView(mapView);
        mMapViews[position] = mapView;
        return mapView;
    }

    /**
     * Refreshes all the view.
     */
    void refreshViews() {
        for (MapView mapView : mMapViews) {
            mapView.invalidate();
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
