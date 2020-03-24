package com.course.social.controller;

import com.course.social.domain.Message;
import com.course.social.domain.User;
import com.course.social.repos.UserRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.ConfigurableMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.MockMvcConfigurerAdapter;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql", "/messages-list-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/messages-list-after.sql", "/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@WithUserDetails(value = "dru")
public class MessageControllerTest {

    @Autowired
    WebApplicationContext webApplicationContext;
    @Autowired
    MessageController messageController;
    @Autowired
    UserRepo userRepo;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void likeTest() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(new HttpHeaderMockMvcConfigurer()).build();
        this.mockMvc.perform(get("/messages/{message}/like", 2)
                .header("test", "test").param("referer", "test").with(csrf()))
                .andDo(print())
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void greetingTest() throws Exception {
        this.mockMvc.perform(get("/").with(csrf()))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void home() {
        assertEquals("greeting", messageController.home(new HashMap<>(), null));
    }

    @Test
    public void add() throws IOException {
        Model model = new ExtendedModelMap();
        Pageable pageable = Pageable.unpaged();
        MultipartFile multipartFile = new MockMultipartFile("file", "123".getBytes());
        Object target = null;
        User user = userRepo.findByUsername("dru");
        BindingResult bindingResult = new BeanPropertyBindingResult(target, "test");
        bindingResult.addError(new ObjectError("error", "error"));
        messageController.add(user, new Message(), bindingResult, model, "ff", pageable, multipartFile);

        BindingResult bindingResult1 = new BeanPropertyBindingResult(target, "test");
        Message message = new Message();
        message.setText("132");
        messageController.add(user, message, bindingResult1, model, "ff", pageable, null);

        MultipartFile multipartFile1 = new MockMultipartFile("1", "".getBytes());
        messageController.add(user, message, bindingResult1, model, "ff", pageable, multipartFile1);


    }

    @Test
    public void feedback1() {
        assertEquals("greeting", messageController.feedback(new HashMap<>(), null, "", "",
                "", ""));
    }

    @Test
    public void feedback2() {
        assertEquals("greeting", messageController.feedback(new HashMap<>(), null, "1", "",
                "", ""));
    }

    @Test
    public void feedback3() {
        assertEquals("greeting", messageController.feedback(new HashMap<>(), null, "", "1",
                "", ""));
    }

    @Test
    public void feedback4(){
        assertEquals("greeting", messageController.feedback(new HashMap<>(), null, "", "",
                "1", ""));
    }

    @Test
    public void feedback5(){
        assertEquals("greeting", messageController.feedback(new HashMap<>(), null, "", "",
                "", "1"));
    }

    @Test
    public void like(){
        User user = new User();
        Set<User> likes = new HashSet<>();
        likes.add(user);
        Message message = new Message();
        message.setLikes(likes);
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        messageController.like(user,message,redirectAttributes,"http://localhost:8080/main");
    }

    public class HttpHeaderMockMvcConfigurer extends MockMvcConfigurerAdapter {
        @Override
        public RequestPostProcessor beforeMockMvcCreated(ConfigurableMockMvcBuilder<?> builder, WebApplicationContext cxt) {
            builder.defaultRequest(MockMvcRequestBuilders.post("test").header("appId", "aaa"));
            return super.beforeMockMvcCreated(builder, cxt);
        }
    }
}
