package com.planyourlifeapp.api.repository;

import com.planyourlifeapp.api.models.VerifyUser;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface VerifyUserRepository extends PagingAndSortingRepository<VerifyUser,Long> {
    VerifyUser getByVerifyKey(String key);
}
