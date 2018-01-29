package com.example.lucas.prototipo;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.BitmapCompat;
import android.support.v7.app.AppCompatActivity;
import java.lang.Math;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.lucas.prototipo.select.CircleOverlayView;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

public class MainActivity extends AppCompatActivity {

    private static final int RESULT_LOAD_IMAGE = 0;
    private static final int MY_PERMISSIONS_REQUEST_STORAGE = 1;



    Math math;
    Uri imageUri;
    ImageView iv_image, iv_color;
    TextView tv_color;
    Button b_pick;
    CircleOverlayView cicleOverlay;
    float mediaR, mediaG, mediaB;
    int r, g, b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv_image = (ImageView) findViewById(R.id.iv_image);
        iv_color = (ImageView) findViewById(R.id.iv_color);
        tv_color = (TextView) findViewById(R.id.tv_color);
        b_pick = (Button) findViewById(R.id.b_pick);
       // cicleOverlay = (CircleOverlayView) findViewById(R.id.cicleOverlay);

        getSupportActionBar().hide();
        setOnClickListeners();
        setOnTouchListeners();



    }

    private void setOnTouchListeners() {
        iv_image.setOnTouchListener(new View.OnTouchListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                if(!(iv_image.getDrawable() == null)){

                    DisplayMetrics metrics = new DisplayMetrics();
                    getWindowManager().getDefaultDisplay().getMetrics(metrics);

                    int touchX = (int) motionEvent.getX();
                    int touchY = (int) motionEvent.getY();
                    iv_image.buildDrawingCache();



                    //Bitmap bitmap = iv_image.getDrawingCache();
                    Bitmap bitmap = Bitmap.createBitmap(metrics, iv_image.getWidth(),
                            iv_image.getHeight(),Bitmap.Config.ARGB_8888);

                    bitmap = iv_image.getDrawingCache();
                    //Bitmap bitmap = immutableBitmap.copy(Bitmap.Config.ARGB_8888, true);
                    Drawable d = new BitmapDrawable(getResources(),bitmap);

                    //Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());

                    int valorMinimoR = 0, valorMaximoR = 0, valorMinimoG = 0,
                            valorMaximoG = 0, valorMinimoB = 0, valorMaximoB = 0;
                    for (int x=-10; x < 10; x++){
                        for (int y=-10; y < 10; y++) {
                            if (!(bitmap.isRecycled()) && (touchX) > 0 && (touchY) > 0 && (touchX) < bitmap.getWidth() && (touchY) < bitmap.getHeight()) {
                                int pixelColor = bitmap.getPixel(touchX+x, touchY+y);
                                int A = Color.alpha(pixelColor);
                                int R = Color.red(pixelColor);
                                int G = Color.green(pixelColor);
                                int B = Color.blue(pixelColor);

                                if (mediaB == 0 && mediaG == 0 && mediaR == 0) {
                                    mediaR = R;
                                    mediaG = G;
                                    mediaB = B;
                                }

                                if (valorMinimoR > R || valorMinimoR == 0)
                                    valorMinimoR = R;
                                if (valorMaximoR < R)
                                    valorMaximoR = R;

                                if (valorMinimoG > G || valorMinimoG == 0)
                                    valorMinimoG = G;
                                if (valorMaximoG < G)
                                    valorMaximoG = G;

                                if (valorMinimoB > B || valorMinimoB == 0)
                                    valorMinimoB = B;
                                if (valorMaximoB < B)
                                    valorMaximoB = B;

                                mediaR = (mediaR + R) / 2;
                                mediaG = (mediaG + G) / 2;
                                mediaB = (mediaB + B) / 2;

                                if (x == 0 && y == 0) {
                                    r = R;
                                    g = G;
                                    b = B;
                                }
                                if (x == 9 && y == 9) {
                                    tv_color.setText("\nVermelho: " + r + "\nVerde: " + g + "\nAzul: " + b +
                                            "\nMedia Vermelho: " + mediaR + "\nMedia Verde: " + mediaG + "\nMedia Azul: " + mediaB +
                                            "\nMenor/Maior Vermelho: " + valorMinimoR + "/" + valorMaximoR + "\nMenor/Maior Verde: " +
                                            valorMinimoG + "/" + valorMaximoG + "\nMenor/Maior Azul: " + valorMinimoB + "/" + valorMaximoB);

                                    int rr = math.round(mediaR);
                                    int gg = math.round(mediaG);
                                    int bb = math.round(mediaB);
                                    iv_color.setBackgroundColor(Color.rgb(rr, gg, bb));
                                    //iv_color.setBackground(d);
                                }
                            }
                        }
                    }
                    //bitmap.recycle();
                }
                mediaR = 0;
                mediaG = 0;
                mediaB = 0;
                return true;
            }
        });
    }


    /** Start pick image activity with chooser. */
    public void onSelectImageClick(View view) {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setActivityTitle("My Crop")
                .setCropShape(CropImageView.CropShape.OVAL)
                .setCropMenuCropButtonTitle("Done")
                .setRequestedSize(400, 400)
                .setCropMenuCropButtonIcon(R.drawable.ic_launcher)
                .start(this);
    }

    public void setOnClickListeners(){
        b_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                    if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)){
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_STORAGE);
                    } else {
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_STORAGE);
                    }
                } else {
                    Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, RESULT_LOAD_IMAGE);
                }

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case MY_PERMISSIONS_REQUEST_STORAGE:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if(ContextCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(i, RESULT_LOAD_IMAGE);
                    }
                } else {
                    Toast.makeText(MainActivity.this, "No perm", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data){
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            iv_image.setImageBitmap(BitmapFactory.decodeFile(picturePath));

        }
        // handle result of CropImageActivity
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                ((ImageView) findViewById(R.id.iv_image)).setImageURI(result.getUri());
                Toast.makeText(
                        this, "Corte de imagem bem sucedido, Amostra: " + result.getSampleSize(), Toast.LENGTH_LONG)
                        .show();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Corte de Imagem Falhou: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }


    }

}
