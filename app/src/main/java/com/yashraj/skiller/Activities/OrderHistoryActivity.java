package com.yashraj.skiller.Activities;

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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.yashraj.skiller.R;
import com.yashraj.skiller.model.OrderHistoryModel;

public class OrderHistoryActivity extends AppCompatActivity {
    RecyclerView orderRecyclerView;
    FirebaseRecyclerAdapter<OrderHistoryModel, OrderDetailsViewHolder> firebaseRecyclerAdapter;
    private FirebaseAuth mAuth;
    private DatabaseReference completedDatabasereference;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        completedDatabasereference = FirebaseDatabase.getInstance().getReference().child("CompletedTask");
        orderRecyclerView = findViewById(R.id.oderHistoryRecyclerView);
        orderRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        orderRecyclerView.setHasFixedSize(true);

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<OrderHistoryModel> options = new FirebaseRecyclerOptions.Builder<OrderHistoryModel>().setQuery(completedDatabasereference, OrderHistoryModel.class)
                .build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<OrderHistoryModel, OrderDetailsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull OrderDetailsViewHolder holder, int i, @NonNull OrderHistoryModel orderHistoryModel) {
                holder.startingDate.setText(orderHistoryModel.getStartingDate());
                holder.endingDate.setText(orderHistoryModel.getEndingDate());
                holder.location.setText(orderHistoryModel.getLocation());
                holder.description.setText(orderHistoryModel.getDescription());
                holder.vendorName.setText(orderHistoryModel.getVendorName());
                holder.charges.setText(orderHistoryModel.getCharges());

            }

            @NonNull
            @Override
            public OrderDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_history_layout, parent, false);
                return new OrderDetailsViewHolder(view);
            }
        };
        firebaseRecyclerAdapter.startListening();
        orderRecyclerView.setAdapter(firebaseRecyclerAdapter);


    }

    private static class OrderDetailsViewHolder extends RecyclerView.ViewHolder {
        TextView vendorName, location, description, charges, endingDate, startingDate;

        public OrderDetailsViewHolder(@NonNull View itemView) {
            super(itemView);

            vendorName = itemView.findViewById(R.id.order_vendor_name);
            location = itemView.findViewById(R.id.order_location);
            charges = itemView.findViewById(R.id.order_charges);
            description = itemView.findViewById(R.id.order_description);
            endingDate = itemView.findViewById(R.id.order_endingDate);
            startingDate = itemView.findViewById(R.id.order_startingDate);

        }
    }
}