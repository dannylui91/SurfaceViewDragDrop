package dannylui.c4q.nyc.surfaceviewdragdrop;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Danny on 1/17/2017.
 */

public class MainFragment extends Fragment {
    public static final String IMAGE_KEY = "image.key";
    private View rootView;
    private RelativeLayout surfaceViewContainer;
    private Bitmap image;
    private MySurfaceView mySurfaceView;

    public static MainFragment newInstance(@Nullable Parcelable bmp) {
        MainFragment mainFragment = new MainFragment();
        Bundle args = new Bundle();
        args.putParcelable(IMAGE_KEY, bmp);
        mainFragment.setArguments(args);
        return mainFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main, container, false);
        return rootView;
    }

    public void readyForSave(){
        Intent intent = new Intent(rootView.getContext(), SaveActivity.class);
        startActivity(intent);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        image = getArguments().getParcelable(IMAGE_KEY);
        setupToolkit();
        addSurfaceViewToFragment();

        setupRecyclerView();
    }

    private void addSurfaceViewToFragment() {
        surfaceViewContainer = (RelativeLayout) rootView.findViewById(R.id.sv_container) ;

        // Image goes behind/under the surface view
        ImageView backgroundImage = (ImageView) rootView.findViewById(R.id.bg_photo);
        backgroundImage.setImageBitmap(image);

        // Sets the surface view on top and transparent
        mySurfaceView = new MySurfaceView(getActivity(), image, this);
        mySurfaceView.setZOrderOnTop(true);
        mySurfaceView.getHolder().setFormat(PixelFormat.TRANSPARENT);
        surfaceViewContainer.addView(mySurfaceView);
    }

    // TODO Real recycler view
    private void setupRecyclerView() {
        List<Integer> iconIds = Arrays.asList(
                R.drawable.andres, R.drawable.ashique2, R.drawable.eddie
        );

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(new FaceIconAdapter(mySurfaceView, iconIds));

    }

    private void setupToolkit() {
        ImageView undoIv = (ImageView) rootView.findViewById(R.id.undo);
        ImageView clearIv = (ImageView) rootView.findViewById(R.id.clear);
        ImageView saveIv = (ImageView) rootView.findViewById(R.id.save);

        undoIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mySurfaceView.undo();
            }
        });

        clearIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mySurfaceView.clear();
            }
        });

        saveIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mySurfaceView.save();
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
        mySurfaceView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mySurfaceView.onResume();
    }
}
