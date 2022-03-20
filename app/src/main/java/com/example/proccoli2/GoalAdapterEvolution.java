package com.example.proccoli2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proccoli2.NewModels.GoalModel;
import com.example.proccoli2.ui.home.HomeFragment_VC;

import java.util.List;

public class GoalAdapterEvolution extends RecyclerView.Adapter<GoalAdapterEvolution.ViewHolder> {

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {

        // Your holder should contain a member variable
        // for any view that will be set as you render a row

        TextView goalText;
        TextView countDownDueDate;
        TextView countDownPersonal;
        ImageView avatarGoal;
        GoalModel goalModel;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            goalText = (TextView) itemView.findViewById(R.id.goalText);
            countDownDueDate = (TextView) itemView.findViewById(R.id.countDownDueDate);
            countDownPersonal = (TextView) itemView.findViewById(R.id.countDownPersonal);
            avatarGoal = (ImageView) itemView.findViewById(R.id.avatarGoal);

        }
    }

    // Store a member variable for the contacts
    private List<GoalModel> mGoals;

    // Pass in the contact array into the constructor
    public GoalAdapterEvolution(List<GoalModel> goals) {
        mGoals = goals;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public GoalAdapterEvolution.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View goalView = inflater.inflate(R.layout.goalitem_view, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(goalView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(GoalAdapterEvolution.ViewHolder holder, int position) {
        Log.d("onBind", "onBindViewHolder: ");
        // Get the data model based on position
        holder.goalModel = mGoals.get(position);

        // Set item views based on your views and data model
        holder.goalText.setText(holder.goalModel.getGoalId());
       // holder.countDownDueDate.setText(controller.calculateDaysLeft((int)mGoals.get(position).getWhenIsDue()) + "  Days Left");
       // holder.countDownPersonal.setText(controller.calculateDaysLeft((int)mGoals.get(position).getPersonalDeadline()) + " Days Left");

        //if(items.get(position).getGoalType().equals("individual"))
        holder.avatarGoal.setImageResource(R.drawable.individualgoal_foreground);
        //else
        //    viewHolder.avatarGoal.setImageResource(R.drawable.groupgoal_foreground);
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mGoals.size();
    }
}