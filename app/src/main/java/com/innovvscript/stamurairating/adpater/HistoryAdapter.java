package com.innovvscript.stamurairating.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.innovvscript.stamurairating.R;
import com.innovvscript.stamurairating.db.DbHandler;
import com.innovvscript.stamurairating.db.HistoryItem;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyViewHolder> {

    private List<HistoryItem> itemItems;
    private DbHandler db;
    private Context context;
    
    public HistoryAdapter(Context context, List<HistoryItem> itemItems) {
        this.context = context;
        this.itemItems = itemItems;
        db = new DbHandler(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater
                .inflate(R.layout.history_row, parent,
                        false);
        return new MyViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final HistoryItem item = itemItems.get(position);
        holder.rate.setText("Rating: " + item.getRating());
        holder.time.setText(item.getTime());
    }

    @Override
    public int getItemCount() {
        return itemItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView rate, time;

        public MyViewHolder(View itemView) {
            super(itemView);
            rate = itemView.findViewById(R.id.h_rating);
            time = itemView.findViewById(R.id.h_time);
        }
    }
}
