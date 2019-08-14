package com.example.bunnyeats;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.google.firebase.ml.common.modeldownload.FirebaseLocalModel;
import com.google.firebase.ml.common.modeldownload.FirebaseModelManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FloatingActionButton floatingCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseLocalModel localModel = new FirebaseLocalModel.Builder("local_model")
                .setAssetFilePath("local_model/manifest.json")
                .build();
        FirebaseModelManager.getInstance().registerLocalModel(localModel);

        floatingCamera = findViewById(R.id.floatingCamera);
        floatingCamera.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.floatingCamera):
                //NEED TO OPEN CAMERA AND CAPTURE A PICTURE
                Intent intent = new Intent(this, CameraActivity.class);
                startActivity(intent);
                break;
        }
    }
}
