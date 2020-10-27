package com.yashraj.skiller.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.yashraj.skiller.Activities.ServiceProviderActivity;
import com.yashraj.skiller.R;
import com.yashraj.skiller.model.Service_class;

public class CategoryFragment extends Fragment {
    RecyclerView categoryRecyclerView;
    DatabaseReference databaseReference;
    StorageReference storageReference;
    FirebaseRecyclerAdapter<Service_class,ViewHolder> firebaseRecyclerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_category, container, false);
        categoryRecyclerView = view.findViewById(R.id.category_recyclerView);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        databaseReference=FirebaseDatabase.getInstance().getReference().child("Category");
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Service_class> options=new FirebaseRecyclerOptions.Builder<Service_class>().setQuery(databaseReference,Service_class.class).build();
        firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Service_class, ViewHolder>(options) {
            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view=LayoutInflater.from(getContext()).inflate(R.layout.service_card,parent,false);
                return new ViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i, @NonNull Service_class service_class) {
                viewHolder.service_name.setText(service_class.getService());
                Picasso.get().load(service_class.getImage()).into(viewHolder.imageView);

                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(getContext(), ServiceProviderActivity.class);
                        intent.putExtra("servicetype",viewHolder.service_name.getText().toString());
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                });

            }

        };
        categoryRecyclerView.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();


    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView service_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.service_image);
            service_name=itemView.findViewById(R.id.service_name);
        }
    }
}