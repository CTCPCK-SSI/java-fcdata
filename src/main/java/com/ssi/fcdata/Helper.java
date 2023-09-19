package com.ssi.fcdata;

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
        PrivateKey priv = Helper.importPrivateKey("");
        System.out.println("SIGNATURE = " + Helper.sign(priv, "abc".getBytes(StandardCharsets.UTF_8)));
    }
}



