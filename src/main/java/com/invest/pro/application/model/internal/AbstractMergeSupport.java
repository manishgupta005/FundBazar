package com.invest.pro.application.model.internal;

import com.invest.pro.application.exception.ApplicationException;
import com.invest.pro.application.property.info.PropertyInfo;
import org.hibernate.SessionFactory;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created with IntelliJ IDEA.
 * User: Manish G
 * Date: 10/11/2015
 * Time: 1:39 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractMergeSupport<S> implements MergeSupport<S>  {

    @Override
    public void merge(MergeSupport<S> updated, SessionFactory sessionFactory) {
        try {
            checkNotNull(updated, "Updated property is null.");
            checkNotNull(getPropertyHolder(), "Property Holder cannot be null");
            for (PropertyInfo propertyInfo : getPropertyHolder().getPropertiesInfoToBeMerged()) {
                propertyInfo.checkAndMerge(updated, this, sessionFactory);
            }
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException("System Error. Please try later.", e);
        }

    }

    @Override
    public void load(MergeSupport<S> entity, SessionFactory sessionFactory) {
        try {
            checkNotNull(entity, "Entity property is null.");
            checkNotNull(getPropertyHolder(), "Property Holder cannot be null");
            for (PropertyInfo propertyInfo : getPropertyHolder().getPropertiesInfoToBeMerged()) {
                propertyInfo.load(entity, sessionFactory);
            }
        } catch (ApplicationException e) {
            throw e;
        } catch (Exception e) {
            throw new ApplicationException("System Error. Please try later.", e);
        }
    }
}
