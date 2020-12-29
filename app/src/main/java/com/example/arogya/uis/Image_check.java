package com.example.arogya.uis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arogya.R;
import com.example.arogya.manager.ServiceManager.APIService;
import com.example.arogya.manager.ServiceManager.APIUtils;
import com.example.arogya.models.Requests.ImageRequest;
import com.example.arogya.models.Requests.TextRequest;
import com.example.arogya.models.Response.ImageResponse;
import com.example.arogya.models.Member;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.io.ByteArrayOutputStream;
import java.text.BreakIterator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Image_check extends AppCompatActivity {

    private ImageView capturedImg;
    private Button identifyBtn,shareBtn;
    private TextView discription,sName,fName,eName,edibleParts,dis,usage;
    private LinearLayout typeSpinnerLayout,rootTypeSpinnerLayout,btnRelative2;
    Member member;
    DatabaseReference reff,reff2,reff3,reff4,reff5,reff6;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    Spinner typeSpinner,rootTypeSpinner;
    String[] types = {"Single","Multiple"};
    String[] rootTypes = {"Leaf","Bark"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_check);



        sName = findViewById(R.id.scientificNameAns);
        fName = findViewById(R.id.familyNameAns);
        eName = findViewById(R.id.englishNameAns);
        edibleParts = findViewById(R.id.ediblePartsAns);
        dis = findViewById(R.id.descriptionAns);
        usage = findViewById(R.id.usageAns);
        discription = findViewById(R.id.palantNameAns);
        identifyBtn = findViewById(R.id.identifyBtn);
        capturedImg = findViewById(R.id.capturedImg);
        shareBtn = findViewById(R.id.shareBtn);
        typeSpinnerLayout = findViewById(R.id.typeSinnerLayout);
        rootTypeSpinnerLayout = findViewById(R.id.rootTypeSinnerLayout);
        btnRelative2 = findViewById(R.id.btnRelative2);

        btnRelative2.setVisibility(View.GONE);
        rootTypeSpinnerLayout.setVisibility(View.VISIBLE);
        typeSpinnerLayout.setVisibility(View.VISIBLE);
        final String[] type = new String[1];
        final String[] rootType = new String[1];

        typeSpinner = findViewById(R.id.typeSpinner);
        ArrayAdapter adapter01 = new ArrayAdapter(this,android.R.layout.simple_spinner_item,types);
        typeSpinner.setAdapter(adapter01);
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type[0] = types[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                identifyBtn.setVisibility(View.GONE);

            }
        });

        rootTypeSpinner = findViewById(R.id.rootTypeSpinner);
        ArrayAdapter adapter02 = new  ArrayAdapter(this,android.R.layout.simple_spinner_item,rootTypes);
        rootTypeSpinner.setAdapter(adapter02);
        rootTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                rootType[0] = rootTypes[position];
                identifyBtn.setVisibility(View.VISIBLE);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                identifyBtn.setVisibility(View.GONE);

            }
        });


         member = new Member();
         reff = FirebaseDatabase.getInstance().getReference().child("Member");

         reff2 = FirebaseDatabase.getInstance().getReference().child("Akkapana");
         reff3 = FirebaseDatabase.getInstance().getReference().child("Kurundu");
         reff4 = FirebaseDatabase.getInstance().getReference().child("Katupila");
         reff5 = FirebaseDatabase.getInstance().getReference().child("Kohomba");
         reff6 = FirebaseDatabase.getInstance().getReference().child("Kaha");



