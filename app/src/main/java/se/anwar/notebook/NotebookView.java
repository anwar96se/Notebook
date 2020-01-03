package se.anwar.notebook;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Created by anwar_se on 1/3/2020.
 * Email: anwar.dev.96@gmail.com
 */
public class NotebookView extends View {

    // region Constants

    public static final int DEF_WIDTH = 230;
    public static final int DEF_HEIGHT = 300;

    //endregion

    // region Variables

    private float width = DEF_WIDTH;
    private float height = DEF_HEIGHT;
    private int startColor;
    private int endColor;
    private Angle angle = Angle.ANGLE_0;

    private Paint bgPaint, vPaint, textPaint;
    private Path bgPath;
    private RectF vRect;

    //endregion

    // region Constructor

    public NotebookView(Context context) {
        this(context, null);
    }

    public NotebookView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, android.R.attr.textViewStyle);
    }

    public NotebookView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        getAttr(context, attrs, defStyleAttr);
        initDraw();
    }

    //endregion

    // region Attributes

    private void getAttr(Context context, AttributeSet attrs, int defStyle) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.NotebookView, defStyle, 0);
        int layoutWidth = a.getLayoutDimension(R.styleable.NotebookView_android_layout_width, DEF_WIDTH);
        int layoutHeight = a.getLayoutDimension(R.styleable.NotebookView_android_layout_height, DEF_HEIGHT);
        initLayoutDimension(layoutWidth, layoutHeight);
        setStartColor(a.getColor(R.styleable.NotebookView_nv_start_color, Color.WHITE));
        setEndColor(a.getColor(R.styleable.NotebookView_nv_end_color, Color.BLACK));
        setAngle(a.getInt(R.styleable.NotebookView_nv_angle, 0));

        a.recycle();

    }

    private void initLayoutDimension(int layoutWidth, int layoutHeight) {
        int maxWidth;
        int maxHeight;
        if (getParent() == null) {
            maxWidth = DEF_WIDTH;
            maxHeight = DEF_HEIGHT;
        } else {
            maxWidth = ((View) getParent()).getWidth();
            maxHeight = ((View) getParent()).getHeight();
        }
        width = getLayoutDimension(layoutWidth, DEF_WIDTH, maxWidth);
        height = getLayoutDimension(layoutHeight, DEF_HEIGHT, maxHeight);
    }

    private int getLayoutDimension(int dimen, int defValue, int maxValue) {
        switch (dimen) {
            case -2:
                return defValue;
            case -1:
                return maxValue;
            default:
                return dimen;
        }
    }

    private void initDraw() {
        drawBG();
        drawVLine();
        drawText();
    }

    //endregion

    // region Draw

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure((int) width, (int) height);
        setMeasuredDimension((int) width, (int) height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(bgPath, bgPaint);
        canvas.drawRect(vRect, vPaint);
//        canvas.drawText("Notebook", width / 2, height / 2, textPaint);

    }

    private void drawBG() {
        bgPaint = new Paint();
        bgPaint.setStyle(Paint.Style.FILL);

        bgPaint.setShader(getGradient());

        bgPath = new Path();
        bgPath.addRect(0, 0, width, height, Path.Direction.CW);
    }

    private void drawVLine() {
        vPaint = new Paint();
        vPaint.setColor(Color.WHITE);
        vRect = new RectF(width - 10, 0, width - 5, height);

    }

    private void drawText() {
        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(20);

    }

    @Override
    public void invalidate() {
        initDraw();
        super.invalidate();
    }

    private LinearGradient getGradient() {

        RectF points = new RectF();
        switch (getAngle()) {
            case ANGLE_90:
                points.left = 0;
                points.top = 0;
                points.right = width;
                points.bottom = 0;
                break;
            case ANGLE_180:
                points.left = 0;
                points.top = 0;
                points.right = 0;
                points.bottom = height;
                break;
            case ANGLE_270:
                points.left = width;
                points.top = 0;
                points.right = 0;
                points.bottom = 0;
                break;
            case ANGLE_0:
            default:
                points.left = 0;
                points.top = height;
                points.right = 0;
                points.bottom = 0;
                break;
        }

        return new LinearGradient(points.left, points.top, points.right, points.bottom, startColor, endColor, Shader.TileMode.MIRROR);
    }

    //endregion

    //region Setter & Getter

    public int getStartColor() {
        return startColor;
    }

    public void setStartColor(int startColor) {
        this.startColor = startColor;
        invalidate();
    }

    public int getEndColor() {
        return endColor;
    }

    public void setEndColor(int endColor) {
        this.endColor = endColor;
        invalidate();
    }

    public Angle getAngle() {
        return angle;
    }

    private void setAngle(int angle) {
        switch (angle) {
            case 90:
                setAngle(Angle.ANGLE_90);
                break;
            case 180:
                setAngle(Angle.ANGLE_180);
                break;
            case 270:
                setAngle(Angle.ANGLE_270);
                break;
            case 0:
            default:
                setAngle(Angle.ANGLE_0);
        }
    }

    public void setAngle(Angle angle) {
        this.angle = angle;
        invalidate();
    }

    //endregion

    //region Angle

    public enum Angle {

        ANGLE_0(0), ANGLE_90(90), ANGLE_180(180), ANGLE_270(270);

        int value;

        Angle(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

    }

    //endregion

}
