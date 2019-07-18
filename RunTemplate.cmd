set PATH_TO_JARS=C:\NTJP-DOC\VirtualServices\Dev
set INSTALLATION_PATH_ON_SERVER=/opt/vp/wsdl
rem set INSTALLATION_PATH_ON_SERVER=c:\wsdl\
java -jar target/vp-virtualservice-converter-1.0-SNAPSHOT-jar-with-dependencies.jar %PATH_TO_JARS% %INSTALLATION_PATH_ON_SERVER%
pause