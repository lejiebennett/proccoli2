package com.lejiebennett.proccoli2.UnusedFiles;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lejiebennett.proccoli2.NewModels.GoalModel;
import com.lejiebennett.proccoli2.R;

import java.util.ArrayList;

public class recyclerTest extends AppCompatActivity {
    ArrayList<GoalModel> recyclerList = new ArrayList();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclertest);
        RecyclerView recyclerView = findViewById(R.id.recyclerTestTest);

        GoalModel g1 = new GoalModel("bigGoal1", 200, "individual", "eventId", 100, false, "goalType", 300, "eventCreaterUid", 0, 0, false);
        GoalModel g2 = new GoalModel("bigGoal2", 200, "individual", "eventId", 100, false, "goalType", 300, "eventCreaterUid", 0, 0, false);
        GoalModel g3 = new GoalModel("bigGoal3", 200, "individual", "eventId", 100, false, "goalType", 300, "eventCreaterUid", 0, 0, false);

        recyclerList.add(g1);
        recyclerList.add(g2);
        recyclerList.add(g3);

        // Set layout manager to position the items
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Create adapter passing in the sample user data
        GoalAdapterEvolution adapter = new GoalAdapterEvolution(recyclerList);
        // Attach the adapter to the recyclerview to populate items
        recyclerView.setAdapter(adapter);

    }

}
