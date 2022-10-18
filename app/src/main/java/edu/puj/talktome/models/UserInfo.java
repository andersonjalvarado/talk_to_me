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
    private String email;
    private long phoneNumber;
    private long createdAt;
    private long lastLogin;
}
