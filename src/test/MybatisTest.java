import com.tianlei.dao.UserMapper;
import com.tianlei.pojo.Product;
import com.tianlei.pojo.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by tianlei on 2017/8/2.
 */

public class MybatisTest {

    SqlSessionFactory sqlSessionFactory;

    @Before
    public void setUp() throws Exception {

//        String source = "mybatis-config.xml";
//        InputStream stream = Resources.getResourceAsStream(source);
//        sqlSessionFactory = new SqlSessionFactoryBuilder().build(stream);

    }

    @Test
    public void varDelegate() {

        String str = "0x056261b7427ff682ca5d014120331695ae23d29e";
        String hash = "0x3c80d06f2de392c7a6271b8741b035269898da5ccacd8f8011491d484debee5f";

//      SqlSession sqlSession = sqlSessionFactory.openSession();
//      UserMapper userMapper =  sqlSession.getMapper(UserMapper.class);
//      int count = userMapper.checkUser("13868074590");
//      int a = 0;


    }

    @Test
    public void mybatisByAnnotations() throws Exception {


        SqlSession sqlSession = this.sqlSessionFactory.openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
//            List<User> users = userMapper.getUsers();
//            User user = userMapper.selectUser(2);
        sqlSession.close();

    }

    @Test
    public void testMybatis() throws Exception {


        String source = "mybatis-config.xml";

        SqlSession sqlSession = sqlSessionFactory.openSession();
        //查询
        List<User> users = sqlSession.selectList("UserMapper.getUser");

        //插入
        User user = new User();
        user.setPhone("11011000100001");
        user.setUsername("sky");
        user.setPassword("password");
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        sqlSession.insert("UserMapper.insertUser", user);

        sqlSession.close();


    }

    @Test
    public String validted() {

        Product product = new Product();
        product.setName("商品名称");
        product.setCreateDatetime(new Date());


        //
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<Object>> set = validator.validate(product);

        // default is ""
        StringBuilder stringBuilder = new StringBuilder("");
        if (set.size() <= 0) {
            return stringBuilder.toString();
        }

        // 有异常信息
        Iterator<ConstraintViolation<Object>> iterator = set.iterator();
        while (iterator.hasNext()) {
            ConstraintViolation<Object> constraintViolation = iterator.next();

            //获取属性名称
            PathImpl path = (PathImpl) constraintViolation.getPropertyPath();
            String propertyName = path.getLeafNode().getName();
            if (propertyName != null) {

                stringBuilder.append(propertyName);
                stringBuilder.append(":");

            }

            //获取异常信息
            //注释的信息
            String msg = constraintViolation.getMessage();
            if (msg != null) {
                stringBuilder.append(msg);
                stringBuilder.append("\n");
            }

        }

        String lastInfoStr = stringBuilder.toString();

        return null;
    }

    public static void main(String[] args) {

        Runtime runtime = Runtime.getRuntime();

        try {

         Process process =  runtime.exec("pwd");
         int a = 10;

        } catch (Exception e)  {

        }

    }


}
