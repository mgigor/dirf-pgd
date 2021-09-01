package serpro.dirf.pgd;

import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceProvider;
import javax.persistence.spi.PersistenceUnitInfo;
import javax.persistence.spi.ProviderUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("rawtypes")
public class PersistenceProviderStub implements PersistenceProvider {

	@Autowired
	private EntityManagerStub ems;

	@Override
	public EntityManagerFactory createEntityManagerFactory(String emName, Map map) {
		return new EntityManagerFactoryStub(ems);
	}

	@Override
	public EntityManagerFactory createContainerEntityManagerFactory(PersistenceUnitInfo info, Map map) {
		return new EntityManagerFactoryStub(ems);
	}

	@Override
	public void generateSchema(PersistenceUnitInfo info, Map map) {
		
	}

	@Override
	public boolean generateSchema(String persistenceUnitName, Map map) {
		return true;
	}

	@Override
	public ProviderUtil getProviderUtil() {
		return null;
	}

}
