package dannylui.c4q.nyc.surfaceviewdragdrop;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import com.github.nisrulz.sensey.PinchScaleDetector;
import com.github.nisrulz.sensey.Sensey;

import java.util.Iterator;
import java.util.Stack;

/**
 * Created by Danny on 1/17/2017.
 */

public class EditableView extends SurfaceView implements Runnable, View.OnTouchListener {
    private Stack<MoveableIcon> iconStack = new Stack<>();
    private Thread t;
    private SurfaceHolder holder;
    private boolean isItOk = false;
    private boolean isIterating = false;

    public EditableView(Context context) {
        super(context);
        setOnTouchListener(this);
        holder = getHolder();

        Sensey.getInstance().init(context);

        PinchScaleDetector.PinchScaleListener pinchScaleListener = new PinchScaleDetector.PinchScaleListener() {
            @Override
            public void onScale(ScaleGestureDetector scaleGestureDetector, boolean b) {
                if (b) {
                    // Scaling Out;
                    Iterator<MoveableIcon> it = iconStack.iterator();
                    while (it.hasNext()) {
                        MoveableIcon moveableIcon = it.next();
                        if (moveableIcon.isActive()) {
                            Bitmap bitmap = moveableIcon.getBitmap();
                            Bitmap resizedBitmap = Bitmap.createScaledBitmap(
                                    bitmap, bitmap.getWidth() + 10, bitmap.getHeight() + 10, false);
                            moveableIcon.setBitmap(resizedBitmap);
                        }
                    }
                    System.out.println("Scaling out");
                } else {
                    // Scaling In
                    Iterator<MoveableIcon> it = iconStack.iterator();
                    while (it.hasNext()) {
                        MoveableIcon moveableIcon = it.next();
                        if (moveableIcon.isActive()) {
                            Bitmap bitmap = moveableIcon.getBitmap();
                            Bitmap resizedBitmap = Bitmap.createScaledBitmap(
                                    bitmap, bitmap.getWidth() - 10, bitmap.getHeight() - 10, false);
                            moveableIcon.setBitmap(resizedBitmap);
                        }
                    }
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
                isIterating = true;
                MoveableIcon moveableIcon = it.next();
                Bitmap bitmap = moveableIcon.getBitmap();
                canvas.drawBitmap(bitmap, moveableIcon.getxPos() - bitmap.getWidth() / 2, moveableIcon.getyPos() - bitmap.getHeight() / 2, null);
            }
            isIterating = false;
            holder.unlockCanvasAndPost(canvas);
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        //System.out.println("TOUCH MOTION ACTIVATED!!");

        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                System.out.println("DOWN");
                //updateCurrentIconPosition(motionEvent.getX(), motionEvent.getY());
                break;
            case MotionEvent.ACTION_MOVE:

                updateCurrentIconPosition(motionEvent.getX(), motionEvent.getY());
                break;
            case MotionEvent.ACTION_UP:
                //updateCurrentIconPosition(motionEvent.getX(), motionEvent.getY());
                break;

        }
        return true;
    }

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
        //Iterator<MoveableIcon> it = getIterator();
        if (!iconStack.isEmpty()) {
            iconStack.peek().setActive(false);
        }
        while (true) {
            if (!isIterating) {
                iconStack.push(newIcon);
                break;
            }
        }
        System.out.println(iconStack);
    }

    public void undo() {
        while (true) {
            if (!isIterating) {
                if (!iconStack.isEmpty()) {
                    iconStack.pop();
                }
                if (!iconStack.isEmpty()) {
                    iconStack.peek().setActive(true);
                }
                break;
            }
        }
    }

    public void clear() {
        while (true) {
            if (!isIterating) {
                iconStack.clear();
                break;
            }
        }
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

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        // Setup onTouchEvent for detecting type of touch gesture
        Sensey.getInstance().setupDispatchTouchEvent(event);
        return super.dispatchTouchEvent(event);
    }
}
