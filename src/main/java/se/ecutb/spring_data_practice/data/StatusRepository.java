package se.ecutb.spring_data_practice.data;

import org.springframework.data.repository.CrudRepository;
import se.ecutb.spring_data_practice.entity.Status;

public interface StatusRepository extends CrudRepository<Status, Integer> {
}
