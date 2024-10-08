package de.hamburg.comicdatabase_backend.series.dataaccess.api.entity;

import de.hamburg.comicdatabase_backend.foundation.dataaccess.api.entity.Publisher;
import de.hamburg.comicdatabase_backend.medium.dataaccess.api.entity.impl.Medium;
import de.hamburg.comicdatabase_backend.series.common.api.PublicationPeriod;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Series <T extends Medium> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String seriesName;



    @ManyToOne
    @JoinColumn(name = "publisher_Id")
    private Publisher publisher;

    @Embedded
    private PublicationPeriod publicationPeriod;


    @Getter
    @OneToMany(mappedBy = "series", fetch = FetchType.EAGER, orphanRemoval = true, targetEntity = Medium.class)
    private Set<T> media = new HashSet<>();

    public Series(String seriesName, Publisher publisher, PublicationPeriod publicationPeriod) {
        this.seriesName = seriesName;
        this.publisher = publisher;
        this.publicationPeriod = publicationPeriod;

    }

    public Integer getQuantity(){
        return media.size();
    }

    public boolean addMedium(T medium){
        return media.add(medium);
    }

    public boolean removeMedium(T medium){
        return media.remove(medium);
    }


}
