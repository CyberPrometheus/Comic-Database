package de.hamburg.comicdatabase_backend.foundation.dataaccess.api.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@RequiredArgsConstructor

public class Publisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NonNull
    @Column(nullable = false, unique = true)
    private String publisherName;
}
