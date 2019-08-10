package com.example.bunnyeats;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.ml.common.FirebaseMLException;
import com.google.firebase.ml.common.modeldownload.FirebaseLocalModel;
import com.google.firebase.ml.common.modeldownload.FirebaseModelManager;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabeler;
import com.google.firebase.ml.vision.label.FirebaseVisionOnDeviceAutoMLImageLabelerOptions;

import java.util.List;

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

        classify();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.floatingCamera):
                //NEED TO OPEN CAMERA AND CAPTURE A PICTURE
                break;
        }
    }

    private void classify() {
        Bitmap icon = BitmapFactory.decodeResource(getResources(),R.drawable.sunset);

        FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(icon);
        FirebaseVisionOnDeviceAutoMLImageLabelerOptions labelerOptions =
                new FirebaseVisionOnDeviceAutoMLImageLabelerOptions.Builder()
                        .setLocalModelName("local_model")    // Skip to not use a local model
                        //.setConfidenceThreshold(0.5f)  // Evaluate your model in the Firebase console
                        // to determine an appropriate value.
                        .build();
        FirebaseVisionImageLabeler labeler =
                null;
        try {
            labeler = FirebaseVision.getInstance().getOnDeviceAutoMLImageLabeler(labelerOptions);
            labeler.processImage(image)
                    .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionImageLabel>>() {
                        @Override
                        public void onSuccess(List<FirebaseVisionImageLabel> labels) {
                            // Task completed successfully
                            // ...
                            if (labels.size()>0){
                                String text = labels.get(0).getText() + " " + labels.get(0).getConfidence();
                                Toast.makeText(MainActivity.this, text, Toast.LENGTH_LONG).show();
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Task failed with an exception
                            // ...
                            Toast.makeText(MainActivity.this, "Fail one", Toast.LENGTH_LONG).show();
                        }
                    });
        } catch (FirebaseMLException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "Fail three", Toast.LENGTH_LONG).show();
        }

    }
}
