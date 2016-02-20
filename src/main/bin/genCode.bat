set classpath=.;.\lib\*;.\lib\;.\conf\*;.\conf\;.\vm\;.\vm\*;
java -cp %classpath% com.xunlei.game.velocity.GetAllTableMain

java -cp %classpath% com.xunlei.game.velocity.CreateServletMain -codeSrcDir ../src

java -cp %classpath% com.xunlei.game.velocity.CreateVoMain -codeSrcDir ../src

java -cp %classpath% com.xunlei.game.velocity.CreateDaoMain -codeSrcDir ../src

java -cp %classpath% com.xunlei.game.velocity.CreateDaoFactoryMain -codeSrcDir ../src

java -cp %classpath% com.xunlei.game.velocity.CreateBoMain -codeSrcDir ../src

java -cp %classpath% com.xunlei.game.velocity.CreatePackageMain -codeSrcDir ../src

xcopy /e /y copysrc\* ..\src
pause
