package com.example.proccoli2.ui.individualWall;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proccoli2.NewModels.DataServices;
import com.example.proccoli2.NewModels.IndividualGoalModel;
import com.example.proccoli2.NewModels.IndividualSubGoalStructModel;
import com.example.proccoli2.NewModels.ResultHandler;
import com.example.proccoli2.NewModels.UserDataModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class singleGoalView_VC extends AppCompatActivity {

    private final singleGoalView singleGoalView;
    public singleGoalView_VC(singleGoalView singleGoalView){
        this.singleGoalView = singleGoalView;
    }

    public String unixToString(int unix){
        Date date = new java.util.Date(unix*1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("MMM, dd, yyyy");
        return sdf.format(date);

    }

    public String unixToStringDateTime(int unix){
        Date date = new java.util.Date(unix*1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("MMM, dd, yyyy --\nhh:mm aa");
        return sdf.format(date);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String calculateMinutesAgo(int unix){
        /*
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime unixConverted = LocalDateTime.ofEpochSecond(unix, 0, ZoneOffset.UTC);
        Log.d("currentUnix", "calculateMinutesAgo: " +now + " - " + unixConverted);
        return String.valueOf(Duration.between(now,unixConverted).toMinutes());

         */
        long today = System.currentTimeMillis()/1000L;

        Log.d("calculateMinutes", "calculateMinutesAgo: " + today + " - " + unix);
        int difference = (int)today - unix;
        if ((difference/3600)<1)
            return "\t"+ difference / 60 + " m ago";
        else{
            int hours = 0;
            while(difference >3600){
                hours++;
                difference = difference-3600;
            }
            return "\t" + hours + "h " + (difference/60) + " m ago";
        }
    }

    public int dateStrToUnix(String time) {
        long unixTime = 0;
        //SimpleDateFormat sf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");//Specify your timezone
        SimpleDateFormat sf = new SimpleDateFormat("MMM, dd, yyyy --hh:mm aa");

        try {
            unixTime = sf.parse(time).getTime();
            unixTime = unixTime / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (int)unixTime;
    }

    public int dateStrToUnix2(String time) {
        long unixTime = 0;
        SimpleDateFormat sf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");//Specify your timezone
       // SimpleDateFormat sf = new SimpleDateFormat("MMM, dd, yyyy --hh:mm aa");

        try {
            unixTime = sf.parse(time).getTime();
            unixTime = unixTime / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (int)unixTime;
    }


    public void completeBtnTapped(IndividualGoalModel goalModel) {
        if(goalModel==null){
            return;
        }
        if(goalModel.getSubGoals().size()!=0){
            //chech if there is any uncheck sub goal
            int uncheck = countCompletedSubGoals(goalModel.getSubGoals());
            if( countCompletedSubGoals(goalModel.getSubGoals()) > 0){
                //send alert here
                AlertDialog.Builder builder1 = new AlertDialog.Builder(singleGoalView);
                builder1.setTitle("Warning!");
                builder1.setMessage("You have " + uncheck + "  incomplete subgoals!\nDo you still want to complete the goal?");
                builder1.setCancelable(true);
                builder1.setPositiveButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                proccedGoalCompletion(goalModel);
                                evaluationCheckerForCompletion();
                            }
                        });

                builder1.setNegativeButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                }
                            });

                AlertDialog alert11 = builder1.create();
                alert11.show();
                return;
            }
			else {
                //procceed the completion
                //all sub goals are checked
                proccedGoalCompletion(goalModel);
                evaluationCheckerForCompletion();
                return;
            }
        }else {
            //there is no sub goal
            //procceed the completion
            proccedGoalCompletion(goalModel);
            evaluationCheckerForCompletion();
        }

    }

    public void evaluationCheckerForCompletion() {
        /*
        guard let mainGoalId = self.data?.goalId,
                let goalName = self.data?.bigGoal  else {return}
        var subGoalIds = [NO_SUB_GOAL_REF]
        if self.data?.subGoals?.count ?? 0 > 0 {
            if let sortedSubIds = self.data?.subGoals?.sorted(by: {$0._subGoalId < $1._subGoalId}){
                for subId in sortedSubIds {
                    subGoalIds.append(subId._subGoalId)
                    if sortedSubIds.count + 1 == subGoalIds.count{
                        //make one more sort because of no sub
                        let newSortedIds = subGoalIds.sorted(by: {$0 < $1})
                        self.letUsKnowChartDataDownloaded(goalName: goalName, goalId: mainGoalId, subGoalIds: newSortedIds)
                    }
                }
            }
        }
			else {
            self.letUsKnowChartDataDownloaded(goalName: goalName, goalId: mainGoalId, subGoalIds:subGoalIds)
        }

         */
    }

    public void proccedGoalCompletion(IndividualGoalModel newData) {
        //procceed the completion
        newData.setCompleted(true);
        singleGoalView.myGoal.setCompleted(true);
        //send data server here
        DataServices.completeGoal(newData.getGoalId());
        //update local data on Profile VC
        UserDataModel.sharedInstance.setCompletedGoalTotal((int)getValueOrDefault(UserDataModel.sharedInstance.getCompletedGoalTotal(),0)+1);
        //let profile VC data need to refresh
        /*
        if let index = UserDataModel.sharedInstance.rawGoalsData?.firstIndex(where: {$0.goalId == newData.goalId}) {
            UserDataModel.sharedInstance.rawGoalsData?[index].isCompleted = true
            UserDataModel.sharedInstance.rawGoalsData = UserDataModel.sharedInstance.rawGoalsData
        }


        //activity log activity
        activityChain?.addActivityForGoal(type: COMPLETE_GOAL_BTN_TAPPED_REF, goalId: newData.goalId)
        //end log activiy

         */
    }



    public int countCompletedSubGoals(ArrayList<IndividualSubGoalStructModel> subgoals){
        int counter = 0;
        for(int i = 0; i<subgoals.size();i++) {
            if (subgoals.get(i).is_isChecked() == true)
                counter++;
        }
        return counter;
    }


    public static <T> T getValueOrDefault(T value, T defaultValue) {
        return value == null ? defaultValue : value;
    }

    public String getAlphaNumericString(int n)
    {

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int)(AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }

}
