package me.widua.jwtauthorization.manager;

import me.widua.jwtauthorization.authorization.Roles;
import me.widua.jwtauthorization.models.UserChangePasswordModel;
import me.widua.jwtauthorization.models.UserModel;
import me.widua.jwtauthorization.models.UserRegisterModel;
import me.widua.jwtauthorization.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Random;

import static me.widua.jwtauthorization.authorization.Roles.*;

@Component
public class UserManager {

    private final UserRepository repository;
    private final PasswordEncoder encoder ;

    @Autowired
    UserManager( UserRepository repository , PasswordEncoder encoder ){
        this.repository = repository;
        this.encoder = encoder;
    }

    public Optional<UserModel> getUserByUsername(String username){
        return repository.findById(username);
    }

    public String registerUser (UserRegisterModel registerModel){

        if ( registerModel.getPassword().equals(registerModel.getRepeatedPassword()) ){

            if (repository.findById(registerModel.getUsername()).isEmpty()){

                UserModel user = new UserModel( registerModel.getUsername(), encoder.encode( registerModel.getPassword() ) , USER.getAuthorities(), true ) ;
                repository.save( user ) ;
                return "User successfully registered!";

            } else {
                return "Username exist!" ;
            }

        } else {
            return "Passwords not same!" ;
        }

    }

    public String changePassword (UserChangePasswordModel changePasswordModel , String username){
        if (changePasswordModel.getNewPassword().equals(changePasswordModel.getRepeatedNewPassword())){
            UserModel user = repository.findById( username ).get() ;
            user.setPassword( encoder.encode( changePasswordModel.getNewPassword() ) );
            return "Password changed correctly!";
        } else {
            return "Passwords are not same!";
        }


    }

    public String disableAccount ( String username ){

        UserModel user = repository.findById( username ).get() ;
        if (user.isEnabled()){
            user.setEnabled(false);


            String generatedString = generateString(16);
            user.setSecretCode( generatedString );
            repository.save(user) ;
            return "User account has been successfully disabled, if you want to enable your account in future, you must remember this code: " + generatedString ;
        } else {
            return "User account is currently disabled!" ;
        }

    }

    public String enableAccount ( String username , String accessCode ) {

        if ( repository.findById(username).isPresent() ){
            UserModel user = repository.findById( username ).get() ;

            if (user.isEnabled()){
                return "User account is currently enabled!" ;
            }

            if ( accessCode.equals(user.getSecretCode()) ){
                user.setEnabled(true);
                repository.save(user) ;
                return "User account has been successfully enabled" ;
            } else {
                return "Bad secret code!" ;
            }

        }else {
            return "User account not exist!" ;
        }


    }



    private String generateString (int length){
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }


}
