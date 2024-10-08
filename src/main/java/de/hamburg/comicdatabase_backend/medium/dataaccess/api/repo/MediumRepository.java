package de.hamburg.comicdatabase_backend.medium.dataaccess.api.repo;

import de.hamburg.comicdatabase_backend.medium.dataaccess.api.entity.impl.Comic;
import de.hamburg.comicdatabase_backend.medium.dataaccess.api.entity.impl.Medium;
import de.hamburg.comicdatabase_backend.series.dataaccess.api.entity.Series;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MediumRepository<T extends Medium> extends JpaRepository<T, Long> {
    List<T> findByTitle(String title);
    Optional<T> findById(Long id);
    @Query("SELECT s FROM Series s JOIN  s.media m WHERE m.id = :mediumId")
    Optional<Series<T>> findSeriesForMedium(@Param("mediumId") Long id);
}
