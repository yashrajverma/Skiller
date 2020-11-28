package com.yashraj.skiller.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.yashraj.skiller.R;
import com.yashraj.skiller.model.ServiceVendor;

public class ServiceProviderActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    private FirebaseRecyclerAdapter<ServiceVendor,ServiceProvider> firebaseRecyclerAdapter;
    private String occupation = "";
    private TextView no_service_provider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider);
        recyclerView=findViewById(R.id.serviceprovider_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Vendors");
        occupation = getIntent().getExtras().get("servicetype").toString();
        no_service_provider = findViewById(R.id.no_service_text);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<ServiceVendor> options=null;
        if (occupation.equals("")) {
            options = new FirebaseRecyclerOptions.Builder<ServiceVendor>().setQuery(databaseReference, ServiceVendor.class).build();
        }else{
            options=new FirebaseRecyclerOptions.Builder<ServiceVendor>().setQuery(databaseReference.orderByChild("occupation").equalTo(occupation), ServiceVendor.class).build();
        }
        firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<ServiceVendor, ServiceProvider>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final ServiceProvider serviceProvider, final int i, @NonNull final ServiceVendor serviceVendor) {
                serviceProvider.servicetype.setText(serviceVendor.getOccupation());
                serviceProvider.service_provider_name.setText("Provider : " + serviceVendor.getName());
                serviceProvider.service_provider_mobile.setText("Contact : " + serviceVendor.getPhoneNo());
                no_service_provider.setVisibility(View.GONE);

                serviceProvider.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String visitorUid = getRef(i).getKey();
                        Intent intent = new Intent(ServiceProviderActivity.this, BookService.class);
                        intent.putExtra("servicetype", serviceProvider.servicetype.getText().toString());
                        intent.putExtra("serviceprovider_name", serviceProvider.service_provider_name.getText().toString());
                        intent.putExtra("serviceprovider_mobile", serviceProvider.service_provider_mobile.getText().toString());
                        intent.putExtra("charges", serviceVendor.getCharges());
                        intent.putExtra("visitorUid", visitorUid);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);

                    }
                });
            }

            @NonNull
            @Override
            public ServiceProvider onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(getApplicationContext()).inflate(R.layout.serviceprovider_card,parent,false);
                return new ServiceProvider(view);
            }
        };
        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    private static class ServiceProvider extends RecyclerView.ViewHolder{
        TextView servicetype,service_provider_name,service_provider_mobile;
        public ServiceProvider(@NonNull View itemView) {
            super(itemView);
            service_provider_name=itemView.findViewById(R.id.serviceprovide_name);
            service_provider_mobile=itemView.findViewById(R.id.serviceprovide_mobile);
            servicetype=itemView.findViewById(R.id.servicetype);
        }
    }

}