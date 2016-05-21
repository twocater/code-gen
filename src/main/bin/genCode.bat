set classpath=.;.\lib\*;.\lib\;.\conf\*;.\conf\;
java -cp %classpath% com.xunlei.game.velocity.GetAllTableMain

::设置源代码目录，存放生成的源码
set src=src
::设置项目名称，打包生成的源码时，会用到
set projectName=dayouaccount
set lib=..\lib\

rd /s /q %src%

java -cp %classpath% com.xunlei.game.velocity.CodeGenMain -codeSrcDir %src%

::xcopy /e /y copysrc\* ..\src

call ant -Dsrc=%src% -DprojectName=%projectName% -buildfile %~dp0build-template.xml

::echo d | xcopy /e /y dist\lib-dao\codegen-*-dao.jar %lib%
::echo d | xcopy /e /y dist\lib-dao\codegen-dao-%projectName%.jar %lib%
xcopy /e /y dist\lib-dao\codegen-*-dao.jar %lib%
xcopy /e /y dist\lib-dao\codegen-dao-%projectName%.jar %lib%
pause
