package com.kara4k.popupblocker.model;


import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;


@Entity(active = true, nameInDb = "rules")
public class Rule implements Serializable{

    public static final long serialVersionUID = 42L;

    @Id
    Long id;

    private String packageName;
    private String searchText;
    private boolean sendBackPress;
    private String pressText;

    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    @Generated(hash = 1504632639)
    private transient RuleDao myDao;

    @Generated(hash = 1283363733)
    public Rule(Long id, String packageName, String searchText,
            boolean sendBackPress, String pressText) {
        this.id = id;
        this.packageName = packageName;
        this.searchText = searchText;
        this.sendBackPress = sendBackPress;
        this.pressText = pressText;
    }

    @Generated(hash = 1416885836)
    public Rule() {
    }

      public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public boolean isSendBackPress() {
        return sendBackPress;
    }

    public void setSendBackPress(boolean sendBackPress) {
        this.sendBackPress = sendBackPress;
    }

    public String getPressText() {
        return pressText;
    }

    public void setPressText(String pressText) {
        this.pressText = pressText;
    }

    public boolean getSendBackPress() {
        return this.sendBackPress;
    }

    public String getPackageName() {
        return this.packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1848977951)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getRuleDao() : null;
    }


}
