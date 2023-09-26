package exceptions;
public class CannotCloseEvent extends Exception {
    private static final long serialVersionUID = 1L;

    public CannotCloseEvent()
    {
        super();
    }
    /**This exception is triggered if the event has already finished
     *@param s String of the exception
     */
    public CannotCloseEvent(String s)
    {
        super(s);
    }
}
