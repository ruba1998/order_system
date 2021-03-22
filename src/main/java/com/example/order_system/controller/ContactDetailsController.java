package com.example.order_system.controller;

import com.example.order_system.domain.Auditable;
import com.example.order_system.domain.ContactDetails;
import com.example.order_system.domain.Restaurant;
import com.example.order_system.exceptionHandler.ContactWithThisTypeAlreadyExist;
import com.example.order_system.exceptionHandler.ResourceNotFoundException;
import com.example.order_system.service.ContactDetailsService;
import com.example.order_system.service.RestaurantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * This is a controller to take the requests which related to the contact details for specific restaurant
 * requests can be: create a new contact for this restaurant, edit specific contact details, delete specific contact,
 * show a contact details and get a list contains all contact details for specific restaurant
 */


@RestController
public class ContactDetailsController {

    private static final Logger logger = LoggerFactory.getLogger(ContactDetailsController.class);

    private RestaurantService restaurantService;
    private ContactDetailsService contactDetailsService;

    public ContactDetailsController(RestaurantService restaurantService, ContactDetailsService contactDetailsService) {
        this.restaurantService = restaurantService;
        this.contactDetailsService = contactDetailsService;
    }

    @GetMapping("/restaurants/{restaurantId}/contactDetails/{id}")
    public ResponseEntity<ContactDetails> findContactDetailsById(@PathVariable Long restaurantId, @PathVariable Long id) {
        if(!restaurantService.existsById(restaurantId)){
            logger.info("Error occurred because this restaurant is not found!");
            throw new ResourceNotFoundException("There is no restaurant with this id");
        }
        Optional<ContactDetails> optionalContactDetails = contactDetailsService.findById(id);
        if(!optionalContactDetails.isPresent()){
            logger.info("Error occurred because this Contact is not found!");
            throw new ResourceNotFoundException("There is no contact with this id");
        }
        return new ResponseEntity<>(optionalContactDetails.get(),HttpStatus.OK);
    }

    @DeleteMapping("/restaurants/{restaurantId}/contactDetails/{id}")
    public ResponseEntity<String> deleteContactDetailsById(@PathVariable Long restaurantId,@PathVariable Long id) {
        if(!restaurantService.existsById(restaurantId)){
            logger.info("Error occurred because this restaurant is not found!");
            throw new ResourceNotFoundException("There is no restaurant with this id");
        } else if(!contactDetailsService.existsById(id)){
            logger.info("Error occurred because this Contact is not found!");
            throw new ResourceNotFoundException("There is no contact with this id");
        }
        contactDetailsService.deleteById(id);
        return new ResponseEntity<>("deleted",HttpStatus.OK);
    }

    @GetMapping("/restaurants/{restaurantId}/contactDetails")
    public ResponseEntity<List<ContactDetails>> getAllContactDetails(@PathVariable Long restaurantId) {
        if (!restaurantService.existsById(restaurantId)) {
            logger.error("There is an Error of adding a new contact,The given ID is not found ");
            throw new ResourceNotFoundException("RestaurantId " + restaurantId + " not found");
        }
        List<ContactDetails> contactDetailsList = contactDetailsService.findAllByRestaurantId(restaurantId);
        return new ResponseEntity<>(contactDetailsList,HttpStatus.OK);
    }

    @PostMapping("/restaurants/{restaurantId}/contactDetails/create")
    public ResponseEntity<ContactDetails> addContact(@RequestBody ContactDetails contactDetails,@PathVariable Long restaurantId) {
        Optional<Restaurant> restaurantOptional = restaurantService.findById(restaurantId);
        if (!restaurantOptional.isPresent()) {
            logger.error("There is an Error of adding a new contact,The given ID is not found ");
            throw new ResourceNotFoundException("RestaurantId " + restaurantId + " not found");
        }
        List<ContactDetails> contactDetailsList = contactDetailsService.findAllByRestaurantId(restaurantId);
        for (ContactDetails contact : contactDetailsList ) {
            if( contactDetails.getType().equals(contact.getType()) ){
                throw new ContactWithThisTypeAlreadyExist("There is a contact with this type for the restaurant with id: "
                        +restaurantId);
            }
        }
        Restaurant restaurant = restaurantOptional.get();
        contactDetails.setRestaurant(restaurant);
        contactDetailsService.save(contactDetails);
        logger.info("New contact was saved successfully to Restaurant with Id " + restaurantId);
        return new ResponseEntity<>(contactDetails, HttpStatus.CREATED);
    }

    @PostMapping("/restaurants/{restaurantId}/contactDetails/{id}/update")
    public ResponseEntity<ContactDetails> updateContact(@PathVariable Long restaurantId,
                                                           @PathVariable Long id,
                                                           @RequestBody Map<String, Object> updatedContact) {
        Optional<Restaurant> restaurantOptional = restaurantService.findById(restaurantId);
        if (!restaurantOptional.isPresent()) {
            logger.error("There is an Error of updating a contact,The given ID is not found ");
            throw new ResourceNotFoundException("RestaurantId " + restaurantId + " not found");
        }
        Optional<ContactDetails> contactDetailsOptional = contactDetailsService.findById(id);
        if (!contactDetailsOptional.isPresent()) {
            logger.error("There is an Error of updating a contact,The given ID is not found ");
            throw new ResourceNotFoundException("Contact Id " + id + " not found");
        }
        ContactDetails contactDetails = contactDetailsOptional.get();
        for (Map.Entry<String, Object> entry : updatedContact.entrySet()) {
            if( entry.getKey().equals("type") )
                contactDetails.setType((String)entry.getValue());
            else if( entry.getKey().equals("value") )
                contactDetails.setValue((String)entry.getValue());
        }
        logger.info("restaurant information edited successfully");
        return new ResponseEntity<>(contactDetailsService.save(contactDetails), HttpStatus.OK);
    }

}
