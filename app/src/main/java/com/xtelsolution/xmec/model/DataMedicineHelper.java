package com.xtelsolution.xmec.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by phimau on 2/13/2017.
 */

public class DataMedicineHelper extends SQLiteOpenHelper {
    private static final int VERSION_DATA=1;
    private static final String DATA_NAME = "DataMedicine";
    private static final String TABLE_MEDICINE = "dic_thuoc";
    private static final String COLL_ID = "idno";
    private static final String COLL_TEN = "ten";
    private static final String COLL_DANG_BAO_CHE="dangbaoche";
    private static final String COLL_NHOM_DUOC_LY="nhomduocly";
    private static final String COLL_THANH_PHAN="thanhphan";
    private static final String COLL_CHI_DINH="chidinh";
    private static final String COLL_CHONG_CHI_DINH="chongchidinh";
    private static final String COLL_TUONG_TAC_THUOC="tuongtacthuoc";
    private static final String COLL_TAC_DUNG_PHU="tacdungphu";
    private static final String COLL_CHU_Y_DE_PHONG="chuydephong";
    private static final String COLL_LIEU_LUONG="lieuluong";
    private static final String COLL_BAO_QUAN="baoquan";

    public static final String STRING_CREATE_TABLE_MEDICINE="CREATE TABLE `dic_thuoc` (" +
            " `idno` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
            " `ten` TEXT NOT NULL," +
            " `dangbaoche` TEXT," +
            " `nhomduocly` TEXT," +
            " `thanhphan` TEXT," +
            " `chidinh` TEXT," +
            " `chongchidinh` TEXT," +
            " `tuongtacthuoc` TEXT," +
            " `tacdungphu` TEXT," +
            " `chuydephong` TEXT," +
            " `lieuluong` TEXT," +
            " `baoquan` TEXT" +
            ");";
    private SQLiteDatabase mdb;

    public DataMedicineHelper(Context context) {
        super(context, DATA_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        mdb.execSQL(STRING_CREATE_TABLE_MEDICINE);
    }

    public boolean insertMedicine(Medicine medicine){
        if (mdb.insert(TABLE_MEDICINE,null,convertContentValues(medicine))!=-1){
            mdb.close();
            return true;
        }
        mdb.close();
        return false;
    }

    public boolean deleteMedicine(int id){
        mdb=getWritableDatabase();
        long rs= mdb.delete(TABLE_MEDICINE,COLL_ID+" = ?"+id,null);
        if (rs!=-1){
            mdb.close();
            return false;
        }
        mdb.close();
        return false;
    }

    public ArrayList<Medicine> getAllMedicine(){
        mdb = getReadableDatabase();
        ArrayList<Medicine> medicines = new ArrayList<>();
        Cursor cursor = mdb.query(TABLE_MEDICINE,null,null,null,null,null,null);
        while (cursor.moveToNext()){
            int id= cursor.getInt(cursor.getColumnIndex(COLL_ID));
            String name= cursor.getString(cursor.getColumnIndex(COLL_TEN));
            String type = cursor.getString(cursor.getColumnIndex(COLL_DANG_BAO_CHE));
            String group = cursor.getString(cursor.getColumnIndex(COLL_NHOM_DUOC_LY));
            String component = cursor.getString(cursor.getColumnIndex(COLL_THANH_PHAN));
            String indication = cursor.getString(cursor.getColumnIndex(COLL_CHI_DINH));
            String contraindication = cursor.getString(cursor.getColumnIndex(COLL_CHONG_CHI_DINH));
            String drugInteraction = cursor.getString(cursor.getColumnIndex(COLL_TUONG_TAC_THUOC));
            String sidEeffect = cursor.getString(cursor.getColumnIndex(COLL_TAC_DUNG_PHU));
            String warning = cursor.getString(cursor.getColumnIndex(COLL_CHU_Y_DE_PHONG));
            String dosage = cursor.getString(cursor.getColumnIndex(COLL_LIEU_LUONG));
            String preservation = cursor.getString(cursor.getColumnIndex(COLL_BAO_QUAN));
            Medicine medicine = new Medicine(id,name,type,group,component,indication,contraindication,drugInteraction,sidEeffect,warning,dosage,preservation);
            medicines.add(medicine);
        }
        mdb.close();
        return medicines;
    }


    public boolean updateMedicine(String id,Medicine medicine){
        mdb = getWritableDatabase();
        if (mdb.update(TABLE_MEDICINE,convertContentValues(medicine),COLL_ID+ " = "+id,null)>0){
            mdb.close();
            return true;
        }
        mdb.close();
        return  false;
    }

    private ContentValues convertContentValues(Medicine medicine){
        ContentValues values = new ContentValues();
        values.put(COLL_TEN,medicine.getName());
        values.put(COLL_DANG_BAO_CHE,medicine.getType());
        values.put(COLL_NHOM_DUOC_LY,medicine.getGroup());
        values.put(COLL_THANH_PHAN,medicine.getComponent());
        values.put(COLL_CHI_DINH,medicine.getIndication());
        values.put(COLL_CHONG_CHI_DINH,medicine.getContraindication());
        values.put(COLL_TUONG_TAC_THUOC,medicine.getDrugInteraction());
        values.put(COLL_TAC_DUNG_PHU,medicine.getSidEeffect());
        values.put(COLL_CHU_Y_DE_PHONG,medicine.getWarning());
        values.put(COLL_LIEU_LUONG,medicine.getDosage());
        values.put(COLL_BAO_QUAN,medicine.getPreservation());
        return  values;
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
