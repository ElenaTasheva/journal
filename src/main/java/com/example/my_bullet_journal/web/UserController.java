package com.example.my_bullet_journal.web;

import com.example.my_bullet_journal.models.bindings.UserRegisterBindingModel;
import com.example.my_bullet_journal.models.services.UserRegisterServiceModel;
import com.example.my_bullet_journal.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
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

      @ModelAttribute("registrationBindingModel")
       public UserRegisterBindingModel createBindingModel() {
         return new UserRegisterBindingModel();
      }

  @PostMapping("/login-error")
  @PreAuthorize("isAnonymous()")
    public String failedLogin(@ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
        String username, RedirectAttributes attributes) {


        attributes.addFlashAttribute("bad_credentials", true);
        attributes.addFlashAttribute("email", username);


        return "redirect:/users/login";

    }



    @PostMapping("/register")
    public String register(@Valid UserRegisterBindingModel userRegisterBindingModel,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userRegisterBindingModel", bindingResult);
            redirectAttributes.addFlashAttribute("userExistsError", false);
            return "redirect:register";
        }

         try {
             userService.registerAndLogin(this.modelMapper.map(userRegisterBindingModel, UserRegisterServiceModel.class));
         } catch (IllegalArgumentException ex) {
          redirectAttributes.addFlashAttribute("userRegisterBindingModel", userRegisterBindingModel);
          redirectAttributes.addFlashAttribute("userExistsError", true);
          return "redirect:register";
         }
           
             return "redirect:/home";
        }



    @GetMapping("/register")
    public String showRegister(Model model){
        if(!model.containsAttribute("userRegisterBindingModel")){
            model.addAttribute("userRegisterBindingModel", new UserRegisterBindingModel());
            model.addAttribute("userExistsError", false);
        }

        return "register";

    }
}
