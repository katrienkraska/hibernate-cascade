package core.basesyntax.dao.impl;

import core.basesyntax.HibernateUtil;
import core.basesyntax.dao.MessageDetailsDao;
import core.basesyntax.model.MessageDetails;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class MessageDetailsDaoImpl extends AbstractDao implements MessageDetailsDao {
    public MessageDetailsDaoImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public MessageDetails create(MessageDetails entity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.persist(entity);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Can't remove entity " + entity, e);
        } finally {
            session.close();
        }
        return entity;
    }

    @Override
    public MessageDetails get(Long id) {
        Session session = factory.openSession();
        return session.get(MessageDetails.class, id);
    }
}
