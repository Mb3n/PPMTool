package com.marcbengsch.ppmtool.repositories;

import com.marcbengsch.ppmtool.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

	User findByUsername(String username);
	User getById(Long id); // instead of Optional<User> findById(Long id) -> with this new method we get no Optional(User)


}
