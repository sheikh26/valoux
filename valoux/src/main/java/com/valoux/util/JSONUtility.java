/*
 * Copyright (c) 2015, 2016, Valoux. All rights reserved.
 * Valoux/CONFIDENTIAL. Use is subject to license terms.
 */

package com.valoux.util;

import java.util.HashMap;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.google.gson.Gson;

/**
 * This <java>class</java> JSONUtility is use to provide operations to json
 * object.
 * 
 * @author param Sheikh/Ankita Garg
 * 
 */
public class JSONUtility {

	public static Integer getSafeInteger(JSONObject jsonObject, String propertyName) throws JSONException {
		Integer integerVal = null;
		if (jsonObject.has(propertyName)) {

			integerVal = jsonObject.getInt(propertyName);
		}
		return integerVal;
	}

	public static Double getSafeDouble(JSONObject jsonObject, String propertyName) throws JSONException {
		Double integerVal = null;
		if (jsonObject.has(propertyName)) {

			integerVal = jsonObject.getDouble(propertyName);
		}
		return integerVal;
	}

	public static JSONArray getSafeJSONArray(JSONObject jsonObject, String propertyName) throws JSONException {
		JSONArray jsonArray = null;
		if (jsonObject.has(propertyName)) {
			jsonArray = jsonObject.getJSONArray(propertyName);
		}
		return jsonArray;
	}

	public static String getSafeString(JSONObject jsonObject, String propertyName) throws JSONException {
		String strVal = null;
		if (jsonObject.has(propertyName)) {
			strVal = jsonObject.getString(propertyName);
		}
		return strVal;
	}

	public static String getSafeStringIfNullSetBlank(JSONObject jsonObject, String propertyName) throws JSONException {
		String strVal = null;
		if (jsonObject.has(propertyName)) {
			strVal = jsonObject.getString(propertyName);
		} else {
			strVal = "";
		}
		return strVal;
	}

	public static JSONArray convertListToArray(List listObj) throws JSONException {
		Gson gson = new Gson();
		JSONArray jsonArray = new JSONArray(gson.toJson(listObj));
		return jsonArray;
	}

	public static JSONObject convertMapToJSON(HashMap<String, Object> map) throws JSONException {
		Gson gson = new Gson();
		JSONObject jsonObject = new JSONObject(gson.toJson(map));
		return jsonObject;
	}

}
