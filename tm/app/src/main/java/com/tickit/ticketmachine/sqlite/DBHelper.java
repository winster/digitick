package com.tickit.ticketmachine.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tickit.ticketmachine.sqlite.vo.Ticket;
import com.tickit.ticketmachine.sqlite.vo.Trip;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by wjose on 31/03/2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Ticket.db";

    private static final String SQL_DELETE_TRIPS = "DROP TABLE IF EXISTS trips";
    private static final String SQL_DELETE_TICKETS = "DROP TABLE IF EXISTS tickets";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table trips " +
                        "(tripnumber integer primary key, busid text, org text, dst text, date integer)"
        );
        db.execSQL(
                "create table tickets " +
                        "(ticketid integer primary key, busid text, tripnumber integer, date integer, " +
                        "org text, dst text, points integer, passengers integer, fare integer, type text, " +
                        "transactionid integer, accountid integer)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TICKETS);
        db.execSQL(SQL_DELETE_TRIPS);
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

    public long insertTicket(Ticket ticket) {
        long ticketId = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        if(getLastTicketNumber()==0) {
            contentValues.put("ticketid", getLastTicketNumber() + 1);
        }
        contentValues.put("busid", ticket.getBusId());
        contentValues.put("tripnumber", ticket.getTripNumber());
        contentValues.put("date", ticket.getDate());
        contentValues.put("org", ticket.getOrg());
        contentValues.put("dst", ticket.getDst());
        contentValues.put("points", ticket.getPoints());
        contentValues.put("passengers", ticket.getPassengers());
        contentValues.put("fare", ticket.getFare());
        contentValues.put("type", ticket.getType());
        contentValues.put("transactionid", ticket.getTransactionId());
        contentValues.put("accountid", ticket.getAccountId());
        return db.insert("tickets", null, contentValues);
    }

    public long insertTrip(String busId, String org, String dst) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        if(getLastTripNumber()==0) {
            contentValues.put("tripnumber", getLastTripNumber() + 1);
        }
        contentValues.put("busid", busId);
        contentValues.put("date", new Date().getTime());
        contentValues.put("org", org);
        contentValues.put("dst", dst);
        return db.insert("trips", null, contentValues);
    }

    public int getLastTripNumber() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = null;
        res = db.rawQuery("select max(tripnumber) as tripnumber from tickets", null);
        res.moveToFirst();
        int tripnumber = 0;
        while(!res.isAfterLast()){
            tripnumber = res.getInt(res.getColumnIndex("tripnumber"));
            break;
        }
        return tripnumber;
    }

    public int getLastTicketNumber() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = null;
        res = db.rawQuery("select max(ticketid) as ticketid from tickets", null);
        res.moveToFirst();
        int ticketId = 0;
        while(!res.isAfterLast()){
            ticketId = res.getInt(res.getColumnIndex("ticketid"));
            break;
        }
        return ticketId;
    }

    public ArrayList<Ticket> getTickets(long tripNumber){
        ArrayList<Ticket> tickets = new ArrayList<Ticket>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = null;
        res = db.rawQuery("select * from tickets where tripnumber = ? order by date", new String[]{String.valueOf(tripNumber)});
        res.moveToFirst();
        while(!res.isAfterLast()){
            Ticket ticket = new Ticket();
            ticket.setTicketId(res.getLong(res.getColumnIndex("ticketid")));
            ticket.setBusId(res.getString(res.getColumnIndex("busid")));
            ticket.setTripNumber(res.getLong(res.getColumnIndex("tripnumber")));
            ticket.setDate(res.getLong(res.getColumnIndex("date")));
            ticket.setOrg(res.getString(res.getColumnIndex("org")));
            ticket.setDst(res.getString(res.getColumnIndex("dst")));
            ticket.setPoints(res.getInt(res.getColumnIndex("points")));
            ticket.setFare(res.getDouble(res.getColumnIndex("fare")));
            ticket.setType(res.getString(res.getColumnIndex("type")));
            ticket.setTransactionId(res.getString(res.getColumnIndex("transactionid")));
            ticket.setAccountId(res.getString(res.getColumnIndex("accountid")));
            ticket.setDate(res.getLong(res.getColumnIndex("date")));
            tickets.add(ticket);
            res.moveToNext();
        }
        return tickets;
    }

    public ArrayList<Trip> getTrips(long fromTime, long toTime){
        ArrayList<Trip> trips = new ArrayList<Trip>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = null;
        res = db.rawQuery("select a.tripnumber, count(a.ticketid) as tickets, sum(a.passengers) as passengers, sum(a.fare) as fare, b.date from tickets a, trips b where a.tripnumber=b.tripnumber and a.date between ? and ? group by a.tripnumber order by a.tripnumber", new String[]{String.valueOf(fromTime), String.valueOf(toTime)});
        res.moveToFirst();
        while(!res.isAfterLast()){
            Trip trip = new Trip();
            trip.setTripNumber(res.getInt(res.getColumnIndex("tripnumber")));
            trip.setTotalTickets(res.getInt(res.getColumnIndex("tickets")));
            trip.setTotalPassengers(res.getInt(res.getColumnIndex("passengers")));
            trip.setTotalFare(res.getDouble(res.getColumnIndex("fare")));
            trip.setDate(new Date(res.getLong(res.getColumnIndex("date"))));
            trips.add(trip);
            res.moveToNext();
        }
        return trips;
    }

    public boolean clearDatabase(){
        SQLiteDatabase db = this.getReadableDatabase();
        db.execSQL("delete from trips");
        db.execSQL("delete from tickets");
        return true;
    }
}
