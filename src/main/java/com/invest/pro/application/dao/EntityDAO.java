package com.invest.pro.application.dao;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.invest.pro.application.model.internal.Entity;
import com.invest.pro.application.model.internal.MergeSupport;
import com.invest.pro.application.utils.Utils;
import com.yammer.dropwizard.hibernate.AbstractDAO;
import org.hibernate.*;
import org.hibernate.context.internal.ManagedSessionContext;
import org.hibernate.criterion.Order;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Manish G
 * Date: 10/31/2015
 * Time: 1:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class EntityDAO<T extends Entity, S extends Serializable> extends AbstractDAO<T> {
    private final SessionFactory sessionFactory;
    private final Map<String, Object> EMPTY_MAP = Maps.newHashMap();

    public EntityDAO() {
        super(Utils.getSessionFactory());
        this.sessionFactory = Utils.getSessionFactory();
    }

    public T save(T entity) {
        Session session = openSession(false);
        try {
            entity = persist(entity);
            commitTransaction(session, false);
        } catch (Exception e) {
            rollbackTransaction(session, false);
        } finally {
            closeSession(session);
        }
        return entity;
    }

    public T findById(S id) {
        Session session = openSession(true);
        T entity = null;
        try {
            entity = get(id);
            if (entity != null) {
                entity.init();
            }
            commitTransaction(session, true);
        } catch (Exception e) {
            rollbackTransaction(session, true);
        } finally {
            closeSession(session);
        }
        return entity;
    }

    public T update(T entity) {
        Session session = openSession(false);
        try {
            T original = get(entity.getId());
            if (original != null) {
                original.init();
            }
            if (original instanceof MergeSupport) {
                ((MergeSupport) original).merge((MergeSupport) entity, sessionFactory);
            }
            original.beforeUpdate();
            entity = persist(original);
            commitTransaction(session, false);
        } catch (Exception e) {
            rollbackTransaction(session, false);
        } finally {
            closeSession(session);
        }
        return entity;
    }

    public List<T> findAll(Integer number, Integer offset) {
        return findAll(number, offset, "id", true);
    }

    public List<T> findAll(Integer number, Integer offset, String orderByPropertyName) {
        return findAll(number, offset, orderByPropertyName, true);
    }

    public List<T> findAll(Integer number, Integer offset, String orderByPropertyName, boolean asc) {
        Session session = openSession(true);
        List<T> results = Lists.newArrayList();
        try {
            Criteria criteria = criteria().setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            if (number != 1) {
                criteria.setFirstResult(number - 1);
            }
            if (offset != 0) {
                criteria.setMaxResults(offset);
            }
            if (asc) {
                criteria.addOrder(Order.asc(orderByPropertyName));
            } else {
                criteria.addOrder(Order.desc(orderByPropertyName));
            }
            results = list(criteria);
            for (T result : results) {
                result.init();
            }
            commitTransaction(session, true);
        } catch (Exception e) {
            rollbackTransaction(session, true);
        } finally {
            closeSession(session);
        }
        return results;
    }

    public void remove(S id) {
        Session session = openSession(false);
        try {
            T entity = get(id);
            if (entity != null) {
                currentSession().delete(entity);
            }
            commitTransaction(session, false);
        } catch (Exception e) {
            rollbackTransaction(session, false);
        } finally {
            closeSession(session);
        }
    }

    public T uniqueResult(Query query) {
        return uniqueResult(query);
    }

    public List<T> list(Query query) {
        Session session = openSession(true);
        List<T> results = Lists.newArrayList();
        try {
            results = query.list();
            for (T result : results) {
                result.init();
            }
            commitTransaction(session, true);
        } catch (Exception e) {
            rollbackTransaction(session, true);
        } finally {
            closeSession(session);
        }
        return results;
    }

    public T namedQueryUniqueResult(String namedQuery, Map<String, Object> parameters) {
        Session session = openSession(true);
        T result = null;
        try {
            Query query = namedQuery(namedQuery);
            for (Map.Entry<String, Object> entry : parameters.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
            result = (T) query.uniqueResult();
            if (result != null) {
                result.init();
            }
            commitTransaction(session, true);
        } catch (Exception e) {
            rollbackTransaction(session, true);
        } finally {
            closeSession(session);
        }
        return result;
    }

    public List<T> namedQueryListResult(String namedQuery) {
        return namedQueryListResult(namedQuery, EMPTY_MAP);
    }

    public List<T> namedQueryListResult(String namedQuery, Map<String, Object> parameters) {
        return namedQueryListResult(namedQuery, -1, parameters);
    }

    public List<T> namedQueryListResult(String namedQuery, int maxSize, Map<String, Object> parameters) {
        Session session = openSession(true);
        List<T> results = Lists.newArrayList();
        try {
            Query query = namedQuery(namedQuery);
            for (Map.Entry<String, Object> entry : parameters.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
            if (maxSize != -1) {
                query.setMaxResults(maxSize);
            }
            results = query.list();
            for (T result : results) {
                if (result != null) {
                    result.init();
                }
            }
            commitTransaction(session, true);
        } catch (Exception e) {
            rollbackTransaction(session, true);
        } finally {
            closeSession(session);
        }
        return results;
    }

    private Session openSession(boolean readOnly) {
        final Session session = sessionFactory.openSession();
        try {
            session.setDefaultReadOnly(readOnly);
            session.setFlushMode(FlushMode.AUTO);
            session.setCacheMode(CacheMode.NORMAL);
            ManagedSessionContext.bind(session);
            if (!readOnly) {
                session.beginTransaction();
            }
        } finally {

        }
        return session;
    }

    private void closeSession(Session session) {
        session.close();
    }

    private void commitTransaction(Session session, boolean readOnly) {
        if (!readOnly) {
            final Transaction txn = session.getTransaction();
            if (txn != null && txn.isActive()) {
                txn.commit();
            }
        }
    }

    private void rollbackTransaction(Session session, boolean readOnly) {
        if (!readOnly) {
            final Transaction txn = session.getTransaction();
            if (txn != null && txn.isActive()) {
                txn.rollback();
            }
        }
    }

}