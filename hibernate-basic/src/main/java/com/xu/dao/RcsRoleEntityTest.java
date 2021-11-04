package com.xu.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xu.pojo.RcsRoleEntity;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.type.IntegerType;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

/**
 * @author xuhongda on 2019/6/18
 * com.xu.dao
 * hibernate-exe
 */
@Slf4j
public class RcsRoleEntityTest {

    private static Session session;

    private ObjectMapper objectMapper = new ObjectMapper();

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
            criteria.add(Expression.eq("id",21));
            RcsRoleEntity rcsRoleEntity = (RcsRoleEntity)criteria.uniqueResult();
            System.out.println(rcsRoleEntity);
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
    public void test001_1(){

        try {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<RcsRoleEntity> query = criteriaBuilder.createQuery(RcsRoleEntity.class);
            Root<RcsRoleEntity> from = query.from(RcsRoleEntity.class);
            query.select(from);
            List<RcsRoleEntity> resultList = session.createQuery(query).getResultList();
            resultList.forEach(System.out::println);
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
        rcsRoleEntity.setRoleName("一二三");
        rcsRoleEntity.setDescri("xx");
        Serializable save = session.save(rcsRoleEntity);
        tx.commit();
        log.info("save =",save);
    }

    /**
     *  主键查询 方法
     */
    @Test
    public void test003(){

        //立即发送 sql --- 空 -> null
        RcsRoleEntity rcsRoleEntity = session.get(RcsRoleEntity.class, 0);
        log.info("save =",rcsRoleEntity);

        // 延迟 代理 --- 返回空 抛异常
        RcsRoleEntity rcsRoleEntity2 = session.load(RcsRoleEntity.class, 0);
        log.info("save =",rcsRoleEntity2);

        tx.commit();
        session.close();
    }

    /**
     *  hql  查询
     *  HQL查询是Hibernate提供的面向对象的查询语句，查询的是对象以及对象的属性，区分大小写的。
     *  from RcsRoleEntity 查询的实体类名称不是表名称
     */
    @Test
    public void test003_1() throws JsonProcessingException {

        Query query = session.createQuery("from RcsRoleEntity rcs where rcs.id = 1");
        RcsRoleEntity rcsRoleEntity = (RcsRoleEntity)query.uniqueResult();
        log.info("rcs =",objectMapper.writeValueAsString(rcsRoleEntity));
        tx.commit();
        session.close();
    }

    /**
     * sql 查询 addEntity()
     *     1、查询返回的是某个数据表的全部数据列
     *     2、该数据表有对应的持久化类映射
     */
    @Test
    public void test003_2(){

        NativeQuery sqlQuery = session.createSQLQuery("select rcs.id,rcs.role_name from rcs_role rcs where rcs.id = 1");
       /* NativeQuery nativeQuery = sqlQuery.addEntity(RcsRoleEntity.class);
        RcsRoleEntity rcsRoleEntity = (RcsRoleEntity)nativeQuery.uniqueResult();*/
        Object[] objects = (Object[])sqlQuery.uniqueResult();


        log.info("rcs =",objects[1]);
        tx.commit();
        session.close();
    }

    /**
     * sql 连表查询 ， addScalar
     *
     *   1、指定查询结果包含哪些数据列---没有被addScalar选出的列将不会包含在查询结果中。
     *   2、指定查询结果中数据列的数据类型
     *
     */
    @Test
    public void test003_3() throws JsonProcessingException {

        NativeQuery sqlQuery = session.createSQLQuery("select role.ROLE_NAME,power.POWER_ID,power.ID from rcs_role role left join rcs_role_power power on role.ID = power.ROLE_ID where role.id = 1");
        List list = sqlQuery.addScalar("POWER_ID", LongType.INSTANCE).addScalar("ROLE_NAME", StringType.INSTANCE).list();
        log.info("list =",objectMapper.writeValueAsString(list));
        tx.commit();
        session.close();
    }

    /**
     * sql : setParameter :  position
     *
     */

    @Test
    public void test003_4() throws JsonProcessingException {

        NativeQuery sqlQuery = session.createSQLQuery("select role.ROLE_NAME,power.POWER_ID,power.ID from rcs_role role left join rcs_role_power power on role.ID = power.ROLE_ID where role.id = ?");
        List list = sqlQuery.addScalar("POWER_ID", LongType.INSTANCE).addScalar("ROLE_NAME", StringType.INSTANCE).setParameter(1,1).list();
        log.info("list =",objectMapper.writeValueAsString(list));
        tx.commit();
        session.close();
    }

    /**
     * sql : setParameter : name
     * @throws JsonProcessingException
     */
    @Test
    public void test003_5() throws JsonProcessingException {

        NativeQuery sqlQuery = session.createSQLQuery("select role.ROLE_NAME,power.POWER_ID,power.ID from rcs_role role left join rcs_role_power power on role.ID = power.ROLE_ID where role.id = :id");
        List list = sqlQuery.addScalar("POWER_ID", LongType.INSTANCE).addScalar("ROLE_NAME", StringType.INSTANCE).setParameter("id",1, IntegerType.INSTANCE).list();
        log.info("list =",objectMapper.writeValueAsString(list));
        tx.commit();
        session.close();
    }


    /**
     * 修改
     */
    @Test
    public void test004(){

        RcsRoleEntity rcsRoleEntity = new RcsRoleEntity();
        rcsRoleEntity.setId(35);
        rcsRoleEntity.setRoleName("tt");
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
     * 有主键,更新;无，save
     */
    @Test
    public void test006(){

        try{
            RcsRoleEntity rcsRoleEntity = new RcsRoleEntity();
            rcsRoleEntity.setRoleName("zz");
            rcsRoleEntity.setId(33);
            session.saveOrUpdate(rcsRoleEntity);
            tx.commit();
            session.close();
        }catch (Exception e){
            log.error("error =",e);
        }
    }

    @Test
    public void test007(){
        try{
            RcsRoleEntity rcsRoleEntity = new RcsRoleEntity();
            rcsRoleEntity.setRoleName("zz");
            rcsRoleEntity.setId(31);
            rcsRoleEntity.setDescri("xcv");
            session.update(rcsRoleEntity);
            tx.commit();
            session.close();
        }catch (Exception e){
            log.error("error = ",e);
        }
    }

    @Test
    public void test008(){
        try{
            String sql = "update rcs_role rcsRole set DESCRI=:cri where ROLE_NAME=:name";
            int update = session.createSQLQuery(sql).setParameter("cri","uuu").setParameter("name","zz").executeUpdate();
            System.out.println(update);
            tx.commit();
            session.close();
        }catch (Exception e){
            log.error("error =",e);
        }
    }

    /**
     * createQuery 面向对象
     */
    @Test
    public void test008_1(){
        try{
            String sql = "update RcsRoleEntity rcsRole set rcsRole.descri=:cri where rcsRole.roleName=:name";
            int update = session.createQuery(sql).setParameter("cri","uuu").setParameter("name","zz").executeUpdate();
            System.out.println(update);
            tx.commit();
            session.close();
        }catch (Exception e){
            log.error("error =",e);
        }
    }


    @Test
    public void test009(){
        try{
            String sql = "select * from rcs_role rcsRole  where ROLE_NAME=:name";
            List<Object> rcsRoleEntityList = (List)session.createSQLQuery(sql).addEntity(RcsRoleEntity.class).setParameter("name", "zz").list();
            System.out.println(rcsRoleEntityList.size());
            RcsRoleEntity rcsRoleEntity = (RcsRoleEntity)rcsRoleEntityList.get(0);
            System.out.println(rcsRoleEntity.toString());
            tx.commit();
            session.close();
        }catch (Exception e){
            log.error("error",e);
        }
    }


    @Test
    public void test010(){
        try{
            String sql = "select rcsRole.DESCRI,rcsRole.ROLE_NAME from rcs_role rcsRole  where ROLE_NAME=:name";
            Object[] objects= (Object[])session.createSQLQuery(sql).setParameter("name", "壹车族风控角色").uniqueResult();
            System.out.println(objects[0]);
            tx.commit();
        }catch (Exception e){
            log.error("error",e);
        }finally {
            System.out.println("finally");
            session.close();
        }
        System.out.println("close");
    }

}
