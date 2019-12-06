package appinventor.ai_pawchism.Rubic_Cube;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Timer_ScoreAdapter extends ArrayAdapter<Timer_Score> implements CompoundButton.OnCheckedChangeListener {
    private static final String TAG = "Timer_ScoreAdapter";
    private Context mContext;
    private int mResource;
    private Set<Timer_Score> selectedIds = new HashSet<>();

    public Timer_ScoreAdapter(Context context, int resource, ArrayList<Timer_Score> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

    }

    public Set<Timer_Score> getItemsToRemove() {
        return selectedIds;
    }

    private class ViewHolder {
        TextView idHloder;
        TextView scoreHolder;
        TextView dateHolder;
        TextView cubeHolder;
        CheckBox checkBox;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);

            holder.idHloder = (TextView) convertView.findViewById(R.id.list_scoreId);
            holder.scoreHolder = (TextView) convertView.findViewById(R.id.list_scoreTime);
            holder.dateHolder = (TextView) convertView.findViewById(R.id.list_scoreDate);
            holder.cubeHolder = (TextView) convertView.findViewById(R.id.list_cube);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.list_checkbox);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            holder.checkBox.setOnCheckedChangeListener(null);
        }

        final Timer_Score score = getItem(position);

        holder.idHloder.setText(score.getId());
        holder.scoreHolder.setText(score.getScore());
        holder.dateHolder.setText(score.getDate());
        holder.cubeHolder.setText(score.getCube());
        holder.checkBox.setChecked(selectedIds.contains(score));
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedIds.add(score);
                } else {
                    selectedIds.remove(score);
                }
            }
        });

        //********************
        /*
        String id = getItem(position).getId();
        String score = getItem(position).getScore();
        String date = getItem(position).getDate();
        String cube = getItem(position).getCube();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView txId = (TextView) convertView.findViewById(R.id.list_scoreId);
        TextView txScore = (TextView) convertView.findViewById(R.id.list_scoreTime);
        TextView txDate = (TextView) convertView.findViewById(R.id.list_scoreDate);
        TextView txCube = (TextView) convertView.findViewById(R.id.list_cube);

        txId.setText(id);
        txScore.setText(score);
        txDate.setText(date);
        txCube.setText(cube);
        */


        return convertView;
    }
}
