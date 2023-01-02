package pl.mw.restapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import pl.mw.restapi.model.Post;
import pl.mw.restapi.repository.PostRepository;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// testy integracyjne - do testowania JWT uzywanie usera Moc

@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser //(username="")
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PostRepository postRepository;

    @Test
    @Transactional // unikniecie zasmiecania bazy danych
    void shouldGetSinglePost() throws Exception {
        // given
        // setup
        Post newPost=new Post();
        newPost.setTitle("Test");
        newPost.setContent("content");
        newPost.setCreated(LocalDateTime.now());
        postRepository.save(newPost);
        // when
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/posts/"+newPost.getId()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is(200)) // .andExpect(MockMvcResultMatchers.status().is(200)); -> statyczny import Alt+Enter
                //.andExpect(jsonPath("$.id", Matchers.is(1))) // opcja 1 - sprawdzanie pojedynczej wartosci z json
                .andReturn() // opcja 2 - wywolanie metody wg stringa
        ;
        // then
        Post post=objectMapper.readValue(mvcResult.getResponse().getContentAsString(), Post.class);
        assertThat(post).isNotNull();
        assertThat(post.getId()).isEqualTo(newPost.getId());
        //assertThat(post.getTitle()).isEqualTo("Test");
        //assertThat(post.getComment()).hasSize(9);
    }
}