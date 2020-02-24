package appinventor.ai_pawchism.Rubic_Cube;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

public class GridViewAdapter extends BaseAdapter {

    private final int [] imageList;
    Context mContex;
    View view;
    LayoutInflater layoutInflater;

    public GridViewAdapter(int[] imageList, Context mContex) {
        this.imageList = imageList;
        this.mContex = mContex;
    }

    @Override
    public int getCount() {
        return imageList.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        layoutInflater = (LayoutInflater) mContex.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView==null){
            view = new View(mContex);
            view = layoutInflater.inflate(R.layout.item_grid_view,null);
            ImageView imageView = (ImageView) view.findViewById(R.id.imageView_for_gridView);
            imageView.setImageResource(imageList[position]);

        }
        return view;
    }
}
