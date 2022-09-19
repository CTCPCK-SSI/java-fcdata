package vn.com.ssi.Trading;

import org.apache.commons.codec.binary.Hex;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;

import org.apache.commons.codec.binary.Base64;

public class Helper {
    public  static  byte[] decodeBase64(String dataToDecode){
        return Base64.decodeBase64(dataToDecode);
    }
    public static Document convertStringToXMLDocument(String xmlString)
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try
        {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse( new InputSource( new StringReader( xmlString ) ) );
            return doc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public  static PrivateKey importPrivateKey(String privKey) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String xmlStr = null;
        try {
            xmlStr = new String(Base64.decodeBase64(privKey), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        Document doc = convertStringToXMLDocument( xmlStr );
        assert doc != null;
        NodeList nodes = doc.getElementsByTagName("RSAKeyValue").item(0).getChildNodes();
        String m = null;
        String d = null;
        for(int i =0;i < nodes.getLength(); i ++){
            Node n = nodes.item(i);
            String nodeName = n.getNodeName();
            if(nodeName.equals("Modulus"))
                m= n.getTextContent();
            else if(nodeName.equals("D") )
                d = n.getTextContent();
        }
        assert m != null;
        BigInteger modules = new BigInteger(1, Base64.decodeBase64(m.trim()));
        assert d != null;
        BigInteger dBig = new BigInteger(1, Base64.decodeBase64(d.trim()));
        KeyFactory factory = KeyFactory.getInstance("RSA");
        RSAPrivateKeySpec privSpec = new RSAPrivateKeySpec(modules, dBig);
        return factory.generatePrivate(privSpec);
    }
    //====================================================================================
    // AUTOMATICALLY SIGN
    //====================================================================================
    public static String sign(PrivateKey privateKey, byte[] dataBytes) throws Exception {

        //CREATE SIGNATURE (use Hash first)
        Signature         signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(dataBytes);
        byte[]            signatureBytes = signature.sign();

        //RETURN SIGNATURE
        return Hex.encodeHexString(signatureBytes);

    }
    //====================================================================================
    // MAIN
    //====================================================================================
    public static void main(String[] args) throws Exception {
        PrivateKey priv = Helper.importPrivateKey("PFJTQUtleVZhbHVlPjxNb2R1bHVzPnNhZDhnM0tGZVhrK1BBOUdlUERkdVUwUnY5ZXpsTGpSRjExdjV0UEFMZk8zYnRtbmlucmJWdmxuU3FPTGluRkpPNVhwbGtzMy80dE01WGxVeTM2dndzYlBleld4UVE1dThodEo0bGZGL2F6NkdGZVZsZWFBR0RmRnZJWVUyS2dNRHA1bGZwVkNkdE5HczRCVVNtd0JlWWVXSWdBMEVjbFgrMjFKalR5Z2Q2VT08L01vZHVsdXM+PEV4cG9uZW50PkFRQUI8L0V4cG9uZW50PjxQPjBDREcwU3RyOUpnNFBIQnBiUEtialFWcjlvUXlyVm9nU2dscHZQZUh2bmx5TCtHOFBEK3hKcnBJMndmZ3RLa3o4c3lpM0NSd3lkUDJzYjY2eUZQa2Z3PT08L1A+PFE+Mm9SUmp6a3FrL2RTNDg2REpTNjRpTmpyMEZhVElVK0Y5OCsrZUJCeENnYW5lamoraDRVbk4rWXB2bWZzdFdIUVNDd01wZXByd1E2bWhQSnhMVWlCMnc9PTwvUT48RFA+clhMelhqUjZ0by81SmQxazd1Zk0zSnl0R2ZlWUtFSVk4THFoaFZzZ3BJdWZydW5JTHNuQndBMjFVOXhmMXcrLy9GT3dVaGlJYXBzY1Y0c2xMSGhGenc9PTwvRFA+PERRPjBIaUVSeHZHM3p1SnZRUjhZYkRkSk5HdXlDaVFYYXM4cUZ0dDM2WHY4aHkvRXYvazlPMjNxTURRK25LemhhZzN5V01jL2YxVHArK09OakFHZ2FrM0dRPT08L0RRPjxJbnZlcnNlUT5EN0VSd2Y3RWxoR2t2NjBNRFM5Z2VtNXhpOXJuUktZeGJ5TzROMWVNS0tqMmNoM1hqc3g3Z3dOcGp3K3hJRTB0dkZhN09hb085N0daVE5icUhDR3NUUT09PC9JbnZlcnNlUT48RD5xeSszOUxYeTFnVzFxWXdTblZHRVpoMVVvQ2JLM2VGbFlmMWdhZTNiZnF3ZE1zeTYrOTY4NHNjNitCbzF5VGEybVpzd1ZlbXZVU2c2OWRoL2xBTkVlbkdZaWxuOUlVZ1U2SmU1alBCaFppVzgwVWJsTE1YN2x4NmhWSjN4S1B5eFVWM2hlV1d4aFhIa0RpQXZSaUlSQ2lERWl1SHhHcWhQMG5RTFM1bHgydlU9PC9EPjwvUlNBS2V5VmFsdWU+");
        System.out.println("SIGNATURE = " + Helper.sign(priv, "abc".getBytes(StandardCharsets.UTF_8)));
    }
}



