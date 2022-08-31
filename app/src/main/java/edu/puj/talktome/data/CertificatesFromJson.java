package edu.puj.talktome.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;

public class CertificatesFromJson {
    public static final String COUNTRIES_FILE = "certificados.json";
    private JSONArray certificados;
    private int size;

    public JSONArray getCertificates() {
        return certificados;
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

    public void loadCertificatesByJson(InputStream is) throws JSONException {
        certificados = new JSONObject(loadJSONFromAsset(is)).getJSONArray("Certificados");
        size = certificados.length();
    }
}

