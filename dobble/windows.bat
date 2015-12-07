@echo off

echo verification du SDK (java)
javac -version 2> tmp_java_version.txt
set /p JAVA_VERSION= < tmp_java_version.txt
del tmp_java_version.txt
set JAVA_VERSION=%JAVA_VERSION:~6,3%

echo java version: %JAVA_VERSION%

if %JAVA_VERSION% GEQ 1.5 (goto javaok)
echo necessite une version de java supérieur ou égal à 1.5
start http://www.oracle.com/technetwork/java/javase/downloads/index.html
goto fin

:javaok
echo java OK


echo verification de ant

if exist /ant/bin/ant.bat ( goto antok)
echo necessite ant
start http://ant.apache.org/bindownload.cgi
goto fin

:antok
echo ant ok

echo compilation et generation du jar
/ant/bin/ant.bat install

:fin
pause 