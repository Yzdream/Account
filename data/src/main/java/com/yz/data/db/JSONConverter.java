package com.yz.data.db;

import com.raizlabs.android.dbflow.converter.TypeConverter;

import org.json.JSONArray;
import org.json.JSONException;

@com.raizlabs.android.dbflow.annotation.TypeConverter
public class JSONConverter extends TypeConverter<String, JSONArray> {

    @Override
    public String getDBValue(JSONArray model) {
        return model == null ? null : model.toString();
    }

    @Override
    public JSONArray getModelValue(String data) {
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }
}
