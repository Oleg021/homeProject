package com.nix.vyrvykhvost.repository.hibernate;

import com.nix.vyrvykhvost.annotations.Autowired;
import com.nix.vyrvykhvost.annotations.Singleton;
import com.nix.vyrvykhvost.config.HibernateSessionFactory;
import com.nix.vyrvykhvost.model.phone.Phone;
import com.nix.vyrvykhvost.repository.CrudeRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

@Singleton
public class PhoneRepositoryHibernate implements CrudeRepository<Phone> {
    private static PhoneRepositoryHibernate instance;
    private final SessionFactory sessionFactory = HibernateSessionFactory.getSessionFactory();

    @Autowired
    public PhoneRepositoryHibernate() {
    }

    public static PhoneRepositoryHibernate getInstance() {
        if (instance == null) {
            instance = new PhoneRepositoryHibernate();
        }
        return instance;
    }

    @Override
    public void save(Phone phone) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(phone);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void saveAll(List<Phone> phones) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        for (Phone phone : phones) {
            session.save(phone);
        }
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Phone> findAll() {
        Session session = sessionFactory.openSession();
        List<Phone> phones = session.createQuery("select phone from Phone phone", Phone.class).getResultList();
        session.close();
        return phones;
    }

    @Override
    public Optional<Phone> findById(String id) {
        Session session = sessionFactory.openSession();
        Optional<Phone> phone = Optional.ofNullable(session.find(Phone.class, id));
        session.close();
        return phone;
    }

    @Override
    public boolean update(Phone phone) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(phone);
            session.getTransaction().commit();
            session.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(String id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.remove(findById(id).get());
            session.getTransaction().commit();
            session.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Phone> getAll() {
        Session session = sessionFactory.openSession();
        List<Phone> phones = session.createQuery("select phone from Phone phone", Phone.class).getResultList();
        session.close();
        return phones;
    }
}
