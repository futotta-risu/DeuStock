package es.deusto.deustock.client.simulation.investment.operations;


public enum OperationType {
    LONG, SHORT;

    @Override
    public String toString() {
        return name();
    }
}
