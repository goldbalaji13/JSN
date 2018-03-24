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
import org.hibernate.jdbc.ReturningWork;
import org.hibernate.jdbc.Work;
import org.hibernate.stat.SessionStatistics;
import org.jsn.dto.DrugDto;
import org.jsn.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;

@Repository
@Scope(scopeName = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class SessionWrapper {

	private Session session;

	private final ResourceLoader resourceLoader;

	@Autowired
	public SessionWrapper(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	public Transaction beginTransaction() {
		return this.session.beginTransaction();
	}

	public LockRequest buildLockRequest(LockOptions lockOptions) {
		return this.session.buildLockRequest(lockOptions);
	}

	public void cancelQuery() throws HibernateException {
		this.session.cancelQuery();
	}

	public void clear() {
		this.session.clear();
	}

	public boolean contains(Object object) {
		return this.session.contains(object);
	}

	public Criteria createCriteria(Class persistentClass) {
		return this.session.createCriteria(persistentClass);
	}

	public Criteria createCriteria(Class persistentClass, String alias) {
		return this.session.createCriteria(persistentClass, alias);
	}

	public Criteria createCriteria(String entityName) {
		return this.session.createCriteria(entityName);
	}

	public Criteria createCriteria(String entityName, String alias) {
		return this.session.createCriteria(entityName, alias);
	}

	public Query createFilter(Object collection, String queryString) {
		return this.session.createFilter(collection, queryString);
	}

	public Query createQuery(String queryString) {
		return this.session.createQuery(queryString);
	}

	public SQLQuery createSQLQuery(String queryString) {
		return this.session.createSQLQuery(queryString);
	}

	public void delete(Object object) throws HibernateException {
		this.session.delete(object);
	}

	public void delete(String entityName, Object object) throws HibernateException {
		this.session.delete(entityName, object);
	}

	@PreDestroy
	public void destroy() {
		this.session.flush();
		this.session.close();
	}

	public void disableFetchProfile(String name) throws UnknownProfileException {
		this.session.disableFetchProfile(name);

	}

	public void disableFilter(String filterName) {
		this.session.disableFilter(filterName);
	}

	public <T> T doReturningWork(ReturningWork<T> work) throws HibernateException {
		return this.session.doReturningWork(work);
	}

	public void doWork(Work work) throws HibernateException {
		this.session.doWork(work);
	}

	public void enableFetchProfile(String name) throws UnknownProfileException {
		this.session.enableFetchProfile(name);

	}

	public Filter enableFilter(String filterName) {
		return this.session.enableFilter(filterName);
	}

	public void evict(Object object) throws HibernateException {
		this.session.evict(object);
	}

	public void flush() throws HibernateException {
		this.session.flush();
	}

	public Object get(Class clazz, Serializable id) throws HibernateException {
		return this.session.get(clazz, id);
	}

	public Object get(Class clazz, Serializable id, LockMode lockMode) throws HibernateException {
		return this.session.get(clazz, id, lockMode);
	}

	public Object get(Class clazz, Serializable id, LockOptions lockOptions) throws HibernateException {
		return this.session.get(clazz, id, lockOptions);
	}

	public Object get(String entityName, Serializable id) throws HibernateException {
		return this.session.get(entityName, id);
	}

	public Object get(String entityName, Serializable id, LockMode lockMode) throws HibernateException {
		return this.session.get(entityName, id, lockMode);
	}

	public Object get(String entityName, Serializable id, LockOptions lockOptions) throws HibernateException {
		return this.session.get(entityName, id, lockOptions);
	}

	public CacheMode getCacheMode() {
		return this.session.getCacheMode();
	}

	public LockMode getCurrentLockMode(Object object) throws HibernateException {
		return this.session.getCurrentLockMode(object);
	}

	public Filter getEnabledFilter(String filterName) {
		return this.session.getEnabledFilter(filterName);
	}

	public String getEntityName(Object object) throws HibernateException {
		return this.session.getEntityName(object);
	}

	public FlushMode getFlushMode() {
		return this.session.getFlushMode();
	}

	public Serializable getIdentifier(Object object) throws HibernateException {
		return this.session.getIdentifier(object);
	}

	public LobHelper getLobHelper() {
		return this.session.getLobHelper();
	}

	public Query getNamedQuery(String queryName) {
		return this.session.getNamedQuery(queryName);
	}

	public SessionFactory getSessionFactory() {
		return this.session.getSessionFactory();
	}

	public SessionStatistics getStatistics() {
		return this.session.getStatistics();
	}

	public String getTenantIdentifier() {
		return this.session.getTenantIdentifier();
	}

	public Transaction getTransaction() {
		return this.session.getTransaction();
	}

	public TypeHelper getTypeHelper() {
		return this.session.getTypeHelper();
	}

	@PostConstruct
	public void init() throws IOException {
		Configuration conf = new Configuration();
		File file = this.resourceLoader.getResource("classpath:configration.xml").getFile();
		conf.configure(file);
		conf.addAnnotatedClass(UserDto.class);
		conf.addAnnotatedClass(DrugDto.class);
		SessionFactory fact = conf.buildSessionFactory();
		this.session = fact.openSession();
	}

	public boolean isConnected() {
		return this.session.isConnected();
	}

	public boolean isDefaultReadOnly() {
		return this.session.isDefaultReadOnly();
	}

	public boolean isDirty() throws HibernateException {
		return this.session.isDirty();
	}

	public boolean isFetchProfileEnabled(String name) throws UnknownProfileException {
		return this.session.isFetchProfileEnabled(name);
	}

	public boolean isOpen() {
		return this.session.isOpen();
	}

	public boolean isReadOnly(Object entityOrProxy) {
		return this.session.isReadOnly(entityOrProxy);
	}

	public Object load(Class theClass, Serializable id) throws HibernateException {
		return this.session.load(theClass, id);
	}

	public Object load(Class theClass, Serializable id, LockMode lockMode) throws HibernateException {
		return this.session.load(theClass, id, lockMode);
	}

	public Object load(Class theClass, Serializable id, LockOptions lockOptions) throws HibernateException {
		return this.session.load(theClass, id, lockOptions);
	}

	public void load(Object object, Serializable id) throws HibernateException {
		this.session.load(object, id);
	}

	public Object load(String entityName, Serializable id) throws HibernateException {
		return this.session.load(entityName, id);
	}

	public Object load(String entityName, Serializable id, LockMode lockMode) throws HibernateException {
		return this.session.load(entityName, id, lockMode);
	}

	public Object load(String entityName, Serializable id, LockOptions lockOptions) throws HibernateException {
		return this.session.load(entityName, id, lockOptions);
	}

	public void lock(Object object, LockMode lockMode) throws HibernateException {
		this.session.lock(object, lockMode);
	}

	public void lock(String entityName, Object object, LockMode lockMode) throws HibernateException {
		this.session.lock(entityName, object, lockMode);
	}

	public Object merge(Object object) throws HibernateException {
		return this.session.merge(object);
	}

	public Object merge(String entityName, Object object) throws HibernateException {
		return this.session.merge(entityName, object);
	}

	public void persist(Object object) throws HibernateException {
		this.session.persist(object);
	}

	public void persist(String entityName, Object object) throws HibernateException {
		this.session.persist(entityName, object);
	}

	public void refresh(Object object) throws HibernateException {
		this.session.refresh(object);
	}

	public void refresh(Object object, LockMode lockMode) throws HibernateException {
		this.session.refresh(object, lockMode);
	}

	public void refresh(Object object, LockOptions lockOptions) throws HibernateException {
		this.session.refresh(object, lockOptions);
	}

	public void refresh(String entityName, Object object) throws HibernateException {
		this.session.refresh(entityName, object);
	}

	public void refresh(String entityName, Object object, LockOptions lockOptions) throws HibernateException {
		this.session.refresh(entityName, object, lockOptions);

	}

	public void replicate(Object object, ReplicationMode replicationMode) throws HibernateException {
		this.session.replicate(object, replicationMode);
	}

	public void replicate(String entityName, Object object, ReplicationMode replicationMode) throws HibernateException {
		this.session.replicate(entityName, object, replicationMode);
	}

	public Serializable save(Object object) throws HibernateException {
		return this.session.save(object);
	}

	public Serializable save(String entityName, Object object) throws HibernateException {
		return this.session.save(entityName, object);
	}

	public void saveOrUpdate(Object object) throws HibernateException {
		this.session.saveOrUpdate(object);
	}

	public void saveOrUpdate(String entityName, Object object) throws HibernateException {
		this.session.saveOrUpdate(entityName, object);
	}

	public SharedSessionBuilder sessionWithOptions() {
		return this.session.sessionWithOptions();
	}

	public void setCacheMode(CacheMode cacheMode) {
		this.session.setCacheMode(cacheMode);
	}

	public void setDefaultReadOnly(boolean readOnly) {
		this.session.setDefaultReadOnly(readOnly);
	}

	public void setFlushMode(FlushMode flushMode) {
		this.session.setFlushMode(flushMode);
	}

	public void setReadOnly(Object entityOrProxy, boolean readOnly) {
		this.session.setReadOnly(entityOrProxy, readOnly);
	}

	public void update(Object object) throws HibernateException {
		this.session.update(object);
	}

	public void update(String entityName, Object object) throws HibernateException {
		this.session.update(entityName, object);
	}
}
