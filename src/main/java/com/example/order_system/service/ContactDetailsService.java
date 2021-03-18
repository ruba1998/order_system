package com.example.order_system.service;

import com.example.order_system.domain.ContactDetails;
import com.example.order_system.repository.ContactDetailsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactDetailsService {

    private final ContactDetailsRepository contactDetailsRepository;

    public ContactDetailsService(ContactDetailsRepository contactDetailsRepository) {
        this.contactDetailsRepository = contactDetailsRepository;
    }

    public ContactDetails save(ContactDetails contactDetails){
        return contactDetailsRepository.save(contactDetails);
    }

    public List<ContactDetails> findAllByRestaurantId(Long restaurantId){
        return contactDetailsRepository.findAllByRestaurantId(restaurantId);
    }

    public boolean existsById(Long id) {
        return contactDetailsRepository.existsById(id);
    }

    public Optional<ContactDetails> findById(Long id) {
        return contactDetailsRepository.findById(id);
    }

    public void deleteById(Long id) {
        contactDetailsRepository.deleteById(id);
    }


}
