package es.deusto.deustock.dao;

import es.deusto.deustock.data.auth.Token;

import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

public class TokenDAO implements IDAO<Token>{

    private static final TokenDAO instance = new TokenDAO();

    private IDBManager idbManager;

    private TokenDAO(){
        this.idbManager = DBManager.getInstance();
    }

    public static TokenDAO getInstance(){
        return instance;
    }

    public void setIdbManager(IDBManager idbManager){
        this.idbManager = idbManager;
    }

    @Override
    public boolean has(Object identity) throws SQLException {
        return get(identity) != null;
    }

    @Override
    public void store(Token object) throws SQLException {
        idbManager.store(object);
    }

    @Override
    public void update(Token object) throws SQLException {
        idbManager.update(object);
    }

    @Override
    public void delete(Token object) {
        idbManager.delete(object);
    }

    @Override
    public Token get(Object identity) throws SQLException {
        var whereCondition = "token == :token";
        HashMap<String,Object> params = new HashMap<>();
        params.put("token", identity);

        return (Token) idbManager.get(Token.class,whereCondition, params);
    }

    @Override
    public Collection<Token> getAll() {
        Collection<Object> result = idbManager.getAll(Token.class);

        return result.stream().map(c -> (Token) c).collect(Collectors.toList());
    }
}
