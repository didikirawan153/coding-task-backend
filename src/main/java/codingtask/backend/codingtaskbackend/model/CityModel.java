package codingtask.backend.codingtaskbackend.model;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@Data
@Entity
@Table(name = "table_geoname")
@NoArgsConstructor
@AllArgsConstructor
public class CityModel extends BaseDao {
    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "geoname_id")
    private Long geonameId;

    @Column(name = "name")
    private String name;

    @Column(name = "ascii")
    private String ascii;

    @Column(name = "alt_name")
    private String altName;

    @Column(name = "lat")
    private String lat;

    @Column(name = "longi")
    private String longi;

    @Column(name = "feat_class")
    private String featClass;

    @Column(name = "feat_code")
    private String featCode;

    @Column(name = "cc2")
    private String ccTwo;

    @Column(name = "country")
    private String country;

    @Column(name = "admin_one")
    private String adminOne;
    @Column(name = "admin_two")
    private String adminTwo;
    @Column(name = "admin_three")
    private String adminThree;
    @Column(name = "admin_four")
    private String adminFour;

    @Column(name = "population")
    private String population;

    @Column(name = "elevation")
    private String elevation;

    @Column(name = "dem")
    private String dem;

    @Column(name = "tz")
    private String timeZone;
}
