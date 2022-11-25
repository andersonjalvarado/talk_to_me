package edu.puj.talktome.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {
    private String name;
    //private long fechaNacimiento;
    private String tipoDocumento;
    private long numeroDocumento;
    private long phoneNumber;
    private String email;
    private String role;
//    private double latitud;
//    private double longitud;
    //private String pass2;
    //private long createdAt;
    //private long lastLogin;
}
