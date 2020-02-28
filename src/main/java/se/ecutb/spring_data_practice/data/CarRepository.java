package se.ecutb.spring_data_practice.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import se.ecutb.spring_data_practice.entity.Car;
import se.ecutb.spring_data_practice.entity.Status;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CarRepository extends CrudRepository<Car, Integer> {
    //Hitta en bil med specifikt registreringsnummer.
    Optional<Car> findByRegNumberIgnoreCase(String regnumber);
    @Query("SELECT car FROM Car car WHERE UPPER(car.regNumber) = UPPER(:reg)")
    Optional<Car> findByRegNumberIgnoreCaseWithQuery(@Param("reg") String regnumber);

    //Hitta alla bilar som har en viss status kod.
    List<Car> findByStatusCodesStatusCodeIgnoreCase(String statusCode);
    @Query("SELECT car FROM Car car JOIN FETCH car.statusCodes status WHERE UPPER(status.statusCode) = UPPER(:statusCode)")
    List<Car> findByStatusCodesStatusCodeIgnoreCaseWithQuery(@Param("statusCode") String statusCode);

    //Hitta alla bilar äldre än ett visst datum.
    List<Car> findByRegDateBefore(LocalDate end);
    @Query("SELECT car FROM Car car WHERE car.regDate < :end")
    List<Car> findByRegDateBeforeWithQuery(@Param("end") LocalDate end);

    //Hitta alla bilar nyare än ett visst datum.
    List<Car> findByRegDateAfter(LocalDate start);
    @Query("SELECT car FROM Car car WHERE car.regDate > :start")
    List<Car> findByRegDateAfterWithQuery(@Param("start") LocalDate start);

    //Hitta alla bilar registrerade mellan två datum.
    List<Car> findByRegDateBetween(LocalDate start, LocalDate end);
    @Query("SELECT car FROM Car car WHERE car.regDate BETWEEN :start AND :end")
    List<Car> findByRegDateBetweenWithQuery(@Param("start") LocalDate start, @Param("end") LocalDate end);
}
