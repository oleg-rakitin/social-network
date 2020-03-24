package com.course.social.controller;

import com.course.social.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.validation.BindingResult;

import java.nio.charset.StandardCharsets;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql", "/messages-list-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/messages-list-after.sql", "/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@WithUserDetails(value = "dru")
public class MainControllerTest {
    @Autowired
    private MessageController controller;

    @Autowired
    private MockMvc mockMvc;

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void errorPageTest() throws Exception {
        this.mockMvc.perform(get("/error"))
                .andDo(print())
                .andExpect(redirectedUrl("/home"));
    }

    @Test
    public void homeTest() throws Exception {
        this.mockMvc.perform(get("/home"))
                .andDo(print());
    }

    @Test
    public void feedBackTest() throws Exception {
        this.mockMvc.perform(get("/feedback").param("name", "fifth")
                .param("email", "email@email.ru").param("phone", "8-888-888-88-88")
                .param("message", "messagemessage"))
                .andDo(print()).andExpect(authenticated()).andExpect(redirectedUrl("/"));
    }

    @Test
    public void feedBackTestErrorTest() throws Exception {
        this.mockMvc.perform(get("/feedback").param("name", "fifth")
                .param("email", "").param("phone", "")
                .param("message", "messagemessage"))
                .andDo(print()).andExpect(authenticated()).andExpect(redirectedUrl(null));
    }

    @Test
    public void userMessages() throws Exception {
        User user = new User();
        user.setUsername("dru");
        this.mockMvc.perform(get("/user-messages/1")).andDo(print());
    }

    @Test
    public void userMessage1() throws Exception{
        MockMultipartFile file =
                new MockMultipartFile(
                        "file",
                        "contract.pdf",
                        MediaType.APPLICATION_PDF_VALUE,
                        "<<pdf data>>".getBytes(StandardCharsets.UTF_8));

        this.mockMvc.perform(MockMvcRequestBuilders.multipart("/user-messages/1" ).file(file)
                .param("id","2")
                .param("text","text").param("tag","tag").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/user-messages/1"));

        this.mockMvc.perform(MockMvcRequestBuilders.multipart("/user-messages/2" ).file(file)
                .param("id","3")
                .param("text","").param("tag","").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/user-messages/2"));

        this.mockMvc.perform(MockMvcRequestBuilders.multipart("/user-messages/1" ).file(file)
                .param("id","4")
                .param("text","").param("tag","").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/user-messages/1"));

    }

    @Test
    @WithUserDetails(value = "mike")
    public void userMessagesMike() throws Exception {
        MockMultipartFile file =
                new MockMultipartFile(
                        "file",
                        "contract.pdf",
                        MediaType.APPLICATION_PDF_VALUE,
                        "<<pdf data>>".getBytes(StandardCharsets.UTF_8));

        this.mockMvc.perform(MockMvcRequestBuilders.multipart("/user-messages/1" ).file(file)
                .param("id","1")
                .param("text","").param("tag","").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print()).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/user-messages/1"));
    }



    @Test
    public void mainPageTest() throws Exception {
        this.mockMvc.perform(get("/main"))
                .andDo(print())
                .andExpect(authenticated());
        // .andExpect(xpath("//*[@id='navbarSupportedContent']/div").string("dru"));
    }

    @Test
    public void messageListTest() throws Exception {
        this.mockMvc.perform(get("/main"))
                .andDo(print())
                .andExpect(authenticated()).andExpect(status().isOk());
                //.andExpect(xpath("//*[@id='message-list']/div").nodeCount(4));
    }

    @Test
    public void filterMessageTest() throws Exception {
        this.mockMvc.perform(get("/main").param("filter", "my-tag"))
                .andDo(print())
                .andExpect(authenticated()).andExpect(status().isOk());
           /*     .andExpect(xpath("//*[@id='message-list']/div").nodeCount(2))
                .andExpect(xpath("//*[@id='message-list']/div[@data-id='1']").exists())
                .andExpect(xpath("//*[@id='message-list']/div[@data-id='3']").exists());*/
    }

    @MockBean
    private BindingResult bindingResult;

    @Test
    public void addMessageToListTest() throws Exception {

        MockMultipartFile file =
                new MockMultipartFile(
                        "file",
                        "contract.pdf",
                        MediaType.APPLICATION_PDF_VALUE,
                        "<<pdf data>>".getBytes(StandardCharsets.UTF_8));

        MockHttpServletRequestBuilder multipart = multipart("/main")
                .file( file)
                .param("text", "fifth")
                .param("tag", "new one")
                .with(csrf());

        this.mockMvc.perform(multipart)
                .andDo(print())
                .andExpect(authenticated()).andExpect(status().isOk());
/*                .andExpect(xpath("//*[@id='message-list']/div").nodeCount(5))
                .andExpect(xpath("//*[@id='message-list']/div[@data-id='10']").exists())
                .andExpect(xpath("//*[@id='message-list']/div[@data-id='10']/div/span").string("fifth"))
                .andExpect(xpath("//*[@id='message-list']/div[@data-id='10']/div/i").string("#new one"));*/
    }
}
