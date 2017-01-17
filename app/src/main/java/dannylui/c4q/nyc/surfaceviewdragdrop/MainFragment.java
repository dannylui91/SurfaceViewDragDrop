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
    ImageView icon1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        icon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
//        editableView = new EditableView(getActivity(), icon);
//        return editableView;
        rootView = inflater.inflate(R.layout.fragment_main, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        icon1 = (ImageView) view.findViewById(R.id.icon1);
        final RelativeLayout container = (RelativeLayout) view.findViewById(R.id.editable_view) ;
        icon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        editableView = new EditableView(getActivity(), icon);
        container.addView(editableView);

        icon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                ViewGroup owner = (ViewGroup) view.getParent();
//                owner.removeView(view);
                editableView.addView(BitmapFactory.decodeResource(getResources(), R.id.));
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
