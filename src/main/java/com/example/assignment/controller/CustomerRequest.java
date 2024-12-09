package com.example.assignment.controller;

import com.example.assignment.exceptions.InvalidValueException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.util.ObjectUtils;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest {

    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private String phone;


    public CustomerRequest validateFirstName(){
        String firstName = this.getFirstName();
        if(ObjectUtils.isEmpty(firstName) || firstName.matches(".*\\d.*")){
            throw new InvalidValueException("Value in Firstname is absent or not valid");
        }
        return this;
    }


    public CustomerRequest validateLastName(){
        String lastName = this.getLastName();
        if(ObjectUtils.isEmpty(lastName) || lastName.matches(".*\\d.*")){
            throw new InvalidValueException("Value in Lastname is absent or not valid");
        }
        return this;
    }


    public CustomerRequest validateMiddleName(){
        String middleName = this.getMiddleName();
        if(!ObjectUtils.isEmpty(middleName) && middleName.matches(".*\\d.*")){
            throw new InvalidValueException("Value in MiddleName is not valid");
        }
        return this;
    }


    public CustomerRequest validateEmail(){
        String email = this.getEmail();
        if(!EmailValidator.getInstance().isValid(email)){
            throw new InvalidValueException("Value in Email is absent or not valid");
        }
        return this;
    }

    public CustomerRequest validatePhone(){
        String phone = this.getPhone();
        if(ObjectUtils.isEmpty(phone) || !phone.matches("[0-9]+") || phone.length()<10){
            throw new InvalidValueException("Value in Phone is absent or not valid");
        }
        return this;
    }


}
