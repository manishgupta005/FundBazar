package com.invest.pro.application.model.internal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.invest.pro.application.enums.ActionType;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Manish G
 * Date: 10/11/2015
 * Time: 1:43 PM
 * To change this template use File | Settings | File Templates.
 */
@MappedSuperclass
public abstract class AbstractEntity<T extends Serializable, S extends Entity> extends AbstractMergeSupport<S> implements Entity<T, S> {

    @Column(name = "CREATE_DTTM")
    @Temporal(value = TemporalType.TIMESTAMP)
    @JsonIgnore
    private Date creationDateTime;

    @Column(name = "LAST_UPD_DTTM")
    @Temporal(value = TemporalType.TIMESTAMP)
    @JsonIgnore
    private Date lastUpdated;

    @Column(name = "VERSION")
    @Version
    @JsonIgnore
    private Long version;

    @Transient
    private ActionType action;

    @Override
    public ActionType getAction() {
        return action;
    }

    public void setAction(ActionType action) {
        this.action = action;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public DateTime getCreationDateTime() {
        return creationDateTime != null ? new DateTime(creationDateTime) : null;
    }

    public void setCreationDateTime(DateTime creationDateTime) {
        this.creationDateTime = creationDateTime != null ? creationDateTime.toDate() : null;
    }

    public DateTime getLastUpdated() {
        return lastUpdated != null ? new DateTime(lastUpdated) : null;
    }

    public void setLastUpdated(DateTime lastUpdated) {
        this.lastUpdated = lastUpdated != null ? lastUpdated.toDate() : null;
    }

    @Override
    @JsonIgnore
    public void init() {
    }

    @Override
    public void beforeAdd() {
        setCreationDateTime(DateTime.now());
    }

    @Override
    public void beforeUpdate() {
        setLastUpdated(DateTime.now());
    }



}
