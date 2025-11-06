package uo.ri.cws.domain;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name = "TCONTRACTS", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "mechanic_id", "startdate" }) })
public class Contract extends BaseEntity {

    public enum ContractState {
        IN_FORCE, TERMINATED
    }

    @ManyToOne
    private Mechanic mechanic;

    private LocalDate startDate;
    private LocalDate endDate;
    private double annualBaseSalary;
    private double settlement;
    private double taxRate;

    @Enumerated(EnumType.STRING)
    private ContractState state = ContractState.IN_FORCE;

    // Atributos accidentales
    @OneToMany(mappedBy = "contract")
    private Set<Payroll> payrolls = new HashSet<> ( );
    @ManyToOne
    private ContractType contractType;
    @ManyToOne
    private ProfessionalGroup professionalGroup;

    Contract ( ) {
        // for JPA
    }

    public Contract ( Mechanic mechanic, ContractType type,
            ProfessionalGroup category, LocalDate signingDate,
            LocalDate endDate, double baseSalary ) {
        validateArguments ( mechanic, type, category, signingDate, endDate,
                baseSalary );
        fillEntity ( signingDate, endDate, baseSalary, type );
        terminatePreviousContract ( mechanic );
        Associations.Binds.link ( mechanic, this );
        Associations.Categorizes.link ( category, this );
        Associations.Defines.link ( type, this );
    }

    private void terminatePreviousContract ( Mechanic mechanic ) {
        mechanic.getContractInForce ( )
            .ifPresent ( c -> c.terminate ( LocalDate.now ( ) ) );
    }

    private void fillEntity ( LocalDate signingDate, LocalDate endDate,
            double baseSalary, ContractType type ) {
        this.startDate = signingDate.withDayOfMonth ( 1 );
        this.endDate = ("FIXED_TERM".equals ( type.getName ( ) ))
                ? endDate.withDayOfMonth ( endDate.lengthOfMonth ( ) )
                : null;
        this.annualBaseSalary = baseSalary;
        this.taxRate = findTaxRate ( );
        this.settlement = 0.0;
    }

    private void validateArguments ( Mechanic mechanic, ContractType type,
            ProfessionalGroup category, LocalDate signingDate,
            LocalDate endDate, double baseSalary ) {
        ArgumentChecks.isNotNull ( mechanic, "Mechanic cannot be null" );
        ArgumentChecks.isNotNull ( type, "Contract type cannot be null" );
        ArgumentChecks.isNotNull ( category,
                "Professional group cannot be null" );
        ArgumentChecks.isNotNull ( signingDate, "Signing date cannot be null" );
        ArgumentChecks.isTrue ( baseSalary > 0,
                "Base salary must be positive" );

        if ( "FIXED_TERM".equals ( type.getName ( ) ) ) {
            ArgumentChecks.isNotNull ( endDate );
            ArgumentChecks.isTrue ( endDate.isAfter ( signingDate ),
                    "End date must be after signing date" );
        }
    }

    public Contract ( Mechanic m, ContractType t, ProfessionalGroup pg,
            LocalDate d, double salary ) {
        this ( m, t, pg, d, null, salary );
    }

    public Double getAnnualBaseSalary ( ) {
        return this.annualBaseSalary;
    }

    public Mechanic getMechanic ( ) {
        return mechanic;
    }

    public ContractType getContractType ( ) {
        return contractType;
    }

    public ProfessionalGroup getProfessionalGroup ( ) {
        return professionalGroup;
    }

    public LocalDate getStartDate ( ) {
        return startDate;
    }

    public LocalDate getEndDate ( ) {
        return endDate;
    }

    public double getSettlement ( ) {
        return settlement;
    }

    public double getTaxRate ( ) {
        return taxRate;
    }

    public boolean isInForce ( ) {
        return state == ContractState.IN_FORCE;
    }

    public boolean isTerminated ( ) {
        return state == ContractState.TERMINATED;
    }

    public Set<Payroll> getPayrolls ( ) {
        return new HashSet<> ( payrolls );
    }

    void _setMechanic ( Mechanic mechanic ) {
        this.mechanic = mechanic;
    }

    void _setProfessionalGroup ( ProfessionalGroup group ) {
        this.professionalGroup = group;
    }

    void _setContractType ( ContractType t ) {
        this.contractType = t;
    }

    Set<Payroll> _getPayrolls ( ) {
        return payrolls;
    }

	public String getState() {
		return state.toString();
	}

    @Override
    public String toString ( ) {
        return "Contract [mechanic=" + mechanic + ", startDate=" + startDate
                + ", endDate=" + endDate + ", annualBaseSalary="
                + annualBaseSalary + ", settlement=" + settlement + ", taxRate="
                + taxRate + ", state=" + state + ", type=" + contractType
                + ", professionalGroup="
                + professionalGroup + "]";
    }

    private double findTaxRate ( ) {
        if ( annualBaseSalary < 12450 ) {
            return 0.19;
        } else if ( annualBaseSalary < 20200 ) {
            return 0.24;
        } else if ( annualBaseSalary < 35200 ) {
            return 0.30;
        } else if ( annualBaseSalary < 60000 ) {
            return 0.37;
        } else if ( annualBaseSalary < 300000 ) {
            return 0.45;
        } else {
            return 0.47;
        }
    }

    public void terminate ( LocalDate endDate ) {
        ArgumentChecks.isNotNull ( endDate, "End date cannot be null" );
        checkIsNotFinished ( );
        checkEndDate ( endDate );
        this.endDate = endDate.withDayOfMonth ( endDate.lengthOfMonth ( ) );
        this.settlement = calculateSettlement ( );
        this.state = ContractState.TERMINATED;
    }

    private double calculateSettlement ( ) {
        int fullYearsInService = calculateFullYearsInService ( );
        if ( fullYearsInService < 1 ) {
            return 0.0;
        }
        double avgDailySalary = calculateAverageDailySalary ( );
        double compensationDays = contractType.getCompensationDaysPerYear ( );
        return fullYearsInService * compensationDays * avgDailySalary;
    }

    private double calculateAverageDailySalary ( ) {
        List<Payroll> last12Payrolls = payrolls.stream ( )
            .sorted ( ( p1, p2 ) -> p2.getDate ( )
                .compareTo ( p1.getDate ( ) ) )
            .limit ( 12 )
            .toList ( );

        return last12Payrolls.stream ( )
            .mapToDouble ( Payroll::getGrossSalary )
            .sum ( ) / 365;
    }

    private int calculateFullYearsInService ( ) {
        return (int) (ChronoUnit.DAYS.between ( startDate,
                endDate.plusDays ( 1 ) ) / 365);
    }

    private void checkEndDate ( LocalDate endDate ) {
        if ( endDate.isBefore ( startDate ) ) {
            throw new IllegalArgumentException (
                    "End date cannot be before start date" );
        }
    }

    private void checkIsNotFinished ( ) {
        if ( isTerminated ( ) ) {
            throw new IllegalStateException (
                    "Contract is already terminated" );
        }
    }

	public void setAnnualBaseSalary(double arg) {
		ArgumentChecks.isTrue(arg > 0, "Invalid annual base salary");
		this.annualBaseSalary = arg;
	}

	public void setEndDate(LocalDate arg) {
		ArgumentChecks.isNotNull(arg, "Invalid null end date");
		ArgumentChecks.isTrue(arg.isAfter(this.startDate), "Invalid end date is not after start date");
		this.endDate= arg;
	}

}
