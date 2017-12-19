package grammar;

/**
 * Created by tianlei on 2017/十二月/18.
 */
public class S {
    private static S ourInstance = new S();

    public static S getInstance() {
        return ourInstance;
    }

    private S() {
    }
}
