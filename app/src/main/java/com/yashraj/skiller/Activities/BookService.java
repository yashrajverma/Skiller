package com.yashraj.skiller.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.yashraj.skiller.R;

import java.util.HashMap;

public class BookService extends AppCompatActivity {
    TextView book_servicename, book_serviceType, book_service_mobile, book_hourly_rate;
    String str_book_servicename, str_book_serviceType, str_book_service_mobile, str_book_imageurl = "", str_duration
            ,senderUid, visitorUid, str_address, str_city, str_desc;
    Button book_service_button;
    DatabaseReference databaseReference, imagereference, book_service_reference, user_database;
    ImageView book_service_image;
    FirebaseAuth mAuth;
    private String currentstate;
    TextInputEditText book_username, book_address, book_mobile, book_city, book_desc, book_duration;
    Button book_submit_button;
    RelativeLayout book_vendor_details_relativeLayout, book_vendor_address_relativeLayout;
    private final String TAG = BookService.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_service);
        mAuth = FirebaseAuth.getInstance();
        book_servicename = findViewById(R.id.book_service_vendor_name);
        book_hourly_rate = findViewById(R.id.book_service_rate);
        book_service_mobile = findViewById(R.id.book_service_mobile);
        book_serviceType = findViewById(R.id.book_Service_Type);
        book_service_button = findViewById(R.id.book_service_button);
        book_service_image = findViewById(R.id.book_service_image);
        book_mobile = findViewById(R.id.req_mobile);
        book_username = findViewById(R.id.req_username);
        book_address = findViewById(R.id.req_address);
        book_city = findViewById(R.id.req_city);
        book_desc = findViewById(R.id.req_desc);
        book_duration = findViewById(R.id.req_duration);

        book_submit_button = findViewById(R.id.book_submit_button);
        book_vendor_details_relativeLayout = findViewById(R.id.book_vendor_details_relative_layout);
        book_vendor_address_relativeLayout = findViewById(R.id.book_vendor_address_relative_layout);

        senderUid = mAuth.getCurrentUser().getUid();


        ////////////////////////////////////////////////////////////////  Databases  /////////////////////////////////////////////////////////

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Vendors");
        imagereference = FirebaseDatabase.getInstance().getReference().child("Category");
        book_service_reference = FirebaseDatabase.getInstance().getReference().child("NewTask");
        user_database = FirebaseDatabase.getInstance().getReference().child("User_database");

        //////////////////////////////////////////////////////////////// Intent Calls /////////////////////////////////////////////////////////

        str_book_service_mobile = getIntent().getExtras().get("serviceprovider_mobile").toString();
        str_book_servicename = getIntent().getExtras().get("serviceprovider_name").toString();
        str_book_serviceType = getIntent().getExtras().get("servicetype").toString();
        visitorUid = getIntent().getExtras().get("visitorUid").toString();

        book_service_mobile.setText(str_book_service_mobile);
        book_serviceType.setText(str_book_serviceType);
        book_servicename.setText(str_book_servicename);
        UserData();
        imagereference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    str_book_imageurl = snapshot.child(str_book_serviceType).child("image").getValue().toString();
                    Picasso.get().load(str_book_imageurl).into(book_service_image);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        book_service_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                book_vendor_details_relativeLayout.setVisibility(View.GONE);
                book_vendor_address_relativeLayout.setVisibility(View.VISIBLE);
            }
        });
        book_submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(book_city.getText().toString()) && TextUtils.isEmpty(book_address.getText().toString()) && TextUtils.isEmpty(book_desc.getText().toString())
                        && TextUtils.isEmpty(book_duration.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Provide Required Details", Toast.LENGTH_LONG).show();
                } else {

                    BOOKASERVICE();
                }
            }
        });
    }

    private void UserData() {
        user_database.child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    book_username.setText(snapshot.child("user_name").getValue().toString());
                    book_mobile.setText(snapshot.child("user_mobile").getValue().toString());
                    if (snapshot.hasChild("user_address") && snapshot.hasChild("user_city") && snapshot.hasChild("user_desc") && snapshot.hasChild("user_duration")) {
                        str_address = snapshot.child("user_address").getValue().toString();
                        str_desc = (snapshot.child("user_desc").getValue().toString());
                        str_city = snapshot.child("user_city").getValue().toString();
                        str_duration=snapshot.child("user_duration").getValue().toString();

                        book_duration.setText(str_duration);
                        book_city.setText(str_city);
                        book_desc.setText(str_desc);
                        book_address.setText(str_address);

                    }

                }
                 else {
                    Toast.makeText(getApplicationContext(), "Fill out the fields", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void BOOKASERVICE() {
        str_address=book_address.getText().toString();
        str_desc =book_desc.getText().toString();
        str_city=book_city.getText().toString();
        str_duration=book_duration.getText().toString();

        final HashMap<String, Object> map = new HashMap<>();
        map.put("user_address",str_address);
        map.put("user_city", str_city);
        map.put("user_name",str_book_servicename);
        map.put("user_mobile",str_book_service_mobile);
        map.put("user_desc", str_desc);
        map.put("user_duration", str_duration);
        map.put("userid", senderUid);

        user_database.child(mAuth.getCurrentUser().getUid()).updateChildren(map);

        book_service_reference.child(senderUid)
                .child(visitorUid)
                .child("booked")
                .setValue("booked")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            book_service_reference
                                    .child(visitorUid)
                                    .child(senderUid).child("booked").setValue("booked").addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        currentstate = "booked";
                                        book_service_button.setEnabled(false);
                                        Toast.makeText(BookService.this, "Vendor Booked", Toast.LENGTH_SHORT).show();

                                        book_service_reference.child(visitorUid).child("userId").child("details").updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Intent intent = new Intent(BookService.this, DashboardActivity.class);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            }
                                        });
                                    }
                                }
                            });
                        }
                    }
                });
    }

}