package com.example.musicapp;


import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class FileManager {
    private final static String FILE_NAME = "sin.mp3";
    private Context mContext;
    short[] buffer;

    public File getSinFile() {
        return sinFile;
    }

    File sinFile;

    FileManager(Context context, Buffer mBuffer) {
        mContext = context;
        buffer = mBuffer.getBuffer();
    }

    public void writeFile() {
        File internalStorageDir = mContext.getFilesDir();
        sinFile = new File(internalStorageDir, "sin.mp3");
        try {
            FileOutputStream fos = new FileOutputStream(sinFile);
            fos.write(0xFF);
            fos.write(0xFB);
            fos.write(0x90);
            fos.write(0x00);

           /* OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            outputStreamWriter.write(0xFF);
            outputStreamWriter.write(0xFB);
            outputStreamWriter.write(0x90);
            outputStreamWriter.write(0x00);
*/
            /*outFile.writeByte(0xFF);
            outFile.writeByte(0xFB);
            outFile.writeByte(0x90);
            outFil e.writeByte(0x00);*/
            for (int i = 0; i < buffer.length; i++) {
                fos.write((byte)buffer[i]);
                //outFile.write(buffer[i]);
            }
            fos.close();
        } catch (Exception e) {
            int i = 0;
            i++;
        }
       // readFromFile();

    }

    public void readFromFile() {
        String s="";
        char[] inputBuffer = new char[100];
        int charRead;
        try {
            FileInputStream fileInputStream = mContext.openFileInput(FILE_NAME);
            InputStreamReader reader = new InputStreamReader(fileInputStream);


            // цикл читает данные из файла,
            while ((charRead = reader.read(inputBuffer)) != -1) {
                // конвертируем char в строку
                String rString = String.copyValueOf(inputBuffer, 0, charRead);
                s += rString;
            }
            reader.close();
        } catch (Exception e) {
            int i = 0;
            i++;
        }

    }
}
