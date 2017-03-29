package com.twocater.daocodegen.velocity;

/**
 * Created by cpaladin on 2016/5/21 16:35.
 * Email: 784615815@qq.com
 */
public class CodeGenMain {
    public static void main(String[] args) {
//        CreateServletMain.main(args);

        GetAllTableMain.main(null);
        args = new String[]{"-codeSrcDir", "src/main/java"};
        CreateVoMain.main(args);

        CreateDaoMain.main(args);
        CreateDaoFactoryMain.main(args);
        CreateBoMain.main(args);



//        CreatePackageMain.main(args);
    }
}
