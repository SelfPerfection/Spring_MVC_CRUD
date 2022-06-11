package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import web.model.User;
import web.service.SiteService;
import web.service.UserService;

import java.util.List;

@Controller
public class UserController {

    private UserService userService;
    private SiteService siteService;

    @Autowired
    public UserController(UserService userService, SiteService siteService) {
        this.userService = userService;
        this.siteService = siteService;
    }

    @RequestMapping("/")
    public String index(ModelMap model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/add_user")
    public String addUserForm(ModelMap model) {
        model.addAttribute("user", new User());
        return "add_user";
    }

    @PostMapping("/add_user")
    public String saveUser(@ModelAttribute User user, ModelMap model) {
        userService.createUser(user);
        model.addAttribute("user", user);
        return ("redirect:/edit/" + user.getId());
    }

    @GetMapping("/edit/{id}")
    public String editUserForm(@PathVariable("id") int id, ModelMap model) {
        model.addAttribute("user", userService.getUserById(id));
        return "edit_user";
    }

    @PostMapping("/edit_user")
    public String editUser(@ModelAttribute User user, ModelMap model) {
        userService.updateUser(user);
        model.addAttribute("user", user);
        return ("redirect:/edit/" + user.getId());
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") int id, ModelMap model) {
        userService.deleteUser(id);
        return "redirect:/";
    }

    @RequestMapping("/create_users")
    public String createUsers(ModelMap model) {
        userService.createUser(new User("Albert", "Mukhametzianov", "a.mukhametzianov@bk.ru", 27));
        userService.createUser(new User("Robert", "Axelrod", "r.axelrod@axe.capital", 43));
        userService.createUser(new User("James", "Bond", "james007@bond.com", 38));
        userService.createUser(new User("Clark", "Kent", "clark-kent@gmail.com", 35));
        userService.createUser(new User("Bruce", "Wayne", "Wayne@brucewayne.corp", 41));
        userService.createUser(new User("Lex", "Lutor", "lex@lutor.org", 39));
        userService.createUser(new User("Александр", "Пушкин", "alex@pushkin.ru", 37));
        userService.createUser(new User("Николай", "Гоголь", "nikolagogol@liter.ru", 42));

        return "redirect:/";
    }

    /*
     * Service Method to check POST data
     *
    @PostMapping("/add_user")
    @ResponseBody
    public String saveUser(@RequestParam Map<String,String> allParams) {
        return "Parameters are " + allParams.entrySet();
    }
    */

}
