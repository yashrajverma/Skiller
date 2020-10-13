package com.yashraj.skiller.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.yashraj.skiller.R;

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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context=getActivity();
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        imageView=context.findViewById(R.id.image);
        list=new ArrayList<>();
        imageCarousel=context.findViewById(R.id.carousel);
        list.add(new CarouselItem(R.drawable.electrician,"Electrician"));
        list.add(new CarouselItem(R.drawable.plumber,"Plumber"));
        list.add(new CarouselItem(R.drawable.ac,"AC Repair"));
        list.add(new CarouselItem(R.drawable.mechanic,"Car Repair"));
        imageCarousel.addData(list);
        imageCarousel.setCaptionTextSize(Utils.spToPx(20,context));
        imageCarousel.setAutoPlay(true);
        imageCarousel.setAutoPlayDelay(3000);

    }
}