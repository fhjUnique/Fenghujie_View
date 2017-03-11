package com.bwie.hhww.fenghujie_view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by dell on 2017/3/10.
 */

public class Config extends View{
    private Paint mDeafultPaint = new Paint();
    private Paint mTextPaint = new Paint();
    private int viweColor;
    private Region region;
    private Path path;
    private int index = 0;
    private boolean flag = true;
    private int integer;


    public Config(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public Config(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CustomTitleView);
        viweColor = array.getColor(R.styleable.CustomTitleView_bg_Color, Color.RED);
        integer = array.getInteger(R.styleable.CustomTitleView_titleTextSize, 30);
        region = new Region();
        path = new Path();


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mDeafultPaint.setColor(viweColor);
        path.addRect(getWidth()/2-200, getHeight()/2-200, getWidth()/2+200, getHeight()/2+200, Path.Direction.CW);

        // ▼将剪裁边界设置为视图大小
        Region globalRegion = new Region(-w, -h, w, h);
        // ▼将 Path 添加到 Region 中
        region.setPath(path, globalRegion);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(index == 0){
            mDeafultPaint.setColor(Color.parseColor("#800000"));
        } else if(index == 1){
            mDeafultPaint.setColor(Color.parseColor("#C71585"));
        }else if( index == 2){
            mDeafultPaint.setColor(viweColor);
            index = -1;
        }
        Path rect = path;
        canvas.drawPath(rect, mDeafultPaint);

        mTextPaint.setTextSize(integer);
        if(index == 0){
            canvas.drawText("#800000",0,7,getWidth()/2-40,getHeight()/2,mTextPaint);
        } else if(index == 1){
            canvas.drawText("#C71585",0,7,getWidth()/2-40,getHeight()/2,mTextPaint);
        }else if(index == -1){
            canvas.drawText("#8B4513",0,7,getWidth()/2-40,getHeight()/2,mTextPaint);
        }
        //进行重绘
        if(flag){
            index++;
            postInvalidateDelayed(3000);
        }



    }
    int num = 0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                int x = (int) event.getX();
                int y = (int) event.getY();
                if(region.contains(x,y)){
                    if(num == 0){
                        Toast.makeText(getContext(), "关闭", Toast.LENGTH_SHORT).show();
                        flag = false;
                        num = 1;
                    }else{
                        flag = true;
                        num = 0;
                        Toast.makeText(getContext(), "开启", Toast.LENGTH_SHORT).show();
                        postInvalidate();
                    }
                }
                break;
        }
        return true;
    }

}
