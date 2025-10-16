package uo.ri.cws.application.persistence.intervention.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import uo.ri.cws.application.persistence.intervention.InterventionGateway.InterventionRecord;

public class InterventionAssembler {

    public static List<InterventionRecord> toInterventionList(ResultSet rs)
            throws SQLException {
        List<InterventionRecord> res = new ArrayList<>();

        while (rs.next()) {
            InterventionRecord ir = new InterventionRecord();
            ir.id = rs.getString("id");
            ir.version = rs.getLong("version");
            ir.createdAt = rs
                    .getTimestamp("createdAt").toLocalDateTime();
            ir.updatedAt = rs
                    .getTimestamp("updatedAt").toLocalDateTime();
            ir.entityState = rs.getString("entityState");

            ir.date = rs.getTimestamp("date").toLocalDateTime();
            ir.minutes = rs.getInt("minutes");
            ir.mechanicId = rs.getString("mechanic_Id");
            ir.workorderId = rs.getString("workorder_Id");

            res.add(ir);
        }
        return res;
    }

}
