package com.example.rajk.raktdoot;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.ArrayList;
import java.util.List;

public class findDonor extends AppCompatActivity  {

    EditText search_bg,search_state;

    DatabaseReference mDatabase;
    private ArrayList<Donor> mDonorList,m,filtered;
    ProgressDialog progressDialog;
    DatabaseReference mRef;
    RecyclerView recyclerView;
    private MoviesAdapter mAdapter;
    public static Donor selDonor;
    Button button;
    LinearLayout l;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_donor);
        mDatabase= FirebaseDatabase.getInstance().getReference();

        mRef = mDatabase.child("Users").getRef();
        search_bg =  (EditText) findViewById(R.id.search_bg);
        search_state =  (EditText) findViewById(R.id.search_state);
        progressDialog = new ProgressDialog(this);
        mDonorList = new ArrayList<>();
        m = new ArrayList<>();
        filtered = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.donor_list);
        button= (Button)findViewById(R.id.button);
        l= (LinearLayout)findViewById(R.id.l);

        button.setVisibility(View.VISIBLE);
        l.setVisibility(View.INVISIBLE);

        addingrows(mDonorList);
        m=mDonorList;

        new ChildAddAsync().execute();

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                selDonor = m.get(position);
                Toast.makeText(getApplicationContext(),selDonor.getUId(),Toast.LENGTH_LONG).show();
                }

            @Override
            public void onLongClick(View view, final int position) {
            }

        }));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setVisibility(View.INVISIBLE);
                l.setVisibility(View.VISIBLE);
            }
        });

        search_bg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(search_bg.getText().equals(""))
                {
                    addingrows(m);
                }
                else
                {
                    filtered.clear();
                    addingrows(filtered);

                    s = s.toString().toLowerCase();
                    for (int i = 0; i < mDonorList.size(); i++) {
                        String item = mDonorList.get(i).getBloodGroup().toString().toLowerCase();

                        if (item.contains(s)) {
                            filtered.add(mDonorList.get(i));
                        }
                    }

                    m = filtered;
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                mAdapter.notifyDataSetChanged();
            }
        });

        search_state.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(search_state.getText().equals(""))
                {
                    addingrows(m);
                }
                else
                {
                    filtered.clear();
                    addingrows(filtered);

                    s = s.toString().toLowerCase();
                    for (int i = 0; i < mDonorList.size(); i++) {
                        String item = mDonorList.get(i).getState().toString().toLowerCase();

                        if (item.contains(s)) {
                            filtered.add(mDonorList.get(i));
                        }
                    }

                    m = filtered;
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    private void addingrows(final List<Donor> k)
    {
        mAdapter = new MoviesAdapter(k);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
      //  progressDialog.dismiss();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(findDonor.this,Main2Activity.class));
    }
    class ChildAddAsync extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(findDonor.this);
            pd.setMessage("Loading...");
            pd.setIndeterminate(false);
            pd.setCancelable(true);
            pd.show();

        }

        @Override
        protected String doInBackground(final String... params) {
            try {
                mRef.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                      //  progressDialog.show();
                        Donor donor = dataSnapshot.getValue(Donor.class);
                        mDonorList.add(donor);
                        mAdapter.notifyDataSetChanged();
                        pd.dismiss();
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
                     //   progressDialog.show();
                    }

                });

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


