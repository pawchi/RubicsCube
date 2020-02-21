package appinventor.ai_pawchism.Rubic_Cube;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.List;

public class GridViewAdapter extends BaseAdapter {

    List<Drawable> imageList;
    Context mContex;

    public GridViewAdapter(List<Drawable> imageList, Context mContex) {
        this.imageList = imageList;
        this.mContex = mContex;
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public Object getItem(int position) {
        return imageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        final ImageView imageView;
        if (view==null){
            imageView = new ImageView(mContex);
            imageView.setLayoutParams(new GridView.LayoutParams(100,100));
            imageView.setPadding(10,10,10,10);
            imageView.setImageDrawable(imageList.get(position));


        } else {
            imageView = (ImageView)view;
        }
        return imageView;
    }
}
