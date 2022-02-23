package com.example.proccoli2.NewModels;

import java.util.ArrayList;
import java.util.HashMap;

public class SelfTimeReportGoalModel {
    class groupGoalStructureForSelfTimeReport {
        String goalId;
        String bigGoal;
        String goalType;
        boolean isGoalCompleted;
        String taskType;
        HashMap<String, groupMembersPack> groupMembers;
        groupSubgoalPack subGoalPack;s

        static func subgoalChecker(subPack:groupSubgoalPack?,uid:String, completion:@escaping(_ isThereSub:Bool, _ response:[groupSubGoalFields]?)->()) {
            let subFilter = subPack?.subGoalsArray?.filter({$0.assignedToUid == uid && $0.isChecked != true && $0.isDeleted == false}).sorted(by: {$0.subDeadline > $1.subDeadline })
            if (subFilter?.count ?? 0) > 0  {
                completion(true, subFilter);
            }else {
                completion(false, nil);
            }
        }
    }

    class individualGoalStructForSelfTimeReport{
        String goalId;
        String bigGoal;
        String goalType;
        boolean isGoalCompleted;
        String taskType;
        subGoalPacks subGoalsPack;
    }

    class subGoalPacks{
    }

    class subGoalFields{
        boolean isChecked;
        boolean isDeleted;
        double subDeadline;
        String subGoalName;
        int totalStudiedTime;
        String subGoalId;

    }

}
