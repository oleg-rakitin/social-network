package com.course.social.controller;

import com.course.social.domain.FeedBack;
import com.course.social.domain.Message;
import com.course.social.domain.Role;
import com.course.social.domain.User;
import com.course.social.domain.dto.MessageDto;
import com.course.social.repos.FeedBackRepo;
import com.course.social.repos.MessageRepo;
import com.course.social.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Controller
public class MessageController {
    @Autowired
    private MessageRepo messageRepo;

    @Autowired
    private MessageService messageService;

    @Autowired
    private FeedBackRepo feedBackRepo;

    @Value("${upload.path}")
    private String uploadPath;


    @GetMapping("/error")
    public String error(Map<String, Object> model){
        return "redirect:/home";
    }

    @GetMapping("/home")
    public String home(Map<String,Object> model,
                       @AuthenticationPrincipal User user){
        if (user != null) {
            model.put("username", user.getUsername());
            model.put("user", user);
        } else {
            model.put("username","гость");
        }
        return "greeting";
    }

    @GetMapping("/feedback")
    public String feedback(Map<String,Object> model,
                           @AuthenticationPrincipal User user,
                           @RequestParam("name") String name,
                           @RequestParam("email") String email,
                           @RequestParam("phone") String phone,
                           @RequestParam("message") String message) {

        System.out.println(name);
        System.out.println(message);
        if (user != null) {
            model.put("username", user.getUsername());
        } else {
            model.put("username", "гость");
        }

        if(name.equals("")||email.equals("")||phone.equals("")||message.equals("")) {
            model.put("errorFeedback", "ERROR!");
            return "greeting";
        }

        FeedBack feedBack = new FeedBack();
        feedBack.setName(name);
        feedBack.setPhone(phone);
        feedBack.setEmail(email);
        feedBack.setMessage(message);

        feedBackRepo.save(feedBack);

        model.put("errorFeedback","SUCCESS");

        return "redirect:/";
    }

    @GetMapping("/")
    public String greeting(Map<String, Object> model,
                           @AuthenticationPrincipal User user) {
        if (user != null) {
            model.put("username", user.getUsername());
            model.put("user", user);
            return "greeting";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/main")
    public String main(
            @RequestParam(required = false, defaultValue = "") String filter,
            Model model,
            @PageableDefault(sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable,
            @AuthenticationPrincipal User user
    ) {
        Page<MessageDto> page = messageService.messageList(pageable, filter, user);

        model.addAttribute("page", page);
        model.addAttribute("url", "/main");
        model.addAttribute("filter", filter);
        model.addAttribute("user", user);

        return "main";
    }

    @PostMapping("/main")
    public String add(
            @AuthenticationPrincipal User user,
            @Valid Message message,
            BindingResult bindingResult,
            Model model,
            @RequestParam(required = false, defaultValue = "") String filter,
            @PageableDefault(sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        message.setAuthor(user);

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);

            model.mergeAttributes(errorsMap);
            model.addAttribute("message", message);
        } else {
            saveFile(message, file);

            model.addAttribute("message", null);

            messageRepo.save(message);
        }

        Page<MessageDto> page = messageService.messageList(pageable, filter, user);
        model.addAttribute("page", page);

        return "main";
    }

    private void saveFile(@Valid Message message, @RequestParam("file") MultipartFile file) throws IOException {
        if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));

            message.setFilename(resultFilename);
        }
    }

    @GetMapping("/user-messages/{author}")
    public String userMessges(
            @AuthenticationPrincipal User currentUser,
            @PathVariable User author,
            Model model,
            @RequestParam(required = false) Message message,
            @PageableDefault(sort = { "id" }, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<MessageDto> page = messageService.messageListForUser(pageable, currentUser, author);

        model.addAttribute("userChannel", author);
        model.addAttribute("subscriptionsCount", author.getSubscriptions().size());
        model.addAttribute("subscribersCount", author.getSubscribers().size());
        model.addAttribute("isSubscriber", author.getSubscribers().contains(currentUser));
        model.addAttribute("page", page);
        model.addAttribute("message", message);
        model.addAttribute("isCurrentUser", currentUser.equals(author));
        model.addAttribute("url", "/user-messages/" + author.getId());
        model.addAttribute("user", currentUser);

        return "userMessages";
    }

    @PostMapping("/user-messages/{user}")
    public String updateMessage(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Long user,
            @RequestParam("id") Message message,
            @RequestParam("text") String text,
            @RequestParam("tag") String tag,
            @RequestParam("file") MultipartFile file
    ) throws IOException {
        if (message.getAuthor().equals(currentUser)
                ||currentUser.getRoles().contains(Role.ADMIN)) {
            if (!StringUtils.isEmpty(text)) {
                message.setText(text);
            }

            if (!StringUtils.isEmpty(tag)) {
                message.setTag(tag);
            }

            saveFile(message, file);

            messageRepo.save(message);
        }

        return "redirect:/user-messages/" + user;
    }

    @GetMapping("/messages/{message}/like")
    public String like(
            @AuthenticationPrincipal User currentUser,
            @PathVariable Message message,
            RedirectAttributes redirectAttributes,
            @RequestHeader(required = false) String referer
    ) {
        Set<User> likes = message.getLikes();

        if (likes.contains(currentUser)) {
            likes.remove(currentUser);
        } else {
            likes.add(currentUser);
        }

        UriComponents components = UriComponentsBuilder.fromHttpUrl(referer).build();

        components.getQueryParams()
                .entrySet()
                .forEach(pair -> redirectAttributes.addAttribute(pair.getKey(), pair.getValue()));

        return "redirect:" + components.getPath();
    }
}
