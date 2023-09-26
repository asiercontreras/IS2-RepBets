package exceptions;
public class ObjectAlreadyExistException extends Exception {
    private static final long serialVersionUID = 1L;

    public ObjectAlreadyExistException()
    {
        super();
    }
    /**This exception is triggered if the event has already finished
     *@param s String of the exception
     */
    public ObjectAlreadyExistException(String s)
    {
        super(s);
    }
}
