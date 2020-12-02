package cn.congee.api.test;

import cn.congee.api.constant.Constant;
import cn.congee.api.util.FileUtils;
import org.junit.Test;

import java.util.List;

/**
 * @Author: yang
 * @Date: 2020-12-02 20:58
 */
public class FileUtilsTest {

    @Test
    public void readFileTest() {
        List<String> inputs = FileUtils.readFile(Constant.INPUT_FILE_NAME);
        inputs.forEach(line -> System.out.println(line));
    }

}
