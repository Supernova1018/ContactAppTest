package com.contactapp.servlet;

import com.contactapp.dao.ContactDAO;
import com.contactapp.model.Contact;
import com.google.gson.Gson;

import javax.servlet.http.*;
import java.io.*;
import java.util.Collections;
import java.util.List;


public class ContactServlet extends HttpServlet {
    private ContactDAO dao;
    private Gson gson;

    @Override
    public void init() {
        System.out.println("🚀 ContactServlet inicializado correctamente");
        dao = new ContactDAO();
        gson = new Gson();
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("📥 Petición GET recibida");

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String idParam = req.getParameter("id");

        if (idParam != null) {
            try {
                int id = Integer.parseInt(idParam);
                Contact contact = dao.getContactById(id);
                resp.getWriter().write(gson.toJson(contact));
            } catch (NumberFormatException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("{\"error\":\"ID inválido\"}");
            }
        } else {
            List<Contact> contacts = dao.getAllContacts();
            resp.getWriter().write(gson.toJson(contacts));
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        System.out.println("Petición POST recibida");

        req.setCharacterEncoding("UTF-8");

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        BufferedReader reader = req.getReader();
        Contact contact = gson.fromJson(reader, Contact.class);

        if (contact.getName() == null || !contact.getName().matches("[A-Za-zÁÉÍÓÚáéíóúÑñ ]+")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Nombre inválido. Solo letras y espacios.\"}");
            return;
        }

        if (contact.getEmail() == null || contact.getEmail().trim().isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Correo no puede estar vacío\"}");
            return;
        }

        if (contact.getPhone() == null || !contact.getPhone().matches("\\d+")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Teléfono inválido. Solo números.\"}");
            return;
        }

        if (contact.getCedula() == null || !contact.getCedula().matches("\\d+")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Cédula inválida. Solo números.\"}");
            return;
        }

        dao.addContact(contact);
        resp.getWriter().write("{\"message\":\"Contacto creado exitosamente\"}");
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader reader = req.getReader();
        Contact contact = gson.fromJson(reader, Contact.class);

        // Verificar si el contacto con ese ID existe

        Contact existingContact = dao.getContactById(contact.getId());
        if (existingContact == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("{\"error\":\"No se encontró el contacto con ID: " + contact.getId() + "\"}");
            return;
        }

        // VALIDACIONES

        if (contact.getName() == null || !contact.getName().matches("[A-Za-zÁÉÍÓÚáéíóúÑñ ]+")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Nombre inválido. Solo letras y espacios.\"}");
            return;
        }

        if (contact.getEmail() == null || contact.getEmail().trim().isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Correo no puede estar vacío\"}");
            return;
        }

        if (contact.getPhone() == null || !contact.getPhone().matches("\\d+")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Teléfono inválido. Solo números.\"}");
            return;
        }

        if (contact.getCedula() == null || !contact.getCedula().matches("\\d+")) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Cédula inválida. Solo números.\"}");
            return;
        }

        dao.updateContact(contact);
        resp.getWriter().write("{\"message\":\"Contacto actualizado exitosamente\"}");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idParam = req.getParameter("id");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        if (idParam == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.getWriter().write("{\"error\":\"Debe proporcionar un ID para eliminar\"}");
            return;
        }

        int id = Integer.parseInt(idParam);

        // Verificar si el contacto existe
        Contact contact = dao.getContactById(id);
        if (contact == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.getWriter().write("{\"error\":\"No se encontró el contacto con ID: " + id + "\"}");
            return;
        }

        boolean deleted = dao.deleteContact(id);
        if (deleted) {
            resp.getWriter().write("{\"message\":\"Contacto eliminado exitosamente\"}");
        } else {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.getWriter().write("{\"error\":\"Error al eliminar el contacto\"}");
        }
    }
}
