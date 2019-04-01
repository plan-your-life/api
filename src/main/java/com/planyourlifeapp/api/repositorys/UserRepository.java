package com.planyourlifeapp.api.repositorys;

import org.springframework.data.repository.PagingAndSortingRepository;
import com.planyourlifeapp.api.models.User;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    User getByUsername(String username);
    User getByEmail(String email);
}
