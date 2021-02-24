package com.example.order_system.service;

import com.example.order_system.domain.ContactDetails;
import com.example.order_system.repository.ContactDetailsRepository;
import org.springframework.stereotype.Service;

@Service
public class ContactDetailsService {

    private final ContactDetailsRepository contactDetailsRepository;

    public ContactDetailsService(ContactDetailsRepository contactDetailsRepository) {
        this.contactDetailsRepository = contactDetailsRepository;
    }

    public ContactDetails save(ContactDetails contactDetails){
        return contactDetailsRepository.save(contactDetails);
    }
}
