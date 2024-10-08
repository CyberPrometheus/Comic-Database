package de.hamburg.comicdatabase_backend.medium.dataaccess.api.entity.impl;


import de.hamburg.comicdatabase_backend.medium.dataaccess.api.entity.api.MediumOperations;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@SuperBuilder
//@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"title", "number", "date", "publisher_Id"})})
public class Comic extends Medium implements MediumOperations<ComicIssue> {

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "Comic_US_Comic_IssueSet", joinColumns = @JoinColumn(name = "comic_Id"),
    inverseJoinColumns = @JoinColumn(name = "us_comic_Id"))
    @Builder.Default
    @Getter
    Set<ComicIssue> comicIssues = new HashSet<>();


    @Override
    public boolean addMedium(ComicIssue medium) {
       return comicIssues.add(medium);
    }

    @Override
    public ComicIssue getMedium(long id) {
        return comicIssues.stream().filter(comic -> comic.getId() == id).findFirst().orElse(null);
    }

    @Override
    public boolean removeMedium(ComicIssue medium) {
        return comicIssues.remove(medium);
    }

    @Override
    public Set getSet(){
        return comicIssues;
    }
}
