@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  jGemstone-Client startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Add default JVM options here. You can also use JAVA_OPTS and J_GEMSTONE_CLIENT_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if "%ERRORLEVEL%" == "0" goto init

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto init

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:init
@rem Get command-line arguments, handling Windows variants

if not "%OS%" == "Windows_NT" goto win9xME_args

:win9xME_args
@rem Slurp the command line arguments.
set CMD_LINE_ARGS=
set _SKIP=2

:win9xME_args_slurp
if "x%~1" == "x" goto execute

set CMD_LINE_ARGS=%*

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\jGemstone-Client.jar;%APP_HOME%\lib\javacsv-2.0.jar;%APP_HOME%\lib\json-20180130.jar;%APP_HOME%\lib\qrgen-1.4.jar;%APP_HOME%\lib\sqlite-jdbc-3.25.2.jar;%APP_HOME%\lib\jfoenix-8.0.7.jar;%APP_HOME%\lib\ikonli-javafx-2.1.1.jar;%APP_HOME%\lib\ikonli-elusive-pack-2.1.1.jar;%APP_HOME%\lib\ikonli-materialdesign-pack-2.1.1.jar;%APP_HOME%\lib\GMapsFX-2.12.0.jar;%APP_HOME%\lib\JavaAPIforKml-2.2.0.jar;%APP_HOME%\lib\javase-3.0.0.jar;%APP_HOME%\lib\ikonli-core-2.1.1.jar;%APP_HOME%\lib\logback-classic-1.2.1.jar;%APP_HOME%\lib\slf4j-api-1.7.23.jar;%APP_HOME%\lib\jaxb-xjc-2.2.jar;%APP_HOME%\lib\jaxb-impl-2.2.jar;%APP_HOME%\lib\xmlunit-1.2.jar;%APP_HOME%\lib\core-3.0.0.jar;%APP_HOME%\lib\logback-core-1.2.1.jar;%APP_HOME%\lib\jaxb-api-2.2.jar;%APP_HOME%\lib\stax-api-1.0-2.jar;%APP_HOME%\lib\activation-1.1.jar

@rem Execute jGemstone-Client
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %J_GEMSTONE_CLIENT_OPTS%  -classpath "%CLASSPATH%" net.yuvideo.jgemstone.client.ClientMain %CMD_LINE_ARGS%

:end
@rem End local scope for the variables with windows NT shell
if "%ERRORLEVEL%"=="0" goto mainEnd

:fail
rem Set variable J_GEMSTONE_CLIENT_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
if  not "" == "%J_GEMSTONE_CLIENT_EXIT_CONSOLE%" exit 1
exit /b 1

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
