package de.hamburg.comicdatabase_backend.medium.dataaccess.api.repo;

import de.hamburg.comicdatabase_backend.medium.dataaccess.api.entity.impl.ComicIssue;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.Optional;


public interface ComicIssueRepository extends MediumRepository<ComicIssue> {
    @Query("SELECT c FROM ComicIssue c LEFT JOIN FETCH c.comicSet WHERE c.id = :id")
    Optional<ComicIssue> findUS_ComicByIdWithIssues(@Param("id") Long id);
}
