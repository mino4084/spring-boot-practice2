package com.restApi.jpa;

import org.springframework.data.repository.CrudRepository;

/**
 * packageName    : com.restApi.redis
 * fileName       : AircraftRepository
 * author         : user
 * date           : 2023-11-21
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2023-11-21        user       최초 생성
 */
public interface AircraftRepository extends CrudRepository<Aircraft, Long> {
}
