package itm.codingmaxing.phone.book.db.services;

import java.util.List;


import org.springframework.stereotype.Service;

import itm.codingmaxing.phone.book.db.entity.Contact;
import itm.codingmaxing.phone.book.db.entity.ContactType;

@Service
public interface ContactOperation {


    public boolean addContact(Contact c);
    public List<Contact> getContactByName(String name);
    public Contact getContactByMobileNumber(long mobileNumber);
    public List<Contact> searchContact(String key);
    public boolean deleteContact(long mobileNumber);
    public Contact updateContactName(long mobileNumber, String name);
    public Contact updateContactType(long mobileNumber, ContactType type);
    public List<Contact> getAllContacts();
    public List<Contact> sortContact(String property);
    
}
