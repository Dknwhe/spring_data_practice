package se.ecutb.spring_data_practice.service;

import org.springframework.transaction.annotation.Transactional;
import se.ecutb.spring_data_practice.entity.Address;
import se.ecutb.spring_data_practice.entity.AppUser;

public interface CreateAppUserService {
    @Transactional(rollbackFor = RuntimeException.class)
    AppUser createAndSave(String email, String name, String password, String passwordConfirmation, Address address);
}
