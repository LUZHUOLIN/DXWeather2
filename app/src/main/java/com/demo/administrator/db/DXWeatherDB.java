package com.demo.administrator.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.demo.administrator.model.City;
import com.demo.administrator.model.County;
import com.demo.administrator.model.Province;

import java.util.ArrayList;
import java.util.List;

public class DXWeatherDB {
    /**
     * 数据库名字
     */
    private static final String DB_NAME = "DX_Weather";
    /**
     * 数据库版本
     */
    private static final int VERSION = 1;
    private static DXWeatherDB dxWeatherDB;
    private static SQLiteDatabase db;

    /**
     * 私有化构造函数
     */

    private DXWeatherDB(Context context) {
        DXWeaherHelper dxWeaherHelper = new DXWeaherHelper(context, DB_NAME, null, VERSION);
        db = dxWeaherHelper.getWritableDatabase();
    }

    /**
     * 获取DXWeather实例
     */
    private synchronized static DXWeatherDB getInstance(Context context) {
        if (dxWeatherDB == null) {
            dxWeatherDB = new DXWeatherDB(context);
        }
        return dxWeatherDB;


    }

    /**
     * 将Province的实例储存到数据库
     */

    public void saveProvince(Province province) {
        if (province != null) {
            ContentValues values = new ContentValues();
            values.put("province_name", province.getProvincename());
            values.put("province_code", province.getProvincecode());
            db.insert("Province", null, values);

        }
    }

    /**
     * 在数据库中获取全国省份的信息
     */

    public List<Province> loadProvince() {
        List<Province> provinces = new ArrayList<Province>();
        Cursor cursor = db.query("Province", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            while (cursor.moveToNext()) {
                Province province = new Province();
                province.setProvincename(cursor.getString(cursor
                        .getColumnIndex("province_name")));
                province.setProvincecode(cursor.getString(cursor
                        .getColumnIndex("province_code")));
                provinces.add(province);

            }
            cursor.close();
        }
        return provinces;

    }

    /**
     * 将City的实例储存到数据库中
     */
    public void saveCity(City city) {
        if (city != null) {
            ContentValues values = new ContentValues();
            values.put("city_name", city.getCityname());
            values.put("city_code", city.getCitycode());
            values.put("province_id", city.getProvinceid());
            db.insert("City", null, values);
        }
    }

    /**
     * 在数据库中获取城市的信息
     */

    public List<City> lodaCity() {
        List<City> citys = new ArrayList<City>();
        Cursor cursor = db.query("City", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            while (cursor.moveToNext()) {
                City city = new City();
                city.setCityname(cursor.getString(cursor
                        .getColumnIndex("city_name")));
                city.setCitycode(cursor.getString(cursor
                        .getColumnIndex("city_code")));
                city.setProvinceid(cursor.getInt(cursor
                        .getColumnIndex("province_id")));
                citys.add(city);
            }
            cursor.close();
        }
        return citys;
    }
    /**
     * 将City的实例储存到数据库中
     */
    public void saveCounty(County county) {
        if (county != null) {
            ContentValues values = new ContentValues();
            values.put("county_name", county.getCountyname());
            values.put("county_code", county.getCountycode());
            values.put("city_id", county.getCityid());
            db.insert("County", null, values);
        }
    }

    /**
     * 在数据库中获取城市的信息
     */

    public List<County> lodaCounty() {
        List<County> countys = new ArrayList<County>();
        Cursor cursor = db.query("County", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            while (cursor.moveToNext()) {
                County county = new County();
                county.setCountyname(cursor.getString(cursor
                        .getColumnIndex("county_name")));
                county.setCountycode(cursor.getString(cursor
                        .getColumnIndex("county_code")));
                county.setCityid(cursor.getInt(cursor
                        .getColumnIndex("city_id")));
                countys.add(county);
            }
            cursor.close();
        }
        return countys;
    }
}
