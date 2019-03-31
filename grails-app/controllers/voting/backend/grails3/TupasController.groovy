package voting.backend.grails3

import grails.converters.JSON
import org.apache.commons.codec.digest.DigestUtils
import org.springframework.beans.factory.annotation.Value

class TupasController {

    @Value('${tupas.key}')
    String tupasKey

    static responseFormats = ['json']

    def index() {
        String currentStamp = new Date().format("yyyyMMddhhmmss")+"000000"
        def returnValues = ['loginUrl'      : 'http://localhost:3000',
                            'loginVariables': ['A01Y_ACTION_ID': '701',
                                               'A01Y_VERS'     : '0002',
                                               'A01Y_RCVID'    : '999',
                                               'A01Y_LANGCODE' : 'FI',
                                               'A01Y_STAMP'    : currentStamp,
                                               'A01Y_IDTYPE'   : '12',
                                               'A01Y_RETLINK'  : 'http://localhost/tupasreturn',
                                               'A01Y_CANLINK'  : 'http://localhost/tupascancel',
                                               'A01Y_REJLINK'  : 'http://localhost/tupasreject',
                                               'A01Y_KEYVERS'  : '0001',
                                               'A01Y_ALG'      : '03',
                                               'A01Y_MAC'      : calculateMac(currentStamp, tupasKey)]
        ]
        render returnValues as JSON
    }

    private static String calculateMac(String stamp, String tupasKey)
    {
        def content = ['701', '0002', '999', 'FI', stamp, '12', 'http://localhost/tupasreturn', 'http://localhost/tupascancel', 'http://localhost/tupasreject', '0001', '03', tupasKey]
        return TupasUtil.calculateSha256(content)
    }
}
