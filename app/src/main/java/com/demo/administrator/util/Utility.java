package com.demo.administrator.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.demo.administrator.db.DXWeatherDB;
import com.demo.administrator.model.City;
import com.demo.administrator.model.County;
import com.demo.administrator.model.Province;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utility {

    public synchronized static boolean handleProvinceResponse(String response, DXWeatherDB dxWeatherDB) {
        if (!TextUtils.isEmpty(response)) {
            String[] allProvinces = response.split(",");
            if (allProvinces.length > 0) {
                for (String p : allProvinces) {
                    String[] arry = p.split("\\|");
                    Province province = new Province();
                    province.setProvinceCode(arry[0]);
                    province.setProvinceName(arry[1]);
                    dxWeatherDB.saveProvince(province);
                }
                return true;
            }
        }
        return false;
    }

    public synchronized static boolean handleCityResponse(String response, int provinceid, DXWeatherDB dxWeatherDB) {
        if (!TextUtils.isEmpty(response)) {
            String[] allCitys = response.split(",");
            if (allCitys.length > 0) {
                for (String c : allCitys) {
                    String[] arry = c.split("\\|");
                    City city = new City();
                    city.setCityCode(arry[0]);
                    city.setCityName(arry[1]);
                    city.setProvinceId(provinceid);
                    dxWeatherDB.saveCity(city);
                }
                return true;

            }
        }
        return false;
    }

    public synchronized static boolean handleCountyResponse(String response, int cityid, DXWeatherDB dxWeatherDB) {
        if (!TextUtils.isEmpty(response)) {
            String[] allCountys = response.split(",");
            if (allCountys.length > 0) {
                for (String c : allCountys) {
                    String[] arry = c.split("\\|");
                    County county = new County();
                    county.setCountyCode(arry[0]);
                    county.setCountyName(arry[1]);
                    county.setCityId(cityid);
                    dxWeatherDB.saveCounty(county);
                }
                return true;

            }
        }
        return false;
    }

    public static void handleWeatherResponse(Context context, String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONObject weatherInfo = jsonObject.getJSONObject("weaherinfo");
            String cityName = weatherInfo.getString("city");
            String weatherCode = weatherInfo.getString("cityid");
            String temp1 = weatherInfo.getString("temp1");
            String temp2 = weatherInfo.getString("temp2");
            String weatherDesp = weatherInfo.getString("weather");
            String publishTime = weatherInfo.getString("ptime");
            saveWeatherInfo(context, cityName, weatherCode, temp1, temp2,
                    weatherDesp, publishTime);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static void saveWeatherInfo(Context context, String cityName, String weaherCode, String temp1, String temp2, String weatherDesp,
                                String publishTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年M月d日", Locale.CHINA);
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putBoolean("city_selected", true);
        editor.putString("city_name", cityName);
        editor.putString("weather_code", weaherCode);
        editor.putString("temp1", temp1);
        editor.putString("temp2", temp2);
        editor.putString("weather_desp", weatherDesp);
        editor.putString("publish_time", publishTime);
        editor.putString("current_date", sdf.format(new Date()));
        editor.apply();
        editor.clear();

    }

    ;
}
