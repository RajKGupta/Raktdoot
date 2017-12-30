package com.example.rajk.raktdoot;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class MessageRequestShow extends Snackbar {
    private RecyclerView mBlogList;
    private DatabaseReference mDatabase;

    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frame = (FrameLayout)findViewById(R.id.frame);
        getLayoutInflater().inflate(R.layout.activity_message_request_show, frame);

        notify.setEnabled(false);
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("User_Messages").child(MainActivity.user.getUid()).child("Messages");
        mDatabase.child("-KchGzmzEHwZj0pO3y1P").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Map<String,String> map = dataSnapshot.getValue(Map.class);

                //Toast.makeText(getApplicationContext(),messageRequest.getContact()+messageRequest.getSender(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mBlogList = (RecyclerView)findViewById(R.id.message_list);
        mBlogList.setHasFixedSize(true);
        mBlogList.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();
        new ChildAddAsync().execute();
    }

    private static class MRViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public MRViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
        }
        public void set_Sender(String title)
        {
            TextView Sender=(TextView)mView.findViewById(R.id.tv_name);
        Sender.setText(title);
        }

        public void set_Message(String desc)
        {
           TextView Message=(TextView)mView.findViewById(R.id.tv_message);
            Message.setText(desc);
        }
        public void set_Date(String d)
        {
            TextView Date=(TextView)mView.findViewById(R.id.tv_date);
            Date.setText(d);
        }
        public void set_Contact(String d)
        {
            TextView Contact=(TextView)mView.findViewById(R.id.tv_contact);
            Contact.setText(d);
        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(MessageRequestShow.this,Main2Activity.class));
    }


    class ChildAddAsync extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(MessageRequestShow.this);
            pd.setMessage("Loading...");
            pd.setIndeterminate(false);
            pd.setCancelable(true);
            pd.show();
        }

        @Override
        protected String doInBackground(final String... params) {
            try {

                FirebaseRecyclerAdapter<MessageRequest,MRViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<MessageRequest, MRViewHolder>(
                        MessageRequest.class,
                        R.layout.row_layout,
                        MRViewHolder.class,
                        mDatabase
                ) {
                    @Override
                    protected void populateViewHolder(MRViewHolder viewHolder, MessageRequest model, int position) {
                        viewHolder.set_Sender(model.getSender());
                        viewHolder.set_Message(model.getMessage());
                        viewHolder.set_Date(model.getDate());
                        viewHolder.set_Contact(model.getContact());

                        pd.dismiss();
                    }
                };
                mBlogList.setAdapter(firebaseRecyclerAdapter);

            } catch (Exception e) {
                e.printStackTrace();
                Log.d("Failure", "FAILURE,FAILURE 111111");
            }
            return null;

        }

        @Override
        protected void onPostExecute(String j)
        {

        }
    }

}
