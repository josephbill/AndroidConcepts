package com.example.androidjoseph;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.androidjoseph.databinding.ActivityPostJobBinding;
import com.google.firebase.auth.FirebaseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostJob extends AppCompatActivity {

    private ActivityPostJobBinding binding;
    String name,job;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPostJobBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                finishAffinity();
            }
        });

        binding.postJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get text
                name = binding.textName.getText().toString().trim();
                job = binding.textJob.getText().toString().trim();
                //validate 
                if (name.isEmpty() || job.isEmpty()){
                    Toast.makeText(PostJob.this, "Field(s) cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    post_Job(name,job);
                }
                
            }
        });
    }

    private void post_Job(String name, String job) {
        // below line is for displaying our progress bar.
        binding.idLoadingPB.setVisibility(View.VISIBLE);

        // on below line we are creating a retrofit
        // builder and passing our base url
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://reqres.in/api/")
                // as we are sending data in json format so
                // we have to add Gson converter factory
                .addConverterFactory(GsonConverterFactory.create())
                // at last we are building our retrofit builder.
                .build();
        // below line is to create an instance for our retrofit api class.
        RetrofitApi retrofitAPI = retrofit.create(RetrofitApi.class);

        // passing data from our text fields to our modal class.
        JobModal modal = new JobModal(name, job);

        // calling a method to create a post and passing our modal class.
        Call<JobModal> call = retrofitAPI.createPost(modal);

        // on below line we are executing our method.
        call.enqueue(new Callback<JobModal>() {
            @Override
            public void onResponse(Call<JobModal> call, Response<JobModal> response) {
                // this method is called when we get response from our api.
                Toast.makeText(PostJob.this, "Data added to API", Toast.LENGTH_SHORT).show();

                // below line is for hiding our progress bar.
                binding.idLoadingPB.setVisibility(View.GONE);

                // on below line we are setting empty text
                // to our both edit text.
                binding.textJob.setText("");
                binding.textName.setText("");

                // we are getting response from our body 
                // and passing it to our modal class.
                JobModal responseFromAPI = response.body();

                // on below line we are getting our data from modal class and adding it to our string.
                String responseString = "Response Code : " + response.code() + "\nName : " + responseFromAPI.getName() + "\n" + "Job : " + responseFromAPI.getJob();

                // below line we are setting our 
                // string to our text view.
                Toast.makeText(PostJob.this, "Response String " + responseString, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<JobModal> call, Throwable t) {
                // setting text to our text view when 
                // we get error response from API.
                Toast.makeText(PostJob.this, "Error found is : " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}












