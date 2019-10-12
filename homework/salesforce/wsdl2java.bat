cd %~dp0

java -classpath lib\axis.jar;lib\commons-logging-1.0.4.jar;lib\commons-discovery-0.2.jar;lib\jaxrpc.jar;lib\activation.jar;lib\axis-ant.jar;lib\saaj.jar;lib\wsdl4j-1.5.1.jar;lib\mail.jar;lib\activation.jar org.apache.axis.wsdl.WSDL2Java -a enterprise.wsdl -o src

pause