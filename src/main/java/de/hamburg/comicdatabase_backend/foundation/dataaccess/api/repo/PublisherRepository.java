package de.hamburg.comicdatabase_backend.foundation.dataaccess.api.repo;

import de.hamburg.comicdatabase_backend.foundation.dataaccess.api.entity.Publisher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {
    Optional<Publisher> findByPublisherName(String name);
}
