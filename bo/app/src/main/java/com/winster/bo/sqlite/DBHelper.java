package com.winster.bo.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.winster.bo.vo.Bus;
import com.winster.bo.vo.FareDetails;
import com.winster.bo.vo.StopDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wjose on 31/03/2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "bo.db";

    private static final String SQL_DELETE_BUSES = "DROP TABLE IF EXISTS buses";
    private static final String SQL_DELETE_ROUTES = "DROP TABLE IF EXISTS stops";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table buses " +
                        "(busid string primary key, busname text, ownerid text)"
        );
        db.execSQL(
                "create table stops " +
                        "(stop int primary key, name text)"
        );
        db.execSQL(
                "create table fares " +
                        "(point int primary key, full real not null, half real, " +
                        "student real, pass real )"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_BUSES);
        db.execSQL(SQL_DELETE_ROUTES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    @Override
    protected void finalize() throws Throwable {
        this.close();
        super.finalize();
    }

    public long insertBus(Bus bus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("busid", bus.getBusId());
        contentValues.put("busname", bus.getBusName());
        contentValues.put("ownerid", bus.getOwnerId());
        return db.insert("buses", null, contentValues);
    }

    public long deleteBus(String busId) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("buses", "busid=?", new String[]{busId});
    }

    public ArrayList<Bus> getBuses(){
        ArrayList<Bus> buses = new ArrayList<Bus>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = null;
        res = db.rawQuery("select * from buses", new String[]{});
        res.moveToFirst();
        while(!res.isAfterLast()){
            Bus bus = new Bus();
            bus.setBusId(res.getString(res.getColumnIndex("busid")));
            bus.setBusName(res.getString(res.getColumnIndex("busname")));
            bus.setOwnerId(res.getString(res.getColumnIndex("ownerid")));
            buses.add(bus);
            res.moveToNext();
        }
        return buses;
    }

    public long insertStop(StopDetails stop) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("stop", stop.getStop());
        contentValues.put("name", stop.getDescription());
        return db.insert("stops", null, contentValues);
    }

    public long updateStop(StopDetails stop) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", stop.getDescription());
        return db.update("stops", contentValues, "stop=?", new String[]{String.valueOf(stop.getStop())});
    }

    public List<StopDetails> getStops(){
        List<StopDetails> routes = new ArrayList<StopDetails>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = null;
        res = db.rawQuery("select * from stops", new String[]{});
        res.moveToFirst();
        while(!res.isAfterLast()){
            StopDetails stop = new StopDetails();
            stop.setStop(res.getInt(res.getColumnIndex("stop")));
            stop.setDescription(res.getString(res.getColumnIndex("name")));
            routes.add(stop);
            res.moveToNext();
        }
        return routes;
    }

    public boolean clearStops(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("delete from stops");
        return true;
    }

    public long insertFare(FareDetails fare) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("point", fare.getPoint());
        contentValues.put("full", fare.getFull());
        contentValues.put("half", fare.getHalf());
        contentValues.put("student", fare.getStudent());
        contentValues.put("pass", fare.getPass());
        return db.insert("fares", null, contentValues);
    }

    public long updateFare(FareDetails fare) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("full", fare.getFull());
        contentValues.put("half", fare.getHalf());
        contentValues.put("student", fare.getStudent());
        contentValues.put("pass", fare.getPass());
        return db.update("fares", contentValues, "point=?", new String[]{
                String.valueOf(fare.getFull()), String.valueOf(fare.getHalf()),
                String.valueOf(fare.getStudent()),String.valueOf(fare.getPass())});
    }

    public List<FareDetails> getFares(){
        List<FareDetails> fares = new ArrayList<FareDetails>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = null;
        res = db.rawQuery("select * from fares", new String[]{});
        res.moveToFirst();
        while(!res.isAfterLast()){
            FareDetails fare = new FareDetails();
            fare.setPoint(res.getInt(res.getColumnIndex("point")));
            fare.setFull(res.getDouble(res.getColumnIndex("full")));
            fare.setHalf(res.getDouble(res.getColumnIndex("half")));
            fare.setStudent(res.getDouble(res.getColumnIndex("student")));
            fare.setPass(res.getDouble(res.getColumnIndex("pass")));
            fares.add(fare);
            res.moveToNext();
        }
        return fares;
    }

    public boolean clearFares(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("delete from fares");
        return true;
    }

    public boolean clearDatabase(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL(SQL_DELETE_BUSES);
        db.execSQL(SQL_DELETE_ROUTES);
        return true;
    }
}
