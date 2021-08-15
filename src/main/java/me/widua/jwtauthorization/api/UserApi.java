package me.widua.jwtauthorization.api;

import me.widua.jwtauthorization.manager.UserManager;
import me.widua.jwtauthorization.models.EnableAccountModel;
import me.widua.jwtauthorization.models.UserChangePasswordModel;
import me.widua.jwtauthorization.models.UserRegisterModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserApi {

    private final UserManager manager;

    @Autowired
    UserApi(UserManager manager) {
        this.manager = manager;
    }

    @GetMapping("/getUser")
    String getUser (){

        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @PostMapping("/register")
    ResponseEntity<String> registerUser(@RequestBody UserRegisterModel registerModel) {
        return ResponseEntity.ok(manager.registerUser(registerModel));
    }

    @PostMapping("/changePasswd")
    @PreAuthorize("isAuthenticated()")
    ResponseEntity<String> changePassword(@RequestBody UserChangePasswordModel changePasswordModel) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.status(HttpStatus.OK).body(manager.changePassword(changePasswordModel,auth.getName()));
    }

    @PostMapping("/disable")
    @PreAuthorize("isAuthenticated()")
    ResponseEntity<String> disableAccount (@CurrentSecurityContext(expression = "authentication") Authentication auth ){
        return ResponseEntity.ok(manager.disableAccount(auth.getName())) ;

    }

    @PostMapping("/enable")
    @PreAuthorize("isAuthenticated()")
    ResponseEntity<String> enableAccount (@RequestBody EnableAccountModel enableAccount){
        return ResponseEntity.ok(manager.enableAccount(enableAccount.getUsername(),enableAccount.getSpecialCode())) ;
    }


}
