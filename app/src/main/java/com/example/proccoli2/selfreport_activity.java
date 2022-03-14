package com.example.proccoli2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proccoli2.oldModels.GoalModel;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the selfreport_activity that works but is based on an activity structure rather than the fragment. Same code used for Notification_Fragment
 */
public class selfreport_activity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<GoalModel> searchGoalList;
    searchGoalAdapter adapter;
    int goalSelected;
    List<GoalModel>itemsCopy;

    //Catch data from time Report
    ActivityResultLauncher<Intent> activityResultLaunch2 = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode()==RESULT_OK){
                        GoalModel timeReportedGoal = (GoalModel) result.getData().getSerializableExtra("bigGoal");
                        Log.d("RecievedFromTimeReport", "onActivityResult: " + timeReportedGoal);

                    }
                }
            });


    public selfreport_activity() {
        super(R.layout.fragment_notifications);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("notificationFragment", "onCreateView: starting activity");
        super.onCreate(savedInstanceState);
        /*
        setContentView(R.layout.fragment_home);

        if(savedInstanceState==null){
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.nav_host_fragment_container,selfreport_fragment.class,null)
                    .commit();
        }

         */

        Bundle bundle = getIntent().getExtras();
        searchGoalList = (ArrayList<GoalModel>) bundle.getSerializable("goalList");
        Log.d("recievedGoalList", "onCreate: " + searchGoalList);


        recyclerView = findViewById(R.id.searchGoalList);
        setUpGoalSearchRecyclerView();

        SearchView searchView = findViewById(R.id.search_bar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filter(s);
                return false;
            }
        });
    }

    private void filter(String text) {
        ArrayList<GoalModel> filteredList = new ArrayList<>();

        for (GoalModel item : searchGoalList) {
            if (item.getBigGoal().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        if (filteredList.isEmpty()) {
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
            adapter.filterList(filteredList);
        } else {
            adapter.filterList(filteredList);
        }
    }

    class searchGoalAdapter extends RecyclerView.Adapter{
        List<GoalModel> items;


        public searchGoalAdapter() {
            items = new ArrayList<>();

            for(int i = 0; i < searchGoalList.size(); i++){
                items.add(searchGoalList.get(i));
                Log.d("Added", "setUpRecyclerView: " + searchGoalList.get(i));
            }
        }

        public void filterList(ArrayList<GoalModel> filterllist) {
            // below line is to add our filtered
            // list in our course array list.
            items = filterllist;
            itemsCopy = filterllist;
            // below line is to notify our adapter
            // as change in recycler view data.
            notifyDataSetChanged();
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new SearchGoalViewHolder(parent);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            Log.d("Position", "onBindViewHolder: position" + position);
            SearchGoalViewHolder viewHolder = (SearchGoalViewHolder) holder;
            final GoalModel item = items.get(position);
            Log.d("Position", "onBindViewHolder: position" + item);
            viewHolder.goalText.setText(items.get(position).getBigGoal());

        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }

    class SearchGoalViewHolder extends RecyclerView.ViewHolder {

        TextView goalText;
        ImageView gradeReport;
        ImageView timeReport;

        public SearchGoalViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.goalitem_selfreport, parent, false));
            goalText = (TextView) itemView.findViewById(R.id.goalTextForSearch);

            gradeReport = (ImageView) itemView.findViewById(R.id.gradeReport);
            gradeReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("GradeReport", "onClick: I clicked grade report");

                }
            });

            timeReport = (ImageView) itemView.findViewById(R.id.timeReport);
            timeReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("TimeReport", "onClick: I clicked time report");
                    goalSelected = itemView.getVerticalScrollbarPosition();
                    Log.d("goalSelected", "onClick: " + goalSelected);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("passedGoal",itemsCopy.get(goalSelected));
                    Log.d("timeReportPass", "onClick: passedGoal" + itemsCopy.get(goalSelected));
                    Intent intent = new Intent(selfreport_activity.this,timeReportView.class);
                    intent.putExtras(bundle);
                    //startActivity(intent);

                    activityResultLaunch2.launch(intent);
                }
            });

        }
    }

    private void setUpGoalSearchRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new searchGoalAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

    }
}
