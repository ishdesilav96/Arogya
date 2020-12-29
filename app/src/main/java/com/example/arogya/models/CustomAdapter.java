package com.example.arogya.models;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorSpace;
import android.net.Uri;
import android.net.wifi.hotspot2.pps.HomeSp;
import android.os.Build;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.util.Printer;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.example.arogya.R;
import com.example.arogya.manager.ServiceManager.APIService;
import com.example.arogya.manager.ServiceManager.APIUtils;
import com.example.arogya.models.Requests.TextRequest;
import com.example.arogya.models.Response.ListResponse;
import com.example.arogya.models.Response.TextResponse;
import com.example.arogya.uis.Home;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.squareup.picasso.Picasso;

import java.text.BreakIterator;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyviewHolder> {


    private FirebaseAuth mAuth;
    private FirebaseUser user;
    String commentTxt,outputResponse,outputSentence;
    int x;


    public CustomAdapter(List<model> my_list, Context context) {
        this.my_list = my_list;
        this.context = context;
    }

    List<model> my_list;
    Context context;

    @NonNull
    @Override
    public CustomAdapter.MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list_item,parent,false);
        return new MyviewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyviewHolder holder, int position) {

        mAuth = FirebaseAuth.getInstance();
        user =mAuth.getCurrentUser();

            model model= my_list.get(position);
//        holder.dpImage.setImageDrawable(context.getResources().getDrawable(model.getDpImage()));
        holder.dpName.setText(model.getName());
//      holder.image.setImageDrawable(context.getResources().getDrawable(model.getImage()));
        holder.title.setText(model.getDiscription());
        holder.data.setText(model.getDate());
        holder.time.setText(model.getTime());
        holder.image.setImageBitmap(model.getBitmap());
       // notifyDataSetChanged();

    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {

        return my_list.size();
    }

    class MyviewHolder extends RecyclerView.ViewHolder{

        ImageView image,dpImage;
        TextView title,dpName,data,time,commentViewTxt,commentUser,likeCounter;
        Button comment,like;
        LinearLayout commentView;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);
            dpImage = itemView.findViewById(R.id.dpImage);
            dpName = itemView.findViewById(R.id.dpName);
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            data =  itemView.findViewById(R.id.time1);
            time=itemView.findViewById(R.id.time2);
            like = itemView.findViewById(R.id.likeBtn);
            comment = itemView.findViewById(R.id.commentBtn);
            commentView = itemView.findViewById(R.id.commentViewLayout);
            commentViewTxt = itemView.findViewById(R.id.commentViewTxt);
            commentUser = itemView.findViewById(R.id.commentUser);
            likeCounter = itemView.findViewById(R.id.likeCount);

            comment.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(final View v) {
                     final AlertDialog.Builder commentDialog = new AlertDialog.Builder(v.getRootView().getContext(),R.style.MyAlertDialogStyle);
                    commentDialog.setTitle("Add Comment");
                    final EditText commentInput = new EditText(v.getContext());
                    commentInput.setInputType(InputType.TYPE_CLASS_TEXT);
                    commentDialog.setView(commentInput);
                    commentInput.setVisibility(View.VISIBLE);

//


                    commentDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            commentTxt = commentInput.getText().toString();

                            TextRequest textRequest = new TextRequest(commentTxt);
                            APIService apiService = APIUtils.getApiService_2();
                            textPass(textRequest, apiService);

                        }

                        private void textPass(TextRequest textRequest, APIService apiService) {



                            apiService.textPass(textRequest).enqueue(new Callback<TextResponse>() {
                                @Override
                                public void onResponse(Call<TextResponse> call, Response<TextResponse> response) {

                                    try {
//                                        kProgressHUD.dismiss();
                                        Log.d("service done part 1", response.raw().request().url().toString());
                                        if (response.isSuccessful()) {
                                            Log.d("service done part 2", response.message());
                                            TextResponse textResponse = response.body();
                                            if (response.body().getResponse() != null) {

                                                outputResponse = response.body().getResponse().toString();
                                                if (outputResponse == "false"){

                                                    Toast.makeText(v.getContext(), "Comment is not Related ", Toast.LENGTH_SHORT).show();
                                                }else {
                                                    commentView.setVisibility(View.VISIBLE);
                                                    commentViewTxt.setText(commentTxt);

                                                    String commentUserId = mAuth.getCurrentUser().getDisplayName();
                                                    commentUser.setText(commentUserId + " :");
                                                    FirebaseDatabase.getInstance().getReference("Member").getKey();


                                                }
                                            }
                                            if (response.body().getSentence() != null){
                                                outputSentence = response.body().getSentence().toString();
                                            }

                                        }
                                    }catch (Exception e) {
                                        System.out.println("error"+e);

                                    }


                                }

                                @Override
                                public void onFailure(Call<TextResponse> call, Throwable t) {
                                   // kProgressHUD.dismiss();
                                    System.out.println("error"+t);

                                }
                            });
                        }


                    });

                    commentDialog.show();



                }
            });


            like.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {

           //         like.setBackgroundTintList(null);

                    if (x==0){
                        x++;
                        likeCounter.setVisibility(View.VISIBLE);
                        likeCounter.setText(x + " like");
                    }else {
                        x--;
                        if (x==0){
                            likeCounter.setVisibility(View.GONE);
                            likeCounter.setText(null);
                        }else {
                            likeCounter.setText(x+" likes");
                        }

                    }



                }
            });





        }

    }
}
