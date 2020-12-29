package com.example.arogya.uis;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arogya.R;
import com.example.arogya.models.CustomAdapter;
import com.example.arogya.models.Member;
import com.example.arogya.models.model;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {

    RecyclerView recyclerView;
    List<model> main_list;
    
    RecyclerView.Adapter adapter;
    private DatabaseReference databaseReference;

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private EditText searchTxt;
    private Button camera,list,newsFeed,searchBtn;
    LinearLayout titleLayout;
    Button newsFeedBtn;
    String search;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
        mAuth = FirebaseAuth.getInstance();


        main_list= new ArrayList<model>();
        final List<String>[] x = new List[0];



        databaseReference = FirebaseDatabase.getInstance().getReference("Member");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                    model model=postSnapshot.getValue(model.class);
                    main_list.add(model);

                    recyclerView =findViewById(R.id.recyclerView);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(Home.this));
                    adapter= new CustomAdapter(main_list,getApplicationContext());
                    recyclerView.setAdapter(adapter);

                    byte[] imageAsBytes = Base64.decode(model.getPhoto().toString(), Base64.DEFAULT);
                    Bitmap postImg = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
                    model.setBitmap(postImg);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        camera = findViewById(R.id.camara);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(intent1, 1005);


            }
        });

        list = findViewById(R.id.list);
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent listIntent = new Intent(Home.this, com.example.arogya.uis.List.class);
                startActivity(listIntent);

            }
        });

        newsFeed =  findViewById(R.id.newsFeed);
        newsFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent listIntent = new Intent(Home.this, com.example.arogya.uis.Home.class);
                startActivity(listIntent);

            }
        });

        searchBtn = findViewById(R.id.searchBtn);
        searchTxt = findViewById(R.id.searchTxt);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newsFeedBtn.setVisibility(View.GONE);
                searchTxt.setVisibility(View.VISIBLE);
            }
        });
        searchTxt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(final TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                   search = searchTxt.getText().toString();

                    DatabaseReference mDatabaseRef =FirebaseDatabase.getInstance().getReference("Member");

                    Query query=mDatabaseRef.orderByChild("discription").equalTo(search);

                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);


                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()){
                                    model model=postSnapshot.getValue(model.class);
                                    main_list.clear();
                                    main_list.add(model);

                                    recyclerView =findViewById(R.id.recyclerView);
                                    recyclerView.setHasFixedSize(true);
                                    recyclerView.setLayoutManager(new LinearLayoutManager(Home.this));
                                    adapter= new CustomAdapter(main_list,getApplicationContext());
                                    recyclerView.setAdapter(adapter);


                                    byte[] imageAsBytes = Base64.decode(model.getPhoto().toString(), Base64.DEFAULT);
                                    Bitmap postImg = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
                                    model.setBitmap(postImg);


                                }



                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    return true;
                }
                return false;
            }
        });

        newsFeedBtn = findViewById(R.id.newsFeedBtn);
        newsFeedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                String txt1 = "camera";
//                speakTwo(txt1);


                Intent intent2 = new Intent("android.media.action.READ_EXTERNAL_STORAGE");
                intent2.setType("image/*");
                intent2.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult((Intent.createChooser(intent2, "Select Picture")),1024);
                Log.d("Gallary","Gallary");

            }
        });



    }

    /**
     * Dispatch incoming result to the correct fragment.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bitmap photo = null;

        if (resultCode == RESULT_OK ) {


            if (requestCode ==1005){
                photo=(Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();


                Intent anotherIntent = new Intent(this, Image_check.class);
                anotherIntent.putExtra("image", byteArray);
                startActivity(anotherIntent);



            } else {
            Uri uri = data.getData();
            try {
                photo = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                Intent in1 = new Intent(this, Image_check.class);
                in1.putExtra("image",byteArray);
                startActivity(in1);

//                Intent anotherIntent1 = new Intent(this, Image_check.class);
//                anotherIntent1.putExtra("image", photo);
//                startActivity(anotherIntent1);


            } catch (IOException e) {
                e.printStackTrace();



            }


        }


        }




    }


}
