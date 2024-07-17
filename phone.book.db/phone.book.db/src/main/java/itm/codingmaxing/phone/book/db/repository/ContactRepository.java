package itm.codingmaxing.phone.book.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import itm.codingmaxing.phone.book.db.entity.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long>{

	@Query(value="Select * from contact where name= ?1", nativeQuery=true)
	List<Contact> findByName(String name);
	
	@Query(value="Select *from contact order by name",nativeQuery=true)
	List<Contact> getContactOrderByName();
	
	@Query(value="Select * from contact order by mobile_number", nativeQuery=true)
	List<Contact> getContactOrderById();
	

}
