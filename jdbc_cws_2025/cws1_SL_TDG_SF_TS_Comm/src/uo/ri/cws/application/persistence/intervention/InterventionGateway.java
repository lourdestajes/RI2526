package uo.ri.cws.application.persistence.intervention;

import java.time.LocalDateTime;
import java.util.List;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.intervention.InterventionGateway.InterventionRecord;

public interface InterventionGateway extends Gateway<InterventionRecord> {

    public List<InterventionRecord> findByMechanicId(String id);

    public class InterventionRecord {
        public String id;
        public long version;
        public LocalDateTime createdAt;
        public LocalDateTime updatedAt;
        public String entityState;

        public LocalDateTime date;
        public int minutes;
        public String mechanicId;
        public String workorderId;
    }
}