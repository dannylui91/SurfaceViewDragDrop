package dannylui.c4q.nyc.surfaceviewdragdrop;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Danny on 1/20/2017.
 */
public class FaceIconAdapter extends RecyclerView.Adapter<FaceIconAdapter.FaceViewHolder> {
    public static Bitmap bitmap = null;
    private List<Integer> faces = new ArrayList<>();
    private MySurfaceView mySurfaceView = null;

    public FaceIconAdapter(MySurfaceView mySurfaceView, List<Integer> faces) {
        this.mySurfaceView = mySurfaceView;
        this.faces = faces;
    }

    @Override
    public FaceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.vh_face, parent, false);
        return new FaceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FaceViewHolder holder, int position) {
        holder.bind(faces.get(position));
    }

    @Override
    public int getItemCount() {
        return faces.size();
    }

    public class FaceViewHolder extends RecyclerView.ViewHolder {
        private ImageView iconIv;

        public FaceViewHolder(View itemView) {
            super(itemView);
            iconIv = (ImageView) itemView.findViewById(R.id.icon_image);
        }

        public void bind(final int imageDrawableId) {
            iconIv.setImageResource(imageDrawableId);

            final Bitmap icon = BitmapFactory.decodeResource(itemView.getResources(), imageDrawableId);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mySurfaceView.addIcon(icon);
                }
            });
        }


    }
}
