# Äänestysjärjestelmä, backend

Tupas-autentikoinnin kautta äänestämisen mahdollistava sovellus. Muutamia aika isoja tietoturvareikiä tässä, vinkkinä: frameworkin puolen automaatio on välillä myös ei-niin-kiva ystävä.

## Käynnistäminen
Asenna Java JDK, mieluiten vaikka Java 8:
> Linux: https://sdkman.io/

> Mac: https://java.com/en/download/help/mac_install.xml

> Windows: https://docs.oracle.com/javase/10/install/installation-jdk-and-jre-microsoft-windows-platforms.htm

Avaa terminaali.

Kloonaa projekti itsellesi:
> git clone https://github.com/markolenkkeri/voting-backend.git

Mene projektin hakemistoon:
> cd voting-backend

Tee ohjeen mukaisesti salausavaimet ja aseta ne nimillä voting_key.der ja public_voting.der hakemistoon secret
Katso kohta 10.1.3, Certificate generation for JWT
> http://alvarosanchez.github.io/grails-spring-security-rest/latest/docs/#_json_web_token

### Linux + MacOs

Muuta gradlew-tiedosto ajettavaksi, jos se ei jo ole
> chmod +x gradlew

Aja gradlew avulla projekti
> ./gradlew bootRun

### Windows

Aja gradlew.bat-tiedosto
> gradlew.bat bootRun

Jos käyttää gitbashia, syntaksi on sama kuin Linux-esimerkissä.

## Käytännön käyttö:
Alla esimerkkikutsut järjestelmän käyttämiseksi.
Nämä olettavat, että [tupas-testbench](https://github.com/sakumikko/tupas-testbench) on paikallisesti ajossa. (Kloonaa repository, asenna npm jos ei ole asennettuna, aja npm install ja npm start)
Curl-kutsut ovat bash-muodossa, ja curl-kutsujen jälkeen esitetty JSON-data on aina edeltävässä curl-kutsussa @-notaatiolla merkatun tiedoston sisällöt.
Ensimmäiset 3 kutsua voidaan ajaa selaimessa, myös ilma tupas-testbenchiä, jolloin otetaan vain http://... -osio käskystä selaimen osoiteriville.

### Listaa ehdokkaita
> curl -v -H "Content-type:application/json" 'http://localhost:8080/candidate/'

### Tarkastele ehdokkaan tietoja
> curl -v -H "Content-type:application/json" 'http://localhost:8080/candidate/1'

### Aloita kirjautuminen:
> curl -v -H "Content-type:application/json" 'http://localhost:8080/tupas/'

### Aloita Tupas-operaatio:
> curl -X POST --data @body.json -H "Content-Type: application/json" http://localhost:3000/tupas/identify

>{
>"A01Y_ACTION_ID": "701",
>"A01Y_VERS": "0002",
>"A01Y_RCVID": "999",
>"A01Y_LANGCODE": "FI",
>"A01Y_STAMP": "20190328083920000000",
>"A01Y_IDTYPE": "12",
>"A01Y_RETLINK": "http://localhost/tupasreturn",
>"A01Y_CANLINK": "http://localhost/tupascancel",
>"A01Y_REJLINK": "http://localhost/tupasreject",
>"A01Y_KEYVERS": "0001",
>"A01Y_ALG": "03",
>"A01Y_MAC": "D994AA5842A2131085D511B512CC68D64B0E0D50BAD77F98C8D296A3D15E2923"
>}

### Varmista Tupas-tiedot
> curl -X POST --data @verify.json -H "Content-Type: application/json" http://localhost:3000/tupas/verify
 
>{
>"ssn":"010101-001A",
>"cn":"Erkki Esimerkki",
>"returnUri":"http://localhost/tupasreturn",
>"stamp":"20190328083920000000"
>}


### Sisäänkirjautumisen loppuunvienti:
> TOKEN=$(curl -H "Content-type:application/json" --data @callback.json -X POST 'http://localhost:8080/api/login' | jq -r '.access_token')

>{
>"B02K_VERS":"0002",
>"B02K_TIMESTMP":"99920190328064742000000",
>"B02K_IDNBR":"1234567890",
>"B02K_STAMP":"20190328083920000000",
>"B02K_CUSTNAME":"Erkki Esimerkki",
>"B02K_KEYVERS":"0001",
>"B02K_ALG":"03",
>"B02K_CUSTID":"010101-001A",
>"B02K_CUSTTYPE":"01",
>"B02K_MAC":"BFF313764E23635E123AC538601DD4CF4B8FEE8421E855563A2AEF05FB1B8F9F"
>}

### Äänestystoiminnallisuus:
>curl -H 'Accept: application/json' -H 'Content-type:application/json' -H "Authorization: Bearer ${TOKEN}" -X POST --data @vote.json http://localhost:8080/voting/vote

>{
> "candidateId":1
>}
