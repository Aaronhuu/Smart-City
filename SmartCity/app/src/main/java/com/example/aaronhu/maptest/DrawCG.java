package com.example.aaronhu.maptest;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import android.view.View;

import java.util.ArrayList;

class DrawCG extends View {
    private Paint paint;
    private Canvas canv;
    private Bitmap mBitmap;
    private Paint car;
    private Paint you;
    private int[] startPoint = {0,1,3,5,6,7,7,8,10,12};
    int leng = 500, rdi = leng -70, height = 500;


    public DrawCG(Context context, int width, int height) {
        super(context);
        // 声明画笔
        paint = new Paint();
        car = new Paint();
        you = new Paint();
        // 设置颜色
        paint.setColor(Color.rgb(51,153,255));
        car.setColor(Color.rgb(255, 153, 51));
        you.setColor(Color.rgb(128, 255, 0));
        // 设置抗锯齿
        paint.setAntiAlias(true);
        car.setAntiAlias(true);
        you.setAntiAlias(true);
        // 设置线宽
        paint.setStrokeWidth(3);
        car.setStrokeWidth(8);
        you.setStrokeWidth(2);

        // 设置非填充
        paint.setStyle(Style.FILL);
        car.setStyle(Style.FILL);
        you.setStyle(Style.FILL);
        // 声明位图
        mBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        // 声明画布
        canv = new Canvas(mBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mBitmap, 0, 0, null);
        // super.onDraw(canvas);
    }

    /**
     * 画线
     *
     * @return
     */
    public Bitmap drawLine(int carNum, int youNum) {
        DFS dfs = new DFS(carNum,youNum);
        dfs.search(startPoint[youNum]);
        ArrayList<Node> paths = dfs.getPath();
        int index = 0;
        Node previous =paths.get(0);
        for(Node path : paths){
            if(index == 0){
                previous = path;
                index++;
            }else{
                canv.drawLine(previous.positionX, previous.positionY+height, path.positionX, path.positionY+height, car);
                previous = path;
                index++;
            }
        }
        return mBitmap;
    }

    /**
     * 画三角形
     *
     * @return
     */
    public Bitmap drawTriangle() {
        Path path = new Path();
        path.moveTo(300, 600);
        path.lineTo(600, 200);
        path.lineTo(900, 600);
        path.lineTo(300, 600);
        canv.drawPath(path, car);
        return mBitmap;
    }

    /**
     * 画矩形
     *
     * @return
     */
    public Bitmap drawRect(HandleAllBlocks handleAllBlocks) {

        String[] blockStatus = handleAllBlocks.getBlockStatus();
        int index = 0;



        for(int j=0;j<3;j++){
            for(int i=0;i<3;i++){
                if(blockStatus[index].equals("car")){
                    canv.drawRect(new Rect(i*leng,j*leng+height,i*leng+rdi,j*leng+rdi+height), car);
                }else if(blockStatus[index].equals("you")) {
                    canv.drawRect(new Rect(i*leng,j*leng+height,i*leng+rdi,j*leng+rdi+height), you);
                }else{
                    canv.drawRect(new Rect(i*leng,j*leng+height,i*leng+rdi,j*leng+rdi+height), paint);
                }
                index++;

            }
        }


        return mBitmap;
    }

}
