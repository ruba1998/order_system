package com.example.order_system.controller;

import com.example.order_system.domain.Auditable;
import com.example.order_system.service.ContactDetailsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This is a controller to take the requests which related to the contact details for specific restaurant
 * requests can be: create a new contact for this restaurant, edit specific contact details, delete specific contact,
 * show a contact details and get a list contains all contact details for specific restaurant
 */


@RestController
@RequestMapping("/restaurant/{id}/contactDetails")
public class ContactDetailsController extends Auditable {

    private ContactDetailsService contactDetailsService;

    public ContactDetailsController(ContactDetailsService contactDetailsService) {
        this.contactDetailsService = contactDetailsService;
    }


    /*
    @PostMapping("/restaurant/contactdetails/add")
    public String addContactDetails(@Valid ContactDetails contactDetails, BindingResult bindingResult)
            throws BadRequestException
    {
        if(bindingResult.hasErrors()){
            logger.info("Error occurred because not all fields are filled!");
            throw new BadRequestException("Please make sure that you fill all fields");
        }
        else {
            logger.info("New Contact details added to this restaurant!");
            contactDetailsService.save(contactDetails);
            return "redirect:/restaurant/" + contactDetails.getRestaurant().getId();
        }
    }

*/


/*
    @PostMapping("/restaurant/contactdetails/add")
    public ContactDetails addContactDetails(@Valid ContactDetails contactDetails, @PathVariable Long restaurantID, BindingResult bindingResult)
            throws BadRequestException
    {
        Optional<Restaurant> optionalRestaurant = restaurantService.findById(restaurantID);

        if( optionalRestaurant.isPresent() ) {
            Restaurant restaurant = optionalRestaurant.get();
            List<ContactDetails> contactDetailsList = restaurant.getContactDetails();
            contactDetailsList.add(contactDetails);
            contactDetailsService.save(contactDetails);
            restaurant.setContactDetails(contactDetailsList);
            return contactDetails;
        }
            if(bindingResult.hasErrors()){
            logger.info("Error occurred because not all fields are filled!");
            throw new BadRequestException("Please make sure that you fill all fields");
        }
        else {
            logger.info("New Contact details added to this restaurant!");
            return contactDetailsService.save(contactDetails);
        }
    }
*/



}
