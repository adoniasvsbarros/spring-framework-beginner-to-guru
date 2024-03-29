import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.adoniasbarros.sfgpetclinic.model.Owner;
import com.adoniasbarros.sfgpetclinic.services.map.OwnerMapService;
import com.adoniasbarros.sfgpetclinic.services.map.PetMapService;
import com.adoniasbarros.sfgpetclinic.services.map.PetTypeMapService;

public class OwnerMapServiceTest {

	OwnerMapService ownerMapService;
	
	final Long ownerId = 1L;
	final String lastName = "Mark";
	
	@BeforeEach
	void setUp() {
		ownerMapService = new OwnerMapService(new PetTypeMapService(), new PetMapService());
		ownerMapService.save(Owner.builder().id(ownerId).lastName(lastName).build());
	}
	
	@Test
	void findAll() {
		Set<Owner> ownerSet = ownerMapService.findAll();
		assertEquals(1, ownerSet.size());
	}
	
	@Test
	void findById() {
		Owner owner = ownerMapService.findById(ownerId);
		assertEquals(ownerId, owner.getId());
	}
	
	@Test
	void saveExistId() {
		Long id = 2L;
		Owner owner2 = Owner.builder().id(id).build();
		Owner savedOwner = ownerMapService.save(owner2);
		assertEquals(id, savedOwner.getId());
	}
	
	@Test 
	void saveNoId() {
		Owner savedOwner = ownerMapService.save(Owner.builder().build());
		assertNotNull(savedOwner);
		assertNotNull(savedOwner.getId());
	}
	
	@Test
	void delete() {
		ownerMapService.delete(ownerMapService.findById(ownerId));
		assertEquals(0, ownerMapService.findAll().size());
	}
	
	@Test
	void deleteById() {
		ownerMapService.deleteById(ownerId);
		assertEquals(0, ownerMapService.findAll().size());
	}
	
	@Test
	void findByLastName() {
		Owner mark = ownerMapService.findByLastName(lastName);
		assertNotNull(mark);
		assertEquals(ownerId, mark.getId());
	}
}
