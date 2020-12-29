package com.example.arogya.uis;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arogya.R;
import com.example.arogya.manager.ServiceManager.APIService;
import com.example.arogya.manager.ServiceManager.APIUtils;
import com.example.arogya.models.Requests.ListRequest;
import com.example.arogya.models.Response.ListResponse;
import com.kaopiz.kprogresshud.KProgressHUD;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class List extends AppCompatActivity {


    private Button listButton;
    private Button camera, home;
    Spinner plantCategorySpinner, propagationSpinner, leafBaseSpinner, venationSpinner, leafShapeSpinner, leafTypeSpinner,
            phyllotaxySpinner, bloomingPeriodSpinner, flowerColorSpinner, flowerSizeSpinner, havingFruitSpinner, fruitShapeSpinner, partsUsedSpinner, cultivationSpinner, rootSystemSpinner;

    String[] plantCategory1 = {"- Choose - ","Plant", "vine", "shrub"};
    String[] propagation1 = {"- Choose -","rhizome", "seeds", " seeds and tubers", " seeds and cuttings", " root and ahizomes", " root and seeds "};
    String[] leafBase1 = {"- Choose -","wedge-shaped or cuneate", " Rounded", " Heart shaped or cordate "};
    String[] venation1 = {"- Choose -"," Parrallel-veined", " reticulate/net-veined"};
    String[] leafShape1 = {"- Choose -","Acicular","oblong", " Lanceolate", " obovate", " orbicular or peltate'"};
    String[] leafType1 = {"- Choose -"," simple", " narrow", " compound"};
    String[] phyllotaxy1 = {"- Choose -","spiral", "opposite", "whorled", "Alternate"};
    String[] bloomingPeriod1 = {"- Choose -","yes","no"};
    String[] flowerColor1 = {"- Choose -","pale yellow", "yellow green", "Red berrish/white/ pinkis white", "green", "purple", "pinkish white"};
    String[] flowerSize1 = {"- Choose -","small and spikes", "small", "minute and soft spikes", "small and spikes"};
    String[] havingFruits1 = {"- Choose -","Yes", "No"};
    String[] fruitShape1 = {"- Choose -","oval", "round", "club-shaped capsules", "no"};
    String[] partsOfUsed1 = {"- Choose -","whole part", "rhizome and leaves", "flowers", "tuberous root", "bark", "whole parts"};
    String[] cultivation1 = {"- Choose -","wet zone", "any"};
    String[] rootSystem1 = {"- Choose -","fibrous root", "tap root"};

    String plantCategory, propagation, cultivation, rootSystem, havingFruits, fruitShape, bloomingPeriod, flowerSize,
            flowerColor, leafBase, venation, leafShape, leafType, phyllotaxy, partsOfUsed, disease;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);


        listButton = findViewById(R.id.listButton);
        listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ListRequest listRequest = new ListRequest(plantCategory, propagation, cultivation, rootSystem, havingFruits, fruitShape, bloomingPeriod, flowerSize,
                        flowerColor, leafBase, venation, leafShape, leafType, phyllotaxy, partsOfUsed);
 //               ListRequest listRequest = new ListRequest("plant","rhizome","wet zone",
 //               "Fibrous root","No","No","yes","small","yellow green",
 //               "wedge-shaped or cuneate","parrallel-veined","lanceolate","narrow","spiral",
 //               "rhizome and leaves");
                APIService apiService = APIUtils.getApiService_2();
                listPass(listRequest, apiService);

            }
        });


        plantCategorySpinner = findViewById(R.id.plantCategorySpinner);
        ArrayAdapter adapter01 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, plantCategory1);
        plantCategorySpinner.setAdapter(adapter01);
        plantCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                plantCategory = plantCategory1[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                listButton.setVisibility(View.GONE);
            }
        });


        propagationSpinner = findViewById(R.id.propagationSpinner);
        ArrayAdapter adapter02 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, propagation1);
        propagationSpinner.setAdapter(adapter02);
        propagationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                propagation = propagation1[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                listButton.setVisibility(View.GONE);
            }
        });

        leafBaseSpinner = findViewById(R.id.leafBaseSpinner);
        ArrayAdapter adapter03 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, leafBase1);
        leafBaseSpinner.setAdapter(adapter03);
        leafBaseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                leafBase = leafBase1[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                listButton.setVisibility(View.GONE);
            }
        });

        venationSpinner = findViewById(R.id.venationSpinner);
        ArrayAdapter adapter04 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, venation1);
        venationSpinner.setAdapter(adapter04);
        venationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                venation = venation1[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                listButton.setVisibility(View.GONE);
            }
        });


        leafShapeSpinner = findViewById(R.id.leafShapeSpinner);
        ArrayAdapter adapter05 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, leafShape1);
        leafShapeSpinner.setAdapter(adapter05);
        leafShapeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                leafShape = leafShape1[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                listButton.setVisibility(View.GONE);
            }
        });


        leafTypeSpinner = findViewById(R.id.leafTypeSpinner);
        ArrayAdapter adapter06 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, leafType1);
        leafTypeSpinner.setAdapter(adapter06);
        leafTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                leafType = leafType1[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                listButton.setVisibility(View.GONE);
            }
        });


        phyllotaxySpinner = findViewById(R.id.phyllotaxySpinner);
        ArrayAdapter adapter07 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, phyllotaxy1);
        phyllotaxySpinner.setAdapter(adapter07);
        phyllotaxySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                phyllotaxy = phyllotaxy1[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                listButton.setVisibility(View.GONE);
            }
        });


        bloomingPeriodSpinner = findViewById(R.id.bloomingPeriodSpinner);
        ArrayAdapter adapter08 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, bloomingPeriod1);
        bloomingPeriodSpinner.setAdapter(adapter08);
        bloomingPeriodSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                bloomingPeriod = bloomingPeriod1[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                listButton.setVisibility(View.GONE);
            }
        });


        flowerColorSpinner = findViewById(R.id.flowerColorSpinner);
        ArrayAdapter adapter09 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, flowerColor1);
        flowerColorSpinner.setAdapter(adapter09);
        flowerColorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                flowerColor = flowerColor1[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                listButton.setVisibility(View.GONE);
            }
        });


        flowerSizeSpinner = findViewById(R.id.flowerSizeSpinner);
        ArrayAdapter adapter10 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, flowerSize1);
        flowerSizeSpinner.setAdapter(adapter10);
        flowerSizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                flowerSize = flowerSize1[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                listButton.setVisibility(View.GONE);
            }
        });


        havingFruitSpinner = findViewById(R.id.havingFruitSpinner);
        ArrayAdapter adapter11 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, havingFruits1);
        havingFruitSpinner.setAdapter(adapter11);
        havingFruitSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                havingFruits = havingFruits1[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                listButton.setVisibility(View.GONE);
            }
        });


        fruitShapeSpinner = findViewById(R.id.fruitShapeSpinner);
        ArrayAdapter adapter12 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, fruitShape1);
        fruitShapeSpinner.setAdapter(adapter12);
        fruitShapeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                fruitShape = fruitShape1[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                listButton.setVisibility(View.GONE);
            }
        });


        partsUsedSpinner = findViewById(R.id.partsUsedSpinner);
        ArrayAdapter adapter13 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, partsOfUsed1);
        partsUsedSpinner.setAdapter(adapter13);
        partsUsedSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                partsOfUsed = partsOfUsed1[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                listButton.setVisibility(View.GONE);
            }
        });


        cultivationSpinner = findViewById(R.id.cultivationSpinner);
        ArrayAdapter adapter14 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, cultivation1);
        cultivationSpinner.setAdapter(adapter14);
        cultivationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                cultivation = cultivation1[position];


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                listButton.setVisibility(View.GONE);
            }
        });

        rootSystemSpinner = findViewById(R.id.rootSystemSpinner);
        ArrayAdapter adapter15 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, rootSystem1);
        rootSystemSpinner.setAdapter(adapter15);
        rootSystemSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                rootSystem = rootSystem1[position];


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                listButton.setVisibility(View.GONE);
            }
        });


    }


    private void listPass(final ListRequest listRequest, APIService apiService) {

        final KProgressHUD kProgressHUD = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setDimAmount(0.5f)
                .show();

        apiService.listPass(listRequest).enqueue(new Callback<ListResponse>() {
            @Override
            public void onResponse(Call<ListResponse> call, Response<ListResponse> response) {


                try {
                    kProgressHUD.dismiss();
                    Log.d("service done part 1", response.raw().request().url().toString());
                    if (response.isSuccessful()) {
                        Log.d("service done part 2", response.message());
                        ListResponse listResponse = response.body();
                        if (response.body().getDisease() != null) {


                            final AlertDialog.Builder commentDialog = new AlertDialog.Builder(List.this);
                            commentDialog.setTitle("Analyzed ...");
                            final TextView tv = new TextView(List.this);
                            tv.setText("Disease is " + response.body().getDisease());
                            tv.setTextSize(20);
                            tv.setPadding(40, 40, 40, 40);
                            tv.setGravity(Gravity.CENTER);
                            commentDialog.setView(tv);

                            commentDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    Intent intent = new Intent(List.this, Home.class);
                                    startActivity(intent);

                                }
                            });

                            commentDialog.show();


                        }
                       // Toast.makeText(List.this, "Internal Error", Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(List.this, "Error In selected Items", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    Toast.makeText(List.this, "Error" + e, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ListResponse> call, Throwable t) {
                kProgressHUD.dismiss();

                Toast.makeText(List.this, "Service Error" + t, Toast.LENGTH_LONG).show();


            }


        });
    }

}