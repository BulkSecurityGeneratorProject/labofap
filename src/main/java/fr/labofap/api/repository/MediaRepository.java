package fr.labofap.api.repository;

import fr.labofap.api.domain.Comment;
import fr.labofap.api.domain.Media;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Media entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {


    List<Media> findByComment(Comment comment);
}
