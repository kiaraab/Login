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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.CDATASection;

import java.security.Key;
import java.util.ArrayList;
import java.util.Objects;

public class counterPage extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private Button submit;
    private Button decrease;
    private Button increase;
    private TextView occupancyDescriptor;
    private EditText occupancyLevels;
    private EditText BusinessName;
    private EditText userEmail;
    private EditText notes;
    private CheckBox yes ;
    private  CheckBox no ;
    Boolean flag;
    String uid;
    private Button logoutbtn;
    private Integer counter = 0;
    String email,Notes,openClose,business_name;
    String occupancy_Level;
    DatabaseReference databaseRef ;
    ArrayList<OccupancyData> list;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter_page);

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
        logoutbtn = (Button)findViewById(R.id.logoutBtn);

        submit = (Button)findViewById(R.id.btnLogout);

        //list = new ArrayList<OccupancyData>();


         /** possible issue here  **/
        // retrieving from the database to get last saved state
        uid = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getUid();
        databaseRef = FirebaseDatabase.getInstance().getReference().child("Login").child(uid);
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue()!=null){
                    email = snapshot.child("email").getValue().toString();
                    userEmail.setText(email);
                    occupancy_Level = snapshot.child("occupancy level").getValue().toString();
                    occupancyLevels.setText(occupancy_Level);
                    counter = Integer.parseInt(occupancy_Level);
                    Notes = snapshot.child("notes").getValue().toString();
                    notes.setText(Notes);
                    openClose = snapshot.child("open Close").getValue().toString();
                    if(openClose.equals("open")){
                        yes.setChecked(true);
                    }
                    else if (openClose.equals("close")){
                        no.setChecked(true);
                    }
                    business_name = snapshot.child("name").getValue().toString();
                    BusinessName.setText(business_name);

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "The read failed", Toast.LENGTH_SHORT).show();
                System.out.println("The read failed"+ error.getCode());
            }
        });


         //setting onclick listeners
        BusinessName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                business_name = BusinessName.getText().toString();
                BusinessName.setText(business_name);
            }
        });
        userEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userEmail.setText(userEmail.getText().toString());
                email = userEmail.getText().toString();
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


        occupancyLevels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter = Integer.parseInt(occupancyLevels.getText().toString());

                //occupancyLevels.setText(Integer.toString(counter));

            }
        });
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
        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                //finish();
                startActivity(new Intent(counterPage.this,MainActivity.class));
            }
        });
        /** substitute submit button  **/
//        // submit button
//        submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                business_name = BusinessName.getText().toString();
//                email = userEmail.getText().toString();
//                Notes = notes.getText().toString();
//                occupancy_Level = Integer.toString(counter);
//                if(yes.isChecked() && no.isChecked() == false){
//                    yes.setChecked(true);
//                    openClose="open";
//                }
//                else if(no.isChecked() && yes.isChecked()==false){
//                    no.setChecked(true);
//                    openClose="close";
//                }
//                databaseRef = FirebaseDatabase.getInstance().getReference().child("Login").child(firebaseAuth.getCurrentUser().getUid());
//                databaseRef.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        snapshot.getRef().child("name").setValue(business_name);
//                        snapshot.getRef().child("email").setValue(email);
//                        snapshot.getRef().child("notes").setValue(Notes);
//                        snapshot.getRef().child("open Close").setValue(openClose);
//                        snapshot.getRef().child("occupancy level").setValue(occupancy_Level);
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        Toast.makeText(counterPage.this," data Changed but issue while writing", Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//            }
//        });
        /** possible issue here  **/
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // authenticating and then writing to the database
                FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
                    @Override
                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                        business_name = BusinessName.getText().toString();
                        email = userEmail.getText().toString();
                        Notes = notes.getText().toString();
                        occupancy_Level = Integer.toString(counter);
                        if(yes.isChecked()){
                            yes.setChecked(true);
                            openClose="open";
                        }
                        else{
                            no.setChecked(true);
                            openClose="close";
                        }
                        databaseRef = FirebaseDatabase.getInstance().getReference().child("Login").child(firebaseAuth.getCurrentUser().getUid());
                        databaseRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                snapshot.getRef().child("name").setValue(business_name);
                                snapshot.getRef().child("email").setValue(email);
                                snapshot.getRef().child("notes").setValue(Notes);
                                snapshot.getRef().child("open Close").setValue(openClose);
                                snapshot.getRef().child("occupancy level").setValue(occupancy_Level);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(counterPage.this," data Changed but issue while writing", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });

            }
        });



    }

//    private void Logout(){
//        firebaseAuth.signOut();
//        //finish();
//        startActivity(new Intent(counterPage.this,MainActivity.class));
//    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu,menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.logoutMenu:{
//                Logout();
//                break;
//            }
//        }
//        return super.onOptionsItemSelected(item);
//    }
}