package com.example.proccoli2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proccoli2.oldModels.UserModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class searchforfreinds_view extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<UserModel> searchUserList;
    searchUserAdapter adapter;
    int friendSelected;
    List<UserModel> itemsCopy;
    Toolbar toolbar;
    Button inviteByEmailBtn, searchFriendsBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d("notificationFragment", "onCreateView: starting activity");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.searchforfriends_view);



        searchUserList = new ArrayList<UserModel>();
        searchUserList.add(new UserModel("Le Jie Bennett", "lgbennett@albany.edu"));
        searchUserList.add(new UserModel("Anakin Skywalker", "askywalker@albany.edu"));
        searchUserList.add(new UserModel("Bobba Fett", "bfettt@albany.edu"));
        searchUserList.add(new UserModel("Ahsoka Tano", "atano@albany.edu"));
        searchUserList.add(new UserModel("Obi wan Kenobi", "okenobi@albany.edu"));
        searchUserList.add(new UserModel("Padame Amidala", "pamidala@albany.edu"));


        toolbar =findViewById(R.id.toolbarSearchForFriends);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                    int itemId = item.getItemId();
                    if (itemId == R.id.searchFriendsCancel) {
                        finish();
                    }
                return false;
            }
        });


        Log.d("recievedGoalList", "onCreate: " + searchUserList);

        recyclerView = findViewById(R.id.searchUserList);
        setUpUserSearchRecyclerView();

        SearchView searchView = findViewById(R.id.search_bar_searchForFriends);
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

        inviteByEmailBtn = findViewById(R.id.inviteByEmailBtn);
        inviteByEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openInviteUserPopUp();
            }
        });

        searchFriendsBtn = findViewById(R.id.searchFriendsBtn);
        searchFriendsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(searchView.getQuery()!=null){
                    filter(searchView.getQuery().toString());
                }
                else{
                    Toast.makeText(getBaseContext(), "Please enter an email to search for", Toast.LENGTH_SHORT).show();
                }
                
            }
        });



    }

    private void filter(String text) {
        ArrayList<UserModel> filteredList = new ArrayList<>();

        for (UserModel item : searchUserList) {
            if (item.getEmail().toLowerCase().contains(text.toLowerCase())) {
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

    class searchUserAdapter extends RecyclerView.Adapter{
        List<UserModel> items;


        public searchUserAdapter() {
            items = new ArrayList<>();

            for(int i = 0; i < searchUserList.size(); i++){
                items.add(searchUserList.get(i));
                Log.d("Added", "setUpRecyclerView: " + searchUserList.get(i));
            }
        }

        public void filterList(ArrayList<UserModel> filterllist) {
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
            return new SearchUserViewHolder(parent);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            Log.d("Position", "onBindViewHolder: position" + position);
            SearchUserViewHolder viewHolder = (SearchUserViewHolder) holder;
            final UserModel item = items.get(position);
            Log.d("Position", "onBindViewHolder: position" + item);
            viewHolder.friendsEmail.setText(items.get(position).getEmail());
            viewHolder.letterBtn.setText(Character.toString(items.get(position).getEmail().charAt(0)));
        }

        @Override
        public int getItemCount() {
            return items.size();
        }
    }

    class SearchUserViewHolder extends RecyclerView.ViewHolder {

        TextView friendsEmail;
        TextView inviteBtn;
        MaterialButton letterBtn;


        public SearchUserViewHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.friendsitem_view, parent, false));
            friendsEmail= (TextView) itemView.findViewById(R.id.friendEmailLabel);

            inviteBtn = (TextView) itemView.findViewById(R.id.inviteUserItemBtn);
            inviteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    friendSelected = itemView.getVerticalScrollbarPosition();
                    Log.d("ClickedInvite","Invite User" + friendsEmail.getText());
                }
            });

            letterBtn = (MaterialButton) itemView.findViewById(R.id.letterBtn);
            letterBtn.setClickable(false);
        }
    }

    private void setUpUserSearchRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new searchUserAdapter();
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

    }

    public void closeKeyboard(View view){
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void openInviteUserPopUp(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View view = this.getLayoutInflater().inflate(R.layout.invite_popup, null);
        builder.setView(view);

        final TextInputEditText inviteEmailInput = (TextInputEditText) view.findViewById(R.id.invitePopUpEmailInput);



        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                //positive button action
                boolean null0;
                if (null0 = nullFieldCheck(inviteEmailInput.getText().toString(), "Please enter a complete by date", getBaseContext(), inviteEmailInput)) {
                    closeKeyboard(view);
                    Log.d("Invite sending", "onClick: Sending invite to: " + inviteEmailInput.getText());
                }
            }
        })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //negative button action
                        closeKeyboard(view);

                    }
                });
        builder.create().show();
    }



    public boolean nullFieldCheck(String input, String errorMessage, Context context, TextInputEditText textView) {
        if (input.length() == 0) {
            CharSequence text = errorMessage;
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            textView.setHintTextColor(Color.RED);
            return false;
        }
        return true;
    }
}




