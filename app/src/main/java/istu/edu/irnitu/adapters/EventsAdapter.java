package istu.edu.irnitu.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import istu.edu.irnitu.IOPackage.Constants;
import istu.edu.irnitu.IOPackage.InternetController;
import istu.edu.irnitu.R;
import istu.edu.irnitu.dataModels.EventsModel;
import istu.edu.irnitu.fragments.NewsFragment;



public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventsAdapterViewHolder> {

    private final Context mContext;
    private ArrayList<EventsModel> mEventsArrayList;

    public EventsAdapter(Context context, ArrayList<EventsModel> eventsList) {
        this.mContext = context;
        this.mEventsArrayList = eventsList;

        notifyDataSetChanged();
    }
    @Override
    public EventsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_events, parent, false);
        view.setFocusable(true);
        return new EventsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EventsAdapterViewHolder holder, int position) {
        final EventsModel eventsList = mEventsArrayList.get(position);

        if (eventsList != null) {
            holder.nameView.setText(eventsList.getName());
            holder.dateView.setText(eventsList.getDate());
            holder.descriptionView.setText(eventsList.getDescription());
            holder.circleView.setText(eventsList.getName().substring(0, 1));
            holder.cv.setTag(position);

            holder.cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        if (InternetController.hasConnection(mContext)) {
                            int pos = (int) v.getTag();
                            String link =  mEventsArrayList.get(pos).getUrl();
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(link));
                            mContext.startActivity(intent);
                        } else {
                            Toast.makeText(mContext, "Включите интернет", Toast.LENGTH_SHORT).show();
                        }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mEventsArrayList.size();
    }

    class EventsAdapterViewHolder extends RecyclerView.ViewHolder {

        final TextView nameView;
        final TextView dateView;
        final TextView descriptionView;
        CardView cv;
        TextView circleView;

        public EventsAdapterViewHolder(View itemView) {
            super(itemView);

            nameView = itemView.findViewById(R.id.news_theme);
            cv = itemView.findViewById(R.id.cardViewNews);
            dateView = itemView.findViewById(R.id.news_TV);
            descriptionView = itemView.findViewById(R.id.news_dateTV);
            circleView = itemView.findViewById(R.id.circle_view_char);
        }
    }
}

