package com.innovvscript.stamurairating;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import com.innovvscript.stamurairating.adpater.HistoryAdapter;
import com.innovvscript.stamurairating.db.DbHandler;
import com.innovvscript.stamurairating.db.HistoryItem;
import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HistoryAdapter adapter;
    private List<HistoryItem> list;
    private List<HistoryItem> listIems;
    private DbHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        if(getSupportActionBar() != null)
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setUpRecyclerView();
    }

    public void setUpRecyclerView() {

        db = new DbHandler(this);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        list = new ArrayList<>();
        listIems = new ArrayList<>();
        list = db.getAllHistoryItems();

        for(HistoryItem v : list){
            HistoryItem item = new HistoryItem();
            item.setRating(v.getRating());
            item.setTime(v.getTime());
            item.setId(v.getId());
            listIems.add(item);
        }
        adapter = new HistoryAdapter(this,listIems);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
