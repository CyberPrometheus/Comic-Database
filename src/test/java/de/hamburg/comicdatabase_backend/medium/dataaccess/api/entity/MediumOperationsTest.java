package de.hamburg.comicdatabase_backend.medium.dataaccess.api.entity;


import de.hamburg.comicdatabase_backend.ComicDatabaseBackendApplication;
import de.hamburg.comicdatabase_backend.foundation.dataaccess.api.entity.Publisher;
import de.hamburg.comicdatabase_backend.foundation.dataaccess.api.repo.PublisherRepository;
import de.hamburg.comicdatabase_backend.medium.dataaccess.api.entity.impl.Comic;
import de.hamburg.comicdatabase_backend.medium.dataaccess.api.entity.impl.Medium;
import de.hamburg.comicdatabase_backend.medium.dataaccess.api.entity.impl.ComicIssue;
import de.hamburg.comicdatabase_backend.medium.dataaccess.api.repo.ComicRepository;
import de.hamburg.comicdatabase_backend.medium.dataaccess.api.repo.MediumRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.YearMonth;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = ComicDatabaseBackendApplication.class)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MediumOperationsTest {

    @Autowired
    private MediumRepository<Medium> mediumRepository;

    @Autowired
    private ComicRepository comicRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    private Publisher panini = new Publisher("Panini");

    private Publisher dc = new Publisher("DC Comics");

    private Comic comic;

    private ComicIssue usComic;

    @BeforeAll
    public void setAll(){
        publisherRepository.save(panini);
        publisherRepository.save(dc);
    }

    @BeforeEach
    public void setUp() {
        comic = Comic.builder().title("Batman - Death Metal").number(1).date(YearMonth.of(2021, 3)).publisher(panini).build();
        usComic = ComicIssue.builder().title("Dark Knights - Death Metal").number(1).date(YearMonth.of(2020, 8)).publisher(dc).build();

        mediumRepository.save(comic);
        mediumRepository.save(usComic);
    }


    @AfterEach
    public void tearDown(){
        mediumRepository.deleteAll();
    }

    @AfterAll
    public void tearAll(){
        publisherRepository.deleteAll();
        mediumRepository.deleteAll();
    }

    @Test
    public void addIssuesToComicSet(){
        comic.addMedium(usComic);
        validateResults(comic.getComicIssues(), usComic);
    }


    @Test
    public void removeIssueFromComic(){
        comic.addMedium(usComic);
        assertTrue(validateResults(comic.getComicIssues(), usComic));
        comic.removeMedium(usComic);
        assertFalse(validateResults(comic.getComicIssues(), usComic));

    }


    @Test
    public void getIssuesFromComic(){
        comic.addMedium(usComic);
        assertTrue(validateResults(comic.getComicIssues(), usComic));
        assertTrue(usComic.getId() == comic.getMedium(usComic.getId()).getId());
    }



    @Test
    public void getFromDataBaseComic(){
        Comic comic1 = Comic.builder().title("Batman - Death Metal").number(2).date(YearMonth.of(2021, 4)).publisher(panini).build();
        comic1.addMedium(usComic);
        mediumRepository.save(comic1);
        Optional<Comic> testComic = comicRepository.findComicByIdWithIssues(comic1.getId());
        assertTrue(testComic.get().getComicIssues().contains(usComic));

    }

    private <T extends Medium> boolean validateResults(Set<T> media, T medium ){
        return (!media.isEmpty()) && media.contains(medium);
    }



}



