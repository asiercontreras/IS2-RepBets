package exceptions;
public class QuestionAlreadyExist extends Exception {
    private static final long serialVersionUID = 1L;

    public QuestionAlreadyExist()
    {
        super();
    }
    /**This exception is triggered if the event has already finished
     *@param s String of the exception
     */
    public QuestionAlreadyExist(String s)
    {
        super(s);
    }
}
