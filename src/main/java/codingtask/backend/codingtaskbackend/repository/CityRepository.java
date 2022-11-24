package codingtask.backend.codingtaskbackend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import codingtask.backend.codingtaskbackend.model.CityModel;

@Repository
@Transactional
public interface CityRepository extends JpaRepository<CityModel,Long> {
    
    // Query untuk menampilkan data berdasarkan custom trigonomerti
    @Modifying
    @Query(value = "SELECT *,(3959 * ACOS(COS(RADIANS(:lat)) * COS(RADIANS(lat)) * COS(RADIANS(longi) - RADIANS(:longi)) + SIN( RADIANS(:lat)) * SIN( RADIANS(lat)))) AS distance FROM table_geoname WHERE name LIKE %:name% HAVING distance < 50 ORDER BY distance", nativeQuery = true)
    List<CityModel> getPlace(@Param("name") String nameString, @Param("lat") String latString, @Param("longi") String longiString);
}
