package academy.todo.exceptions;

public class DataAccessException extends RuntimeException {

    private static final long serialVersionUID = -3351685840325511723L;

    public DataAccessException(Exception exception) {
        super("A data access framework issue occurred", exception);
    }
}
