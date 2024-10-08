package de.hamburg.comicdatabase_backend.database_persistence;


import de.hamburg.comicdatabase_backend.ComicDatabaseBackendApplication;

import de.hamburg.comicdatabase_backend.foundation.dataaccess.api.entity.Publisher;
import de.hamburg.comicdatabase_backend.foundation.dataaccess.api.repo.PublisherRepository;
import de.hamburg.comicdatabase_backend.medium.dataaccess.api.entity.impl.Comic;
import de.hamburg.comicdatabase_backend.medium.dataaccess.api.entity.impl.Medium;
import de.hamburg.comicdatabase_backend.medium.dataaccess.api.entity.impl.ComicIssue;
import de.hamburg.comicdatabase_backend.medium.dataaccess.api.repo.MediumRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.*;

import java.time.YearMonth;
import java.util.List;

@SpringBootTest(classes = ComicDatabaseBackendApplication.class)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MediumDatabaseTest {

    @Autowired
    private MediumRepository<Medium> mediumRepository;
    @Autowired
    private PublisherRepository publisherRepository;

    private Publisher publisher;
    private Publisher publisher1;


    @BeforeEach
    public void setUp(){
        publisher = new Publisher("DC Comics");
        publisher1 = new Publisher("Panini Comics");
        publisherRepository.save(publisher);
        publisherRepository.save(publisher1);
    }

    @AfterEach
    public void tearDown(){
        mediumRepository.deleteAll();
        publisherRepository.deleteAll();
    }

    @Test
    public void saveComic(){
        Comic comic = Comic.builder().title("Batman").build();
        mediumRepository.save(comic);
        assertFalse(mediumRepository.findByTitle(comic.getTitle()).isEmpty());
        assertTrue(mediumRepository.findByTitle(comic.getTitle()).stream().anyMatch(comicIssue -> comicIssue.getTitle().equals(comic.getTitle())));
    }

    @Test
    public void saveUS_Comics(){
        ComicIssue usComic = ComicIssue.builder().title("Batman").number(210).build();
        mediumRepository.save(usComic);
        assertFalse(mediumRepository.findByTitle(usComic.getTitle()).isEmpty());
        assertTrue(mediumRepository.findByTitle(usComic.getTitle()).stream().anyMatch(comicIssue -> comicIssue.getTitle().equals(usComic.getTitle())));
    }

    @Test
    public void duplicateNumber_Success(){
        Comic comic = Comic.builder().title("Batman - Eternal").number(1).date(YearMonth.of(2015, 1)).publisher(publisher1).build();
        ComicIssue usComic = ComicIssue.builder().title("Batman - Eternal").number(1).date(YearMonth.of(2014, 6)).publisher(publisher).build();

        mediumRepository.save(comic);
        mediumRepository.save(usComic);

        assertTrue(mediumRepository.findByTitle("Batman - Eternal").size() > 1);

    }

    @Test
    public void identicalComicAndUS_Comic_Success(){
        Comic comic = Comic.builder().title("Batman - Eternal").number(1).date(YearMonth.of(2015, 1)).publisher(publisher).build();
        ComicIssue usComic = ComicIssue.builder().title("Batman - Eternal").number(1).date(YearMonth.of(2015, 1)).publisher(publisher).build();

        mediumRepository.save(comic);
        mediumRepository.save(usComic);

        assertTrue(mediumRepository.findByTitle("Batman - Eternal").size() > 1);
        assertTrue(checkData(mediumRepository.findByTitle("Batman - Eternal")));

    }
    @Test
    public void identicalComics_Fail(){
        Comic comic = Comic.builder().title("Batman - Eternal").number(1).date(YearMonth.of(2015, 1)).publisher(publisher).build();
        Comic comic1 = Comic.builder().title("Batman - Eternal").number(1).date(YearMonth.of(2015, 1)).publisher(publisher).build();

        mediumRepository.save(comic);
        assertTrue(mediumRepository.findById(comic.getId()).isPresent());
        assertTrue(comic.getId() != comic1.getId());

        assertThrows(DataIntegrityViolationException.class, () -> {
            mediumRepository.save(comic1);
            mediumRepository.flush();  // um den Fehler sofort auszulösen
        });
    }


    private boolean checkData(List<Medium> mediumList){
        boolean comic = false;
        boolean usComic = false;

        for(Medium medium : mediumList){
            if(medium instanceof Comic){
                comic = true;
            } else if(medium instanceof ComicIssue){
                usComic = true;
            }
        }
        return comic && usComic;
    }
}
