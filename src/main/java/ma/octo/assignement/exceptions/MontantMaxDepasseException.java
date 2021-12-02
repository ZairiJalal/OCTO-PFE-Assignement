package ma.octo.assignement.exceptions;

public class MontantMaxDepasseException extends Exception {

    private static final long serialVersionUID = 1L;

    public MontantMaxDepasseException() {
    }

    public MontantMaxDepasseException(String message) {
        super(message);
    }
}
