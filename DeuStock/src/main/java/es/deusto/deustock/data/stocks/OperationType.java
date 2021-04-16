package es.deusto.deustock.data.stocks;

import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable
public enum OperationType {
    LONG, SHORT
}
