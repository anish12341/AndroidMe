package com.example.android.android_me.ui;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.android.android_me.R;
import com.example.android.android_me.data.AndroidImageAssets;

public class MainActivity extends AppCompatActivity implements MasterListFragment.onImageClickListener {


    //Variables to store the values for the list index of the selected images
    //default value will be index=0
    private int headIndex;
    private int bodyIndex;
    private int legIndex;

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //determine if you are creating a two-pane(tablet) or single-pane(phone) layout
        if(findViewById(R.id.android_me_linear_layout) != null){
           mTwoPane = true;

            if(savedInstanceState == null) {
                //use a FragmentManager and transaction to add the fragment to the screen
                FragmentManager fragmentManager = getSupportFragmentManager();

                //Create a new BodyPartFragment instance and display it using FragmentManager
                BodyPartFragment headFragment = new BodyPartFragment();

                //set the list of image ids for the head fragment and set the position to the second image in the list
                headFragment.setImageIds(AndroidImageAssets.getHeads());
                //Fragment transaction
                fragmentManager.beginTransaction()
                        .add(R.id.head_container, headFragment)
                        .commit();

                BodyPartFragment bodyFragment = new BodyPartFragment();
                bodyFragment.setImageIds(AndroidImageAssets.getBodies());
                fragmentManager.beginTransaction()
                        .add(R.id.body_container, bodyFragment)
                        .commit();

                BodyPartFragment legFragment = new BodyPartFragment();

                legFragment.setImageIds(AndroidImageAssets.getLegs());
                fragmentManager.beginTransaction()
                        .add(R.id.leg_container, legFragment)
                        .commit();
            }
        }
        else{
            mTwoPane = false;
        }
    }


    //Define the behavior for onImageSelected
    @Override
    public void onImageSelected(int position) {

        Toast.makeText(this, "Position clicked = " + position, Toast.LENGTH_LONG).show();

        int bodyPartnumber = position / 12;

        int listIndex = position - 12 * bodyPartnumber;

        if (mTwoPane) {
            //create two pane interaction

            BodyPartFragment newFragment = new BodyPartFragment();

            //set the currently displayed item for the correct body part fragment

            switch (bodyPartnumber) {
                case 0:
                    // A head image has been clicked
                    // Give the correct image resources to the new fragment
                    newFragment.setImageIds(AndroidImageAssets.getHeads());
                    newFragment.setListIndex(listIndex);
                    // Replace the old head fragment with a new one
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.head_container, newFragment)
                            .commit();
                    break;
                case 1:
                    newFragment.setImageIds(AndroidImageAssets.getBodies());
                    newFragment.setListIndex(listIndex);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.body_container, newFragment)
                            .commit();
                    break;
                case 2:
                    newFragment.setImageIds(AndroidImageAssets.getLegs());
                    newFragment.setListIndex(listIndex);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.leg_container, newFragment)
                            .commit();
                    break;
                default:
                    break;
            }
        } else {

            switch (bodyPartnumber) {
                case 0:
                    headIndex = listIndex;
                    break;

                case 1:
                    bodyIndex = listIndex;
                    break;

                case 2:
                    legIndex = listIndex;
                    break;

                default:
                    break;
            }

            //put this information into a bundle object that is attached with new intent which launches AndroidMeActivity
            Bundle b = new Bundle();
            b.putInt("headIndex", headIndex);
            b.putInt("bodyIndex", bodyIndex);
            b.putInt("legIndex", legIndex);

            final Intent intent = new Intent(this, AndroidMeActivity.class);
            intent.putExtras(b);

            Button nextButton = (Button) findViewById(R.id.next_button);
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(intent);
                }
            });
        }
    }

}
