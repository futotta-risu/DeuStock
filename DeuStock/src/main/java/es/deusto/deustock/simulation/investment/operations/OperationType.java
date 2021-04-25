package es.deusto.deustock.simulation.investment.operations;

import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable
public enum OperationType {
    LONG, SHORT;

    OperationType(){}
}
