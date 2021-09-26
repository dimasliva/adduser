package com.maven.dima.repo;

import com.maven.dima.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User, Long> {
}
