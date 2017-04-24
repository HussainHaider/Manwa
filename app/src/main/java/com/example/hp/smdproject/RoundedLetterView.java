package com.example.hp.smdproject;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by HP on 14-Apr-17.
 */
public class RoundedLetterView extends View {
    private static int DEFAULT_TITLE_COLOR = Color.WHITE;
    private static int DEFAULT_BACKGROUND_COLOR = Color.CYAN;
    private static final int DEFAULT_VIEW_SIZE = 90;
    private static float DEFAULT_TITLE_SIZE = 50f;
    private static String DEFAULT_TITLE = "0";
    private Bitmap imagenAndroid;
    private int mTitleColor = DEFAULT_TITLE_COLOR;
    private int mBackgroundColor = DEFAULT_BACKGROUND_COLOR;
    private String mTitleText = DEFAULT_TITLE;
    private float mTitleSize = DEFAULT_TITLE_SIZE;

    private TextPaint mTitleTextPaint;
    private Paint mBackgroundPaint;
    private RectF mInnerRectF;
    private int mViewSize;

    private int Check_variable=0;

    private Typeface mFont = Typeface.defaultFromStyle(Typeface.NORMAL);

    public RoundedLetterView(Context context) {
        super(context);
        init(null, 0);
    }
    public void set_Check_variable(int x)
    {
        Check_variable=x;
    }
    public void setBitmap(Bitmap img)
    {
        imagenAndroid=img;
    }

    public RoundedLetterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }
    public RoundedLetterView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.RoundedLetterView, defStyle, 0);

        if(a.hasValue(R.styleable.RoundedLetterView_rlv_titleText)){
            mTitleText = a.getString(R.styleable.RoundedLetterView_rlv_titleText);
        }

        mTitleColor = a.getColor(R.styleable.RoundedLetterView_rlv_titleColor,DEFAULT_TITLE_COLOR);
//        mBackgroundColor = a.getColor(R.styleable.RoundedLetterView_rlv_backgroundColorValue,DEFAULT_BACKGROUND_COLOR);
        mBackgroundColor=R.color.specail;
        mTitleSize = a.getDimension(R.styleable.RoundedLetterView_rlv_titleSize,DEFAULT_TITLE_SIZE);
        a.recycle();

        //Title TextPaint
        mTitleTextPaint = new TextPaint();
        mTitleTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTitleTextPaint.setTypeface(mFont);
        mTitleTextPaint.setTextAlign(Paint.Align.CENTER);
        mTitleTextPaint.setLinearText(true);
        mTitleTextPaint.setColor(mTitleColor);
        mTitleTextPaint.setTextSize(mTitleSize);

        //Background Paint
        mBackgroundPaint = new Paint();
        mBackgroundPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mBackgroundPaint.setStyle(Paint.Style.FILL);
        mBackgroundPaint.setColor(mBackgroundColor);

        mInnerRectF = new RectF();
    }

    private void invalidateTextPaints(){
        mTitleTextPaint.setTypeface(mFont);
        mTitleTextPaint.setTextSize(mTitleSize);
        mTitleTextPaint.setColor(mTitleColor);
    }

    private void invalidatePaints(){
        mBackgroundPaint.setColor(mBackgroundColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = resolveSize(DEFAULT_VIEW_SIZE, widthMeasureSpec);
        int height = resolveSize(DEFAULT_VIEW_SIZE, heightMeasureSpec);
        mViewSize = Math.min(width, height);

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        mInnerRectF.set(0, 0, mViewSize, mViewSize);
        mInnerRectF.offset((getWidth() - mViewSize) / 2, (getHeight() - mViewSize) / 2);

        float centerX = mInnerRectF.centerX();
        float centerY = mInnerRectF.centerY();

        int xPos = (int) centerX;
        int yPos = (int) (centerY - (mTitleTextPaint.descent() + mTitleTextPaint.ascent()) / 2);


        if(Check_variable==0)
        {
            canvas.drawOval(mInnerRectF, mBackgroundPaint);
            // canvas.draw
            //   canvas.drawBitmap(conv_bm,5,5,mBackgroundPaint);
            canvas.drawText(mTitleText,
                    xPos,
                    yPos,
                    mTitleTextPaint);
            String numberAsString1 = Integer.toString(xPos);
            Log.d("Xpos value for 1 is:",numberAsString1);
        }
        else if (Check_variable==1)
        {
            //  Bitmap imagenAndroid = BitmapFactory.decodeResource(getResources(),R.drawable.python);
            if(imagenAndroid!=null) {

                Bitmap resized = Bitmap.createScaledBitmap(imagenAndroid, 150, 150, true);
                Bitmap conv_bm = getRoundedRectBitmap(resized, 100);
                canvas.drawBitmap(conv_bm, 50, 5, mBackgroundPaint);
                String numberAsString1 = Integer.toString(xPos);
                Log.d("Xpos value for 2 is:", numberAsString1);
            }
        }
    }

    /**
     * Gets the title string attribute value.
     * @return The title string attribute value.
     */
    public  Bitmap getRoundedRectBitmap(Bitmap bitmap, int pixels) {
        Bitmap result = null;
        try {
            result = Bitmap.createBitmap(200, 200, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(result);

            int color = 0xff424242;

            Paint paint = new Paint();
            Rect rect = new Rect(0, 0, 200, 200);

//            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);

            paint.setColor(color);
            canvas.drawCircle(70, 70, 70, paint);

            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmap, 0, 0, paint);

        } catch (NullPointerException e) {
        } catch (OutOfMemoryError o) {
        }
        return result;
    }


    public String getTitleText() {
        return mTitleText;
    }

    /**
     * Sets the view's title string attribute value.
     * @param title The example string attribute value to use.
     */
    public void setTitleText(String title) {
        mTitleText = title;
        invalidate();
    }

    /**
     * Gets the background color attribute value.
     * @return The background color attribute value.
     */
    public int getBackgroundColor() {
        return mBackgroundColor;
    }

    /**
     * Sets the view's background color attribute value.
     * @param backgroundColor The background color attribute value to use.
     */
    public void setBackgroundColor(int backgroundColor) {
        mBackgroundColor = backgroundColor;
        invalidatePaints();
    }

    /**
     * Gets the title size dimension attribute value.
     * @return The title size dimension attribute value.
     */
    public float getTitleSize() {
        return mTitleSize;
    }

    /**
     * Sets the view's title size dimension attribute value.
     * @param titleSize The title size dimension attribute value to use.
     */
    public void setTitleSize(float titleSize) {
        mTitleSize = titleSize;
        invalidateTextPaints();
    }

    /**
     * Sets the view's title typeface.
     * @param font The typeface to be used for the text.
     */
    public void setTextTypeface(Typeface font){
        this.mFont = font;
        invalidateTextPaints();
    }

    /**
     * Sets the view's title color attribute value.
     * @param titleColor The title color attribute value to use.
     */
    public void setTitleColor(int titleColor) {
        mTitleColor = titleColor;
        invalidateTextPaints();
    }



}
