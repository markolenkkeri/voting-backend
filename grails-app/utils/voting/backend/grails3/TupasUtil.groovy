package voting.backend.grails3

import org.apache.commons.codec.digest.DigestUtils

class TupasUtil {

    static String calculateSha256(List content)
    {
        if(content==null)return calculateSha256String("&")
        return calculateSha256String(content.join("&")+"&")
    }

    static String calculateSha256String(String content)
    {
        return DigestUtils.sha256Hex(content).toUpperCase()
    }
}
