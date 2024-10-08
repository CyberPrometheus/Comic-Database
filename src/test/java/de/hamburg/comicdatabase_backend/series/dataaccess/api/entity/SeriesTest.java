package de.hamburg.comicdatabase_backend.series.dataaccess.api.entity;


import de.hamburg.comicdatabase_backend.ComicDatabaseBackendApplication;
import de.hamburg.comicdatabase_backend.foundation.common.api.YearOptionalMonth;
import de.hamburg.comicdatabase_backend.foundation.dataaccess.api.entity.Publisher;
import de.hamburg.comicdatabase_backend.foundation.dataaccess.api.repo.PublisherRepository;
import de.hamburg.comicdatabase_backend.medium.dataaccess.api.entity.impl.Comic;
import de.hamburg.comicdatabase_backend.medium.dataaccess.api.repo.ComicRepository;
import de.hamburg.comicdatabase_backend.medium.dataaccess.api.repo.MediumRepository;
import de.hamburg.comicdatabase_backend.series.common.api.PublicationPeriod;
import de.hamburg.comicdatabase_backend.series.dataaccess.api.repo.SeriesRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.YearMonth;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = ComicDatabaseBackendApplication.class)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SeriesTest {

    @Autowired
    private MediumRepository<Comic> mediumRepository;
    @Autowired
    private ComicRepository comicRepository;
    @Autowired
    private SeriesRepository<Comic> seriesSeriesRepository;
    @Autowired
    private PublisherRepository publisherRepository;

    private Publisher publisher;
    private Comic comic;
    private Series<Comic> series;

    @BeforeEach
    public void setUp(){
        publisher = new Publisher("Panini");
        publisherRepository.save(publisher);
        series = new Series<>("Batman - Eternal", publisher,
                new PublicationPeriod(YearOptionalMonth.of(2012, 04), YearOptionalMonth.of(2015,10)));
        seriesSeriesRepository.saveAndFlush(series);

        comic = Comic.builder().title("Batman - Eternal").number(1).date(YearMonth.of(2012, 4)).publisher(publisher).build();
        comic.setSeries(series);
        comicRepository.saveAndFlush(comic);



    }

    @AfterEach
    public void tearDown(){
        seriesSeriesRepository.deleteAll();
        comicRepository.deleteAll();
        publisherRepository.deleteAll();
    }


    @Test
    public void addComicToSeries(){
        Optional<Comic> comicDb = comicRepository.findById(comic.getId());
        assertEquals(comicDb.get().getSeries().getId(), series.getId());
    }

    @Test
    public void querySeriesForComic(){
        Optional<Comic> comicDb = comicRepository.findById(comic.getId());
        assertTrue(comicDb.get().getSeries() != null);

        Optional<Series<Comic>> comicsSeries = seriesSeriesRepository.findById(series.getId());
        assertFalse(comicsSeries.get().getMedia().isEmpty());
        assertTrue(comicsSeries.get().getMedia().contains(comicDb.get()));
        Optional<Series<Comic>> series1 = seriesSeriesRepository.findSeriesWithMedia(series.getId());
        assertTrue(series1.isPresent());
        assertFalse(series1.get().getMedia().isEmpty());
        assertTrue(series1.get().getMedia().contains(comicDb.get()));
    }


}
