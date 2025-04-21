import com.contactapp.dao.ContactDAO;
import com.contactapp.model.Contact;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ContactDAOTest {
    private final ContactDAO dao = new ContactDAO();

    @Test
    public void testAddContact() {
        Contact contact = new Contact(0, "Ana Cardona", "Ana123@example.com", "12345678", "1002609549");
        boolean result = dao.addContact(contact);
        System.out.println("Contacto agregado: " + contact);
        assertTrue(result, "El contacto no se agregó correctamente.");

    }

    @Test
    public void testGetAllContacts() {
        List<Contact> contactos = dao.getAllContacts();
        System.out.println(" Contactos encontrados: " + contactos);
        assertTrue(contactos.size() > 0, "No se encontraron contactos en la base de datos.");
    }


    @Test
    public void testGetContactById() {
        int id = 2;
        Contact contact = dao.getContactById(id);
        System.out.println("Contacto encontrado: " + contact);
        assertFalse(contact == null, "El contacto no existe en la base de datos");
    }

    @Test
    public void testDeleteContact() {
        int id = 8;

        Contact contact = dao.getContactById(id);

        if (contact == null) {
            throw new IllegalArgumentException(" El contacto con ID " + id + " no existe y no se puede eliminar.");
        }

        boolean result = dao.deleteContact(id);
        System.out.println("Contacto eliminado: ID = " + id);
        assertTrue(result, "Falló la eliminación del contacto con ID: " + id);
    }


    @Test
    public void testUpdateContact() {
        Contact contact = new Contact(3, "Pedro Picapiedra", "pedrito@example.com", "12345678", "1002609666");

        boolean result = dao.updateContact(contact);
        System.out.println("Contacto actualizado: " + contact);
        assertTrue(result, "El contacto no existe en la base de datos");

    }
}
