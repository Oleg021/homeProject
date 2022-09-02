package com.nix.vyrvykhvost.repository.hibernate;

import com.nix.vyrvykhvost.annotations.Autowired;
import com.nix.vyrvykhvost.annotations.Singleton;
import com.nix.vyrvykhvost.config.HibernateSessionFactory;
import com.nix.vyrvykhvost.model.laptop.Laptop;
import com.nix.vyrvykhvost.repository.CrudeRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

@Singleton
public class LaptopRepositoryHibernate implements CrudeRepository<Laptop> {
    private static LaptopRepositoryHibernate instance;
    private final SessionFactory sessionFactory = HibernateSessionFactory.getSessionFactory();

    @Autowired
    public LaptopRepositoryHibernate() {
    }

    public static LaptopRepositoryHibernate getInstance() {
        if (instance == null) {
            instance = new LaptopRepositoryHibernate();
        }
        return instance;
    }

    @Override
    public void save(Laptop laptop) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(laptop);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void saveAll(List<Laptop> laptops) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        for (Laptop laptop : laptops) {
            session.save(laptop);
        }
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Laptop> findAll() {
        Session session = sessionFactory.openSession();
        List<Laptop> laptops = session.createQuery("select laptop from Laptop laptop", Laptop.class).getResultList();
        session.close();
        return laptops;
    }

    @Override
    public Optional<Laptop> findById(String id) {
        Session session = sessionFactory.openSession();
        Optional<Laptop> laptop = Optional.ofNullable(session.find(Laptop.class, id));
        session.close();
        return laptop;
    }

    @Override
    public boolean update(Laptop laptop) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(laptop);
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
    public List<Laptop> getAll() {
        Session session = sessionFactory.openSession();
        List<Laptop> laptops = session.createQuery("select laptop from Laptop laptop", Laptop.class).getResultList();
        session.close();
        return laptops;
    }
}
