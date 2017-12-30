package com.example.rajk.raktdoot;

import android.app.Activity;
import android.app.Dialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> implements View.OnClickListener {

    private List<Donor> moviesList;
    DatabaseReference mDatabase;
    public MoviesAdapter(List<Donor> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public void onClick(View v) {

    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView post_name, post_location, post_bloodG, post_state;
        public Button sendRequest;

        public MyViewHolder(View view) {
            super(view);
            post_name=(TextView)view.findViewById(R.id.name);
            post_state=(TextView)view.findViewById(R.id.state);
             post_location=(TextView)view.findViewById(R.id.city);
             post_bloodG = (TextView)view.findViewById(R.id.bloodG);
             sendRequest  = (Button)view.findViewById(R.id.sendRequest);
             sendRequest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final View view = (LayoutInflater.from(v.getContext())).inflate(R.layout.message_dialog, null);

                    AlertDialog.Builder alertbuilder = new AlertDialog.Builder(v.getContext());
                    alertbuilder.setView(view);
                    alertbuilder.setCancelable(true);
                    final Dialog dialog = alertbuilder.create();
                    dialog.show();
                    final EditText name =(EditText)view.findViewById(R.id.Name);
                    name.setText(MainActivity.user.getDisplayName());
                    final EditText message =(EditText)view.findViewById(R.id.Message);
                    final EditText  contact=(EditText)view.findViewById(R.id.Contact);
                    Button sendM = (Button)view.findViewById(R.id.sendM);
                    sendM.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String Name = name.getText().toString().trim();
                            String Message = message.getText().toString().trim();
                            String  Contact= contact.getText().toString().trim();
                            if(!(TextUtils.isEmpty(Name)||TextUtils.isEmpty(Message)||TextUtils.isEmpty(Contact)))
                            {
                            mDatabase = FirebaseDatabase.getInstance().getReference();
                            DatabaseReference db = mDatabase.child("User_Messages").child(findDonor.selDonor.getUId()).child("Messages").push();
                            db.child("sender").setValue(name.getText().toString());
                            db.child("message").setValue(message.getText().toString());
                            db.child("contact").setValue(Contact);
                                Calendar c = Calendar.getInstance();
                                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                                String formattedDate = df.format(c.getTime());
                                db.child("date").setValue(formattedDate);
                   //         Toast.makeText(v.getContext(),"Message Sent Successfully",Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            }
                            else
                            {
                                Toast.makeText(v.getContext(),"Fill the Required Fields",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }
            });
            view.setOnClickListener(MoviesAdapter.this);
        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_layout_donor, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Donor movie = moviesList.get(position);
        holder.post_name.setText(movie.getName());
        holder.post_location.setText(movie.getCity());
        holder.post_bloodG.setText(movie.getBloodGroup());
        holder.post_state.setText(movie.getState());

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}