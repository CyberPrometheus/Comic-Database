package de.hamburg.comicdatabase_backend.medium.dataaccess.api.repo;

import de.hamburg.comicdatabase_backend.medium.dataaccess.api.entity.impl.Comic;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ComicRepository extends MediumRepository<Comic>{
    @Query("SELECT c FROM Comic c LEFT JOIN FETCH c.comicIssues WHERE c.id = :id")
    Optional<Comic> findComicByIdWithIssues(@Param("id") Long id);
}
