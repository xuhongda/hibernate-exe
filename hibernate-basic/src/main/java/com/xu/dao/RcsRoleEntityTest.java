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

import java.io.Serializable;

/**
 * @author xuhongda on 2019/6/18
 * com.xu.dao
 * hibernate-exe
 */
@Slf4j
public class RcsRoleEntityTest {

    private static Session session;

    private Transaction tx;

    private static SessionFactory sessionFactory;

    static {
        //加载核心配置文件，加载映射文件
        Configuration configuration = new Configuration().configure();
        //手动加载映射
       // configuration.addResource("");
        sessionFactory = configuration.buildSessionFactory();
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

    /**
     * 插入
     */
    @Test
    public void test002(){

        RcsRoleEntity rcsRoleEntity = new RcsRoleEntity();
        rcsRoleEntity.setRoleName("xx");
        rcsRoleEntity.setDescri("xx");
        Serializable save = session.save(rcsRoleEntity);
        tx.commit();
        log.info("save = {}",save);
    }

    /**
     *  查询
     */
    @Test
    public void test003(){

        //立即发送 sql --- 空 -> null
        RcsRoleEntity rcsRoleEntity = session.get(RcsRoleEntity.class, 0);
        log.info("save = {}",rcsRoleEntity);

        // 延迟 代理 --- 返回空 抛异常
        RcsRoleEntity rcsRoleEntity2 = session.load(RcsRoleEntity.class, 0);
        log.info("save = {}",rcsRoleEntity2);

        tx.commit();
        session.close();
    }

    /**
     * 修改
     */
    @Test
    public void test004(){

        RcsRoleEntity rcsRoleEntity = new RcsRoleEntity();
        rcsRoleEntity.setId(0);
        rcsRoleEntity.setRoleName("xx");
        rcsRoleEntity.setDescri("yy");
        rcsRoleEntity.setXx("xx");
        session.update(rcsRoleEntity);
        tx.commit();
        session.close();
    }

    /**
     * 删除
     */
    @Test
    public void test005(){

        RcsRoleEntity rcsRoleEntity = new RcsRoleEntity();
        rcsRoleEntity.setId(0);
        session.delete(rcsRoleEntity);
        tx.commit();
        session.close();
    }

    /**
     *  保存或者更新
     *
     * 有主键修改，无，更新
     */
    @Test
    public void test006(){

        RcsRoleEntity rcsRoleEntity = new RcsRoleEntity();
        rcsRoleEntity.setId(0);
        session.saveOrUpdate(rcsRoleEntity);
        tx.commit();
        session.close();
    }

}
