package exceptions;
public class UserNotFound extends Exception {
    private static final long serialVersionUID = 1L;

    public UserNotFound()
    {
        super();
    }
    /**This exception is triggered if the question already exists 
     *@param s String of the exception
     */
    public UserNotFound(String s)
    {
        super(s);
    }
}
