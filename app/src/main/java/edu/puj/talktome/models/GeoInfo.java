package edu.puj.talktome.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class GeoInfo {
    private String title;
    private String content;
    private Double lat;
    private Double lng;
}
