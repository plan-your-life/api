package com.planyourlifeapp.api.repository;

import com.planyourlifeapp.api.models.Feed;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FeedRepository extends PagingAndSortingRepository<Feed, Long> {
    Feed getFeedById(Long id);
}
