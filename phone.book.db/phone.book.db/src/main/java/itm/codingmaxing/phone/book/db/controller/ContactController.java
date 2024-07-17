package itm.codingmaxing.phone.book.db.controller;

import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import itm.codingmaxing.phone.book.db.entity.Contact;

import itm.codingmaxing.phone.book.db.services.ContactOperation;


@RestController
public class ContactController {

	@Autowired
	ContactOperation service ;
	
	Logger logger = LoggerFactory.getLogger(ContactController.class);
	
	@CrossOrigin("*")
	@RequestMapping(path="/contact/add", method=RequestMethod.POST)
	public boolean addContact(@RequestBody Contact c) {
		logger.info("/contact/add");
		logger.info("Adding... new contact" + c.toString());
		return service.addContact(c);
	}
	
	@CrossOrigin("*")
	@RequestMapping("/contact/getall")
	public List<Contact> getAllContacts(){
		logger.info("/contact/getall");
		logger.info("Getting... all the contacts");
		return service.getAllContacts();
	}
	
	@CrossOrigin("*")
	@GetMapping("/contact/getname/{name}")
	public List<Contact> getContactByName (@PathVariable String name){
		logger.info("/contact/getname/{name}");
		logger.info("Getting... the contacts with name "+ name);
		return service.getContactByName(name);
		
	}
	
	@CrossOrigin("*")
	@RequestMapping(path="/contact/sort")
	public List<Contact> sortContact(@RequestParam(defaultValue="")String property){
		logger.info("/contact/sort");
		logger.info("Sorting... the contacts list on the basis of "+ property);
		return service.sortContact(property);
	}
	
	@CrossOrigin("*")
	@RequestMapping(path="/contact/update/{mobileNumber}",method = RequestMethod.PUT)
	public Contact updateContactName(@PathVariable long mobileNumber,@RequestBody String name) {
		logger.info("/contact/update/{mobileNumber}");
		logger.info("Updating... Contact's name with mobile number "+mobileNumber+" to "+name);
		return service.updateContactName(mobileNumber, name);
	}
	
	@CrossOrigin("*")
	@RequestMapping(path="/contact/delete/{mobileNumber}", method=RequestMethod.DELETE)
	public boolean deleteContact(@PathVariable long mobileNumber) {
		logger.info("/contact/delete/{mobileNumber}");
		logger.info("Deleting... Contact with mobile number "+ mobileNumber);
		return service.deleteContact(mobileNumber);
	}
	
	@CrossOrigin("*")
	@RequestMapping(path="/contact/update/type/{mobileNumber}", method=RequestMethod.PUT)
	public Contact updateContactType(@PathVariable long mobileNumber,@RequestBody Contact type) {
		
		logger.info("/contact/update/type/{mobileNumber}");
		logger.info("Updating Type... Contact with mobile number "+ mobileNumber);
		return service.updateContactType(mobileNumber, type.getType());
	}
}

