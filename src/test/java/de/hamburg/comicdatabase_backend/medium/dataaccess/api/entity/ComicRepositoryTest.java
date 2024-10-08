package de.hamburg.comicdatabase_backend.medium.dataaccess.api.entity;


import de.hamburg.comicdatabase_backend.ComicDatabaseBackendApplication;
import de.hamburg.comicdatabase_backend.foundation.dataaccess.api.entity.Publisher;
import de.hamburg.comicdatabase_backend.foundation.dataaccess.api.repo.PublisherRepository;
import de.hamburg.comicdatabase_backend.medium.dataaccess.api.entity.impl.Comic;
import de.hamburg.comicdatabase_backend.medium.dataaccess.api.entity.impl.ComicIssue;
import de.hamburg.comicdatabase_backend.medium.dataaccess.api.repo.ComicRepository;
import de.hamburg.comicdatabase_backend.medium.dataaccess.api.repo.ComicIssueRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.YearMonth;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = ComicDatabaseBackendApplication.class)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ComicRepositoryTest {

    @Autowired
    private ComicRepository comicRepository;

    @Autowired
    private ComicIssueRepository usComicRepository;

    @Autowired
    private PublisherRepository publisherRepository;

    private Comic comic;

    private ComicIssue usComic;

    private Publisher panini;

    private Publisher dc;


    @BeforeEach
    public void setUp() {
        panini = new Publisher("Panini");
        dc = new Publisher("DC");
        publisherRepository.save(panini);
        publisherRepository.save(dc);
        comic = Comic.builder().title("All-Superman").publisher(panini).date(YearMonth.of(2011, 2)).build();
        usComic = ComicIssue.builder().title("All-Superman").number(1).publisher(dc).date(YearMonth.of(2006, 1)).build();
        usComicRepository.saveAndFlush(usComic);
        comic.addMedium(usComic);
        comicRepository.saveAndFlush(comic);

    }

    @AfterEach
    public void tearDown(){
        comicRepository.deleteAll();
        usComicRepository.deleteAll();
        publisherRepository.deleteAll();
    }

    @Test
    public void comicWithIssues(){
        Optional<Comic> comicDb = comicRepository.findComicByIdWithIssues(comic.getId());
        assertTrue(comicDb.get().getComicIssues().contains(usComic));
    }

    @Test
    public void usComicWithIssues(){
        Optional<ComicIssue> usComic_Db = usComicRepository.findUS_ComicByIdWithIssues(usComic.getId());
        assertTrue(usComic_Db.get().getComicSet().contains(comic));
    }

    @Test
    public void dynamicDeleting(){
        Optional<Comic> comicDb = comicRepository.findComicByIdWithIssues(comic.getId());
        comicDb.get().removeMedium(usComic);
        assertFalse(comicDb.get().getComicIssues().contains(usComic));
        comicRepository.saveAndFlush(comicDb.get());
        Optional<ComicIssue> usComic_DB = usComicRepository.findUS_ComicByIdWithIssues(usComic.getId());
        assertFalse(usComic_DB.get().getComicSet().contains(comic));
    }
}
