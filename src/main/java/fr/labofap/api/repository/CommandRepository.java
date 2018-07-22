package fr.labofap.api.repository;

import fr.labofap.api.domain.Command;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Command entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CommandRepository extends JpaRepository<Command, Long> {


    Page<Command> findAllByCreatedBy(String createdBy, Pageable pageable);

    Page<Command> findAllByAssignedToOrAssignedToIsNull(String assignedTo, Pageable pageable);

    @Modifying
    @Query("update Command c set c.assignedTo = ?1 where c.id = ?2")
    int setAssignedTo(String assignedTo, Long id);
}
