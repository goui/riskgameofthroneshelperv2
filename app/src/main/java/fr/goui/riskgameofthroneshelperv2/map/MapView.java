package fr.goui.riskgameofthroneshelperv2.map;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.HashMap;

import fr.goui.riskgameofthroneshelperv2.Utils;
import fr.goui.riskgameofthroneshelperv2.model.Map;
import fr.goui.riskgameofthroneshelperv2.model.MapModel;
import fr.goui.riskgameofthroneshelperv2.model.Region;
import fr.goui.riskgameofthroneshelperv2.model.Territory;

/**
 * Custom view displaying a map.
 */
public class MapView extends View {

    private static final int TOUCH_CIRCLE_RADIUS = 3;
    private static final int NAME_TAG_RECT_OFFSET = 48;
    private static final int NAME_TAG_PADDING = 12;
    private static final int NAME_TAG_TEXT_SIZE = 32;
    private static final int BORDER_STROKE_WIDTH = 2;

    /* GRAPHIC */
    private Paint mWhitePaint;
    private Paint mGreyPaint;
    private Paint mColorPaint;
    private Path mPath;
    private Rect mNameTagRect;

    /* MAP */
    private Map mMap;
    private int mMapWidth;
    private int mMapHeight;
    private float mRatioX;
    private float mRatioY;

    /* TOUCH */
    private Point mTouchedPoint;
    private boolean mIsTouched;
    private boolean mIsClicked;
    private Territory mClickedTerritory;

    /* MAP GRID */
    private java.util.Map<Point[], Territory> mGrid;

    public MapView(Context context) {
        super(context);
        init(context);
    }

    public MapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    /**
     * Initializes map and graphics.
     *
     * @param context the context
     */
    private void init(Context context) {
        mMap = MapModel.getInstance().getCurrentMap();
        mMapWidth = mMap.getWidth();
        mMapHeight = mMap.getHeight();
        mGreyPaint = new Paint();
        mGreyPaint.setColor(ContextCompat.getColor(context, android.R.color.darker_gray));
        mGreyPaint.setStrokeWidth(BORDER_STROKE_WIDTH);
        mGreyPaint.setStyle(Paint.Style.STROKE);
        mGreyPaint.setTextSize(NAME_TAG_TEXT_SIZE);
        mGreyPaint.setAntiAlias(true);
        mWhitePaint = new Paint();
        mWhitePaint.setColor(ContextCompat.getColor(context, android.R.color.white));
        mWhitePaint.setStyle(Paint.Style.FILL);
        mWhitePaint.setAntiAlias(true);
        mColorPaint = new Paint();
        mColorPaint.setStyle(Paint.Style.FILL);
        mColorPaint.setAntiAlias(true);
        mPath = new Path();
        mGrid = new HashMap<>();
        mTouchedPoint = new Point();
        mNameTagRect = new Rect();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // getting width and height
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        // getting ratio
        mRatioX = (float) width / (float) mMapWidth;
        mRatioY = (float) height / (float) mMapHeight;
        // adjusting view size
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas); // TODO don't do this every draw
        // drawing all the territories
        for (Region region : mMap.getRegions()) {
            for (int i = 0; i < region.getTerritories().size(); i++) {
                Territory territory = region.getTerritories().get(i);
                Point[] coords = new Point[territory.getCoordinates().size()];
                mPath.reset();
                coords[0] = new Point((int) (territory.getCoordinates().get(0).getX() * mRatioX),
                        (int) (territory.getCoordinates().get(0).getY() * mRatioY));
                mPath.moveTo(territory.getCoordinates().get(0).getX() * mRatioX,
                        territory.getCoordinates().get(0).getY() * mRatioY);
                for (int j = 1; j < territory.getCoordinates().size(); j++) {
                    coords[j] = new Point((int) (territory.getCoordinates().get(j).getX() * mRatioX),
                            (int) (territory.getCoordinates().get(j).getY() * mRatioY));
                    mPath.lineTo(territory.getCoordinates().get(j).getX() * mRatioX,
                            territory.getCoordinates().get(j).getY() * mRatioY);
                }
                mPath.close();
                // filling territory with its player color
                mColorPaint.setColor(territory.getColor());
                canvas.drawPath(mPath, mColorPaint);
                // drawing the border
                canvas.drawPath(mPath, mGreyPaint);
                // saving the path
                mGrid.put(coords, territory);
            }
        }
        // if a territory has been clicked, drawing its name tag
        if (mIsClicked) {
            drawNameDialog(canvas);
        }
    }

    /**
     * Draws the name tag of the clicked territory.
     *
     * @param canvas the drawing canvas
     */
    private void drawNameDialog(Canvas canvas) {
        String text = mClickedTerritory.getName();
        int textSize = (int) mGreyPaint.measureText(text);
        // drawing the touch circle
        canvas.drawCircle(mTouchedPoint.x, mTouchedPoint.y, TOUCH_CIRCLE_RADIUS, mGreyPaint);
        // drawing the offset line
        canvas.drawLine(mTouchedPoint.x, mTouchedPoint.y,
                mTouchedPoint.x + NAME_TAG_RECT_OFFSET,
                mTouchedPoint.y - NAME_TAG_RECT_OFFSET,
                mGreyPaint);
        // setting the name tag rect
        mNameTagRect.set(mTouchedPoint.x + NAME_TAG_RECT_OFFSET,
                mTouchedPoint.y - 2 * NAME_TAG_RECT_OFFSET,
                mTouchedPoint.x + 2 * NAME_TAG_PADDING + NAME_TAG_RECT_OFFSET + textSize,
                mTouchedPoint.y - NAME_TAG_RECT_OFFSET);
        // drawing the name tag background
        canvas.drawRect(mNameTagRect, mWhitePaint);
        // drawing the name tag border
        canvas.drawRect(mNameTagRect, mGreyPaint);
        // drawing the name tag text
        canvas.drawText(text, mTouchedPoint.x + NAME_TAG_RECT_OFFSET + NAME_TAG_PADDING,
                mTouchedPoint.y - (NAME_TAG_RECT_OFFSET + NAME_TAG_PADDING),
                mGreyPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN: // touch
                mTouchedPoint.x = (int) event.getX();
                mTouchedPoint.y = (int) event.getY();
                mIsTouched = true;
                break;
            case MotionEvent.ACTION_UP: // maybe a tap
                if (mIsTouched) {
                    onClick();
                    mIsTouched = false;
                }
                break;
        }
        return true;
    }

    /**
     * Map has been clicked.
     */
    private void onClick() {
        if (!mIsClicked) {
            // user clicked
            boolean territoryClicked = false;
            for (Point[] coords : mGrid.keySet()) {
                if (Utils.isPointInPolygon(mTouchedPoint, coords)) {
                    // user clicked on a territory
                    territoryClicked = true;
                    mClickedTerritory = mGrid.get(coords);
                    invalidate();
                    break;
                }
            }
            if (territoryClicked) {
                mIsClicked = true;
            }
        } else if (mNameTagRect.contains(mTouchedPoint.x, mTouchedPoint.y)) {
            // user clicked on the name tag of the territory
            // TODO open color picker to change territory color
            mIsClicked = false;
            invalidate();
        } else {
            // user clicked outside of the map
            mIsClicked = false;
            invalidate();
            onClick();
        }
    }
}
