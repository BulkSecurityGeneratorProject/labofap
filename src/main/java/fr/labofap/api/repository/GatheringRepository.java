package fr.labofap.api.repository;

import fr.labofap.api.domain.Gathering;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data JPA repository for the Gathering entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GatheringRepository extends JpaRepository<Gathering, Long> {

    List<Gathering> findAllByCreatedBy(String createdBy);
}