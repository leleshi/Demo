package lele.shi.demo;

import lele.shi.demo.exception.CheckException;
import lele.shi.demo.model.ResultObj;
import lele.shi.demo.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.StringUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class DemoApplicationTests {

    @Test
    public void checkException() {
        User user = new User();
        try {
            userLogin(user);
        } catch (CheckException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }

    private void userLogin(User user) throws CheckException {
        if (StringUtils.isEmpty(user.getName())){
            throw new CheckException(999, "用户名为空");
        }
    }

}
