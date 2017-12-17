package app.ie.fitnesstracker;
import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import static android.R.attr.duration;
import static app.ie.fitnesstracker.R.id.time;
import static app.ie.fitnesstracker.R.string.date;

public class SQLiteListAdapter extends BaseAdapter {

        Context context;
        ArrayList<String> workoutID;
        ArrayList<String> workoutDate;
        ArrayList<String> workoutTime;
        ArrayList<String> workoutDuration;


        public SQLiteListAdapter(
                Context context2,
                ArrayList<String> id,
                ArrayList<String> date,
                ArrayList<String> time,
                ArrayList<String> duration
        )
        {

            this.context = context2;
            this.workoutID = id;
            this.workoutDate = date;
            this.workoutTime = time;
            this.workoutDuration = duration;
        }

        public int getCount() {
            return workoutID.size();
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View child, ViewGroup parent) {

            Holder holder;

            LayoutInflater layoutInflater;

            if (child == null) {
                layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                child = layoutInflater.inflate(R.layout.listviewdatalayout, null);

                holder = new Holder();

                holder.textviewid= (TextView) child.findViewById(R.id.textViewID);
                holder.textviewdate= (TextView) child.findViewById(R.id.textViewDATE);
                holder.textviewtime = (TextView) child.findViewById(R.id.textViewTIME);
                holder.textviewduration = (TextView) child.findViewById(R.id.textViewDURATION);

                child.setTag(holder);

            } else {

                holder = (Holder) child.getTag();
            }
            holder.textviewid.setText(workoutDate.get(position));
            holder.textviewdate.setText(workoutDate.get(position));
            holder.textviewtime.setText(workoutTime.get(position));
            holder.textviewduration.setText(workoutDuration.get(position));

            return child;
        }

        public class Holder {
            TextView textviewid;
            TextView textviewdate;
            TextView textviewtime;
            TextView textviewduration;
        }

    }
