package de.hamburg.comicdatabase_backend.database_persistence;


import de.hamburg.comicdatabase_backend.ComicDatabaseBackendApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = ComicDatabaseBackendApplication.class)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TempDirTest {

    private String actualStoragePath;

    @TempDir
    private Path tmpDirectory;

    @BeforeEach
    public void setUp(){
        System.setProperty("comic.cover.path", tmpDirectory.toString());
        actualStoragePath = System.getProperty("comic.cover.path");

    }



    @Test
    public void testTempDir() throws IOException {
        assertEquals(tmpDirectory.toString(), actualStoragePath);

        Path coverPath = Path.of(actualStoragePath, "cover.jpg");
        Files.write(coverPath, "dummy image content".getBytes());

        assertTrue(Files.exists(coverPath));

    }
}
