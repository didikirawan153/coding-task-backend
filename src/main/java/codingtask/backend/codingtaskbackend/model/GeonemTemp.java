package codingtask.backend.codingtaskbackend.model;

import org.apache.xmlbeans.impl.xb.xsdschema.Public;

import lombok.Data;

@Data
public class GeonemTemp {
    //Variabel penampung temporary
    private String name;
    private String latitude;
    private String longitude;
    private Double score;
}
