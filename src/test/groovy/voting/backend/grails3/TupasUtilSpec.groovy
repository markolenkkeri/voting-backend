package voting.backend.grails3

import org.apache.commons.codec.digest.DigestUtils
import spock.lang.Specification


class TupasUtilSpec extends Specification {

    static final String correctString = "27C2B12B467AE70A76C453610CD66B3D62DFCD6C8C0468EE242FD3AC51B5548F"

    def "A string is correctly SHA256-hashed"() {
        given: "A string that contains data"
        String contentString = "Test&SHA256&"
        when: "The string is hashed with the util"
        String hashedString = TupasUtil.calculateSha256String(contentString)
        then: "A correct SHA256 hash is generated"
        hashedString == correctString
    }

    def "A list of objects is correctly SHA256-hashed"() {
        given: "A list of strings"
        def stringList = ["Test", "SHA256"]
        when: "The list is hashed with the util"
        String hashedString = TupasUtil.calculateSha256(stringList)
        then: "A correct SHA256 hash is generated"
        hashedString == correctString
    }

    def "An empty list is hashed similarly to a single ampersand"() {
        when: "An empty list is hashed"
        String hashedString = TupasUtil.calculateSha256([])
        then: "The single ampersand is hashed correctly"
        hashedString == DigestUtils.sha256Hex("&").toUpperCase()
    }

    def "A list with null values has null values evaluated as text null"() {
        when: "A list with null values is hashed"
        String hashedString = TupasUtil.calculateSha256([null])
        then: "The content is hashed correctly"
        hashedString == DigestUtils.sha256Hex("null&").toUpperCase()
    }

    def "An null list is hashed similarly to a single ampersand"() {
        when: "An empty list is hashed"
        String hashedString = TupasUtil.calculateSha256(null)
        then: "The single ampersand is hashed correctly"
        hashedString == DigestUtils.sha256Hex("&").toUpperCase()
    }
}