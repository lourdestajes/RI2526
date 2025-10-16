package uo.ri.cws.application.persistence.workorder;

import java.time.LocalDateTime;
import java.util.List;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkorderRecord;

public interface WorkOrderGateway extends Gateway<WorkorderRecord> {

    public List<WorkorderRecord> findByMechanicId(String id);
    public List<WorkorderRecord> findNotInvoicedByClientNif(String id);

    public class WorkorderRecord {
        public String id;
        public long version;
        public LocalDateTime createdAt;
        public LocalDateTime updatedAt;
        public String entityState;

        public String description;
        public LocalDateTime date;
        public double amount;
        public String state;
        public String invoiceId;
        public String mechanicId;
        public String vehicleId;

    }
}