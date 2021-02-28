package com.example.my_bullet_journal.web;

import com.example.my_bullet_journal.models.bindings.UserRegisterBindingModel;
import com.example.my_bullet_journal.models.services.UserRegisterServiceModel;
import com.example.my_bullet_journal.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@RequestMapping("/users")
@Controller
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/login")
    public String showLogin(){

        return "login";

    }

    @PostMapping("/register")
    public String register(@Valid UserRegisterBindingModel userRegisterBindingModel,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors() || !userRegisterBindingModel.getConfirmedPassword().equals(userRegisterBindingModel.getConfirmedPassword())) {
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBindingModel", bindingResult);
            if (!userRegisterBindingModel.getConfirmedPassword().equals(userRegisterBindingModel.getConfirmedPassword())) {
                redirectAttributes.addAttribute("not matching", true);
            }
            return "redirect:register";
        }
        else{
            this.userService.register(this.modelMapper.map(userRegisterBindingModel, UserRegisterServiceModel.class));
            return "redirect:login";
        }

    }

    @GetMapping("/register")
    public String showRegister(Model model){
        if(!model.containsAttribute("userRegisterBindingModel")){
            model.addAttribute("userRegisterBindingModel", new UserRegisterBindingModel());
        }

        return "register";

    }
}
