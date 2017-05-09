package cornelltech.cwkj.photobook_new;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


import com.bumptech.glide.Glide;

import java.util.List;

import static cornelltech.cwkj.photobook_new.R.id.tvImageName;

/**
 * Created by kylin1989 on 5/8/17.
 */

public class ImageListAdapter extends ArrayAdapter<ImageUpload> {
    private Activity context;
    private int resource;
    private List<ImageUpload> listImage;

    public ImageListAdapter(@NonNull Activity context, @LayoutRes int resource, @NonNull List<ImageUpload> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        listImage = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View mView = inflater.inflate(resource, null);
        TextView tvName = (TextView) mView.findViewById(tvImageName);
        ImageView img = (ImageView) mView.findViewById(R.id.imgView);

        tvName.setText(listImage.get(position).getName());
        Glide.with(context).load(listImage.get(position).getUrl()).into(img);

        return mView;

    }
}
