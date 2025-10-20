package uo.ri.cws.domain;

public class Associations {

	public static class Owns {

		public static void link(Client client, Vehicle vehicle) {
		}

		public static void unlink(Client cliente, Vehicle vehicle) {
		}

	}

	public static class Classifies {

		public static void link(VehicleType vehicleType, Vehicle vehicle) {
		}

		public static void unlink(VehicleType tipoVehicle, Vehicle vehicle) {
		}
	}

	public static class Holds {

		public static void link(PaymentMean mean, Client client) {
		}

		public static void unlink(Client client, PaymentMean mean) {
		}
	}

	public static class Fixes {

		public static void link(Vehicle vehicle, WorkOrder workOrder) {
		}

		public static void unlink(Vehicle vehicle, WorkOrder workOrder) {
		}
	}

	public static class Bills {

		public static void link(Invoice invoice, WorkOrder workOrder) {
		}

		public static void unlink(Invoice invoice, WorkOrder workOrder) {
		}
	}

	public static class Settles {

		public static void link(Invoice invoice, Charge cargo, PaymentMean mp) {
		}

		public static void unlink(Charge cargo) {
		}
	}

	public static class Assigns {

		public static void link(Mechanic mechanic, WorkOrder workOrder) {
		}

		public static void unlink(Mechanic mechanic, WorkOrder workOrder) {
		}
	}

	public static class Intervenes {

		public static void link(WorkOrder workOrder,
				Intervention intervention, Mechanic mechanic) {
		}

		public static void unlink(Intervention intervention) {
		}
	}

	public static class Substitutes {

		static void link(SparePart sparePart, Substitution substitution,
				Intervention intervention) {
		}

		public static void unlink(Substitution substitution) {
		}
	}

}
