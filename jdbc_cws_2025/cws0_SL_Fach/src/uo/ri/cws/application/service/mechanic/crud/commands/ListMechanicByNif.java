package uo.ri.cws.application.service.mechanic.crud.commands;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.exception.BusinessException;
import uo.ri.util.jdbc.Jdbc;

public class ListMechanicByNif {

    private static final String TMECHANICS_FINDBYNIF = 
            "SELECT ID, NAME, SURNAME, nif, VERSION FROM TMECHANICS WHERE NIF = ?";
    private String nif;

    public ListMechanicByNif(String nif) {
        ArgumentChecks.isNotEmpty(nif, "Mechanic NIF cannot be null or empty");
        this.nif = nif;
    }

    public Optional<MechanicDto> execute() throws BusinessException {
        Optional<MechanicDto> result = Optional.empty();
        try (Connection c = Jdbc.createThreadConnection()) {
            try (PreparedStatement pst = c
                    .prepareStatement(TMECHANICS_FINDBYNIF)) {
                pst.setString(1, nif);
                try (ResultSet rs = pst.executeQuery()) {
                    if (rs.next()) {
                        MechanicDto dto = new MechanicDto();
                        dto.id = rs.getString(1);
                        dto.name = rs.getString(2);
                        dto.surname = rs.getString(3);
                        dto.nif = rs.getString(4);
                        dto.version = rs.getLong(5);
                        result = Optional.of(dto);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}