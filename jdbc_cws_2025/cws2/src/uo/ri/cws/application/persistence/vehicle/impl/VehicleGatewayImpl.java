package uo.ri.cws.application.persistence.vehicle.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.util.executor.Jdbc;
import uo.ri.cws.application.persistence.util.jdbc.Queries;
import uo.ri.cws.application.persistence.vehicle.VehicleGateway;

public class VehicleGatewayImpl implements VehicleGateway {

	private VehicleAssembler assembler = new VehicleAssembler();

	 
	public void add(VehicleRecord t) throws PersistenceException {
		// TODO Auto-generated method stub
		
	}

	 
	public void remove(String id) throws PersistenceException {
		// TODO Auto-generated method stub
		
	}

	 
	public void update(VehicleRecord t) throws PersistenceException {
		// TODO Auto-generated method stub
		
	}

	 
	public Optional<VehicleRecord> findById(String id) throws PersistenceException {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	 
	public List<VehicleRecord> findAll() throws PersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	 
	public Optional<VehicleRecord> findByPlate(String plate) throws PersistenceException {
	    try {
	        Connection c = Jdbc.getCurrentConnection();
	        try (PreparedStatement pst = c.prepareStatement(
	                Queries.get("TVEHICLES_FINDBYPLATE")
	                )) {
	            pst.setString(1, plate);
	            try (ResultSet rs = pst.executeQuery()) {
	                return new VehicleAssembler().toOptionalRecord(rs);
	            }
	        }
	    } catch(SQLException e) {
	        throw new PersistenceException(e);
	    }
	}
	
}
