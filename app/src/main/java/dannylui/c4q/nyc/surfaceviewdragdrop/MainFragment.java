package dannylui.c4q.nyc.surfaceviewdragdrop;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by Danny on 1/17/2017.
 */

public class MainFragment extends Fragment {
    private View rootView;
    EditableView editableView;
    Bitmap icon;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        setupRecyclerView();
        setupToolkit();
        addSurfaceViewToFragment();
    }



    private void addSurfaceViewToFragment() {
        RelativeLayout container = (RelativeLayout) rootView.findViewById(R.id.editable_view) ;
        icon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        editableView = new EditableView(getActivity());
        container.addView(editableView);
    }

    private void setupRecyclerView() {
        ImageView iconImage = (ImageView) rootView.findViewById(R.id.icon1);
        iconImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editableView.addView(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
            }
        });
    }

    private void setupToolkit() {
        ImageView undoIv = (ImageView) rootView.findViewById(R.id.undo);
        ImageView clearIv = (ImageView) rootView.findViewById(R.id.clear);
        ImageView saveIv = (ImageView) rootView.findViewById(R.id.save);

        undoIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editableView.undo();
            }
        });

        clearIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editableView.clear();
            }
        });

        saveIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editableView.save();
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
        editableView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        editableView.onResume();
    }
}
