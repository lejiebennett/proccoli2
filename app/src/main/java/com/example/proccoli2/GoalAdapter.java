package com.example.proccoli2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proccoli2.oldModels.GoalModel;
import com.example.proccoli2.oldModels.SubGoalModel;

import java.util.ArrayList;

public class GoalAdapter extends RecyclerView.Adapter {
    ArrayList<GoalModel> goalList;
    Context context;

    public GoalAdapter(Context context, ArrayList<GoalModel> goalList){
        this.context = context;
        this.goalList = goalList;
        this.goalList.add(new GoalModel("To Graduate", 12345, "Project", 12345, 12345, 12345,new ArrayList<SubGoalModel>()));

    }

    @Override
    public GoalViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.goalitem_view,parent,false);
        return new GoalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        GoalViewHolder goalHolder = (GoalViewHolder) holder;
        Log.d("Position", "onBindViewHolder: position" + position);
        Log.d("Position", "onBindViewHolder: position" + goalList.get(position));
        goalHolder.goalText.setText(goalList.get(position).getBigGoal());
      //  goalHolder.countDownDueDate.setText(goalList.get(position).getDeadline());
      //  goalHolder.countDownPersonal.setText(goalList.get(position).getCompletedBy());

        goalHolder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.d("IndividualGoal", "onClick:" + goalHolder.goalText);
            }
        });

    }

    @Override
    public int getItemCount(){
        return goalList.size();
    }

    static class GoalViewHolder extends RecyclerView.ViewHolder{

        TextView goalText;
        TextView countDownDueDate;
        TextView countDownPersonal;

        public GoalViewHolder(View itemView){
            super(itemView);
            goalText = (TextView) itemView.findViewById(R.id.goalText);
            countDownDueDate = (TextView) itemView.findViewById(R.id.countDownDueDate);
            countDownPersonal = (TextView) itemView.findViewById(R.id.countDownPersonal);
        }

    }
}
