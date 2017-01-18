package dannylui.c4q.nyc.surfaceviewdragdrop;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    public static Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_main, new MainFragment())
                .commit();


    }
}
