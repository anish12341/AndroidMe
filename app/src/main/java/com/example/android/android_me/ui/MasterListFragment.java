package com.example.android.android_me.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.android.android_me.R;
import com.example.android.android_me.data.AndroidImageAssets;

/**
 * Created by anish on 12/13/2017.
 */

public class MasterListFragment extends Fragment {


    //Define a new interface OnImageClickListener that triggers a callback in taht host activity
    //the callback is method named "onImageSelected(int position)" that contains information about
    //which position on the grid of images a user a has clicked
    onImageClickListener mCallback;

    public interface onImageClickListener{
        void onImageSelected(int position);
    }


    //Override onAttach to make sure that the container activity has implemented the callback

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            mCallback = (onImageClickListener) context;
        }catch(Exception e){
           throw new ClassCastException(context.toString()+"must implement OnImageClickListener");
        }
    }

    public MasterListFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_master_list,container,false);

        GridView gridView = (GridView) rootView.findViewById(R.id.images_grid_view);

        MasterListAdapter mAdapter = new MasterListAdapter(getContext(), AndroidImageAssets.getAll());

        gridView.setAdapter(mAdapter);

        //set a click listener on the gridview and trigger the callback onImageSelected when an item is clicked
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCallback.onImageSelected(position);
            }
        });

        return rootView;
    }
}
