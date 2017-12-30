package com.example.rajk.raktdoot;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.transition.Transition;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Map;

public class registerDonor extends AppCompatActivity {
    static SharedPreferences sharedPreference;
    static SharedPreferences.Editor editor;

    DatabaseReference mDatabase;
    EditText name, email,contact,city,diseases,age;
    AutoCompleteTextView state;
    Button button;
    private ProgressDialog progressDialog;
    private RadioGroup radioSexGroup;
    Spinner spinner;
    TextInputLayout input_name,input_contact,input_city,input_state,input_diseases,input_age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_donor);
        input_name= (TextInputLayout)findViewById(R.id.input_name);
        input_contact= (TextInputLayout)findViewById(R.id.input_contact);
        input_city= (TextInputLayout)findViewById(R.id.input_city);
        input_state= (TextInputLayout)findViewById(R.id.input_state);
        input_diseases= (TextInputLayout)findViewById(R.id.input_diseases);
        input_age= (TextInputLayout)findViewById(R.id.input_age);
        progressDialog = new ProgressDialog(registerDonor.this);
        name = (EditText)findViewById(R.id.name);
        name.setText(MainActivity.user.getDisplayName());
        contact = (EditText)findViewById(R.id.contact);
        contact.addTextChangedListener(contactTextWatcher);
        email = (EditText)findViewById(R.id.email);
        email.setText(MainActivity.user.getEmail());
        state = (AutoCompleteTextView)findViewById(R.id.state);
        city = (EditText)findViewById(R.id.city);
        age = (EditText)findViewById(R.id.dob);
        age.addTextChangedListener(ageTextWatcher);
        diseases = (EditText)findViewById(R.id.diseases);
        radioSexGroup=(RadioGroup)findViewById(R.id.radioGroup);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.group, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPost();

            }
        });

        String[] states = getResources().getStringArray(R.array.list_of_countries);
        ArrayAdapter<String> adapterstate = new ArrayAdapter<String>
                (this,android.R.layout.simple_dropdown_item_1line,states);
        state.setAdapter(adapterstate);
        state.setThreshold(1);//will start working from first character
        state.setTextColor(Color.BLACK);



    }
    private final TextWatcher contactTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (contact.getText().toString().length()!=10) {
                input_contact.setError("Enter a valid Contact Number");
                if (contact.requestFocus()) {
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }
                else {
                    input_contact.setErrorEnabled(false);
                }

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    private final TextWatcher ageTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }


        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {


            String k= age.getText().toString();
            int j=0;
            if(!TextUtils.isEmpty(k))
            {
                 j=Integer.parseInt(k);;
            }
            if (j<17||j>60) {
                input_age.setError("Enter valid Age");
                if(age.requestFocus()) {
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
                }
                else
                {
                    input_age.setErrorEnabled(false);
                }


        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private void startPost() {
         final String Name = name.getText().toString().trim();
         final String Email = email.getText().toString().trim();
         final String Contact = contact.getText().toString().trim();
         final String City = city.getText().toString().trim();
         final String State = state.getText().toString().trim();
         final String Diseases = diseases.getText().toString().trim();
         RadioButton radioButton  = (RadioButton)findViewById(radioSexGroup.getCheckedRadioButtonId());
         final String Gender = radioButton.getText().toString().trim();
         final String BloodGroup = spinner.getSelectedItem().toString().trim();
         final String Age = age.getText().toString().trim();


        if (TextUtils.isEmpty(Name) || TextUtils.isEmpty(Email)||TextUtils.isEmpty(Contact) || TextUtils.isEmpty(City)||TextUtils.isEmpty(State) || TextUtils.isEmpty(Diseases) ||TextUtils.isEmpty(Age)) {


            if (name.getText().toString().trim().isEmpty()) {
                input_name.setError("Field cannot be empty");
                if (name.requestFocus()) {
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }
            }
                else
                {
                    input_name.setErrorEnabled(false);
                }



            if (contact.getText().toString().length()<10) {
                input_contact.setError("Enter a valid Contact Number");
                if(contact.requestFocus()) {
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }    }
                else
                {
                    input_contact.setErrorEnabled(false);
                }



            if (city.getText().toString().trim().isEmpty()) {
                input_city.setError("Field cannot be empty");
                if(city.requestFocus())
                {
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }}
                else
                {
                    input_city.setErrorEnabled(false);
                }


            if (state.getText().toString().trim().isEmpty()) {
                input_state.setError("Field cannot be empty");
                if(state.requestFocus()) {
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }    }
                else
                {
                    input_state.setErrorEnabled(false);
                }



            if (diseases.getText().toString().trim().isEmpty()) {
                input_diseases.setError("Field cannot be empty");
                if(diseases.requestFocus()) {
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }    }
                else
                {
                    input_diseases.setErrorEnabled(false);
                }
            String k= age.getText().toString();
            int j=Integer.parseInt(k);



            if (age.getText().toString().isEmpty()) {
                input_age.setError("Enter valid Age");
                if(age.requestFocus()) {
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                }    }
                else
                {
                    input_age.setErrorEnabled(false);
                }

            Toast.makeText(registerDonor.this, "Enter Valid Details", Toast.LENGTH_SHORT).show();

        }

        else {
            sharedPreference = getSharedPreferences("UserRegistered", MODE_PRIVATE);
            editor = sharedPreference.edit();

            String c = sharedPreference.getString("UserRegistered", "");
            if(TextUtils.isEmpty(c))
            {

                DatabaseReference dbr = mDatabase.child("Users").child(MainActivity.user.getUid());
                dbr.child("Name").setValue(Name);
                dbr.child("Email").setValue(Email);
                dbr.child("Gender").setValue(Gender);
                dbr.child("Contact").setValue(Contact);
                dbr.child("City").setValue(City);
                dbr.child("State").setValue(State);
                dbr.child("Age").setValue(Age);
                dbr.child("Diseases").setValue(Diseases);
                dbr.child("BloodGroup").setValue(BloodGroup);
                dbr.child("UId").setValue(MainActivity.user.getUid());
                Toast.makeText(registerDonor.this,"Registered Succesfully",Toast.LENGTH_SHORT).show();
                DatabaseReference db = mDatabase.child("User_Messages").child(MainActivity.user.getUid());
                db.child("Dummy").setValue(Name);
                editor.putString("Name",Name);
                editor.putString("Email",Email);
                editor.putString("Gender",Gender);
                editor.putString("Contact",Contact);
                editor.putString("City",City);
                editor.putString("Age",Age);
                editor.putString("State",State);
                editor.putString("Diseases",Diseases);
                editor.putString("BloodG",BloodGroup);
                editor.putString("UserRegistered",MainActivity.user.getUid());
                editor.commit();
                startActivity(new Intent(registerDonor.this,Main2Activity.class));

            }
            else
            {
                Toast.makeText(registerDonor.this,"Donor already registered with this Email Id. Register with a different Email Id.",Toast.LENGTH_SHORT).show();

            }

}
    }
    private void showProgress()
    {
        progressDialog.setMessage("Uploading");
        progressDialog.show();
    }
    private void dismissProgress()
    {
        progressDialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(registerDonor.this,Main2Activity.class));
    }

}