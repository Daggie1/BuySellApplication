package com.example.anonymous.tomatoesapp.UI.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.anonymous.tomatoesapp.InferenceEngine.MainActivity;
import com.example.anonymous.tomatoesapp.R;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;

public class DiagnosisActivity extends AppCompatActivity {


    ArrayList<String> selectedSymptomps = new ArrayList<>();
    ArrayList<String> symptompschosen = new ArrayList<>();

    RecyclerView mRecyclerView;

    MyAdapter myAdapter = new MyAdapter(selectedSymptomps);

private Button processdiagnoBtn;

    @Override
    public void onResume() {
        super.onResume();

        updateRecyclerView();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis);
        processdiagnoBtn=(Button) findViewById(R.id.processdiagnobtn);
        mRecyclerView = (RecyclerView) findViewById(R.id.diagnosisRecycler);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(DiagnosisActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setHasFixedSize(true);

        processdiagnoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
if(selectedSymptomps.size()>0){
    Intent symptompsIntent=new Intent(DiagnosisActivity.this, MainActivity.class);
    symptompsIntent.putExtra("selectedSymptopmps",symptompschosen);
    startActivity(symptompsIntent);
}


            }
        });
        if (selectedSymptomps.size() <= 0) {


            mRecyclerView.setAdapter(myAdapter);}

        mRecyclerView.setLayoutManager(layoutManager);
    }


    public void updateRecyclerView() {
String [] sysmptomps={"Misshapen, curling, stunted, or yellowing leaves"
        ,"Flowers or fruit become distorted or deformed"
        ,"Galls  form on roots or leaves"
        ,"cut off the plant from underneath the soil"
        , "the bottom of the plant is destroyed"
        ,"Shotholes in leaves, especially on young seedlings"
        , "A lacy appearance"
        ,"The leaves are mottled with yellow"
        , "the leaves have a blister-like appearance"
        ,"Plants are often stunted, or they grow poorly"
        ,"blossom-end rot occurs to the fruit "
        ,"the rotting of stem and root tissues at and below "
                        };
selectedSymptomps.clear();
for(int i=0;i<=sysmptomps.length-1;i++){
    selectedSymptomps.add(sysmptomps[i]);
}
myAdapter.notifyDataSetChanged();
    }

    public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        private ArrayList<String> list;

        public MyAdapter(ArrayList<String> Data) {
            list = Data;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(DiagnosisActivity.this);
            // create a new view

            return new MyViewHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
           String symptomp = selectedSymptomps.get(position);
            holder.bind(symptomp);


        }

        @Override
        public int getItemCount() {
            return list.size();
        }


    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView symptomptxt;

        ImageView check;
        String symptompsModel;

        public MyViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.sysmptops_item, parent, false));

            check=(ImageView) itemView.findViewById(R.id.addsymptompsimageview);
            symptomptxt = (TextView) itemView.findViewById(R.id.symptompname_name);
            itemView.setOnClickListener(this);

        }

        public void bind(String symptomp) {
            symptompsModel = symptomp;

            symptomptxt.setText(symptompsModel);


        }

        @Override
        public void onClick(View view) {
            if(check.getVisibility()==view.GONE){
                check.setVisibility(View.VISIBLE);
                symptompschosen.add(symptompsModel);


                return;
            }
            else{
                check.setVisibility(View.GONE);
                symptompschosen.remove(symptompsModel);
                check.setVisibility(View.VISIBLE);

            }

        }
    }
}
