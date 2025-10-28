package uo.ri.cws.application.persistence.workorder;

import java.time.LocalDateTime;
import java.util.List;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.base.BaseRecord;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkOrderRecord;

public interface WorkOrderGateway extends Gateway<WorkOrderRecord> {

    /**
     * @param nif, the client nif
     * @return a list of all work orders not invoiced for a specific client
     * @throws PersistenceException
     */
    List<WorkOrderRecord> findNotInvoicedByClientNif(String nif);

    /**
     * @param workorder Ids, list of workorders to retrieve
     * @return list of workorder dto whose id is included in the parameter,
     *         probably empty
     * @throws PersistenceException
     */
    List<WorkOrderRecord> findByIds(List<String> workOrderIds);

    /**
     * @return a list of all work orders for an specific vehicle id
     * @throws PersistenceException
     */
    List<WorkOrderRecord> findByVehicleId(String id);

    /**
     * @param a mechanic id
     * @return a list of all work orders assigned to this specific mechanic
     * @throws PersistenceException
     */
    List<WorkOrderRecord> findByMechanicId(String id);

    /**
     * @param the state
     * @return a list of all work orders in the specific state
     * 
     */
    List<WorkOrderRecord> findByState(String state);

    /**
     * @return a list of all invoiced work orders for an specific vehicle id
     * @throws PersistenceException
     */
    List<WorkOrderRecord> findInvoicedByVehicleId(String id);

    public static class WorkOrderRecord extends BaseRecord {
        public String vehicleId;
        public String description;
        public LocalDateTime date;
        public double amount;
        public String state;

        // might be null
        public String mechanicId;
        public String invoiceId;
    }

}