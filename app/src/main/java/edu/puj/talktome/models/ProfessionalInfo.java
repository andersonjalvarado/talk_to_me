package edu.puj.talktome.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfessionalInfo {
    private String name;
    private String tipoDocumento;
    private long numeroDocumento;
    private long phoneNumber;
    private String email;
    private String role;
    private double latitud;
    private double longitud;

    //private long createdAt;
    //private long lastLogin;
}
