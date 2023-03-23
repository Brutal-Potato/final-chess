package com.chess.registration;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PlayerRepository extends PagingAndSortingRepository<Players, Long>, JpaSpecificationExecutor<Players>, CrudRepository<Players, Long> {

}
