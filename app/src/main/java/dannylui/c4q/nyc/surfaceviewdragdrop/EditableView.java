package dannylui.c4q.nyc.surfaceviewdragdrop;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.github.nisrulz.sensey.PinchScaleDetector;
import com.github.nisrulz.sensey.Sensey;

import java.util.Iterator;
import java.util.Stack;

/**
 * Created by Danny on 1/17/2017.
 */

public class EditableView extends SurfaceView implements Runnable {
    private Stack<MoveableIcon> iconStack = new Stack<>();
    private Thread t;
    private SurfaceHolder holder;
    private boolean isItOk = false;

    public EditableView(Context context) {
        super(context);
        //Sensey.getInstance().init(context);
        //setOnTouchListener(this);
        holder = getHolder();

        Sensey.getInstance().init(MainActivity.activity);

        PinchScaleDetector.PinchScaleListener pinchScaleListener = new PinchScaleDetector.PinchScaleListener() {
            @Override
            public void onScale(ScaleGestureDetector scaleGestureDetector, boolean b) {
                if (b) {
                    // Scaling Out;
                    System.out.println("Scaling out");
                } else {
                    // Scaling In
                    System.out.println("Scaling in");
                }
            }

            @Override
            public void onScaleStart(ScaleGestureDetector scaleGestureDetector) {
                // Scaling Started
                System.out.println("STARTED SCALING");

            }

            @Override
            public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
                // Scaling Stopped
                System.out.println("STOPPED SCALING");

            }
        };

        Sensey.getInstance().startPinchScaleDetection(pinchScaleListener);

    }

    public void run() {
        while (isItOk) {
            if (!holder.getSurface().isValid()) {
                continue;
            }
            Canvas canvas = holder.lockCanvas();
            canvas.drawARGB(255, 150, 150, 10);
            Iterator<MoveableIcon> it = iconStack.iterator();
            while (it.hasNext()) {
                MoveableIcon moveableIcon = it.next();
                Bitmap bitmap = moveableIcon.getBitmap();
                canvas.drawBitmap(bitmap, moveableIcon.getxPos() - bitmap.getWidth() / 2, moveableIcon.getyPos() - bitmap.getHeight() / 2, null);
            }
            holder.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        // Setup onTouchEvent for detecting type of touch gesture
        Sensey.getInstance().setupDispatchTouchEvent(event);
        return super.dispatchTouchEvent(event);
    }

//    @Override
//    public boolean onTouch(View view, MotionEvent motionEvent) {
//        //System.out.println("TOUCH MOTION ACTIVATED!!");
//
//        switch (motionEvent.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                System.out.println("DOWN");
//                //updateCurrentIconPosition(motionEvent.getX(), motionEvent.getY());
//                break;
//            case MotionEvent.ACTION_MOVE:
//
//                updateCurrentIconPosition(motionEvent.getX(), motionEvent.getY());
//                break;
//            case MotionEvent.ACTION_UP:
//                //updateCurrentIconPosition(motionEvent.getX(), motionEvent.getY());
//                break;
//
//        }
//        return true;
//    }

    public void updateCurrentIconPosition(float x, float y) {
        Iterator<MoveableIcon> it = iconStack.iterator();
        while (it.hasNext()) {
            MoveableIcon currentActiveIcon = it.next();
            if (currentActiveIcon.isActive()) {
                float currentX = currentActiveIcon.getxPos();
                float currentY = currentActiveIcon.getyPos();
                Bitmap bitmap = currentActiveIcon.getBitmap();
                if (x >= currentX - bitmap.getWidth()/2 && x <= currentX + bitmap.getWidth()/2
                        && y >= currentY - bitmap.getHeight()/2 && y <= currentY + bitmap.getHeight()/2) {
                    currentActiveIcon.setxPos(x);
                    currentActiveIcon.setyPos(y);
                }
            }
        }
    }

    public void addView(Bitmap bitmap) {
        MoveableIcon newIcon = new MoveableIcon(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);
        Iterator<MoveableIcon> it = iconStack.iterator();
        while (it.hasNext()) {
            MoveableIcon icon = it.next();
            icon.setActive(false);
        }
        iconStack.push(newIcon);
        System.out.println(iconStack);
    }

    public void undo() {
        if (!iconStack.isEmpty()) {
            iconStack.pop();
        }
        if (!iconStack.isEmpty()) {
            iconStack.peek().setActive(true);
        }
    }

    public void clear() {
        iconStack.clear();
    }

    public void save() {
        System.out.println("SAVING...");
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
}
