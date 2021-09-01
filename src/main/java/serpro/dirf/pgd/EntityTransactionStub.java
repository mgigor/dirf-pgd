package serpro.dirf.pgd;

import javax.persistence.EntityTransaction;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EntityTransactionStub implements EntityTransaction{
	
	@Override
	public void begin() {

	}

	@Override
	public void commit() {
		
	}

	@Override
	public void rollback() {
		
	}

	@Override
	public void setRollbackOnly() {
		
	}

	@Override
	public boolean getRollbackOnly() {
		return false;
	}

	@Override
	public boolean isActive() {
		return true;
	}

}
