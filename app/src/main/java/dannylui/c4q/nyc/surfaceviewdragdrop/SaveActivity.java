package dannylui.c4q.nyc.surfaceviewdragdrop;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class SaveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);

        Bitmap bitmap = MySurfaceView.lastCanvas;
        if (bitmap != null) {
            System.out.println("NOT NULL BITMAP");
        } else {
            System.out.println("NULL BIT MAP");
        }
        ImageView saved = (ImageView) findViewById(R.id.saved_image);
        saved.setImageBitmap(bitmap);
    }
}
