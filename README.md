# VP Virtual Service Converter

Projektet är ett verktyg för att anpassa VP mules virtuella tjänster till VP-camel
Följande steg utförs:
1. Extraherar alla wsdl och xsd filer från VPs jar filer
2. Läser mule descriptorn i jar filerna och extraherar url:en för tjänstekontraktet.
3. Skapar en json konfig fil för VP-camel

# Hur används projektet
1. Bygg med maven: "mvn clean install"
    Note: Projektet använder dependencies från vp-camel. Under utvecklingsfasen kan man behöva köra "mvn clean install" för vp-camel då de inte är säkert det ligger i Nexus.
2. Lägg de virtuella tjänster (jar filer) som ska konverteras i en katalog på disk.
3. Redigera filen "RunTemplate.cmd" (för Windows) med katalogen till jarer samt med katalogen där wsdler ska ligga på servern.
4. Kör RunTemplate.cmd. 
5. Relutatet ska nu ligga i en undermapp till jar katalogen som heter 'generated' och innehålla:
    - Underkataloger med alla wsdl/xsd:er för alla tjänstekontrakt.
    - Filen WsdlConfigSettings.json som innehåller konfigurering för vp-camel.
    - Filen problematicJars.txt innehåller information om det har gått fel att packa upp/parsa några jarer.
    
    