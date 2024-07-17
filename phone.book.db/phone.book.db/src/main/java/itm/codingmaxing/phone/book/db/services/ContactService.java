package itm.codingmaxing.phone.book.db.services;

import java.util.ArrayList;


import java.util.List;

import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import itm.codingmaxing.phone.book.db.controller.ContactController;
import itm.codingmaxing.phone.book.db.entity.Contact;
import itm.codingmaxing.phone.book.db.entity.ContactType;
import itm.codingmaxing.phone.book.db.repository.ContactRepository;

@Service
public class ContactService implements ContactOperation{
	
	@Autowired
	ContactRepository repository;

	Logger logger = LoggerFactory.getLogger(ContactController.class);
	@Override
	public boolean addContact(Contact c) {
		
		Optional<Contact> cc= repository.findById(c.getMobileNumber());
		
		if(cc.isPresent()) {
			logger.error("contact add was failed because of "+ c.getMobileNumber() + " already present");
			throw new ResponseStatusException(HttpStatus.CONFLICT,
					c.getMobileNumber()+" already exist");
		}
		else {
			repository.save(c);
			logger.info("contact with mobile number"+c.getMobileNumber()+" was added successfully");
			throw new ResponseStatusException(HttpStatus.CREATED, "Contact Added");
		}
	}

	@Override
	public List<Contact> getContactByName(String name) {
		
		List<Contact> temp = repository.findByName(name);
		if(temp.size()==0)
		{
			logger.error("Contacts with the name ",name," was not found");
			throw new ResponseStatusException(HttpStatus.NOT_FOUND , "No contacts exist with name " + name);
		}
		logger.info("Contacts list with the name are",temp);
		return temp;
		
	}

	@Override
	public Contact getContactByMobileNumber(long mobileNumber) {
		Optional <Contact> obj = repository.findById(mobileNumber);
		if(obj.isPresent()) {
			logger.info("Contact found with mobile number", mobileNumber);
			return obj.get();
		}
		else {
			logger.error("Contact not found with mobile number",mobileNumber);
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"contact with"+mobileNumber
					+"not found");
			
		}
		
		
	}

	@Override
	public List<Contact> searchContact(String key) {
	     
		List<Contact> list = repository.findAll();
		List<Contact> temp = new ArrayList<Contact>();

	        for (Contact c: list){
	            if (c.getName().contains(key)){
	                temp.add(c);
	            }
	        }
	        return temp;
	}

	@Override
	public boolean deleteContact(long mobileNumber) {
		Optional <Contact> obj = repository.findById(mobileNumber);
		
		if(obj.isPresent()) {
			logger.info("Mobile number with "+mobileNumber+" was deleted successfully");
			repository.deleteById(mobileNumber);
		}
		else {
			logger.error("Cannot find mobile number with "+mobileNumber);
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,
			"Cannot find mobile number with "+mobileNumber+" to delete");
		}
		
		obj = repository.findById(mobileNumber);
		
		if(obj.isPresent()) {
			logger.error("Cannot find mobile number with "+ mobileNumber);
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST , "Unable to delete contact with number "+mobileNumber);
		}
		throw new ResponseStatusException(HttpStatus.ACCEPTED , "Successfully completed ");
		
	}

	@Override
	public Contact updateContactName(long mobileNumber, String name) {
		Optional<Contact> obj = repository.findById(mobileNumber);
		if(obj.isPresent()) {
			Contact c = obj.get();
			c.setName(name);
			repository.save(c);
			logger.info("Contact with mobile number "+mobileNumber+", was updated");
			Optional<Contact> obj1 = repository.findById(mobileNumber);
				if(obj1.isPresent()) {
//					return obj1.get();
					throw new ResponseStatusException(HttpStatus.ACCEPTED);
				}
			}
		throw new ResponseStatusException(HttpStatus.NOT_FOUND,
				"Contact not found with mobile number "+ mobileNumber);
		}
	

	@Override
	public List<Contact> getAllContacts() {
		List<Contact> temp = repository.findAll();
		if(temp.size() == 0) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND,"There are no contacts in the database");
		}
		else {
			return temp;
		}
	
	}

	@Override
	public List<Contact> sortContact(String property) {
	
		if(property.isEmpty()) {
			return repository.getContactOrderById();
		}
		else if(property.equals("id")) {
			return repository.getContactOrderById();
		}else if(property.equals("name")){
			return repository.getContactOrderByName();
		}
		else if(property.equals("type")) {
			return repository.getContactOrderByType();
		}
		else
		throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"INVALID SORTING");
	}

	@Override
	public Contact updateContactType(long mobileNumber, ContactType type) {
		Optional<Contact> obj = repository.findById(mobileNumber);
		if(obj.isPresent()) {
			Contact c = obj.get();
			c.setType(type);
			repository.save(c);
			
		logger.info("Contact with mobile number "+mobileNumber+", was updated");
		Optional<Contact> obj1 = repository.findById(mobileNumber);
			if(obj1.isPresent()) {
				return obj1.get();
			}
		}throw new ResponseStatusException(HttpStatus.NOT_FOUND,
			"Contact not found with mobile number "+ mobileNumber);
	}
	
	
	

}
