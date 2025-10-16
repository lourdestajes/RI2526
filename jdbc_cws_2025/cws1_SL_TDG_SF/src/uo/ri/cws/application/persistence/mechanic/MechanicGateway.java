package uo.ri.cws.application.persistence.mechanic;

import java.time.LocalDateTime;
import java.util.Optional;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicRecord;

public interface MechanicGateway extends Gateway<MechanicRecord> {

    Optional<MechanicRecord> findByNif(String nif);

    public class MechanicRecord {
        public String id;
        public long version;
        public LocalDateTime createdAt;
        public LocalDateTime updatedAt;
        public String entityState;

        public String nif;
        public String name;
        public String surname;
    }
}
