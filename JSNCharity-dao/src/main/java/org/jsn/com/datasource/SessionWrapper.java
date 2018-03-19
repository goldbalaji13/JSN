package org.jsn.com.datasource;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.hibernate.CacheMode;
import org.hibernate.Criteria;
import org.hibernate.Filter;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.LobHelper;
import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.Query;
import org.hibernate.ReplicationMode;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Session.LockRequest;
import org.hibernate.SessionFactory;
import org.hibernate.SharedSessionBuilder;
import org.hibernate.Transaction;
import org.hibernate.TypeHelper;
import org.hibernate.UnknownProfileException;
import org.hibernate.cfg.Configuration;
import org.hibernate.exception.spi.Configurable;
import org.hibernate.jdbc.ReturningWork;
import org.hibernate.jdbc.Work;
import org.hibernate.stat.SessionStatistics;
import org.jsn.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Repository
@Scope(scopeName=ConfigurableBeanFactory.SCOPE_SINGLETON)
public class SessionWrapper {

	private Session session;

	private final ResourceLoader resourceLoader;
	
	@Autowired
	public SessionWrapper(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	@PostConstruct
	public void init() throws IOException {
		Configuration conf = new Configuration();
		File file = resourceLoader.getResource("classpath:configration.xml").getFile();
		conf.configure(file);
		conf.addAnnotatedClass(UserDto.class);
		SessionFactory fact = conf.buildSessionFactory();
		session = fact.openSession();
	}

	public String getTenantIdentifier() {
		return session.getTenantIdentifier();
	}

	public Transaction beginTransaction() {
		return session.beginTransaction();
	}

	public Transaction getTransaction() {
		return session.getTransaction();
	}

	public Query getNamedQuery(String queryName) {
		return session.getNamedQuery(queryName);
	}

	public Query createQuery(String queryString) {
		return session.createQuery(queryString);
	}

	public SQLQuery createSQLQuery(String queryString) {
		return session.createSQLQuery(queryString);
	}

	public Criteria createCriteria(Class persistentClass) {
		return session.createCriteria(persistentClass);
	}

	public Criteria createCriteria(Class persistentClass, String alias) {
		return session.createCriteria(persistentClass, alias);
	}

	public Criteria createCriteria(String entityName) {
		return session.createCriteria(entityName);
	}

	public Criteria createCriteria(String entityName, String alias) {
		return session.createCriteria(entityName, alias);
	}

	public SharedSessionBuilder sessionWithOptions() {
		return session.sessionWithOptions();
	}

	public void flush() throws HibernateException {
		session.flush();
	}

	public void setFlushMode(FlushMode flushMode) {
		session.setFlushMode(flushMode);
	}

	public FlushMode getFlushMode() {
		return session.getFlushMode();
	}

	public void setCacheMode(CacheMode cacheMode) {
		session.setCacheMode(cacheMode);
	}

	public CacheMode getCacheMode() {
		return session.getCacheMode();
	}

	public SessionFactory getSessionFactory() {
		return session.getSessionFactory();
	}

	public void cancelQuery() throws HibernateException {
		session.cancelQuery();
	}

	public boolean isOpen() {
		return session.isOpen();
	}

	public boolean isConnected() {
		return session.isConnected();
	}

	public boolean isDirty() throws HibernateException {
		return session.isDirty();
	}

	public boolean isDefaultReadOnly() {
		return session.isDefaultReadOnly();
	}

	public void setDefaultReadOnly(boolean readOnly) {
		session.setDefaultReadOnly(readOnly);
	}

	public Serializable getIdentifier(Object object) throws HibernateException {
		return session.getIdentifier(object);
	}

	public boolean contains(Object object) {
		return session.contains(object);
	}

	public void evict(Object object) throws HibernateException {
		session.evict(object);
	}

	public Object load(Class theClass, Serializable id, LockMode lockMode) throws HibernateException {
		return session.load(theClass, id, lockMode);
	}

	public Object load(Class theClass, Serializable id, LockOptions lockOptions) throws HibernateException {
		return session.load(theClass, id, lockOptions);
	}

	public Object load(String entityName, Serializable id, LockMode lockMode) throws HibernateException {
		return session.load(entityName, id, lockMode);
	}

	public Object load(String entityName, Serializable id, LockOptions lockOptions) throws HibernateException {
		return session.load(entityName, id, lockOptions);
	}

	public Object load(Class theClass, Serializable id) throws HibernateException {
		return session.load(theClass, id);
	}

	public Object load(String entityName, Serializable id) throws HibernateException {
		return session.load(entityName, id);
	}

	public void load(Object object, Serializable id) throws HibernateException {
		session.load(object, id);
	}

	public void replicate(Object object, ReplicationMode replicationMode) throws HibernateException {
		session.replicate(object, replicationMode);
	}

	public void replicate(String entityName, Object object, ReplicationMode replicationMode) throws HibernateException {
		session.replicate(entityName, object, replicationMode);
	}

	public Serializable save(Object object) throws HibernateException {
		return session.save(object);
	}

	public Serializable save(String entityName, Object object) throws HibernateException {
		return session.save(entityName, object);
	}

	public void saveOrUpdate(Object object) throws HibernateException {
		session.saveOrUpdate(object);
	}

	public void saveOrUpdate(String entityName, Object object) throws HibernateException {
		session.saveOrUpdate(entityName, object);
	}

	public void update(Object object) throws HibernateException {
		session.update(object);
	}

	public void update(String entityName, Object object) throws HibernateException {
		session.update(entityName, object);
	}

	public Object merge(Object object) throws HibernateException {
		return session.merge(object);
	}

	public Object merge(String entityName, Object object) throws HibernateException {
		return session.merge(entityName, object);
	}

	public void persist(Object object) throws HibernateException {
		session.persist(object);
	}

	public void persist(String entityName, Object object) throws HibernateException {
		session.persist(entityName, object);
	}

	public void delete(Object object) throws HibernateException {
		session.delete(object);
	}

	public void delete(String entityName, Object object) throws HibernateException {
		session.delete(entityName, object);
	}

	public void lock(Object object, LockMode lockMode) throws HibernateException {
		session.lock(object, lockMode);
	}

	public void lock(String entityName, Object object, LockMode lockMode) throws HibernateException {
		session.lock(entityName, object, lockMode);
	}

	public LockRequest buildLockRequest(LockOptions lockOptions) {
		return session.buildLockRequest(lockOptions);
	}

	public void refresh(Object object) throws HibernateException {
		session.refresh(object);
	}

	public void refresh(String entityName, Object object) throws HibernateException {
		session.refresh(entityName, object);
	}

	public void refresh(Object object, LockMode lockMode) throws HibernateException {
		session.refresh(object, lockMode);
	}

	public void refresh(Object object, LockOptions lockOptions) throws HibernateException {
		session.refresh(object, lockOptions);
	}

	public void refresh(String entityName, Object object, LockOptions lockOptions) throws HibernateException {
		session.refresh(entityName, object, lockOptions);

	}

	public LockMode getCurrentLockMode(Object object) throws HibernateException {
		return session.getCurrentLockMode(object);
	}

	public Query createFilter(Object collection, String queryString) {
		return session.createFilter(collection, queryString);
	}

	public Object get(Class clazz, Serializable id) throws HibernateException {
		return session.get(clazz, id);
	}

	public Object get(Class clazz, Serializable id, LockMode lockMode) throws HibernateException {
		return session.get(clazz, id, lockMode);
	}

	public Object get(Class clazz, Serializable id, LockOptions lockOptions) throws HibernateException {
		return session.get(clazz, id, lockOptions);
	}

	public Object get(String entityName, Serializable id) throws HibernateException {
		return session.get(entityName, id);
	}

	public Object get(String entityName, Serializable id, LockMode lockMode) throws HibernateException {
		return session.get(entityName, id, lockMode);
	}

	public Object get(String entityName, Serializable id, LockOptions lockOptions) throws HibernateException {
		return session.get(entityName, id, lockOptions);
	}

	public String getEntityName(Object object) throws HibernateException {
		return session.getEntityName(object);
	}

	public Filter enableFilter(String filterName) {
		return session.enableFilter(filterName);
	}

	public Filter getEnabledFilter(String filterName) {
		return session.getEnabledFilter(filterName);
	}

	public void disableFilter(String filterName) {
		session.disableFilter(filterName);
	}

	public SessionStatistics getStatistics() {
		return session.getStatistics();
	}

	public boolean isReadOnly(Object entityOrProxy) {
		return session.isReadOnly(entityOrProxy);
	}

	public void setReadOnly(Object entityOrProxy, boolean readOnly) {
		session.setReadOnly(entityOrProxy, readOnly);
	}

	public void doWork(Work work) throws HibernateException {
		session.doWork(work);
	}

	public <T> T doReturningWork(ReturningWork<T> work) throws HibernateException {
		return session.doReturningWork(work);
	}

	public boolean isFetchProfileEnabled(String name) throws UnknownProfileException {
		return session.isFetchProfileEnabled(name);
	}

	public void enableFetchProfile(String name) throws UnknownProfileException {
		session.enableFetchProfile(name);

	}

	public void disableFetchProfile(String name) throws UnknownProfileException {
		session.disableFetchProfile(name);

	}

	public TypeHelper getTypeHelper() {
		return session.getTypeHelper();
	}

	public LobHelper getLobHelper() {
		return session.getLobHelper();
	}

	@PreDestroy
	public void destroy() {
		session.flush();
		session.close();		
	}
}
