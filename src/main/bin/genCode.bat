set classpath=.;.\lib\*;.\lib\;.\conf\*;.\conf\;.\vm\;.\vm\*;
java -cp %classpath% com.xunlei.game.velocity.GetAllTableMain

::设置源代码目录，存放生成的源码
set src=src
::设置项目名称，打包生成的源码时，会用到
set projectName=project

rd /s /q %src%

::java -cp %classpath% com.xunlei.game.velocity.CreateServletMain -codeSrcDir %src%

java -cp %classpath% com.xunlei.game.velocity.CreateVoMain -codeSrcDir %src%

java -cp %classpath% com.xunlei.game.velocity.CreateDaoMain -codeSrcDir %src%

java -cp %classpath% com.xunlei.game.velocity.CreateDaoFactoryMain -codeSrcDir %src%

java -cp %classpath% com.xunlei.game.velocity.CreateBoMain -codeSrcDir %src%

::java -cp %classpath% com.xunlei.game.velocity.CreatePackageMain -codeSrcDir %src%

::xcopy /e /y copysrc\* ..\src

call ant -Dsrc=%src% -DprojectName=%projectName% -buildfile %~dp0build-template.xml
pause
