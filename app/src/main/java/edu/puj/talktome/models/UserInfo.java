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
    //private String apellido;
    private String email;
    //private long fechaNacimiento;
    //private String tipoDocumento;
    //private long numeroDocumento;
    private long phoneNumber;

    private long createdAt;
    private long lastLogin;
}
