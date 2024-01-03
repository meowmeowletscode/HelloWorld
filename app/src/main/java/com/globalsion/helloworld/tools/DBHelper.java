package com.globalsion.helloworld.tools;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "AssetTracker";
    public static final String PRODUCT_TABLE_NAME = "asset";
    public static final String COLUMN_ID = "id";
    public static final String ASSET_COLUMN_NAME = "asset_name";
    public static final String DEPARTMENT_COLUMN_NAME = "department";
    public static final String COSTCENTRE_COLUMN_NAME = "cost_centre";
    public static final String PURCH_COLUMN_NAME = "purchase_date";
    public static final String DESC_COLUMN_NAME = "description";

    public static final String LOCATION_COLUMN_NAME = "location";
    public static final String SCANTIME_COLUMN_NAME = "scan_at";
    public static final String Quantity_COLUMN_NAME = "quantity";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table if not exists asset (id integer primary key, asset_name text, department text, cost_centre text, purchase_date text, description text, location text, scan_at datetime,remark text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS asset");
        onCreate(db);
    }

    //region Asset Table
    public boolean insertAsset ( String item_name, String department, String cost_centre, String purchase_date, String description, String location) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("asset_name", item_name);
        contentValues.put("department", department);
        contentValues.put("cost_centre", cost_centre);
        contentValues.put("purchase_date", purchase_date);
        contentValues.put("description", description);
        contentValues.put("location", location);
        contentValues.put("scan_at", currentDateandTime);
        db.insert("asset", null, contentValues);
        return true;
    }

    public Cursor getAssetData(String asset_name) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from asset where asset_name = '" +asset_name+ "' order by id desc limit 1", null );
        return res;
    }

    public boolean updateAsset (Integer id, String asset_name, String location, String department, String cost_centre, String purchase_date, String description) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("asset_name", asset_name);
        contentValues.put("department", department);
        contentValues.put("cost_centre", cost_centre);
        contentValues.put("purchase_date", purchase_date);
        contentValues.put("description", description);
        contentValues.put("location", location);
        contentValues.put("scan_at", currentDateandTime);
        db.update("asset", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteAsset (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("asset",
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public Integer deleteAsset (String asset) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("asset",
                "asset_name = ? ",
                new String[] { asset });
    }

    public Integer deleteAllAsset () {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("asset", "1", null);
    }

    public Cursor getAllAssetCursor() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from asset ", null );
        return res;
    }

    public ArrayList<String> getAllAsset() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from asset", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndexOrThrow(ASSET_COLUMN_NAME)));
            res.moveToNext();
        }
        return array_list;
    }

    //endregion

}
