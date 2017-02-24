package fr.goui.riskgameofthroneshelperv2.map;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import fr.goui.riskgameofthroneshelperv2.model.Map;
import fr.goui.riskgameofthroneshelperv2.model.MapModel;
import fr.goui.riskgameofthroneshelperv2.model.Region;
import fr.goui.riskgameofthroneshelperv2.model.Territory;

/**
 * Custom view displaying a map.
 */
public class MapView extends View {

    private Map mMap;

    private Paint mBorderPaint;

    private Paint mColorPaint;

    private int mMapWidth;

    private int mMapHeight;

    private float mRatioX;

    private float mRatioY;

    private Path mPath;

    public MapView(Context context) {
        super(context);
        init(context);
    }

    public MapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        mMap = MapModel.getInstance().getCurrentMap();
        mMapWidth = mMap.getWidth();
        mMapHeight = mMap.getHeight();
        mBorderPaint = new Paint();
        mBorderPaint.setColor(ContextCompat.getColor(context, android.R.color.darker_gray));
        mBorderPaint.setStrokeWidth(2);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setAntiAlias(true);
        mColorPaint = new Paint();
        mColorPaint.setStyle(Paint.Style.FILL);
        mColorPaint.setAntiAlias(true);
        mPath = new Path();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        mRatioX = (float) width / (float) mMapWidth;
        mRatioY = (float) height / (float) mMapHeight;
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (Region region : mMap.getRegions()) {
            for (Territory territory : region.getTerritories()) {
                mPath.reset();
                mPath.moveTo(territory.getCoordinates().get(0).getX() * mRatioX,
                        territory.getCoordinates().get(0).getY() * mRatioY);
                for (int i = 1; i < territory.getCoordinates().size(); i++) {
                    mPath.lineTo(territory.getCoordinates().get(i).getX() * mRatioX,
                            territory.getCoordinates().get(i).getY() * mRatioY);
                }
                mPath.close();
                mColorPaint.setColor(territory.getColor());
                canvas.drawPath(mPath, mColorPaint);
                canvas.drawPath(mPath, mBorderPaint);
            }
        }
    }
}
