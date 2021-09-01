package serpro.dirf.pgd;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.metamodel.Metamodel;

import org.springframework.stereotype.Component;

import lombok.Getter;

@Component
@SuppressWarnings("rawtypes")
public class EntityManagerStub implements EntityManager{

	@Getter
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public void persist(Object entity) {
		em.persist(entity);
	}

	@Override
	public <T> T merge(T entity) {
		return em.merge(entity);
	}

	@Override
	public void remove(Object entity) {
		em.remove(entity);
	}
	
	@Override
	public void flush() {
		em.flush();
	}

	@Override
	public <T> T find(Class<T> entityClass, Object primaryKey) {
		return em.find(entityClass, primaryKey);
	}

	@Override
	public <T> T find(Class<T> entityClass, Object primaryKey, Map<String, Object> properties) {
		return em.find(entityClass, primaryKey, properties);
	}

	@Override
	public <T> T find(Class<T> entityClass, Object primaryKey, LockModeType lockMode) {
		return em.find(entityClass, primaryKey, lockMode);
	}

	@Override
	public <T> T find(Class<T> entityClass, Object primaryKey, LockModeType lockMode, Map<String, Object> properties) {
		return em.find(entityClass, primaryKey, lockMode, properties);
	}

	@Override
	public <T> T getReference(Class<T> entityClass, Object primaryKey) {
		return em.getReference(entityClass, primaryKey);
	}

	@Override
	public void setFlushMode(FlushModeType flushMode) {
		em.setFlushMode(flushMode);
	}

	@Override
	public FlushModeType getFlushMode() {
		return em.getFlushMode();
	}

	@Override
	public void lock(Object entity, LockModeType lockMode) {
		
	}

	@Override
	public void lock(Object entity, LockModeType lockMode, Map<String, Object> properties) {
		
	}

	@Override
	public void refresh(Object entity) {
		em.refresh(entity);

	}

	@Override
	public void refresh(Object entity, Map<String, Object> properties) {
		em.refresh(entity, properties);
		
	}

	@Override
	public void refresh(Object entity, LockModeType lockMode) {
		em.refresh(entity, lockMode);
		
	}

	@Override
	public void refresh(Object entity, LockModeType lockMode, Map<String, Object> properties) {
		em.refresh(entity, lockMode, properties);
		
	}

	@Override
	public void clear() {
		em.clear();
	}

	@Override
	public void detach(Object entity) {
		em.detach(entity);
	}

	@Override
	public boolean contains(Object entity) {
		return em.contains(entity);
	}

	@Override
	public LockModeType getLockMode(Object entity) {
		return null;
	}

	@Override
	public void setProperty(String propertyName, Object value) {
		
	}

	@Override
	public Map<String, Object> getProperties() {
		return null;
	}

	@Override
	public Query createQuery(String qlString) {
		return em.createQuery(qlString);
	}

	@Override
	public <T> TypedQuery<T> createQuery(CriteriaQuery<T> criteriaQuery) {
		return em.createQuery(criteriaQuery);
	}

	@Override
	public Query createQuery(CriteriaUpdate updateQuery) {
		return em.createQuery(updateQuery);
	}

	@Override
	public Query createQuery(CriteriaDelete deleteQuery) {
		return em.createQuery(deleteQuery);
	}

	@Override
	public <T> TypedQuery<T> createQuery(String qlString, Class<T> resultClass) {
		return em.createQuery(qlString, resultClass);
	}

	@Override
	public Query createNamedQuery(String name) {
		return null;
	}

	@Override
	public <T> TypedQuery<T> createNamedQuery(String name, Class<T> resultClass) {
		return null;
	}

	@Override
	public Query createNativeQuery(String sqlString) {
		return em.createNativeQuery(sqlString);
	}

	@Override
	public Query createNativeQuery(String sqlString, Class resultClass) {
		return em.createNativeQuery(sqlString, resultClass);
	}

	@Override
	public Query createNativeQuery(String sqlString, String resultSetMapping) {
		return em.createNativeQuery(sqlString, resultSetMapping);
	}

	@Override
	public StoredProcedureQuery createNamedStoredProcedureQuery(String name) {
		return null;
	}

	@Override
	public StoredProcedureQuery createStoredProcedureQuery(String procedureName) {
		return null;
	}

	@Override
	public StoredProcedureQuery createStoredProcedureQuery(String procedureName, Class... resultClasses) {
		return null;
	}

	@Override
	public StoredProcedureQuery createStoredProcedureQuery(String procedureName, String... resultSetMappings) {
		return null;
	}

	@Override
	public void joinTransaction() {
		
	}

	@Override
	public boolean isJoinedToTransaction() {
		return false;
	}

	@Override
	public <T> T unwrap(Class<T> cls) {
		return null;
	}

	@Override
	public Object getDelegate() {
		return null;
	}

	@Override
	public void close() {
		
	}

	@Override
	public boolean isOpen() {
		return true;
	}

	@Override
	public EntityTransaction getTransaction() {
		return new EntityTransactionStub();
	}

	@Override
	public EntityManagerFactory getEntityManagerFactory() {
		return new EntityManagerFactoryStub(this);
	}

	@Override
	public CriteriaBuilder getCriteriaBuilder() {
		return null;
	}

	@Override
	public Metamodel getMetamodel() {
		return em.getMetamodel();
	}

	@Override
	public <T> EntityGraph<T> createEntityGraph(Class<T> rootType) {
		return null;
	}

	@Override
	public EntityGraph<?> createEntityGraph(String graphName) {
		return null;
	}

	@Override
	public EntityGraph<?> getEntityGraph(String graphName) {
		return null;
	}

	@Override
	public <T> List<EntityGraph<? super T>> getEntityGraphs(Class<T> entityClass) {
		return null;
	}

}
