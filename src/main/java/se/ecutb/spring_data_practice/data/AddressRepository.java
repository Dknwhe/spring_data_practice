package se.ecutb.spring_data_practice.data;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import se.ecutb.spring_data_practice.entity.Address;

import java.util.List;

public interface AddressRepository extends CrudRepository<Address, Integer> {

    //Hitta alla adresser i en viss stad.
    List<Address> findByCityIgnoreCase(String city);
    @Query("SELECT address FROM Address address WHERE UPPER(address.city) = UPPER(:city)")
    List<Address> findByCityWithQuery(@Param("city") String city);

}
