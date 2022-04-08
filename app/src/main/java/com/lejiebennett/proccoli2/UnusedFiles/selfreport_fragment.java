package com.lejiebennett.proccoli2.UnusedFiles;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lejiebennett.proccoli2.R;
import com.lejiebennett.proccoli2.oldModels.GoalModel;

import java.util.ArrayList;
import java.util.List;

public class selfreport_fragment extends Fragment {
    View view;
    ArrayList<GoalModel> goalList;
    RecyclerView recyclerView;
    ArrayList<GoalModel> searchGoalList;
    searchGoalAdapter adapter;

    public selfreport_fragment(){
        super(R.layout.fragment_notifications);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState){
        Log.d("onCreateViewSelfR", "onCreateView: ");

        view = inflater.inflate(R.layout.fragment_notifications,container,false);

        recyclerView = (RecyclerView) view.findViewById(R.id.searchGoalList);
     //   setUpGoalSearchRecyclerView();
        Log.d("set recycler", "onCreateView: Finished setup");
        return view;

    }



    class searchGoalAdapter extends RecyclerView.Adapter {
        List<GoalModel> items;

        public searchGoalAdapter() {
            items = new ArrayList<>();

            for(int i = 0; i < searchGoalList.size(); i++){
                items.add(searchGoalList.get(i));
                Log.d("Added", "setUpRecyclerView: " + searchGoalList.get(i));
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new SearchGoalViewHolder(parent);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            Log.d("Position", "onBindViewHolder: position" + position);
            selfreport_activity.SearchGoalViewHolder viewHolder = (selfreport_activity.SearchGoalViewHolder) holder;
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
                }
            });

        }
    }

    private void setUpGoalSearchRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new searchGoalAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

    }
}
