package com.yashraj.skiller.Fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import com.squareup.picasso.Picasso;
import com.yashraj.skiller.Activities.ServiceProviderActivity;
import com.yashraj.skiller.R;
import com.yashraj.skiller.model.Service_class;

import org.imaginativeworld.whynotimagecarousel.CarouselItem;
import org.imaginativeworld.whynotimagecarousel.ImageCarousel;
import org.imaginativeworld.whynotimagecarousel.Utils;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    Activity context;
    List<CarouselItem> list;
    ImageCarousel imageCarousel;
    ImageView imageView;
    DatabaseReference databaseReference;
    FirebaseRecyclerAdapter<Service_class, CategoryFragment.ViewHolder> firebaseRecyclerAdapter;
    RecyclerView featuredRecyclerView;

    private final ArrayList<String> imageUrls = new ArrayList<>();

    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        featuredRecyclerView = view.findViewById(R.id.recyclerView_home);
        featuredRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext(),LinearLayoutManager.HORIZONTAL,true));
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        imageView = context.findViewById(R.id.image);
        list = new ArrayList<>();
        imageCarousel = context.findViewById(R.id.carousel);
        list.add(new CarouselItem(R.drawable.electrician, "Electrician"));
        list.add(new CarouselItem(R.drawable.plumber, "Plumber"));
        list.add(new CarouselItem(R.drawable.ac, "AC Repair"));
        list.add(new CarouselItem(R.drawable.mechanic, "Car Repair"));
        imageCarousel.addData(list);
        imageCarousel.setCaptionTextSize(Utils.spToPx(20, context));
        imageCarousel.setAutoPlay(true);
        imageCarousel.setAutoPlayDelay(3000);


        databaseReference=FirebaseDatabase.getInstance().getReference().child("Category");
        FirebaseRecyclerOptions<Service_class> options=new FirebaseRecyclerOptions.Builder<Service_class>().setQuery(databaseReference,Service_class.class).build();
        firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<Service_class, CategoryFragment.ViewHolder>(options) {
            @NonNull
            @Override
            public CategoryFragment.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view=LayoutInflater.from(getContext()).inflate(R.layout.service_home_card,parent,false);
                return new CategoryFragment.ViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull final CategoryFragment.ViewHolder viewHolder, int i, @NonNull Service_class service_class) {
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
        featuredRecyclerView.setAdapter(firebaseRecyclerAdapter);
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
