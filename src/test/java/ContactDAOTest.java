import com.contactapp.dao.ContactDAO;
import com.contactapp.model.Contact;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ContactDAOTest {
    private final ContactDAO dao = new ContactDAO();

    @Test
    public void testAddContact() {
        Contact contact = new Contact(0, "Ana Cardona", "Ana123@example.com", "12345678", "1002609549");
        boolean result = dao.addContact(contact);
        assertTrue(result, "El contacto se agrego correctamente");

    }

    @Test
    public void testGetAllContactsNoVacios() {

        assertFalse(dao.getAllContacts().isEmpty(), "No hay contactos en la base de datos");

    }

    @Test
    public void testGetContactById() {
        int id = 2;
        Contact contact = dao.getContactById(id);
        assertTrue(contact != null, "El contacto existe en la base de datos" + contact);
    }

    @Test
    public void testDeleteContact() {
        int id = 6;
        boolean result = dao.deleteContact(id);
        assertTrue(result, "El contacto se elimino correctamente");
    }

    @Test
    public void testUpdateContact() {
        Contact contact = new Contact(3, "Pedro Picapiedra", "pedrito@example.com", "12345678", "1002609666");
        boolean result = dao.updateContact(contact);
        assertTrue(result, "El contacto se actualizo correctamente");

    }
}
