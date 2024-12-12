package com.security.spring_security.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.security.spring_security.Models.EmergencyContact;
import com.twilio.Twilio;
import com.twilio.exception.ApiException;
import com.twilio.rest.api.v2010.account.Message;

@Service
public class Twillo {

    private String accountSid = "AC56aabe5ae28ea40b9277babb9ca27506";
    private String authToken = "e595866f7b041f2fd3ded6137d4e5dd6";
    private String twilioPhoneNumber = "+17753109547";


    public String sendSms(List<EmergencyContact> contact , String messageBody) {

        System.out.println("Message Sent To :- " );

        Twilio.init(accountSid, authToken);
            try {

                for(EmergencyContact emrContact : contact)
                {
                    Message message = Message.creator(
                            new com.twilio.type.PhoneNumber(emrContact.getPhoneNumber()),
                            new com.twilio.type.PhoneNumber(twilioPhoneNumber),
                            messageBody
                    ).create();
                 System.out.println("Message Sent To :- " + emrContact.getPhoneNumber());
               }
            } catch (ApiException e) {
                return "Failed to send message: " + e.getMessage();
            }
        
            return "Delivered";
    }


}
