package dannylui.c4q.nyc.surfaceviewdragdrop;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;


public class CreateMemeActivity extends AppCompatActivity {
    public static final String IMAGE_URI = "image.uri";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meme);

        Bitmap selectedImage = getBitmapFromUri(getIntent().getParcelableExtra(IMAGE_URI));

        MainFragment mainFragment = MainFragment.newInstance(selectedImage);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_create_meme, mainFragment)
                .commit();
    }

    public Bitmap getBitmapFromUri(Parcelable imageUri) {
        Bitmap bmp = null;
        Uri photoUri = (Uri) imageUri;
        try {
            bmp = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmp;
    }
}
