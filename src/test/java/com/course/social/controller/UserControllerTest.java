package com.course.social.controller;

import com.course.social.domain.Role;
import com.course.social.domain.User;
import com.course.social.repos.UserRepo;
import com.course.social.service.UserSevice;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
public class UserControllerTest {

    @Mock
    UserSevice userSevice;
    @MockBean
    PasswordEncoder passwordEncoder;
    @Mock
    UserRepo userRepo;
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
    public void userList() throws Exception {
        this.mockMvc.perform(get("/user"))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void userEditForm() throws Exception {
        User user = new User();
        user.setUsername("dru");
        Set<Role> set = new HashSet<>();
        set.add(Role.USER);
        user.setRoles(set);

        this.mockMvc.perform(get("/user/{user}", 1).content(asJsonString(user)))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void saveUsers() throws Exception {
        this.mockMvc.perform(get("/user/save"))
                .andDo(print()).andExpect(authenticated()).andExpect(redirectedUrl("/user"))
                .andExpect(status().is3xxRedirection());

    }

    @Test
    public void userSave() throws Exception {
        this.mockMvc.perform(post("/user").param("username", "dru")
                .param("form", "10").param("userId", "1").with(csrf()))
                .andDo(print()).andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/user"));
    }

    @Test
    public void getProfile() throws Exception {
        this.mockMvc.perform(get("/user/profile"))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void updateProfile() throws Exception {
        this.mockMvc.perform(post("/user/profile").param("oldPassword", "oldPass")
                .param("newPassword", "newPassword").param("email", "email").with(csrf()))
                .andDo(print()).andExpect(status().isOk());
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(passwordEncoder.encode(anyString())).thenReturn("test");
        this.mockMvc.perform(post("/user/profile").param("oldPassword", "$2a$08$fNUHI3FnO3cbT6VAcClJOOsIq93f2101ud2RAKiZFAh7Y2h")
                .param("newPassword", "$2a$08$fNUHI3FnO3cbT6VAcClJOOsIq93f2101ud2RAKiZFAh7Y2h").param("email", "test@test").with(csrf()))
                .andDo(print()).andExpect(status().is3xxRedirection());

    }

    @Test
    public void subscribe() throws Exception {
        this.mockMvc.perform(get("/user/subscribe/{user}", 2))
                .andDo(print()).andExpect(redirectedUrl("/user-messages/2"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    public void unsubscribe() throws Exception {
        this.mockMvc.perform(get("/user/subscribe/{user}", 2));
        this.mockMvc.perform(get("/user/unsubscribe/{user}", 2))
                .andExpect(redirectedUrl("/user-messages/2"))
                .andExpect(status().is3xxRedirection());
        ;
    }

    @Test
    public void testUserList() throws Exception {
        this.mockMvc.perform(get("/user/{type}/{user}/list", "subscriptions", "1"))
                .andDo(print()).andExpect(status().isOk());

        this.mockMvc.perform(get("/user/{type}/{user}/list", "subscribers", "2"))
                .andDo(print()).andExpect(status().isOk());
    }
}
