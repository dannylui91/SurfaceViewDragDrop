package dannylui.c4q.nyc.surfaceviewdragdrop;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by Danny on 1/17/2017.
 */

public class EditableView extends SurfaceView implements Runnable, View.OnTouchListener {
    Thread t;
    SurfaceHolder holder;
    boolean isItOk = false;
    float x, y;
    Bitmap icon;

    List<Bitmap> images;

    public EditableView(Context context, Bitmap bitmap) {
        super(context);
        icon = bitmap;
        holder = getHolder();
        x = 0;
        y = 0;
        setOnTouchListener(this);
    }

    public void run() {
        while (isItOk) {
            if (!holder.getSurface().isValid()) {
                continue;
            }
            System.out.println("Drawing..");
            Canvas canvas = holder.lockCanvas();
            canvas.drawARGB(255, 150, 150, 10);
//            icon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
//            canvas.drawBitmap(icon, x - (icon.getWidth()/2), y - (icon.getHeight()/2), null);
//            canvas.draw
            holder.unlockCanvasAndPost(canvas);
        }
    }

    public void onPause() {
        isItOk = false;
        while (true) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            break;
        }
        t = null;
    }


    public void onResume() {
        isItOk = true;
        t = new Thread(this);
        t.start();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x = motionEvent.getX();
                y = motionEvent.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                System.out.println("MOVED");
                x = motionEvent.getX();
                y = motionEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x = motionEvent.getX();
                y = motionEvent.getY();
                break;
        }
        return true;
    }

    public void addView(ImageView iv) {

    }
}
