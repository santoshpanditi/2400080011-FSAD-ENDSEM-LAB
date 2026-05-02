package com.klef.fsad.exam;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.List;

public class ClientDemo {

    public static void main(String[] args) {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        SessionFactory factory = null;

        try {
            Metadata metadata = new MetadataSources(registry)
                    .addAnnotatedClass(Payment.class)
                    .getMetadataBuilder()
                    .build();
            factory = metadata.getSessionFactoryBuilder().build();

            int paymentId = insertPaymentRecords(factory);
            listAllPayments(factory);
            deletePaymentById(factory, paymentId);
            listAllPayments(factory);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (factory != null) {
                factory.close();
            }
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    private static int insertPaymentRecords(SessionFactory factory) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        Payment payment1 = new Payment("Rajesh", LocalDate.of(2026, 5, 2), "Completed", 2500.00, "Annual subscription");
        Payment payment2 = new Payment("Sneha", LocalDate.of(2026, 5, 3), "Pending", 1750.00, "Course fee");
        Payment payment3 = new Payment("Amit", LocalDate.of(2026, 5, 4), "Failed", 990.00, "Membership renewal");

        session.persist(payment1);
        session.persist(payment2);
        session.persist(payment3);

        tx.commit();
        session.close();

        System.out.println("Inserted payment record ID: " + payment1.getId());
        return payment1.getId();
    }

    private static void listAllPayments(SessionFactory factory) {
        Session session = factory.openSession();
        List<Payment> payments = session.createQuery("from Payment", Payment.class).list();

        System.out.println("\nCurrent payments:");
        for (Payment payment : payments) {
            System.out.println(payment);
        }

        session.close();
    }

    private static void deletePaymentById(SessionFactory factory, int id) {
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        Query<?> query = session.createQuery("delete from Payment where id = :paymentId");
        query.setParameter("paymentId", id);
        int deletedCount = query.executeUpdate();

        tx.commit();
        session.close();

        if (deletedCount > 0) {
            System.out.println("\nDeleted Payment record with ID " + id + " using HQL named parameter.");
        } else {
            System.out.println("\nNo Payment record found with ID " + id + " to delete.");
        }
    }
}
