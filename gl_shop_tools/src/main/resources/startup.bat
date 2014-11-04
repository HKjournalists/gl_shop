@echo off

REM #
REM # $RCSfile$
REM # $Revision: 1102 $
REM # $Date: 2014-10-30 15:11:30 -0300 $
REM # $Author: Bill Huang
REM #


if "%JAVA_HOME%" == "" goto javaerror
if not exist "%JAVA_HOME%\bin\java.exe" goto javaerror
goto run

:javaerror
echo.
echo Error: JAVA_HOME environment variable not set, scheduleServer not started.
echo.
goto end


:run
set BASEDIR=..
set CLASSPATH=%BASEDIR%\conf
"%JAVA_HOME%\bin\java" -server -ms128m -mx512m -classpath %CLASSPATH% -Dbase.dir=%BASEDIR% -jar %BASEDIR%\schedule-lib.jar
goto end

:end


pause

