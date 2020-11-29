package com.example.sbb;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class CamActivity extends AppCompatActivity {

    int cameraRequestCode = 001;

    Classifier classifier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cam);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        classifier = new Classifier(Utils.assetFilePath(this,"last.torchscript.pt"));

        Button capture = findViewById(R.id.capture);

        capture.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){

                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                startActivityForResult(cameraIntent,cameraRequestCode);

            }


        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        if(requestCode == cameraRequestCode && resultCode == RESULT_OK){

            Intent resultView = new Intent(this,Result.class);

            resultView.putExtra("imagedata",data.getExtras());

            Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");

            String pred = classifier.predict(imageBitmap);
            resultView.putExtra("pred",pred);

            startActivity(resultView);

        }

    }

}
