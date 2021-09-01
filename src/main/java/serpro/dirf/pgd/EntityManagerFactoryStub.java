package serpro.dirf.pgd;

import java.util.Map;

import javax.persistence.Cache;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.Query;
import javax.persistence.SynchronizationType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.metamodel.Metamodel;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@SuppressWarnings("rawtypes")
public class EntityManagerFactoryStub implements EntityManagerFactory{

	private EntityManagerStub ems;
	
	@Override
	public EntityManager createEntityManager() {
		return ems;
	}

	@Override
	public EntityManager createEntityManager(Map map) {
		return ems;
	}

	@Override
	public EntityManager createEntityManager(SynchronizationType synchronizationType) {
		return ems;
	}

	@Override
	public EntityManager createEntityManager(SynchronizationType synchronizationType, Map map) {
		return ems;
	}

	@Override
	public CriteriaBuilder getCriteriaBuilder() {
		return ems.getEm().getEntityManagerFactory().getCriteriaBuilder();
	}

	@Override
	public Metamodel getMetamodel() {
		return ems.getEm().getEntityManagerFactory().getMetamodel();
	}

	@Override
	public boolean isOpen() {
		return true;
	}

	@Override
	public void close() {
	}

	@Override
	public Map<String, Object> getProperties() {
		return ems.getEm().getEntityManagerFactory().getProperties();
	}

	@Override
	public Cache getCache() {
		return ems.getEm().getEntityManagerFactory().getCache();
	}

	@Override
	public PersistenceUnitUtil getPersistenceUnitUtil() {
		return ems.getEm().getEntityManagerFactory().getPersistenceUnitUtil();
	}

	@Override
	public void addNamedQuery(String name, Query query) {
		ems.getEm().getEntityManagerFactory().addNamedQuery(name, query);
	}

	@Override
	public <T> T unwrap(Class<T> cls) {
		return ems.getEm().getEntityManagerFactory().unwrap(cls);
	}

	@Override
	public <T> void addNamedEntityGraph(String graphName, EntityGraph<T> entityGraph) {
		ems.getEm().getEntityManagerFactory().addNamedEntityGraph(graphName, entityGraph);
	}

}
