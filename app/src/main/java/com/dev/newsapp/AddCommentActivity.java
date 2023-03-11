package com.dev.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.dev.newsapp.databinding.ActivityAddCommentBinding;
import com.dev.newsapp.models.CommentsResponse;
import com.dev.newsapp.network.ApiClient;
import com.dev.newsapp.network.ApiInterface;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCommentActivity extends AppCompatActivity {
    ActivityAddCommentBinding binding;
    String id="";
    ApiInterface apiInterface;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddCommentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        id=getIntent().getStringExtra("id");
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Posting Comment..");

        binding.topBar.actionMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        binding.topBar.tvTitle.setText("Post Comment");

        binding.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                String name=binding.etName.getText().toString();
                String comment=binding.etComment.getText().toString();
                if(!name.isEmpty() && !comment.isEmpty()) {
                    JsonObject jsonObject = new JsonObject();

                        jsonObject.addProperty("name", name);
                        jsonObject.addProperty("text", comment);
                        jsonObject.addProperty("news_id", id);


                    apiInterface= ApiClient.getClient().create(ApiInterface.class);
                    apiInterface.saveComment(jsonObject).enqueue(new Callback<CommentsResponse>() {
                        @Override
                        public void onResponse(Call<CommentsResponse> call, Response<CommentsResponse> response) {
                            progressDialog.dismiss();

                            if(response.isSuccessful() && Objects.equals(Objects.requireNonNull(response.body()).getServiceMessageCode(), "1")){
                                Toast.makeText(AddCommentActivity.this, "Comment Added ", Toast.LENGTH_SHORT).show();
                                finish();
                            }else {
                                Toast.makeText(AddCommentActivity.this, "Error posting Comment ", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<CommentsResponse> call, Throwable t) {
                            progressDialog.dismiss();
                            Toast.makeText(AddCommentActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });

                }


            }
        });
    }
}