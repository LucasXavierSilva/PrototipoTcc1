package com.example.lucas.prototipo.histogram;

/**
 * Created by Lucas on 19/02/2018.
 */

public class Globals {
    private static Globals instance;
    private int[] arrayRed;
    private int[] arrayGreen;
    private int[] arrayBlue;

    private int minRed;
    private int minGreen;
    private int minBlue;

    private int maxRed;
    private int maxGreen;
    private int maxBlue;

    private int maxRepeated;

    private double meanRed;
    private double meanGreen;
    private double meanBlue;

    private double medianRed;
    private double medianGreen;
    private double medianBlue;

    private Globals(){}

    public int[] getArrayRed() {
        return arrayRed;
    }

    public void setArrayRed(int[] arrayRed) {
        for(int i = 0; i<=255; i++){
            if (arrayRed[i] > maxRepeated){
                maxRepeated = arrayRed[i];
            }
        }
        this.arrayRed = arrayRed;
    }

    public int[] getArrayGreen() {
        return arrayGreen;
    }

    public void setArrayGreen(int[] arrayGreen) {
        for(int i = 0; i<=255; i++){
            if (arrayGreen[i] > maxRepeated){
                maxRepeated = arrayGreen[i];
            }
        }
        this.arrayGreen = arrayGreen;
    }

    public int[] getArrayBlue() {
        return arrayBlue;
    }

    public void setArrayBlue(int[] arrayBlue) {
        for(int i = 0; i<=255; i++){
            if (arrayBlue[i] > maxRepeated){
                maxRepeated = arrayBlue[i];
            }
        }
        this.arrayBlue = arrayBlue;
    }

    public int getMinRed() {
        return minRed;
    }

    public void setMinRed(int minRed) {
        this.minRed = minRed;
    }

    public int getMinGreen() {
        return minGreen;
    }

    public void setMinGreen(int minGreen) {
        this.minGreen = minGreen;
    }

    public int getMinBlue() {
        return minBlue;
    }

    public void setMinBlue(int minBlue) {
        this.minBlue = minBlue;
    }

    public int getMaxRed() {
        return maxRed;
    }

    public void setMaxRed(int maxRed) {
        this.maxRed = maxRed;
    }

    public int getMaxGreen() {
        return maxGreen;
    }

    public void setMaxGreen(int maxGreen) {
        this.maxGreen = maxGreen;
    }

    public int getMaxBlue() {
        return maxBlue;
    }

    public void setMaxBlue(int maxBlue) {
        this.maxBlue = maxBlue;
    }

    public double getMeanRed() {
        return meanRed;
    }

    public void setMeanRed(double meanRed) {
        this.meanRed = meanRed;
    }

    public double getMeanGreen() {
        return meanGreen;
    }

    public void setMeanGreen(double meanGreen) {
        this.meanGreen = meanGreen;
    }

    public double getMeanBlue() {
        return meanBlue;
    }

    public void setMeanBlue(double meanBlue) {
        this.meanBlue = meanBlue;
    }

    public double getMedianRed() {
        return medianRed;
    }

    public void setMedianRed(double medianRed) {
        this.medianRed = medianRed;
    }

    public double getMedianGreen() {
        return medianGreen;
    }

    public void setMedianGreen(double medianGreen) {
        this.medianGreen = medianGreen;
    }

    public double getMedianBlue() {
        return medianBlue;
    }

    public void setMedianBlue(double medianBlue) {
        this.medianBlue = medianBlue;
    }

    public int getMaxRepeated(){
        return maxRepeated;
    }

    public static synchronized Globals getInstance(){
        if(instance == null){
            instance = new Globals();
        }
        return instance;
    }

}
