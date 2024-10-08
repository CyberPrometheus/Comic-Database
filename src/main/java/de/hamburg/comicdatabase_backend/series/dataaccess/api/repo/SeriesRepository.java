package de.hamburg.comicdatabase_backend.series.dataaccess.api.repo;

import de.hamburg.comicdatabase_backend.medium.dataaccess.api.entity.impl.Medium;
import de.hamburg.comicdatabase_backend.series.dataaccess.api.entity.Series;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SeriesRepository<T extends Medium> extends JpaRepository<Series<T>, Long> {
    Optional<Series<T>> findById(Long id);
    @Query("SELECT s FROM Series s LEFT JOIN FETCH s.media WHERE s.id = :id")
    Optional<Series<T>> findSeriesWithMedia(@Param("id") Long id);
}
