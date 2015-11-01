package com.invest.pro.application.model.internal;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Manish G
 * Date: 10/11/2015
 * Time: 2:04 PM
 * To change this template use File | Settings | File Templates.
 */
@MappedSuperclass
public abstract class PrimitiveIdAbstractEntity <T extends Serializable, S extends Entity> extends AbstractEntity<T, S> {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private T id;

    @Override
    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }
}
