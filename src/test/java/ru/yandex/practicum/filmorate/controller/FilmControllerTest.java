package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FilmController.class)
public class FilmControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Test add film")
    public void addFilmTest() throws Exception {
        mockMvc.perform(post("/api/film")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Test name\", " +
                                "\"description\": \"Test description\", " +
                                "\"releaseDate\": \"1900-03-14\"," +
                                " \"duration\": \"01:10:11\"}"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").hasJsonPath())
                .andExpect(jsonPath("$.name").value("Test name"))
                .andExpect(jsonPath("$.description").value("Test description"))
                .andExpect(jsonPath("$.releaseDate").value("1900-03-14"))
                .andExpect(jsonPath("$.duration").value("01:10:11"));

    }


    @Test
    @DisplayName("Test Valid date")
    public void addFilmCheckValidDateTest() throws Exception {
        mockMvc.perform(post("/api/film")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Test name\", " +
                                "\"description\": \"Test description\", " +
                                "\"releaseDate\": \"1800-03-14\"," +
                                " \"duration\": \"01:10:11\"}"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("Test valid name")
    public void addFilmCheckValidNameTest() throws Exception {
        mockMvc.perform(post("/api/film")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"\", " +
                                "\"description\": \"Test description\", " +
                                "\"releaseDate\": \"1900-03-14\"," +
                                " \"duration\": \"01:10:11\"}"))
                .andExpect(status().is4xxClientError());

    }

    @Test
    @DisplayName("Test valid duration")
    public void addFilmCheckValidDurationTest() throws Exception {
        mockMvc.perform(post("/api/film")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Test name\", " +
                                "\"description\": \"Test description\", " +
                                "\"releaseDate\": \"1950-03-14\"," +
                                " \"duration\": \"00:00:00\"}"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("Test valid description")
    public void addFilmCheckValidDescriptionTest() throws Exception {
        mockMvc.perform(post("/api/film")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Test name\", " +
                                "\"description\": \"Test description 200 aaaaaaaaaaaaaa" +
                                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                                "aaaaaaaaaaaaaaaaaaaaaaaa\", " +
                                "\"releaseDate\": \"1950-03-14\"," +
                                " \"duration\": \"00:00:12\"}"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("Test update film")
    public void updateFilmTest() throws Exception {
        mockMvc.perform(put("/api/updateFilm")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, " +
                                "\"name\": \"Updated name\", " +
                                "\"description\": \"Updated description\", " +
                                "\"releaseDate\": \"1900-11-21\"," +
                                " \"duration\": \"10:10:10\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Updated name"))
                .andExpect(jsonPath("$.description").value("Updated description"))
                .andExpect(jsonPath("$.releaseDate").value("1900-11-21"))
                .andExpect(jsonPath("$.duration").value("10:10:10"));
    }
}
