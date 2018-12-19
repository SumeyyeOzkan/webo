package com.example.sumey.webo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class VeriTabani extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "konum";
    private static final int DATABASE_VERSION = 1;

    private static final String TabloKonum = "EnlemBoylam";
    private static final String RowLat = "latitude";
    private static final String RowLong = "longitude";
    private static final String RowSonuc="sonuc";


    public VeriTabani(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TabloKonum+ "("
                + RowLat + " TEXT NOT NULL, "
                + RowLong + " TEXT NOT NULL, "
                + RowSonuc + " TEXT NOT NULL)");
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TabloKonum);
        onCreate(db);
    }

    public void VeriEkle(String latitude, String longitude, String sonuc){
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues cv = new ContentValues();
            cv.put(RowLat, latitude);
            cv.put(RowLong, longitude);
            cv.put(RowSonuc, sonuc);
            db.insert(TabloKonum, null,cv);

        }catch (Exception e){
        }
        db.close();
    }

    public List<String> VeriListele(){
        List<String> veriler = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            String[] stunlar = {RowLong,RowLat,RowSonuc};
            Cursor cursor = db.query(TabloKonum, stunlar,null,null,null,null,null);
            while (cursor.moveToNext()){
                /*String[] columnNames = cursor.getColumnNames();
                for(int i=0;i<columnNames.length;i++){
                    Log.d("columng name : ",columnNames[i]);
                }*/
                veriler.add(cursor.getString(0)
                        + " - "
                        + cursor.getString(1)
                        + " - "
                        + cursor.getString(2)

                );
                //Log.d("son veri : ",veriler.get(veriler.size()));
            }
        }
        catch (Exception e){
        }
        db.close();
        return veriler;
    }






}
