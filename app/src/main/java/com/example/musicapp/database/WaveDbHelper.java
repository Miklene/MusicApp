package com.example.musicapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.musicapp.database.WaveDb.WaveTable;

import androidx.annotation.Nullable;

public class WaveDbHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "waveBase.db";

    public WaveDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + WaveTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                WaveTable.Columns.TYPE + ", " +
                WaveTable.Columns.FREQUENCY + ", " +
                WaveTable.Columns.HARMONICS_NUMBER + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int odlVersion, int newVersion) {

    }
}
