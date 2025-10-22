package uo.ri.cws.domain;

import java.util.Set;

public class Associations {

	public static class Binds {

		public static void link(Mechanic mechanic, Contract contract) {
			contract._setMechanic(mechanic);
			mechanic._getContracts().add(contract);
		}

		public static void unlink(Mechanic mechanic, Contract contract) {
			mechanic._getContracts().remove(contract);
			contract._setMechanic(null);
		}

	}
	
	public static class Categorizes {

		public static void link(ProfessionalGroup group, Contract contract) {
			contract._setProfessionalGroup(group);
			group._getContracts().add(contract);
		}

		public static void unlink(ProfessionalGroup group, Contract contract) {
			group._getContracts().remove(contract);
			contract._setProfessionalGroup(null);
		}

	}
	
	public static class Defines {

		public static void link(ContractType type, Contract contract) {
			contract._setContractType(type);
			type._getContracts().add(contract);
		}

		public static void unlink(ContractType type, Contract contract) {
			type._getContracts().remove(contract);
			contract._setContractType(null);
		}

	}
	
	public static class Generates {

		public static void link(Contract contract, Payroll payroll) {
			payroll._setContract(contract);
			contract._getPayrolls().add(payroll);
		}

		public static void unlink(Contract contract, Payroll payroll) {
			contract._getPayrolls().remove(payroll);
			payroll._setContract(null);
		}

	}
	
	public static class Owns {

		public static void link(Client client, Vehicle vehicle) {
			vehicle._setClient(client);
			client._getVehicles().add(vehicle);
		}

		public static void unlink(Client cliente, Vehicle vehicle) {
			cliente._getVehicles().remove(vehicle);
			vehicle._setClient(null);
		}

	}

	public static class Classifies {

		public static void link(VehicleType vehicleType, Vehicle vehicle) {
			vehicle._setVehicleType(vehicleType);
			vehicleType._getVehicles().add(vehicle);
		}

		public static void unlink(VehicleType tipoVehicle, Vehicle vehicle) {
			tipoVehicle._getVehicles().remove(vehicle);
			vehicle._setVehicleType(null);
		}
	}

	public static class Holds {

		public static void link(PaymentMean mean, Client client) {
			mean._setClient(client);
			client._getPaymentMeans().add(mean);
		}

		public static void unlink(Client client, PaymentMean mean) {
			client._getPaymentMeans().remove(mean);
			mean._setClient(null);
		}
	}

	public static class Fixes {

		public static void link(Vehicle vehicle, WorkOrder workOrder) {
			workOrder._setVehicle(vehicle);
			vehicle._getWorkOrders().add(workOrder);
		}

		public static void unlink(Vehicle vehicle, WorkOrder workOrder) {
			vehicle._getWorkOrders().remove(workOrder);
			workOrder._setVehicle(null);
		}
	}

	public static class Bills {

		public static void link(Invoice invoice, WorkOrder workOrder) {
			workOrder._setInvoice(invoice);
			invoice._getWorkOrders().add(workOrder);
		}

		public static void unlink(Invoice invoice, WorkOrder workOrder) {
			invoice._getWorkOrders().remove(workOrder);
			workOrder._setInvoice(null);
		}
	}

	public static class Settles {

		public static void link(Invoice invoice, Charge cargo, PaymentMean mp) {
			cargo._setInvoice(invoice);
			cargo._setPaymentMean(mp);
			invoice._getCharges().add(cargo);
			mp._getCharges().add(cargo);
		}

		public static void unlink(Charge cargo) {
			cargo.getInvoice()._getCharges().remove(cargo);
			cargo.getPaymentMean()._getCharges().remove(cargo);
			cargo._setInvoice(null);
			cargo._setPaymentMean(null);
		}
	}

	public static class Assigns {

		public static void link(Mechanic mechanic, WorkOrder workOrder) {
			workOrder._setMechanic(mechanic);
			mechanic._getAssigned().add(workOrder);
		}

		public static void unlink(Mechanic mechanic, WorkOrder workOrder) {
			mechanic._getAssigned().remove(workOrder);
			workOrder._setMechanic(null);
		}
	}

	public static class Intervenes {

		public static void link(WorkOrder workOrder,
				Intervention intervention, Mechanic mechanic) {
			intervention._setWorkOrder(workOrder);
			intervention._setMechanic(mechanic);
			Set<Intervention> woInterventions = workOrder._getInterventions();
			if (woInterventions.contains(intervention)) {
				System.out.println("contains");
			}
			workOrder._getInterventions().add(intervention);
			mechanic._getInterventions().add(intervention);
		}

		public static void unlink(Intervention intervention) {
			intervention.getWorkOrder()._getInterventions().remove(intervention);
			intervention.getMechanic()._getInterventions().remove(intervention);
			intervention._setWorkOrder(null);
			intervention._setMechanic(null);
		}
	}

	public static class Substitutes {

		static void link(SparePart sparePart, Substitution substitution,
				Intervention intervention) {
			substitution._setSparePart(sparePart);
			substitution._setIntervention(intervention);
			sparePart._getSubstitutions().add(substitution);
			intervention._getSubstitutions().add(substitution);
		}

		public static void unlink(Substitution substitution) {
			substitution.getSparePart()._getSubstitutions().remove(substitution);
			substitution.getIntervention()._getSubstitutions().remove(substitution);
			substitution._setSparePart(null);
			substitution._setIntervention(null);
		}
	}

}
