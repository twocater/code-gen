package com.xunlei.game.velocity;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;

import java.io.File;

/**
 * Created by cpaladin on 2016/5/21 16:35.
 * Email: 784615815@qq.com
 */
public class CodeGenMain {
    public static void main(String[] args) {
//        CreateServletMain.main(args);
        CreateVoMain.main(args);
        CreateDaoMain.main(args);
        CreateDaoFactoryMain.main(args);
        CreateBoMain.main(args);
//        CreatePackageMain.main(args);
    }
}
