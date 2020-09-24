package com.example.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.security.Key;

public class counterPage extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private Button logout;
    private Button decrease;
    private Button increase;
    private TextView occupancyDescriptor;
    private EditText occupancyLevels;
    private EditText BusinessName;
    private EditText userEmail;
    private EditText notes;
    private CheckBox yes ;
    private  CheckBox no ;
    private Integer counter = 0;
    String email,Notes,openClose,business_name;
    String occupancy_Level;
    public static final String KeyBusiness = "KeyBusinessName";
    public static final String PrefName = "share";
    SharedPreferences pref;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter_page);

        //editor.putString(KeyBusiness,"");


        

        firebaseAuth = FirebaseAuth.getInstance();
        decrease = (Button)findViewById(R.id.DecreaseBtn);
        increase = (Button)findViewById(R.id.IncreaseBtn);
        occupancyDescriptor = (TextView)findViewById(R.id.OccupancyDescriptor);
        occupancyLevels = (EditText)findViewById(R.id.Occupancy);
        BusinessName = (EditText)findViewById(R.id.businessName);
        userEmail = (EditText)findViewById(R.id.EmailId);
        notes = (EditText)findViewById(R.id.notes);
        yes = (CheckBox)findViewById(R.id.Yes);
        no = (CheckBox)findViewById(R.id.No);

        logout = (Button)findViewById(R.id.btnLogout);
        pref = counterPage.this.getSharedPreferences(PrefName, Context.MODE_PRIVATE);


        // setting onclick listeners
//        BusinessName.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                business_name = BusinessName.getText().toString();
//                SharedPreferences.Editor editor = pref.edit();
//                editor.putString(KeyBusiness, business_name);
//                editor.commit();
//                Toast.makeText(counterPage.this,"done",Toast.LENGTH_SHORT).show();
////                business_name = pref.getString(KeyBusiness,"");
////                BusinessName.setText(business_name);
////                //BusinessName.setText(BusinessName.getText().toString());
////                editor.putString(KeyBusiness,BusinessName.getText().toString());
////                editor.commit();
////
////                // setting it to the new one
////                business_name = pref.getString(KeyBusiness,"");
////                BusinessName.setText(business_name);
//            }
//        });
        userEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userEmail.setText(userEmail.getText().toString());
                email = userEmail.getText().toString();
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                business_name = BusinessName.getText().toString();
                SharedPreferences.Editor editor = pref.edit();
                editor.putString(KeyBusiness, business_name);
                editor.commit();
                Toast.makeText(counterPage.this,"done",Toast.LENGTH_SHORT).show();
                //sendData();
            }
        });
        // setting the note
        notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notes.setText(notes.getText().toString());
                Notes = notes.getText().toString();
            }
        });
        // setting checkboxes for whether open or closed
        yes.setChecked(false);
        no.setChecked(false);
        if(yes.isChecked()){
            yes.setChecked(true);
            openClose="open";
        }

        if(no.isChecked()){
            no.setChecked(true);
            openClose="close";
        }
        // increase and decrease
        decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter--;
                occupancyLevels.setText(Integer.toString(counter));
            }
        });
        increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter++;
                occupancyLevels.setText(Integer.toString(counter));
            }
        });
        occupancyLevels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter = Integer.parseInt(occupancyLevels.getText().toString());
                occupancyLevels.setText(Integer.toString(counter));

            }
        });
        occupancy_Level = counter.toString();

    }

    private void Logout(){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(counterPage.this,MainActivity.class));
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logoutMenu:{
                Logout();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void sendData(){

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        // getting database reference
        DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());
        OccupancyData occupancyData = new OccupancyData(business_name,email,Notes,openClose,occupancy_Level);
        databaseReference.setValue(occupancyData);
    }
}