//         Bundle ex = getIntent().getExtras();
//         final Bitmap bitmap = ex.getParcelable("image");
//         capturedImg.setImageBitmap(bitmap);
//        final Bitmap bitmap = (Bitmap) getIntent().getParcelableExtra("image");
//        capturedImg.setImageBitmap(bitmap);

        final byte[] byteArray = getIntent().getByteArrayExtra("image");
        final Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        capturedImg.setImageBitmap(bitmap);



        mAuth = FirebaseAuth.getInstance();

        user =mAuth.getCurrentUser();
        final String name = user.getDisplayName();
        final Uri fbPhotoUri = user.getPhotoUrl();


        final String[] encoded = new String[1];
        final String[] encodedNew = new String[1];


        identifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                byte[] byteArray = byteArrayOutputStream.toByteArray();

                encodedNew[0] = Base64.encodeToString(byteArray, Base64.DEFAULT);
                encoded[0] = encodedNew[0].replaceAll("\n","");


                ImageRequest imageRequest = new ImageRequest(encoded[0],type[0],rootType[0]);
                APIService apiService = APIUtils.getApiService();
                imagePass(imageRequest,apiService);

            }
        });


        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (discription == null){

                    Toast.makeText(Image_check.this, "Identify the Plant Before Share", Toast.LENGTH_SHORT).show();
                }else {

                    String[] x = encodedNew;
                    List<String> stringList = new ArrayList<String>(Arrays.asList(x));



                    String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                    String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());



                    member.setDate(currentDate);
                    member.setTime(currentTime);
                    member.setName(name);
                    member.setDiscription(discription.getText().toString().trim());
                    member.setPhoto(stringList);
                    member.getFbProfilePhoto(fbPhotoUri);

                    reff.push().setValue(member);

                    Intent ShareIntent = new Intent(Image_check.this, Home.class);
                    startActivity(ShareIntent);


                }



            }
        });



    }

    private void imagePass (final ImageRequest imageRequest, final APIService apiService) {

        final KProgressHUD kProgressHUD = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setDimAmount(0.5f)
                .show();

        apiService.imagePass(imageRequest).enqueue(new Callback<ImageResponse>() {


            /**
             * Invoked for a received HTTP response.
             * <p>
             * Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
             * Call {@link Response#isSuccessful()} to determine if the response indicates success.
             *
             * @param call
             * @param response
             */
            @Override
            public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {

                try {

                    kProgressHUD.dismiss();
                    Log.d("USER_REGISTRATION", response.raw().request().url().toString());
                    if (response.isSuccessful()) {
                        Log.d("USER_REGISTRATION", response.message());
                        ImageResponse imageResponse = response.body();
                        if (response.body().getStatus().equals("200")) {

                            rootTypeSpinnerLayout.setVisibility(View.GONE);
                            typeSpinnerLayout.setVisibility(View.GONE);
                            btnRelative2.setVisibility(View.VISIBLE);



                            if (response.body().getresult().equals("Undifined Image")){

                                final AlertDialog.Builder commentDialog = new AlertDialog.Builder(Image_check.this);
                                commentDialog.setTitle("Error");
                                commentDialog.setMessage("Image cannot identify, Please try again ");

                                commentDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        Intent listIntent = new Intent(Image_check.this, com.example.arogya.uis.Home.class);
                                        startActivity(listIntent);

                                    }

                                });
                                btnRelative2.setVisibility(View.GONE);
                                commentDialog.show();
                            }

                            if (response.body().getresult().equals("Akkapana")){


                                reff2.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        String localName = dataSnapshot.child("Localname").getValue(String.class);
                                        String scientificName = dataSnapshot.child("Scientificname").getValue(String.class);
                                        String familyName = dataSnapshot.child("Familyname").getValue(String.class);
                                        String englishName = dataSnapshot.child("Englishname").getValue(String.class);
                                        String ediblePartsAns = dataSnapshot.child("Edibleparts").getValue(String.class);
                                        String plantdis = dataSnapshot.child("Description ").getValue(String.class);
                                        String ayurvedicUsageAns = dataSnapshot.child("Ayurvedicusage").getValue(String.class);


                                        discription.setText(localName);
                                        sName.setText(scientificName);
                                        fName.setText(familyName);
                                        eName.setText(englishName);
                                        edibleParts.setText(ediblePartsAns);
                                        usage.setText(ayurvedicUsageAns);
                                        dis.setText(plantdis);

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });


                            }

                            if (response.body().getresult().equals("Cinnamon")){


                                reff3.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        String localName = dataSnapshot.child("Localname").getValue(String.class);
                                        String scientificName = dataSnapshot.child("Scientificname").getValue(String.class);
                                        String familyName = dataSnapshot.child("Familyname").getValue(String.class);
                                        String englishName = dataSnapshot.child("Englishname").getValue(String.class);
                                        String ediblePartsAns = dataSnapshot.child("Edibleparts").getValue(String.class);
                                        String plantdis = dataSnapshot.child("Description ").getValue(String.class);
                                        String ayurvedicUsageAns = dataSnapshot.child("Ayurvedicusage").getValue(String.class);


                                        discription.setText(localName);
                                        sName.setText(scientificName);
                                        fName.setText(familyName);
                                        eName.setText(englishName);
                                        edibleParts.setText(ediblePartsAns);
                                        usage.setText(ayurvedicUsageAns);
                                        dis.setText(plantdis);

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });


                            }

                            if (response.body().getresult().equals("Kohomba")){


                                reff4.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        String localName = dataSnapshot.child("Localname").getValue(String.class);
                                        String scientificName = dataSnapshot.child("Scientificname").getValue(String.class);
                                        String familyName = dataSnapshot.child("Familyname").getValue(String.class);
                                        String englishName = dataSnapshot.child("Englishname").getValue(String.class);
                                        String ediblePartsAns = dataSnapshot.child("Edibleparts").getValue(String.class);
                                        String plantdis = dataSnapshot.child("Description ").getValue(String.class);
                                        String ayurvedicUsageAns = dataSnapshot.child("Ayurvedicusage").getValue(String.class);


                                        discription.setText(localName);
                                        sName.setText(scientificName);
                                        fName.setText(familyName);
                                        eName.setText(englishName);
                                        edibleParts.setText(ediblePartsAns);
                                        usage.setText(ayurvedicUsageAns);
                                        dis.setText(plantdis);

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });


                            }
                            if (response.body().getresult().equals("Katupila")){


                                reff5.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        String localName = dataSnapshot.child("Localname").getValue(String.class);
                                        String scientificName = dataSnapshot.child("Scientificname").getValue(String.class);
                                        String familyName = dataSnapshot.child("Familyname").getValue(String.class);
                                        String englishName = dataSnapshot.child("Englishname").getValue(String.class);
                                        String ediblePartsAns = dataSnapshot.child("Edibleparts").getValue(String.class);
                                        String plantdis = dataSnapshot.child("Description ").getValue(String.class);
                                        String ayurvedicUsageAns = dataSnapshot.child("Ayurvedicusage").getValue(String.class);


                                        discription.setText(localName);
                                        sName.setText(scientificName);
                                        fName.setText(familyName);
                                        eName.setText(englishName);
                                        edibleParts.setText(ediblePartsAns);
                                        usage.setText(ayurvedicUsageAns);
                                        dis.setText(plantdis);

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });


                                  }
                            if ( response.body().getresult().equals("TumericRoot")) {


                                reff6.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                        String localName = dataSnapshot.child("Localname").getValue(String.class);
                                        String scientificName = dataSnapshot.child("Scientificname").getValue(String.class);
                                        String familyName = dataSnapshot.child("Familyname").getValue(String.class);
                                        String englishName = dataSnapshot.child("Englishname").getValue(String.class);
                                        String ediblePartsAns = dataSnapshot.child("Edibleparts").getValue(String.class);
                                        String plantdis = dataSnapshot.child("Description ").getValue(String.class);
                                        String ayurvedicUsageAns = dataSnapshot.child("Ayurvedicusage").getValue(String.class);


                                        discription.setText(localName);
                                        sName.setText(scientificName);
                                        fName.setText(familyName);
                                        eName.setText(englishName);
                                        edibleParts.setText(ediblePartsAns);
                                        usage.setText(ayurvedicUsageAns);
                                        dis.setText(plantdis);


                                    }


                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });


                            } else {

                                final AlertDialog.Builder commentDialog = new AlertDialog.Builder(Image_check.this);
                                commentDialog.setTitle("Error");
                                commentDialog.setMessage("Image cannot identify, Please try again ");

                                commentDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        Intent listIntent = new Intent(Image_check.this, com.example.arogya.uis.Home.class);
                                        startActivity(listIntent);

                                    }

                                });
                                btnRelative2.setVisibility(View.GONE);
                                commentDialog.show();


                            }


                        }
                    } else {
                        Toast.makeText(Image_check.this, "Request Fail", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(Image_check.this, "Error"+e, Toast.LENGTH_SHORT).show();
                }


            }

            /**
             * Invoked when a network exception occurred talking to the server or when an unexpected
             * exception occurred creating the request or processing the response.
             *
             * @param call
             * @param t
             */
            @Override
            public void onFailure(Call<ImageResponse> call, Throwable t) {


                kProgressHUD.dismiss();

                Toast.makeText(Image_check.this, "Service Error", Toast.LENGTH_LONG).show();




            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


    }


}
