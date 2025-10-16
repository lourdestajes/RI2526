package uo.ri.cws.application.persistence.mechanic.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;

public class MechanicAssembler {

	public static MechanicRecord toRecord(ResultSet rs) throws SQLException {
		MechanicRecord dto = new MechanicRecord();
        dto.id = rs.getString("id");
        dto.name = rs.getString("name");
        dto.surname = rs.getString("surname");
        dto.nif = rs.getString("nif");
        dto.version = rs.getLong("version");
        dto.createdAt = rs.getTimestamp("createdat").toLocalDateTime();
        dto.updatedAt = rs.getTimestamp("updatedat").toLocalDateTime();
        dto.entityState = rs.getString("entityState");
        return dto;
	}
	
}
