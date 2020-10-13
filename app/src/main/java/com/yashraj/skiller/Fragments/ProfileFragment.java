package com.yashraj.skiller.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yashraj.skiller.R;
import com.yashraj.skiller.RegistrationActivity;

import java.util.HashMap;

public class ProfileFragment extends Fragment {
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference databaseReference;
    EditText usernameET,mobileET;
    LinearLayout shareapp;
    Button logout_button,save_button;
    Activity context;
    String str_mobile;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context=getActivity();
        return inflater.inflate(R.layout.fragment_profile, container, false);

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("User_database");
        logout_button=(Button) context.findViewById(R.id.logout_Button);
        save_button=context.findViewById(R.id.save_Button);
        usernameET=context.findViewById(R.id.username_edittext);
        mobileET=context.findViewById(R.id.username_mobile);
        str_mobile= mUser.getPhoneNumber();
        mobileET.setText(str_mobile);
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String,Object> mapdata=new HashMap<>();
                mapdata.put("user_name",usernameET.getText().toString());
                mapdata.put("user_mobile",mobileET.getText().toString());
                mapdata.put("user_id", mUser.getUid());
                databaseReference.child(mUser.getUid()).updateChildren(mapdata).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(context, "Updated Account", Toast.LENGTH_SHORT).show();
                        }else{
                            String error=task.getException().getMessage();
                            Toast.makeText(context,"Error :"+error,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        databaseReference.child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) {
                    String str_username = snapshot.child("user_name").getValue().toString();
                    str_mobile = snapshot.child("user_mobile").getValue().toString();
                    usernameET.setText(str_username);
                    mobileET.setText(str_mobile);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logoutuser();
            }
        });
    }

    private void logoutuser(){
        mAuth.signOut();
        Intent intent=new Intent(context , RegistrationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        context.finish();
    }
}