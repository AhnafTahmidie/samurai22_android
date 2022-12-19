package com.example.promap.view;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.promap.R;
import com.example.promap.databinding.FragmentMapBinding;
import com.example.promap.model.Projects;
import com.example.promap.viewmodel.MapViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    private FragmentMapBinding binding;
    private FragmentActivity activity;
    private View view;
    private GoogleMap map;
    private MapViewModel viewModel;
    SupportMapFragment mapFragment;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public MapFragment() {
    }

    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = requireActivity();
        activity.setTitle("Map View");
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_map, container,false);
        view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(this).get(MapViewModel.class);
        try {
            viewModel.getData(requireContext());
        } catch (IOException e) {
            e.printStackTrace();
        }
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
        observeViewModel();
    }
    private void observeViewModel(){
        viewModel.projectsLiveData.observe(getViewLifecycleOwner(),projects -> {
            System.out.println("HRE hree awerfasdcacavdsvav");
            mapFragment.getMapAsync(googleMap -> {
                map = googleMap;
                for(Projects p:projects){
                    for(LatLng lt:p.getLocation_coordinates()){
                        map.addMarker(new MarkerOptions()
                                .title(p.getProject_name())
                                .position(lt));
                    }
                }
                map.setOnMarkerClickListener(marker -> {
                    Toast.makeText(activity,marker.getTitle(), Toast.LENGTH_SHORT).show();
                    return false;
                });
            });
        });
    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;
        LatLng dhaka = new LatLng(23.8103,90.4125);
        map.addMarker(new MarkerOptions().position(dhaka).title("Dhaka"));
        map.moveCamera(CameraUpdateFactory.newLatLng(dhaka));

        map.setOnMarkerClickListener(marker -> {
            Toast.makeText(activity,marker.getTitle(), Toast.LENGTH_SHORT).show();
            return false;
        });
    }

}