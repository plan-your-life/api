package com.planyourlifeapp.api.repositorys;

import com.planyourlifeapp.api.models.Feed;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Set;

public interface FeedRepository extends PagingAndSortingRepository<Feed, Long> {
    Feed getFeedById(Long id);
}
