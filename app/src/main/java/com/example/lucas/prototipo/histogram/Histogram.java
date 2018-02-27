package com.example.lucas.prototipo.histogram;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Lucas on 10/01/2018.
 * */

public class Histogram {
/*
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

                    tv_color.setText("\nVermelho: " + r + "\nVerde: " + g + "\nAzul: " + b +
                            "\nMedia Vermelho: " + mediaR + "\nMedia Verde: " + mediaG + "\nMedia Azul: " + mediaB +
                            "\nMenor/Maior Vermelho: " + valorMinimoR + "/" + valorMaximoR + "\nMenor/Maior Verde: " +
                            valorMinimoG + "/" + valorMaximoG + "\nMenor/Maior Azul: " + valorMinimoB + "/" + valorMaximoB);

                    int rr = math.round(mediaR);
                    int gg = math.round(mediaG);
                    int bb = math.round(mediaB);
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

                    mediaR = 0;
                    mediaG = 0;
                    mediaB = 0;
                }
                return true;
            }
        });
    }*/
}
