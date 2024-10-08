package de.hamburg.comicdatabase_backend.medium.dataaccess.api.entity.impl;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Entity
@SuperBuilder
@NoArgsConstructor
//@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"title", "number", "date", "publisher_Id"})})
public class ComicIssue extends Medium {

    @ManyToMany(mappedBy = "comicIssues")
    @Builder.Default
    @Getter
    Set<Comic> comicSet = new HashSet<>();



}
