package com.example.proccoli2;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.opengl.Visibility;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowId;
import android.view.accessibility.AccessibilityEvent;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class goalView2 extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    String[] goalTypes = {"Assignment", "Discussion/Forum","Exam (Final)", "Exam (Midterm)",
            "Exam (Other)", "Group Project", "Project (Individual)", "Studying (Reading)", "Studying (Watching Videos)",
            "Studying (Other)", "Other"};

    GoalModel myGoal = new GoalModel();
    TextInputEditText bigGoalInput, courseNumberInput;
    Button createSubGoalBtn, createGoalBtn;
    TextView goalCompleteBy, goalStartDate, goalDueDate, goalType,displaySubGoals;
    Context context = this;
    RecyclerView subGoalRecyclerView;
    goalCreation_VC controller = new goalCreation_VC(this);
    GoalModel passedGoal;
    Toolbar toolbar;

    //Pickers used to get input
    SingleDateAndTimePicker completeByPicker, startPicker, duePicker;
    NumberPicker goalTypePicker;
    Button hideInputBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goal_view2);
        displaySubGoals = findViewById(R.id.displaySubGoal);
        displaySubGoals.setVisibility(View.INVISIBLE);

        bigGoalInput = findViewById(R.id.bigGoalInput2);
        bigGoalInput.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                bigGoalInput.setCursorVisible(true);
            }
        });

        courseNumberInput = findViewById(R.id.courseNumberInput);
        courseNumberInput.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                courseNumberInput.setCursorVisible(true);
            }
        });

        toolbar = findViewById(R.id.toolbarGoalView2);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("toolbar clicked", "onClick: I was clicked");
                finish();
            }
        });

        hideInputBtn = findViewById(R.id.hideInputBtn);
        goalTypePicker = findViewById(R.id.goalTypePicker);
        goalTypePicker.setVisibility(View.INVISIBLE);
        startPicker = findViewById(R.id.startPicker);
        startPicker.setVisibility(View.INVISIBLE);
        completeByPicker = findViewById(R.id.completeByPicker);
        completeByPicker.setVisibility(View.INVISIBLE);
        duePicker = findViewById(R.id.deadlinePicker);
        duePicker.setVisibility(View.INVISIBLE);

        goalType = findViewById(R.id.goalTypeInput);
        goalTypePicker.setMinValue(0);
        goalTypePicker.setMaxValue(goalTypes.length-1);
        goalTypePicker.setDisplayedValues(goalTypes);
        goalType.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Log.d("goalType", "onClick: I am looking at goalType");
             //   openGoalPicker();
                closeKeyboard(view);
                completeByPicker.setVisibility(View.INVISIBLE);
                duePicker.setVisibility(View.INVISIBLE);
                startPicker.setVisibility(View.INVISIBLE);

                goalTypePicker.setVisibility(View.VISIBLE);

            }
        });

        goalTypePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                Log.d("Picked", "onClick: " + goalTypes[goalTypePicker.getValue()]);
                goalType.setText(goalTypes[goalTypePicker.getValue()]);
            }
        });

        subGoalRecyclerView = findViewById(R.id.subGoalRecyclerView);
        setUpRecyclerView();


        ActivityResultLauncher<Intent> activityResultLaunch = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            passedGoal = (GoalModel) result.getData().getSerializableExtra("bigGoal");
                            Log.d("HERE PassedGoal", String.valueOf(passedGoal));

                           /* for (int i = 0; i < passedGoal.getSubGoals().size(); i++) {
                                myGoal.getSubGoals().add(passedGoal.getSubGoals().get(i));
                            }

                            */
                            myGoal.getSubGoals().add(passedGoal.getSubGoals().get(passedGoal.getSubGoals().size()-1));
                            displaySubGoals.setText("\nSubgoals");
                            displaySubGoals.setVisibility(View.VISIBLE);
                            subGoalRecyclerView.setVisibility(View.VISIBLE);

                            TestAdapter adapter = (TestAdapter) subGoalRecyclerView.getAdapter();
                            adapter.addItems();
                        }
                    }
                });

        createSubGoalBtn = findViewById(R.id.addSubgoalBtn2);
        createSubGoalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("I was clicked", "onClick: addSubGoal");
                boolean null2 = false;
                if (null2 = controller.nullFieldCheck(goalCompleteBy.getText().toString(), "Please enter a complete by date", context, goalCompleteBy)) {
                    goalCompleteBy.setHintTextColor(Color.rgb(128,128,128));
                    Intent i = new Intent(goalView2.this, subGoalView.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("completeBy", goalCompleteBy.getText().toString());
                    i.putExtras(bundle);
                    //Passes bigGoal info to keep working list of subgoals
                    i.putExtra("bigGoal", myGoal);
                  //  startActivity(i);
                    activityResultLaunch.launch(i);
                }
            }
        });

        goalCompleteBy = findViewById(R.id.goalCompleteByInput);
        goalCompleteBy.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Log.d("I was clicked", "onClick: goalCompleteBy");
                closeKeyboard(view);

                duePicker.setVisibility(View.INVISIBLE);
                startPicker.setVisibility(View.INVISIBLE);
                goalTypePicker.setVisibility(View.INVISIBLE);

                completeByPicker.setVisibility(View.VISIBLE);


            }
        });

        completeByPicker.addOnDateChangedListener(new SingleDateAndTimePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(String s, Date date) {
                Log.d("CompletePicker", "onScrollChange: ");
                goalCompleteBy.setText(controller.dateToStr(completeByPicker.getDate()));


            }
        });


        goalDueDate = findViewById(R.id.goalDueDateInput);
        goalDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //cLoses Keyboard
                closeKeyboard(view);
                completeByPicker.setVisibility(View.INVISIBLE);
                startPicker.setVisibility(View.INVISIBLE);
                goalTypePicker.setVisibility(View.INVISIBLE);

                duePicker.setVisibility(View.VISIBLE);
                Log.d("I was clicked", "onClick: goalDueDate");
            }
        });


        duePicker.addOnDateChangedListener(new SingleDateAndTimePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(String s, Date date) {
                Log.d("CompletePicker", "onScrollChange: ");
                goalDueDate.setText(controller.dateToStr(duePicker.getDate()));
            }
        });

        hideInputBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeKeyboard(view);
                duePicker.setVisibility(View.INVISIBLE);
                completeByPicker.setVisibility(View.INVISIBLE);
                startPicker.setVisibility(View.INVISIBLE);
                goalTypePicker.setVisibility(View.INVISIBLE);
            }
        });


        /*
        Goal is not created or checked for errors until the createGoalBtn is clicked.
        This is where it will do nullFieldChecks and other logical checks.
        It also does the actual goal creation of the goal model.
         */
        createGoalBtn = findViewById(R.id.createGoalBtn2);
        createGoalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("I was clicked", "onClick: createGoal");

                closeKeyboard(view);
                //goalCompleteBy, goalStartDate, goalDueDate, goalType

                boolean null0 = false;
                boolean null1 = false;
                boolean null2 = false;
                boolean null3 = false;
                boolean null4 = false;
                boolean null5 = false;


                if(null0 = controller.nullFieldCheck(bigGoalInput.getText().toString(),"Please enter a goal",context,bigGoalInput)) {
                    goalType.setHintTextColor(Color.rgb(128,128,128));
                    if (null1 = controller.nullFieldCheck(goalCompleteBy.getText().toString(), "Please enter a complete by date", context, goalCompleteBy)) {
                        goalCompleteBy.setHintTextColor(Color.rgb(128,128,128));
                        if (null2 = controller.nullFieldCheck(goalStartDate.getText().toString(), "Please enter a goal start date", context, goalStartDate)) {
                            goalStartDate.setHintTextColor(Color.rgb(128,128,128));
                            if (null3 = controller.nullFieldCheck(goalDueDate.getText().toString(), "Please enter a goal due date", context, goalDueDate)) {
                                goalDueDate.setHintTextColor(Color.rgb(128,128,128));;
                                if (null4 = controller.nullFieldCheck(goalType.getText().toString(), "Please select a goal type", context, goalType)) {
                                    goalType.setHintTextColor(Color.rgb(128,128,128));
                                    if (null5 = controller.nullFieldCheck(courseNumberInput.getText().toString().toUpperCase(), "Please enter a course number", context, courseNumberInput)) {
                                        courseNumberInput.setHintTextColor(Color.rgb(128, 128, 128));
                                        Log.d("Date", "compareDates: " + goalStartDate.getText().toString() + goalCompleteBy.getText().toString() + goalDueDate.getText().toString());
                                        try {
                                            if (controller.compareDates(goalStartDate.getText().toString(), goalCompleteBy.getText().toString(), goalDueDate.getText().toString(), context, goalView2.this)) {
                                                Log.d("Success", "onClick: ran");
                                                goalCompleteBy.setHintTextColor(Color.rgb(128, 128, 128));
                                                goalStartDate.setHintTextColor(Color.rgb(128, 128, 128));
                                                goalDueDate.setHintTextColor(Color.rgb(128, 128, 128));

                                                goalCompleteBy.setTextColor(Color.rgb(128, 128, 128));
                                                goalStartDate.setTextColor(Color.rgb(128, 128, 128));
                                                goalDueDate.setTextColor(Color.rgb(128, 128, 128));


                                                int uComplete = controller.dateStrToUnix(goalCompleteBy.getText().toString());
                                                int uStart = controller.dateStrToUnix(goalStartDate.getText().toString());
                                                int uDue = controller.dateStrToUnix(goalDueDate.getText().toString());
                                                long currentUnix = System.currentTimeMillis() / 1000L;

                                                // GoalModel goal = new GoalModel(bigGoalInput.getText().toString(),uComplete,goalType.getText().toString(),uStart,uDue,(int)currentUnix,subgoals);
                                                myGoal.setBigGoal(bigGoalInput.getText().toString());
                                                myGoal.setCompletedBy(uComplete);
                                                myGoal.setGoalType(goalType.getText().toString());
                                                myGoal.setStartDate(uStart);
                                                myGoal.setDeadline(uDue);
                                                myGoal.setCreatedAt((int) currentUnix);
                                                myGoal.setCourseNumber(courseNumberInput.getText().toString().toUpperCase());
                                                myGoal.setGoalType("individual");
                                                String myGoalID = "TESTGOALID";
                                                myGoal.setGoalId(myGoalID);

                                                Log.d("GoalMade", "onClick: " + myGoal.toString());
                                                //  myGoal = new GoalModel();


                                                Intent i = new Intent(goalView2.this, MainActivity.class);
                                                i.putExtra("bigGoal", myGoal);
                                                //  startActivity(i);
                                                //  finish();
                                                Log.d("putExtras", "onClick: " + myGoal);
                                                setResult(RESULT_OK, i);
                                                Log.d("setExtras", "onClick: " + myGoal);
                                                finish();
                                            } else {
                                                Toast toast = Toast.makeText(getBaseContext(), "At least one date value is invalid.", Toast.LENGTH_LONG);
                                                toast.show();

                                                goalStartDate.setTextColor(Color.RED);
                                                goalCompleteBy.setTextColor(Color.RED);
                                                goalDueDate.setTextColor(Color.RED);

                                            }

                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }

                        }

                    }
                }
            }
        });

        goalStartDate = findViewById(R.id.goalStartDateInput);
        goalStartDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //Closes keyboard
                closeKeyboard(view);
                completeByPicker.setVisibility(View.INVISIBLE);
                duePicker.setVisibility(View.INVISIBLE);
                goalTypePicker.setVisibility(View.INVISIBLE);

                Log.d("I was clicked", "onClick: goalStartDate");
                startPicker.setVisibility(View.VISIBLE);

            }
        });

        startPicker.addOnDateChangedListener(new SingleDateAndTimePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(String s, Date date) {
                Log.d("CompletePicker", "onScrollChange: ");
                goalStartDate.setText(controller.dateToStr(startPicker.getDate()));


            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Log.d("goalType selected", "onItemSelected: " + goalTypes[i]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void closeKeyboard(View view){
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            bigGoalInput.setCursorVisible(false);
            courseNumberInput.setCursorVisible(false);

        }
    }

    private void setUpRecyclerView() {
        subGoalRecyclerView.setVisibility(View.INVISIBLE);
        subGoalRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        subGoalRecyclerView.setAdapter(new TestAdapter());
        subGoalRecyclerView.setHasFixedSize(true);
        setUpItemTouchHelper();
        setUpAnimationDecoratorHelper();
    }

    /**
     * This is the standard support library way of implementing "swipe to delete" feature. You can do custom drawing in onChildDraw method
     * but whatever you draw will disappear once the swipe is over, and while the items are animating to their new position the recycler view
     * background will be visible. That is rarely an desired effect.
     */
    private void setUpItemTouchHelper() {

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            // we want to cache these and not allocate anything repeatedly in the onChildDraw method
            Drawable background;
            Drawable xMark;
            int xMarkMargin;
            boolean initiated;

            private void init() {
                background = new ColorDrawable(Color.RED);
                xMark = ContextCompat.getDrawable(goalView2.this, R.drawable.ic_clear_24dp);
                xMark.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
                xMarkMargin = (int) goalView2.this.getResources().getDimension(R.dimen.ic_clear_margin);
                initiated = true;
            }

            // not important, we don't want drag & drop
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                int position = viewHolder.getAdapterPosition();
                TestAdapter testAdapter = (TestAdapter)recyclerView.getAdapter();
                if (testAdapter.isPendingRemoval(position)) {
                    return 0;
                }
                return super.getSwipeDirs(recyclerView, viewHolder);
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int swipedPosition = viewHolder.getAdapterPosition();
                TestAdapter adapter = (TestAdapter)subGoalRecyclerView.getAdapter();
                adapter.remove(swipedPosition);

            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                View itemView = viewHolder.itemView;

                // not sure why, but this method get's called for viewholder that are already swiped away
                if (viewHolder.getAdapterPosition() == -1) {
                    // not interested in those
                    return;
                }

                if (!initiated) {
                    init();
                }

                // draw red background
                background.setBounds(itemView.getRight() + (int) dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
                background.draw(c);

                // draw x mark
                int itemHeight = itemView.getBottom() - itemView.getTop();
                int intrinsicWidth = xMark.getIntrinsicWidth();
                int intrinsicHeight = xMark.getIntrinsicWidth();

                int xMarkLeft = itemView.getRight() - xMarkMargin - intrinsicWidth;
                int xMarkRight = itemView.getRight() - xMarkMargin;
                int xMarkTop = itemView.getTop() + (itemHeight - intrinsicHeight)/2;
                int xMarkBottom = xMarkTop + intrinsicHeight;
                xMark.setBounds(xMarkLeft, xMarkTop, xMarkRight, xMarkBottom);

                xMark.draw(c);

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

        };
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        mItemTouchHelper.attachToRecyclerView(subGoalRecyclerView);
    }

    /**
     * We're gonna setup another ItemDecorator that will draw the red background in the empty space while the items are animating to thier new positions
     * after an item is removed.
     */
    private void setUpAnimationDecoratorHelper() {
       subGoalRecyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {

            // we want to cache this and not allocate anything repeatedly in the onDraw method
            Drawable background;
            boolean initiated;

            private void init() {
                background = new ColorDrawable(Color.RED);
                initiated = true;
            }

            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

                if (!initiated) {
                    init();
                }

                // only if animation is in progress
                if (parent.getItemAnimator().isRunning()) {

                    // some items might be animating down and some items might be animating up to close the gap left by the removed item
                    // this is not exclusive, both movement can be happening at the same time
                    // to reproduce this leave just enough items so the first one and the last one would be just a little off screen
                    // then remove one from the middle

                    // find first child with translationY > 0
                    // and last one with translationY < 0
                    // we're after a rect that is not covered in recycler-view views at this point in time
                    View lastViewComingDown = null;
                    View firstViewComingUp = null;

                    // this is fixed
                    int left = 0;
                    int right = parent.getWidth();

                    // this we need to find out
                    int top = 0;
                    int bottom = 0;

                    // find relevant translating views
                    int childCount = parent.getLayoutManager().getChildCount();
                    for (int i = 0; i < childCount; i++) {
                        View child = parent.getLayoutManager().getChildAt(i);
                        if (child.getTranslationY() < 0) {
                            // view is coming down
                            lastViewComingDown = child;
                        } else if (child.getTranslationY() > 0) {
                            // view is coming up
                            if (firstViewComingUp == null) {
                                firstViewComingUp = child;
                            }
                        }
                    }

                    if (lastViewComingDown != null && firstViewComingUp != null) {
                        // views are coming down AND going up to fill the void
                        top = lastViewComingDown.getBottom() + (int) lastViewComingDown.getTranslationY();
                        bottom = firstViewComingUp.getTop() + (int) firstViewComingUp.getTranslationY();
                    } else if (lastViewComingDown != null) {
                        // views are going down to fill the void
                        top = lastViewComingDown.getBottom() + (int) lastViewComingDown.getTranslationY();
                        bottom = lastViewComingDown.getBottom();
                    } else if (firstViewComingUp != null) {
                        // views are coming up to fill the void
                        top = firstViewComingUp.getTop();
                        bottom = firstViewComingUp.getTop() + (int) firstViewComingUp.getTranslationY();
                    }

                    background.setBounds(left, top, right, bottom);
                    background.draw(c);

                }
                super.onDraw(c, parent, state);
            }

        });
    }

    /**
     * RecyclerView adapter enabling undo on a swiped away item.
     */
    class TestAdapter extends RecyclerView.Adapter {

        private static final int PENDING_REMOVAL_TIMEOUT = 3000; // 3sec

        List<SubGoalModel> items;
        List<SubGoalModel> itemsPendingRemoval;
        int lastInsertedIndex; // so we can add some more items for testing purposes
        boolean undoOn; // is undo on, you can turn it on from the toolbar menu

        public TestAdapter() {
            items = new ArrayList<>();
            itemsPendingRemoval = new ArrayList<>();

            lastInsertedIndex = 0;
            /*
            // let's generate some items
            lastInsertedIndex = 2;
            // this should give us a couple of screens worth


            for (int i=1; i<= lastInsertedIndex; i++) {
                items.add(new SubGoalModel("eat", 1000,10, "1 hour", 2000,3000));
            }
            */


            for(int i = 0; i < myGoal.getSubGoals().size(); i++){
                Log.d("trying to add", "setUpRecyclerView: " + myGoal.getSubGoals().get(i));
                items.add(myGoal.getSubGoals().get(i));
                Log.d("Added", "setUpRecyclerView: " + myGoal.getSubGoals().get(i));
            }


        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new TestViewHolder(parent);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            TestViewHolder viewHolder = (TestViewHolder)holder;
            final SubGoalModel item = items.get(position);

            if (itemsPendingRemoval.contains(item)) {
                // we need to show the "undo" state of the row
                viewHolder.itemView.setBackgroundColor(Color.RED);
                viewHolder.titleTextView.setVisibility(View.GONE);

            } else {
                // we need to show the "normal" state
                viewHolder.itemView.setBackgroundColor(Color.WHITE);
                viewHolder.titleTextView.setVisibility(View.VISIBLE);
                viewHolder.titleTextView.setText("\n" + item.getSubGoalName());
            }
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        /**
         *  Utility method to add some rows for testing purposes. You can add rows from the toolbar menu.
         */
        public void addItems(){
                items.add(myGoal.getSubGoals().get(myGoal.getSubGoals().size()-1));
                notifyItemInserted(items.size() - 1);
                lastInsertedIndex = lastInsertedIndex + 1;
        }

        public void remove(int position) {
            SubGoalModel item = items.get(position);
            if (itemsPendingRemoval.contains(item)) {
                itemsPendingRemoval.remove(item);
            }
            if (items.contains(item)) {
                items.remove(position);
                notifyItemRemoved(position);
            }
        }

        public boolean isPendingRemoval(int position) {
            SubGoalModel item = items.get(position);
            return itemsPendingRemoval.contains(item);
        }
    }

    /**
     * ViewHolder capable of presenting two states: "normal" and "undo" state.
     */
    static class TestViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;

        public TestViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.subgoalitem_view, parent, false));
            titleTextView = (TextView) itemView.findViewById(R.id.subGoalText);
        }

    }


}
