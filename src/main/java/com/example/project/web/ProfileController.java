package com.example.project.web;

import com.example.project.model.binding.ChangePasswordBindingModel;
import com.example.project.model.binding.RatingBindingModel;
import com.example.project.model.binding.UserProfileBindingModel;
import com.example.project.model.service.ChangePasswordServiceModel;
import com.example.project.model.service.UserProfileServiceModel;
import com.example.project.service.HotelService;
import com.example.project.service.RatingService;
import com.example.project.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class ProfileController {
    private final UserService userService;
    private final RatingService ratingService;
    private final HotelService hotelService;
    private final ModelMapper modelMapper;

    public ProfileController(UserService userService, RatingService ratingService, HotelService hotelService, ModelMapper modelMapper) {
        this.userService = userService;
        this.ratingService = ratingService;
        this.hotelService = hotelService;
        this.modelMapper = modelMapper;
    }
    @PreAuthorize("canAccess(#username)")
    @GetMapping("/users/excursions/{username}")
    public String getExcursionsForUser(Model model, @PathVariable String username){
        model.addAttribute("bookings",userService.getAllExcursionBookings(username));
        model.addAttribute("username",username);

        return "user-excursions";
    }

    @ModelAttribute
    public RatingBindingModel ratingBindingModel(){
        return new RatingBindingModel();
    }
    @PreAuthorize("canAccess(#username)")
    @PostMapping("/users/excursions/{username}/{bookingid}")
    public String rating(@Valid RatingBindingModel ratingBindingModel,
                         BindingResult bindingResult,
                         @PathVariable String username,
                         @PathVariable Long bookingid,
                         RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("ratingBindingModel", ratingBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.ratingBindingModel", bindingResult);
            return String.format("redirect:/users/excursions/%s",username);
        }
ratingService.createRating(username, bookingid, ratingBindingModel);
        return String.format("redirect:/users/excursions/%s",username);

    }

    @PreAuthorize("canAccess(#username)")
    @GetMapping("/users/destinations/{username}")
    public String getDestinationsForUser(Model model, @PathVariable String username){
        model.addAttribute("destinations",userService.getAllHotelBookings(username));
        model.addAttribute("username",username);

        return "user-destinations";
    }
    @PreAuthorize("canAccess(#username)")
    @GetMapping("/users/profile/{username}")
    public String viewProfile(Model model, @PathVariable String username){
        model.addAttribute("user", userService.mapUserToBindingModel(username));
        model.addAttribute("username", username);
        return "user-profile";
    }


    @PreAuthorize("canAccess(#username)")
    @GetMapping("/users/profile/{username}/edit")
    public String editProfile(Model model, @PathVariable String username){
        if(!model.containsAttribute("userProfileBindingModel")) {
            model.addAttribute("userProfileBindingModel", userService.mapUserToBindingModel(username));
        }
        model.addAttribute("username", username);

        return "profile-edit";
    }
    @PreAuthorize("canAccess(#username)")
    @PostMapping("/users/profile/{username}/edit")
    public String editProfile(@Valid UserProfileBindingModel userProfileBindingModel,
                         BindingResult bindingResult,
                         @PathVariable String username,
                         RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("userProfileBindingModel", userProfileBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userProfileBindingModel", bindingResult);
            return String.format("redirect:/users/profile/%s/edit",username);
        }
        userService.editUser(username, modelMapper.map(userProfileBindingModel, UserProfileServiceModel.class));
        return "redirect:/users/profile/{username}";

    }

    @ModelAttribute
    public ChangePasswordBindingModel changePasswordBindingModel(){
        return new ChangePasswordBindingModel();
    }

    @PreAuthorize("canAccess(#username)")
    @GetMapping("/users/profile/{username}/changePassword")
    public String changePassword(Model model, @PathVariable String username){
        if(!model.containsAttribute("passwordDoesNotMatch")){
            model.addAttribute("passwordDoesNotMatch", false);
        }
        if(!model.containsAttribute("incorrectPassword")){
            model.addAttribute("incorrectPassword", false);
        }

        return "password-change";
    }

    @PreAuthorize("canAccess(#username)")
    @PostMapping("/users/profile/{username}/changePassword")
    public String changePassword(@Valid ChangePasswordBindingModel changePasswordBindingModel,
                              BindingResult bindingResult,
                              @PathVariable String username,
                              RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("changePasswordBindingModel", changePasswordBindingModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.changePasswordBindingModel", bindingResult);
            return String.format("redirect:/users/profile/%s/changePassword",username);
        }
        ChangePasswordServiceModel changePasswordServiceModel = modelMapper.map(changePasswordBindingModel, ChangePasswordServiceModel.class);
        if(!(changePasswordBindingModel.getNewPassword().equals(changePasswordBindingModel.getConfirmPassword())) ||  !userService.isPasswordCorrect(username, changePasswordServiceModel)){
            if (!changePasswordBindingModel.getNewPassword().equals(changePasswordBindingModel.getConfirmPassword())){
            redirectAttributes.addFlashAttribute("passwordDoesNotMatch", true);}
            if(!userService.isPasswordCorrect(username, changePasswordServiceModel)){
                redirectAttributes.addFlashAttribute("incorrectPassword", true);
            }
            return String.format("redirect:/users/profile/%s/changePassword",username);
        }

        userService.changePassword(username, changePasswordServiceModel);
        return "passwordchange-success";

    }
}
