package com.hmj.message.dao;

import com.hmj.message.model.MessageEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by woshi on 2017/12/24.
 */
@Repository
@Transactional
public class MessageDAO {
    @Resource
    private SessionFactory sessionFactory;

    private Session getSession()
    {
        return sessionFactory.getCurrentSession();
    }

    public MessageEntity getMessageById(int id)
    {
        return (MessageEntity) this.getSession().createQuery("from MessageEntity where id=?").setParameter(0, id).uniqueResult();
    }

    public List<MessageEntity> getAllMessage()
    {
        return (List<MessageEntity>)this.getSession().createCriteria(MessageEntity.class).addOrder(Order.desc("id")).list();
    }

    public List<MessageEntity> getReplyMessage()
    {
        return (List<MessageEntity>)this.getSession().createQuery("from MessageEntity where status=1").list();
    }

    public void addMessage(MessageEntity messageEntity)
    {
        this.getSession().save(messageEntity);
    }

    public void updateMessage(MessageEntity messageEntity)
    {
        this.getSession().update(messageEntity);
    }

    public void deleteMessage(MessageEntity messageEntity)
    {
        this.getSession().delete(messageEntity);
    }
}
