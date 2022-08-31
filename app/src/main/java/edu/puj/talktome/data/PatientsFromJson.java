package edu.puj.talktome.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class PatientsFromJson {
    public static final String COUNTRIES_FILE = "listpacientes.json";
    private JSONArray patients;
    private int size;

    public JSONArray getPatients() {
        return patients;
    }

    public int getSize() {
        return size;
    }

    public String loadJSONFromAsset(InputStream is){
        String json = null;
        try {
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public void loadPatientsByJson(InputStream is) throws JSONException {
        patients = new JSONObject(loadJSONFromAsset(is)).getJSONArray("Patients");
        size = patients.length();
    }
}
