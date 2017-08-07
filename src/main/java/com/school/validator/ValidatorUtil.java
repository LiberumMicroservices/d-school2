package com.school.validator;

import org.springframework.stereotype.Component;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

@Component
public class ValidatorUtil {

    public boolean validateEmail(String email){
        boolean isValidate;

        try {
            InternetAddress  internetAddress = new InternetAddress(email);
            internetAddress.validate();

            isValidate = true;
        }catch (AddressException e){
            isValidate = false;
        }

        return isValidate;
    }

    public boolean validatePhoneUA(String phone){
        if(phone.matches("^((8|\\+3)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{10,12}$"))
            return true;
        else
            return false;
    }
}
