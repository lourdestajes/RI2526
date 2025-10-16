package uo.ri.cws.application.service.mechanic.crud;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;

public class MechanicAssembler {

	public static MechanicDto toDto(MechanicRecord record) {
        if (record == null) {
            return null;
        }
        MechanicDto dto = new MechanicDto();
        dto.id = record.id;
        dto.version = record.version;
        dto.nif = record.nif;
        dto.name = record.name;
        dto.surname = record.surname;
        return dto;
    }

    public static Optional<MechanicDto> toDto(Optional<MechanicRecord> recordOpt) {
        return recordOpt.map(MechanicAssembler::toDto);
    }

    public static List<MechanicDto> toDto(List<MechanicRecord> records) {
        return records.stream()
                .map(MechanicAssembler::toDto)
                .collect(Collectors.toList());
    }

    public static MechanicRecord toRecord(MechanicDto dto) {
        if (dto == null) {
            return null;
        }
        MechanicRecord record = new MechanicRecord();
        record.id = dto.id;
        record.version = dto.version;
        record.nif = dto.nif;
        record.name = dto.name;
        record.surname = dto.surname;
        return record;
    }

    public static Optional<MechanicRecord> toRecord(Optional<MechanicDto> dtoOpt) {
        return dtoOpt.map(MechanicAssembler::toRecord);
    }

    public static List<MechanicRecord> toRecord(List<MechanicDto> dtos) {
        return dtos.stream()
                .map(MechanicAssembler::toRecord)
                .collect(Collectors.toList());
    }
}
