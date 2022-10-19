package edu.puj.talktome.models;

public class DatabaseRoutes {
    public final static String USERS_PATH = "users";
    public final static String CERTIFICATES_PATH = "certificados";

    public static String getUser (String uuid){
        return String.format("%s/%s", USERS_PATH, uuid);
    }

    public static String getCertificatesPath (String uuid) {
        return String.format("%s/%s", CERTIFICATES_PATH, uuid);
    }
}
