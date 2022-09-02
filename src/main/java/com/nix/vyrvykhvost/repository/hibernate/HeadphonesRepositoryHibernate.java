package com.nix.vyrvykhvost.repository.hibernate;

import com.nix.vyrvykhvost.annotations.Autowired;
import com.nix.vyrvykhvost.annotations.Singleton;
import com.nix.vyrvykhvost.config.HibernateSessionFactory;
import com.nix.vyrvykhvost.model.headphone.Headphones;
import com.nix.vyrvykhvost.repository.CrudeRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

@Singleton
public class HeadphonesRepositoryHibernate implements CrudeRepository<Headphones> {
    private static HeadphonesRepositoryHibernate instance;
    private final SessionFactory sessionFactory = HibernateSessionFactory.getSessionFactory();

    @Autowired
    public HeadphonesRepositoryHibernate() {
    }

    public static HeadphonesRepositoryHibernate getInstance() {
        if (instance == null) {
            instance = new HeadphonesRepositoryHibernate();
        }
        return instance;
    }

    @Override
    public void save(Headphones headphone) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(headphone);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void saveAll(List<Headphones> headphones) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        for (Headphones headphone : headphones) {
            session.save(headphone);
        }
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<Headphones> findAll() {
        Session session = sessionFactory.openSession();
        List<Headphones> headphonesList = session.createQuery("select headphones from Headphones headphones", Headphones.class).getResultList();
        session.close();
        return headphonesList;
    }

    @Override
    public Optional<Headphones> findById(String id) {
        Session session = sessionFactory.openSession();
        Optional<Headphones> headphone = Optional.ofNullable(session.find(Headphones.class, id));
        session.close();
        return headphone;
    }

    @Override
    public boolean update(Headphones headphone) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(headphone);
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
    public List<Headphones> getAll() {
        Session session = sessionFactory.openSession();
        List<Headphones> laptops = session.createQuery("select headphones from Headphones headphones", Headphones.class).getResultList();
        session.close();
        return laptops;
    }
}

