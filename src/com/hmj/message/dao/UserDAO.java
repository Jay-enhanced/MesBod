package com.hmj.message.dao;

import com.hmj.message.model.UserEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Repository
@Transactional
public class UserDAO
{
    @Resource
    private SessionFactory sessionFactory;

    private Session getSession()
    {
        return sessionFactory.getCurrentSession();
    }

    public UserEntity getUserById(int id)
    {
        return (UserEntity) this.getSession().createQuery("from UserEntity where id=?").setParameter(0, id).uniqueResult();
    }

    public UserEntity getUserByUsername(String username)
    {
        return (UserEntity) this.getSession().createQuery("from UserEntity where username=?").setParameter(0, username).uniqueResult();
    }

    public void addUser(UserEntity user)
    {
        this.getSession().save(user);
    }

}
