package com.codeMaster.codeMasterAPI.service.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.Enumeration;

@Component
@Slf4j
@Configuration
public class KeyStoreManager {

    private KeyStore keyStore;

    @Value("${server.ssl.key-store-type}")
    private String type;

//    @Value("${server.ssl.trust-store-type}")
//    private String trust_type;

    @Value("${server.ssl.key-store}")
    private String fileLocation;

//    @Value("${server.ssl.trust-store}")
//    private String trust_store;


    @Value("${server.ssl.key-store-password}")
    private String store_password;

    @Value("${server.ssl.key-password}")
    private String key_password;

//    @Value("${server.ssl.trust-store-password}")
//    private String trust_password;

    @Value("${server.ssl.key-alias}")
    private String key_alias;




    public Key getPrivateKey(String alias, String passphrase) throws KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException {

        try {

            keyStore = KeyStore.getInstance(type);
            FileInputStream keyStoreFile = new FileInputStream(fileLocation);
            keyStore.load(keyStoreFile, store_password.toCharArray());
            log.info("Loaded keystore {} ", fileLocation);

        } catch (Exception e) {
            e.printStackTrace();
        }
        Enumeration<String> aliases = keyStore.aliases();
        while (aliases.hasMoreElements()) {
            log.info("Found alias " + aliases.nextElement());
        }
        if (keyStore.containsAlias(key_alias))
            log.info("Keystore contains alias");
        else
            log.info("Keystore does not contains alias");

        Key key = keyStore.getKey(key_alias, store_password.toCharArray());
        log.info(key.toString());
        return key;
    }

    public PublicKey getPublicKey(String alias) {
        try {
            try {
                keyStore=KeyStore.getInstance(type);
                FileInputStream keyStoreFile = new FileInputStream(fileLocation);
                keyStore.load(keyStoreFile,store_password.toCharArray());
                log.info("Loaded keystore {} ", fileLocation);
            } catch (KeyStoreException | CertificateException | IOException | NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            return keyStore.getCertificate(key_alias).getPublicKey();
        }
        catch (KeyStoreException e) {
            throw new RuntimeException("Exception occured while retrieving public key from keystore", e);
        }

    }
}
