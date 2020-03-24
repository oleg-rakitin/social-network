package com.course.social.controller;

import com.course.social.domain.Role;
import com.course.social.domain.User;
import com.course.social.domain.dto.CaptchaResponseDto;
import com.course.social.service.UserSevice;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql", "/messages-list-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/messages-list-after.sql", "/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    RestTemplate restTemplate;

    @Autowired
    RegistrationController registrationController;

    @MockBean
    UserSevice userSevice;

    @Test
    public void registration() throws Exception {
        this.mockMvc.perform(get("/registration"))
                .andDo(print()).andExpect(status().isOk());
    }

    //@MockBean
    //private UserRepo userRepo;

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
    public void registrationPost() throws Exception {
        User user = new User();
        user.setEmail("test");
        user.setPassword("tewtw");
        Set<Role> roles = new HashSet<>();
        roles.add(Role.USER);
        user.setRoles(roles);

        CaptchaResponseDto captchaResponseDto1fa = new CaptchaResponseDto();
        captchaResponseDto1fa.setSuccess(true);
        when(restTemplate.postForObject(anyString(),ArgumentMatchers.any(),ArgumentMatchers.any())).thenReturn(captchaResponseDto1fa);

        when(userSevice.addUser(ArgumentMatchers.any())).thenReturn(false);
        this.mockMvc.perform(post("/registration").param("password2","$2a$08$fNUHI3FnO3cbT6VAcClJOOsIq93f2101ud2RAKiZFAh7Y2h.oFRzC")
                .param("g-recaptcha-response","g-recaptcha-response").param("user","2").with(csrf()))
                .andDo(print()).andExpect(status().isOk());
        this.mockMvc.perform(post("/registration").param("password2","")
                .param("g-recaptcha-response","g-recaptcha-response").param("user","1").with(csrf()))
                .andDo(print()).andExpect(status().isOk());

        when(userSevice.addUser(ArgumentMatchers.any())).thenReturn(true);
        this.mockMvc.perform(post("/registration").param("password2","$2a$08$fNUHI3FnO3cbT6VAcClJOOsIq93f2101ud2RAKiZFAh7Y2h.oFRzC")
                .param("g-recaptcha-response","g-recaptcha-response").param("user","2").with(csrf()))
                .andDo(print()).andExpect(status().is3xxRedirection());

    }

    @Test
    public void activate() throws Exception {
        when(userSevice.activateUser("dru_activate")).thenReturn(true);
        when(userSevice.activateUser("dru_activate1")).thenReturn(false);
        this.mockMvc.perform(get("/activate/{code}","dru_activate").with(csrf()))
                .andDo(print()).andExpect(status().isOk());

        this.mockMvc.perform(get("/activate/{code}","dru_activate1").with(csrf()))
                .andDo(print()).andExpect(status().isOk());
    }

    @Test
    public void addUser(){
        CaptchaResponseDto captchaResponseDto = new CaptchaResponseDto();
        captchaResponseDto.setSuccess(true);
        when(restTemplate.postForObject(anyString(),ArgumentMatchers.any(),ArgumentMatchers.any())).thenReturn(captchaResponseDto);
        Model model =  new ExtendedModelMap();
        User user = new User();
        user.setPassword(null);
        registrationController.addUser("","123",new User(), new BeanPropertyBindingResult("te","te"),model);

        CaptchaResponseDto captchaResponseDto1fa = new CaptchaResponseDto();
        captchaResponseDto1fa.setSuccess(false);
        when(restTemplate.postForObject(anyString(),ArgumentMatchers.any(),ArgumentMatchers.any())).thenReturn(captchaResponseDto1fa);
        User user1 = new User();
        user1.setPassword("122");
        registrationController.addUser("12","123",user1, new BeanPropertyBindingResult("te","te"),model);

        User user2 = new User();
        user2.setPassword("122");
        user2.setEmail("22@test");
        user2.setUsername("test");
        registrationController.addUser("122","123",user2, new BeanPropertyBindingResult("te","te"),model);

        CaptchaResponseDto captchaResponseDto1fa1 = new CaptchaResponseDto();
        captchaResponseDto1fa1.setSuccess(true);
        BindingResult bindingResult =  new BeanPropertyBindingResult("te","te");
        //bindingResult.addError(new ObjectError("f","f"));
        when(restTemplate.postForObject(anyString(),ArgumentMatchers.any(),ArgumentMatchers.any())).thenReturn(captchaResponseDto1fa1);
        registrationController.addUser("122","123",user2,bindingResult,model);

//        registrationController.addUser("123","123",user2,bindingResult,model);

    }

    @Test
    public void reg(){
        Model model =  new ExtendedModelMap();

        CaptchaResponseDto captchaResponseDto1fa = new CaptchaResponseDto();
        captchaResponseDto1fa.setSuccess(true);
        BindingResult bindingResult =  new BeanPropertyBindingResult("te","te");
        //bindingResult.addError(new ObjectError("f","f"));
        when(restTemplate.postForObject(anyString(),ArgumentMatchers.any(),ArgumentMatchers.any())).thenReturn(captchaResponseDto1fa);

        User user2 = new User();
        user2.setPassword("123");
        user2.setEmail("22@test");
        user2.setUsername("test");
        registrationController.addUser("123","123",user2,bindingResult,model);

    }


}
