package com.example.project.service;

import com.example.project.model.service.UserRegisterServiceModel;

public interface UserService {

    boolean isUserNameFree(String username);
    void registerAndLoginUser(UserRegisterServiceModel userRegistrationServiceModel);
}
