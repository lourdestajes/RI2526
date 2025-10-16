package uo.ri.cws.application.persistence.provider;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.base.BaseRecord;
import uo.ri.cws.application.persistence.provider.ProviderGateway.ProviderRecord;

public interface ProviderGateway extends Gateway<ProviderRecord> {

    public Optional<ProviderRecord> findByNif(String nif) throws PersistenceException;

    public Optional<ProviderRecord> findByNameEmailPhone(String name, String email, String phone) throws PersistenceException;

    public List<ProviderRecord> findByName(String name) throws PersistenceException;
    
    public List<ProviderRecord> findBySparePartCode(String code) throws PersistenceException;
    
    public static class ProviderRecord extends BaseRecord {
    	public String nif;
    	public String name;
    	public String email;
    	public String phone;
    }
    
}
