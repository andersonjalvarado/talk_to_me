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
    //private long fechaNacimiento;
    private String tipoDocumento;
    private long numeroDocumento;
    private String direccion;
    private long phoneNumber;
    private String email;

    //private long createdAt;
    //private long lastLogin;
}