package com.example.lucas.prototipo;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lucas.prototipo.histogram.Globals;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.math.BigDecimal;

import static android.app.Activity.RESULT_OK;

/**
 * Created by User on 2/28/2017.
 */

public class Tab1Fragment extends Fragment {
    private static final String TAG = "Tab1Fragment";

    private Button btnTEST;

    private static final int RESULT_LOAD_IMAGE = 0;
    private static final int MY_PERMISSIONS_REQUEST_STORAGE = 1;
    View view;


    Math math;
    ImageView iv_image, iv_color;
    TextView tv_color;
    Button b_pick, onSelectImageClick;
    Double mediaR = 0.0, mediaG = 0.0, mediaB = 0.0, medianaR, medianaG, medianaB, luma, lumaPrecisa, lumaR, lumaG, lumaB;
    int r, g, b;


    int[] arrayR = new int[256];
    int[] arrayG = new int[256];
    int[] arrayB = new int[256];


    Globals globals = Globals.getInstance();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab1_fragment,container,false);
        btnTEST = (Button) view.findViewById(R.id.btnTEST);
        onSelectImageClick = (Button) view.findViewById(R.id.onSelectImageClick);
        iv_image = (ImageView) view.findViewById(R.id.iv_image);
        iv_color = (ImageView) view.findViewById(R.id.iv_color);
        tv_color = (TextView) view.findViewById(R.id.tv_color);
        b_pick = (Button) view.findViewById(R.id.b_pick);


        setOnClickListeners();
        setOnTouchListeners();


        btnTEST.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "TESTING BUTTON CLICK 1", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void setOnTouchListeners() {
        iv_image.setOnTouchListener(new View.OnTouchListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {
                if(!(iv_image.getDrawable() == null) && motionEvent.getAction() == MotionEvent.ACTION_DOWN){

                    int valorMinimoR = 0, valorMaximoR = 0, valorMinimoG = 0,
                            valorMaximoG = 0, valorMinimoB = 0, valorMaximoB = 0;

                    int touchX = (int) motionEvent.getX();
                    int touchY = (int) motionEvent.getY();

                    iv_image.buildDrawingCache();

                    Bitmap bitmap = iv_image.getDrawingCache();

                    //Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
                    for (int x= 0; x < bitmap.getWidth(); x++){
                        for (int y= 0; y < bitmap.getHeight(); y++) {
                            int pixelColor = bitmap.getPixel(x, y);

                            if (Color.red(pixelColor) != 0 && Color.green(pixelColor) != 0 && Color.blue(pixelColor) != 0) {
                                int A = Color.alpha(pixelColor);
                                int R = Color.red(pixelColor);
                                int G = Color.green(pixelColor);
                                int B = Color.blue(pixelColor);

                                if (mediaB == 0.0 && mediaG == 0.0 && mediaR == 0.0) {
                                    mediaR = (double) R;
                                    mediaG = (double) G;
                                    mediaB = (double) B;
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

                                arrayR[R] += 1;
                                arrayG[G] += 1;
                                arrayB[B] += 1;

                                if (x == touchX && y == touchY) {
                                    r = R;
                                    b = B;
                                    g = G;
                                }
                            }
                        }
                    }

                   // medianaR = arrayR[128].toFloat();
                    luma =  (0.2126 * mediaR) +  (0.7152 * mediaG) + (0.0722 * mediaB);

                    lumaR =  (0.299 * mediaR);
                    lumaR =  Math.pow(lumaR,2);

                    lumaG =  (0.587 * mediaG);
                    lumaG =  Math.pow(lumaG,2);

                    lumaB =  (0.114 * mediaB);
                    lumaB = Math.pow(lumaB,2);

                    lumaPrecisa = lumaR + lumaG + lumaB;
                    lumaPrecisa = Math.sqrt(lumaPrecisa);
                    //lumaPrecisa = (float) Math.sqrt ( Math.pow((0.299 * mediaR), 2)  + Math.pow((0.587 * mediaG), 2) + Math.pow((0.114 * mediaB), 2));

                    tv_color.setText("\nVermelho: " + r + "\nVerde: " + g + "\nAzul: " + b +
                            "\nMedia Vermelho: " + mediaR + "\nMedia Verde: " + mediaG + "\nMedia Azul: " + mediaB +
                            "\nMenor/Maior Vermelho: " + valorMinimoR + "/" + valorMaximoR + "\nMenor/Maior Verde: " +
                            valorMinimoG + "/" + valorMaximoG + "\nMenor/Maior Azul: " + valorMinimoB + "/" + valorMaximoB +
                            "\nLuma: " + luma + "\nLuma Precisa: " + lumaPrecisa);

                    int rr = (int) math.round(mediaR);
                    int gg = (int) math.round(mediaG);
                    int bb = (int) math.round(mediaB);
                    iv_color.setBackgroundColor(Color.rgb(rr, gg, bb));

                    int i = bitmap.getWidth();
                    //bitmap.recycle();

                    globals.setArrayRed(arrayR);
                    globals.setArrayGreen(arrayG);
                    globals.setArrayBlue(arrayB);

                    globals.setMeanRed(mediaR);
                    globals.setMeanGreen(mediaG);
                    globals.setMeanBlue(mediaB);

                    //globals.setMedianRed(medianaR);
                    //globals.setMedianGreen(medianaG);
                    //globals.setMedianBlue(medianaB);

                    globals.setMinRed(valorMinimoR);
                    globals.setMinGreen(valorMinimoG);
                    globals.setMinBlue(valorMinimoB);

                    globals.setMaxRed(valorMaximoR);
                    globals.setMaxGreen(valorMaximoG);
                    globals.setMaxBlue(valorMaximoB);

                    for(int i2 = 0; i2 == 256; i2++){
                        arrayR[i2] = 0;
                        arrayG[i2] = 0;
                        arrayB[i2] = 0;
                    }

                    mediaR = 0.0;
                    mediaG = 0.0;
                    mediaB = 0.0;
                }
                return true;
            }
        });
    }


    /** Start pick image activity with chooser. */
    public void onSelectImageClick(View view) {
        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .setActivityTitle("My Crop")
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .setCropMenuCropButtonTitle("Done")
                .setRequestedSize(400, 400)
                .setCropMenuCropButtonIcon(R.drawable.ic_launcher)
                .start(view.getContext(),this);
    }

    public void setOnClickListeners(){
        b_pick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

                    if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                            Manifest.permission.READ_EXTERNAL_STORAGE)){
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_STORAGE);
                    } else {
                        ActivityCompat.requestPermissions(getActivity(),
                                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_STORAGE);
                    }
                } else {
                    Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, RESULT_LOAD_IMAGE);
                }

            }
        });
        onSelectImageClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSelectImageClick(view);

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case MY_PERMISSIONS_REQUEST_STORAGE:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if(ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
                        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(i, RESULT_LOAD_IMAGE);
                    }
                } else {
                    Toast.makeText(getActivity(), "No perm", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data){
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
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
                ((ImageView) view.findViewById(R.id.iv_image)).setImageURI(result.getUri());
                Toast.makeText(
                        getActivity(), "Corte de imagem bem sucedido, Amostra: " + result.getSampleSize(), Toast.LENGTH_LONG)
                        .show();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(getActivity(), "Corte de Imagem Falhou: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }


    }
}
