package sh.platform.example.user.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;


@Controller
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping("/")
    public String start(Model model) {
        model.addAttribute("users", service.findAll());
        return "index";
    }

    @GetMapping("/signup")
    public String showSignUpForm(User user) {
        return "add-user";
    }

    @PostMapping("/adduser")
    public String addUser(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-user";
        }

        service.save(user);
        model.addAttribute("users", service.findAll());
        return "index";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") String id, Model model) {
        User user = service.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        model.addAttribute("user", user);
        return "update-user";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") String id, @Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            user.setId(id);
            return "update-user";
        }

        service.save(user);
        model.addAttribute("users", service.findAll());
        return "index";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") String id, Model model) {
        User user = service.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        service.delete(user);
        model.addAttribute("users", service.findAll());
        return "index";
    }
}