package se.ecutb.spring_data_practice.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import se.ecutb.spring_data_practice.entity.Car;
import se.ecutb.spring_data_practice.entity.Status;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class CarRepositoryTest {

    @Autowired private CarRepository testObject;
    @Autowired private TestEntityManager em;


    private List<Car> carData(){
        return Arrays.asList(
                new Car("ABC123","Volvo","S60", LocalDate.parse("2002-05-20")),
                new Car("BCD123", "BMW","530", LocalDate.parse("2009-09-30"))
        );
    }

    private List<Status> statusData(){
        return Arrays.asList(
                new Status("Körförbud"),
                new Status("Avställd")
        );
    }

    private List<Car> testCars;
    private List<Status> statuses;

    @BeforeEach
    void setUp() {
        testCars = (List<Car>) testObject.saveAll(carData());
        statuses = statusData().stream().map(em::persist).collect(Collectors.toList());
        testCars.get(0).addStatusCode(statuses.get(0));
        testCars.get(1).addStatusCode(statuses.get(0));
        testCars.get(1).addStatusCode(statuses.get(1));
        em.flush();
    }

    @Test
    public void given_regnumber_findByRegNumberIgnoreCase_should_return_optional_present(){
        String regnumber= "abc123";
        Optional<Car> result = testObject.findByRegNumberIgnoreCase(regnumber);
        assertTrue(result.isPresent());
    }

    @Test
    public void given_regnumber_findByRegNumberIgnoreCaseWithQuery_should_return_optional_present(){
        String regnumber= "abc123";
        Optional<Car> result = testObject.findByRegNumberIgnoreCaseWithQuery(regnumber);
        assertTrue(result.isPresent());
    }

    @Test
    public void given_statusCode_findByStatusCodesStatusCodeIgnoreCase_should_return_list_size_2(){
        String statusCode = "körförbud";
        List<Car> result = testObject.findByStatusCodesStatusCodeIgnoreCase(statusCode);
        assertEquals(2, result.size());
    }

    @Test
    public void given_statusCode_findByStatusCodesStatusCodeIgnoreCaseWithQuery_should_return_list_size_2(){
        String statusCode = "körförbud";
        List<Car> result = testObject.findByStatusCodesStatusCodeIgnoreCaseWithQuery(statusCode);
        assertEquals(2, result.size());
    }

    @Test
    public void given_date_findByRegDateBefore_should_return_list_size_1(){
        LocalDate date = LocalDate.parse("2009-09-30");
        List<Car>cars = testObject.findByRegDateBefore(date);
        assertEquals(1, cars.size());
    }

    @Test
    public void given_date_findByRegDateBeforeWithQuery_should_return_list_size_1(){
        LocalDate date = LocalDate.parse("2009-09-30");
        List<Car>cars = testObject.findByRegDateBeforeWithQuery(date);
        assertEquals(1, cars.size());
    }

    @Test
    public void given_date_findByRegDateAfter_should_return_list_size_1(){
        LocalDate date = LocalDate.parse("2002-05-20");
        List<Car> cars = testObject.findByRegDateAfter(date);
        assertEquals(1, cars.size());
    }

    @Test
    public void given_date_findByRegDateAfterWithQuery_should_return_list_size_1(){
        LocalDate date = LocalDate.parse("2002-05-20");
        List<Car> cars = testObject.findByRegDateAfterWithQuery(date);
        assertEquals(1, cars.size());
    }

    @Test
    public void given_startDate_endDate_findByRegDateBetween_should_return_list_size_2(){
        LocalDate start = LocalDate.parse("2002-05-19");
        LocalDate end = LocalDate.parse("2009-10-01");
        List<Car> cars = testObject.findByRegDateBetween(start,end);
        assertEquals(2, cars.size());
    }

    @Test
    public void given_startDate_endDate_findByRegDateBetweenWithQuery_should_return_list_size_2(){
        LocalDate start = LocalDate.parse("2002-05-19");
        LocalDate end = LocalDate.parse("2009-10-01");
        List<Car> cars = testObject.findByRegDateBetweenWithQuery(start,end);
        assertEquals(2, cars.size());
    }
}
