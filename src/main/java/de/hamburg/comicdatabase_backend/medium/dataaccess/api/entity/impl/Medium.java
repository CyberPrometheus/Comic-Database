package de.hamburg.comicdatabase_backend.medium.dataaccess.api.entity.impl;

import de.hamburg.comicdatabase_backend.foundation.dataaccess.api.entity.Publisher;
import de.hamburg.comicdatabase_backend.series.dataaccess.api.entity.Series;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.YearMonth;
import java.util.Objects;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
@DiscriminatorColumn(name = "media_type")
@Table(name = "Medium", uniqueConstraints = {@UniqueConstraint(columnNames = {"title", "number", "date", "publisher_Id", "media_type"})})
public abstract class Medium {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private long id;

     @Nullable
     private String coverPath;

     @NonNull
     private String title;

     @Nullable
     private Integer number;


     private YearMonth date;


     @ManyToOne
     @JoinColumn(name = "publisher_Id")
     private Publisher publisher;

     @ManyToOne(fetch = FetchType.EAGER)
     @JoinColumn(name = "series_id")
     private Series<? extends Medium> series;

     @Nullable
     private String author;






     @Override
     public boolean equals(Object o) {
          if (this == o) return true;
          if (o == null || getClass() != o.getClass()) return false;
          Medium medium = (Medium) o;
          return Objects.equals(title, medium.title) && Objects.equals(number, medium.number) && Objects.equals(date, medium.date) && Objects.equals(publisher, medium.publisher);
     }

     @Override
     public int hashCode() {
          return Objects.hash(title, number, date, publisher);
     }
}
