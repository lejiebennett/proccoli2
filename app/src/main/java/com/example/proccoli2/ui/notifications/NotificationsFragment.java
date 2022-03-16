package com.example.proccoli2.ui.notifications;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proccoli2.oldModels.GoalModel;
import com.example.proccoli2.R;
import com.example.proccoli2.databinding.FragmentNotificationsBinding;
import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NotificationsFragment extends Fragment {

    private NotificationsViewModel notificationsViewModel;
    private FragmentNotificationsBinding binding;
    RecyclerView recyclerView;
    ArrayList<GoalModel> searchGoalList;
    searchGoalAdapter adapter;
    List<GoalModel> itemsCopy;

    NotificationsFragment_VC controller = new NotificationsFragment_VC(this);


    //Time Report
    TextView reportDescription, startTimeLabel,startTimeInput,stopTimeLabel,stopTimeInput;
    android.widget.Button submitTimeReport;
    Button cancelTimeReport;
    SingleDateAndTimePicker setStartPicker, setStopPicker;
    LinearLayout reportPastWorkLayout;

    //Grade Report
    LinearLayout gradeReportLayout, reportPopupLayout;
    NumberPicker letterPicker, percentPicker;
    TextView gradeReportTitle,gradeReportDescription,gradePickedInput;
    Button submitBtnGR, cancelBtnGR;
    //Available to claim
    String [] gradeLetters = {"Grade",
            "A", "A-", "B+", "B", "B-", "C+", "C", "C-", "D+", "D",
            "D-", "E"
    };

    String [] gradeScores = { "Score",
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
            "21", "22", "23", "24", "5", "26", "27", "28", "29", "30",
            "31", "32", "33", "34", "35", "36", "37", "38", "39", "40",
            "41", "42", "43", "44", "45", "46", "47", "48", "49", "50",
            "51", "52", "53", "54", "55", "56", "57", "58", "59", "60",
            "61", "62", "63", "64", "65", "66", "67", "68", "69", "70",
            "71", "72", "73", "74", "75", "76", "77", "78", "79", "80",
            "81", "82", "83", "84", "85", "86", "87", "88", "89", "90",
            "91", "92", "93", "94", "95", "96", "97", "98", "99", "100"
    };



    int goalSelected; //What goal to update with the new completion in the passed goal list

    public static NotificationsFragment newInstance(ArrayList<GoalModel> goalList){
        NotificationsFragment notificationsFragment = new NotificationsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("goalList", goalList);
        notificationsFragment.setArguments(bundle);
        return notificationsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        Log.d("SavedInstanceState", "onCreate: " + savedInstanceState);
        Log.d("arguments", "onCreate: " + bundle);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);


        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textNotifications;
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
                getParentFragmentManager().setFragmentResultListener("requestKey", this, new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        ArrayList<com.example.proccoli2.NewModels.GoalModel> newGoalList = (ArrayList<com.example.proccoli2.NewModels.GoalModel>) result.getSerializable("goalList");
                        Log.d("From home fragment", "onFragmentResult: " + newGoalList);
                    }
                });
            }
        });


        //Initalize Report Timer views
        reportPastWorkLayout = binding.reportPastWorkLayout;
        reportDescription = binding.reportDescriptionPW;
        startTimeLabel = binding.startTimeLabelPW;
        startTimeInput = binding.startTimeInputPW;
        stopTimeLabel = binding.stopTimeLabelPW;
        stopTimeInput = binding.stopTimeInputPW;
        submitTimeReport = binding.submitTimeReportPW;
        cancelTimeReport = binding.cancelTimeReportPW;
        setStartPicker = binding.setStartPW;
        recyclerView = binding.searchGoalList;
        setStopPicker = binding.setStopPW;


        //Initialize Grade Report views
        gradeReportLayout = binding.gradeReportLayout;
        reportPopupLayout = binding.reportPopupLayout;

        letterPicker = binding.letterPickerGR;
        letterPicker.setMinValue(0);
        letterPicker.setMaxValue(gradeLetters.length-1);
        letterPicker.setDisplayedValues(gradeLetters);
        letterPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                if(gradeScores[percentPicker.getValue()].equals("Score") == true && gradeLetters[letterPicker.getValue()].equals("Grade") == true){
                    Toast toast = Toast.makeText(getContext(), "Please select a time duration", Toast.LENGTH_LONG);
                    toast.show();
                }
                else{
                    //If "Score is selected" then only grade
                    if (gradeScores[percentPicker.getValue()].equals("Score") == true) {
                        gradePickedInput.setText(gradeLetters[letterPicker.getValue()]);

                    }
                    //If "Grade is selected" then only score
                    else if (gradeLetters[letterPicker.getValue()].equals("Grade") == true) {
                        gradePickedInput.setText(gradeScores[percentPicker.getValue()]);
                    } else {
                        gradePickedInput.setText(gradeLetters[letterPicker.getValue()]);
                    }

                }
            }
        });

        percentPicker = binding.percentPickerGR;
        percentPicker.setMinValue(0);
        percentPicker.setMaxValue(gradeScores.length-1);
        percentPicker.setDisplayedValues(gradeScores);
        percentPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                if(gradeScores[percentPicker.getValue()].equals("Score") == true && gradeLetters[letterPicker.getValue()].equals("Grade") == true){
                    Toast toast = Toast.makeText(getContext(), "Please select a time duration", Toast.LENGTH_LONG);
                    toast.show();
                }
                else{
                    //If "Score is selected" then only grade
                    if (gradeScores[percentPicker.getValue()].equals("Score") == true) {
                        gradePickedInput.setText(gradeLetters[letterPicker.getValue()]);

                    }
                    //If "Grade is selected" then only score
                    else if (gradeLetters[letterPicker.getValue()].equals("Grade") == true) {
                        gradePickedInput.setText(gradeScores[percentPicker.getValue()]);
                    } else {
                        gradePickedInput.setText(gradeScores[percentPicker.getValue()]);
                    }

                }

            }
        });

        gradeReportTitle = binding.gradeReportTitle;
        gradeReportDescription = binding.gradeReportDescription;
        gradePickedInput = binding.gradePickedInput;
        submitBtnGR = binding.submitBtnGR;
        submitBtnGR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean null0 = false;

                if (null0 = controller.nullFieldCheck(gradePickedInput.getText().toString(), "Please enter a grade", getContext(), gradePickedInput)) {
                    gradePickedInput.setHintTextColor(Color.rgb(128, 128, 128));
                    Log.d("collected grade", "grade: " + gradePickedInput.getText().toString());
                    Log.d("for goal", "onClick for goal: " + searchGoalList.get(goalSelected));
                    searchGoalList.get(goalSelected).setIsGraded(true);

                    adapter = new searchGoalAdapter();
                    recyclerView.setAdapter(adapter);


                    closeGradeReport();

                }
            }
        });
        cancelBtnGR = binding.cancelBtnGR;
        cancelBtnGR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeGradeReport();
            }
        });

        closeGradeReport();

        startTimeInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setStopPicker.setVisibility(View.INVISIBLE);
                setStartPicker.setVisibility(View.VISIBLE);
            }
        });

        stopTimeInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setStartPicker.setVisibility(View.INVISIBLE);
                setStopPicker.setVisibility(View.VISIBLE);
            }
        });

        submitTimeReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean null0 = false;
                boolean null1 = false;


                if (null0 = controller.nullFieldCheck(startTimeInput.getText().toString(), "Please enter a goal start time", getContext(), startTimeInput)) {
                    startTimeInput.setHintTextColor(Color.rgb(128, 128, 128));
                    if (null1 = controller.nullFieldCheck(stopTimeInput.getText().toString(), "Please enter a goal stop time", getContext(), stopTimeInput)) {
                        stopTimeInput.setHintTextColor(Color.rgb(128, 128, 128));
                        Log.d("Date", "compareDates: " + startTimeInput.getText().toString() + stopTimeInput.getText().toString());
                        Log.d("GOAL TO REPORT TIME TO", "onClick: " + goalSelected);
                        try {
                            if (controller.compareDates(controller.unixToStringDateTime(searchGoalList.get(goalSelected).getStartDate()), startTimeInput.getText().toString(), stopTimeInput.getText().toString(), getContext(), NotificationsFragment.this)) {
                                Log.d("Success", "onClick: ran");
                                startTimeInput.setHintTextColor(Color.rgb(128, 128, 128));
                                stopTimeInput.setHintTextColor(Color.rgb(128, 128, 128));

                                startTimeInput.setTextColor(Color.rgb(128, 128, 128));
                                stopTimeInput.setTextColor(Color.rgb(128, 128, 128));

                                int uStart = controller.dateStrToUnix(startTimeInput.getText().toString());
                                int uStop = controller.dateStrToUnix(stopTimeInput.getText().toString());
                                long currentUnix = System.currentTimeMillis() / 1000L;

                                // GoalModel goal = new GoalModel(bigGoalInput.getText().toString(),uComplete,goalType.getText().toString(),uStart,uDue,(int)currentUnix,subgoals);
                                //myGoal.setStartDate(uStart);
                                Log.d("collected Start/stop", "ustart: " + uStart + "stop: " + uStop);
                                Log.d("for goal", "onClick for goal: " + searchGoalList.get(goalSelected));


                                //NEED TO UPDATE STUDIED TIME BY Calculating the difference between start and stop
                                //Then update the appropriate goal
                                Log.d("CURRENT STUDIED", "onClick: " + String.valueOf(searchGoalList.get(goalSelected).getStudiedTime()));
                                searchGoalList.get(goalSelected).setStudiedTime(searchGoalList.get(goalSelected).getStudiedTime()+controller.calculateTimeDifferenceConvertToMins(uStart,uStop));
                                Log.d("New STUDIED", "onClick: " + String.valueOf(searchGoalList.get(goalSelected).getStudiedTime()));
                                //Refresh the adapter

                                adapter = new searchGoalAdapter();
                                recyclerView.setAdapter(adapter);


                                closeTimeReport();
                                new AlertDialog.Builder(getContext())
                                        .setTitle("Sucess")
                                        .setMessage("Thanks for reporting your work session! Proccoli has updated your progress charts")
                                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {

                                            }
                                        })
                                        .show();

                            } else {
                                Toast toast = Toast.makeText(getContext(), "At least one date value is invalid.", Toast.LENGTH_LONG);
                                toast.show();

                                startTimeInput.setTextColor(Color.RED);
                                stopTimeInput.setTextColor(Color.RED);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        cancelTimeReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeTimeReport();
            }
        });

        setStartPicker.addOnDateChangedListener(new SingleDateAndTimePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(String s, Date date) {
                Log.d("CompletePicker", "onScrollChange: ");
                startTimeInput.setText(controller.dateToStr(setStartPicker.getDate()));


            }
        });


        setStopPicker.addOnDateChangedListener(new SingleDateAndTimePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(String s, Date date) {
                Log.d("CompletePicker", "onScrollChange: ");
                stopTimeInput.setText(controller.dateToStr(setStopPicker.getDate()));


            }
        });

        closeTimeReport();

        searchGoalList = new ArrayList<>();

        Bundle bundle = getArguments();
        Log.d("NOTIFICATION FRAGMENT", "onCreateView: HEREEE");
        Log.d("arguments", "onCreateView: " + getArguments());
        if(bundle!=null){
            searchGoalList = (ArrayList<GoalModel>) bundle.getSerializable("goalList");
        }
        Log.d("recievedGoalList", "onCreate: " + searchGoalList);

        //Since the data pass isn't working, hardcoded data in instead
        searchGoalList.add(new GoalModel("GoalIDDD1", "Biggie",1643895301,1643290501,"Project",false));
        searchGoalList.add(new GoalModel("GoalIDDD2", "BigGoal",1643895302,1643290502,"Discussion",false));
        searchGoalList.add(new GoalModel("GoalIDDD3", "Indy",1643895303,1643290503,"Exam",false));
        searchGoalList.add(new GoalModel("GoalIDDD4", "Goally",1643895304,1643290504,"Assignment",false));
        searchGoalList.add(new GoalModel("GoalIDDD5", "BearGoal",1643895305,1643290505,"Project",false));
        setUpGoalSearchRecyclerView();


        SearchView searchView = binding.searchBarGoals;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.d("query", "onQueryTextChange: Query changing");
                filter(s);
                Log.d("itemCount", "onQueryTextChange: " + adapter.getItemCount());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.d("query", "onQueryTextChange: Query changing");
                filter(s);
                Log.d("itemCount", "onQueryTextChange: " + adapter.getItemCount());
                return false;
            }
        });


        return root;


    }

    private void filter(String text) {
        ArrayList<GoalModel> filteredList = new ArrayList<>();
        for (GoalModel item : searchGoalList) {
            if (item.getBigGoal().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapter = new searchGoalAdapter();
        recyclerView.setAdapter(adapter);
        adapter.loadRV(filteredList);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    class searchGoalAdapter extends RecyclerView.Adapter{
        List<GoalModel> items;
        public searchGoalAdapter() {
            items = new ArrayList<>();
        }

        public void loadRV(ArrayList<GoalModel> list){
            for(int i = 0; i <list.size(); i++){
                items.add(list.get(i));
                Log.d("Added", "setUpRecyclerView: " + list.get(i));
                notifyDataSetChanged();
            }
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
            viewHolder.positionInSearchGoalList = position;
            viewHolder.goal = items.get(position);
            if(items.get(position).getIsGraded()==true){
                viewHolder.gradeReport.setVisibility(View.INVISIBLE);
            }
            else{
                viewHolder.gradeReport.setVisibility(View.VISIBLE);
            }
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
        int positionInSearchGoalList;
        GoalModel goal;

        public SearchGoalViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.goalitem_selfreport, parent, false));
            goalText = (TextView) itemView.findViewById(R.id.goalTextForSearch);

            gradeReport = (ImageView) itemView.findViewById(R.id.gradeReport);
            gradeReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("GradeReport", "onClick: I clicked grade report");
                    goalSelected = searchGoalList.indexOf(goal); //Globally assigns positions
                    closeKeyboard(view);
                    openGradeReport();
                }
            });


            timeReport = (ImageView) itemView.findViewById(R.id.timeReport);
            timeReport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("TimeReport", "onClick: I clicked time report");
                    goalSelected = searchGoalList.indexOf(goal); //Globally assigns positions

                    Log.d("goalSelected", "onClick: " + goalSelected);
                    closeKeyboard(view);
                    openTimeReport();

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

    public void openTimeReport(){
        reportDescription.setVisibility(View.VISIBLE);
        startTimeLabel.setVisibility(View.VISIBLE);
        startTimeInput.setVisibility(View.VISIBLE);
        stopTimeLabel.setVisibility(View.VISIBLE);
        stopTimeInput.setVisibility(View.VISIBLE);
        submitTimeReport.setVisibility(View.VISIBLE);
        cancelTimeReport.setVisibility(View.VISIBLE);
        reportPastWorkLayout.setVisibility(View.VISIBLE);
        stopTimeInput.setText("");
        startTimeInput.setText("");
        startTimeInput.setTextColor(Color.rgb(128, 128, 128));
        stopTimeInput.setTextColor(Color.rgb(128, 128, 128));
    }

    public void closeTimeReport(){
        reportDescription.setVisibility(View.INVISIBLE);
        startTimeLabel.setVisibility(View.INVISIBLE);
        startTimeInput.setVisibility(View.INVISIBLE);
        stopTimeLabel.setVisibility(View.INVISIBLE);
        stopTimeInput.setVisibility(View.INVISIBLE);
        submitTimeReport.setVisibility(View.INVISIBLE);
        cancelTimeReport.setVisibility(View.INVISIBLE);
        setStartPicker.setVisibility(View.INVISIBLE);
        setStopPicker.setVisibility(View.INVISIBLE);
        reportPastWorkLayout.setVisibility(View.INVISIBLE);

    }

    public void closeKeyboard(View view){
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        }
    }

    public void openGradeReport(){
        gradeReportLayout.setVisibility(View.VISIBLE);
        reportPopupLayout.setVisibility(View.VISIBLE);
        letterPicker.setVisibility(View.VISIBLE);
        percentPicker.setVisibility(View.VISIBLE);
        gradeReportTitle.setVisibility(View.VISIBLE);
        gradeReportDescription.setVisibility(View.VISIBLE);
        gradePickedInput.setVisibility(View.VISIBLE);
        submitBtnGR.setVisibility(View.VISIBLE);
        cancelBtnGR.setVisibility(View.VISIBLE);

    }

    public void closeGradeReport(){
        gradeReportLayout.setVisibility(View.INVISIBLE);
        reportPopupLayout.setVisibility(View.INVISIBLE);
        letterPicker.setVisibility(View.INVISIBLE);
        percentPicker.setVisibility(View.INVISIBLE);
        gradeReportTitle.setVisibility(View.INVISIBLE);
        gradeReportDescription.setVisibility(View.INVISIBLE);
        gradePickedInput.setVisibility(View.INVISIBLE);
        submitBtnGR.setVisibility(View.INVISIBLE);
        cancelBtnGR.setVisibility(View.INVISIBLE);
    }



}