package com.xu.dao;

import com.xu.pojo.RcsRoleEntity;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Expression;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author xuhongda on 2019/6/18
 * com.xu.dao
 * hibernate-exe
 */
@Slf4j
public class RcsRoleEntityTest {

    private static Session session;

    private Transaction tx;

    static {
        //加载核心配置文件，加载映射文件
        Configuration configuration = new Configuration().configure();
        //手动加载映射
       // configuration.addResource("");
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        session = sessionFactory.getCurrentSession();
    }


    @Before
    public void bf(){
        tx = session.beginTransaction();
    }

    @After
    public void after(){
        log.info("finish ... ");
    }

    /**
     * 废弃api
     */
    @Test
    public void test001(){

        try {
            // do some work
            Criteria criteria = session.createCriteria(RcsRoleEntity.class);
            criteria.add(Expression.eq("id",7));
            Object o = criteria.setMaxResults(1).uniqueResult();
            log.info("o == {}",o instanceof RcsRoleEntity);
            tx.commit();
        }
        catch (Exception e) {
            if (tx!=null) {
                tx.rollback();
            }
            e.printStackTrace();
        }finally {
            session.close();
        }
    }


    @Test
    public void test002(){

    }

}
