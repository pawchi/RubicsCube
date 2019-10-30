package appinventor.ai_pawchism.Rubic_Cube;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Timer_ScoreAdapter extends ArrayAdapter<Timer_Score> {
    private static final String TAG = "Timer_ScoreAdapter";
    private Context mContext;
    private int mResource;

    public Timer_ScoreAdapter(Context context, int resource, ArrayList<Timer_Score> objects) {
        super(context, resource, objects);
        mContext = mContext;
        mResource = mResource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        String id = getItem(position).getId();
        String score = getItem(position).getScore();

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView txId = (TextView) convertView.findViewById(R.id.list_scoreId);
        TextView txScore = (TextView) convertView.findViewById(R.id.list_scoreTime);

        txId.setText(id);
        txScore.setText(score);

        return convertView;
    }
}
