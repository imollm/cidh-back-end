package edu.uoc.hagendazs.macadamianut.config

import mu.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.IOException
import java.security.*
import java.security.cert.Certificate
import java.security.cert.CertificateException
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder


@Configuration
@ConfigurationProperties(prefix = "security.jwt")
class JwtConfiguration {

    lateinit var keyStorePath: String

    lateinit var keyStorePassword: String

    lateinit var keyAlias: String

    lateinit var privateKeyPassphrase: String

    private val logger = KotlinLogging.logger {}

    @Bean
    fun keyStore(): KeyStore {
        try {
            val keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
            val resourceAsStream = Thread.currentThread().contextClassLoader.getResourceAsStream(keyStorePath)
            keyStore.load(resourceAsStream, keyStorePassword.toCharArray())
            return keyStore
        } catch (e: IOException) {
            logger.error("Unable to load keystore: {}", keyStorePath, e)
        } catch (e: CertificateException) {
            logger.error("Unable to load keystore: {}", keyStorePath, e)
        } catch (e: NoSuchAlgorithmException) {
            logger.error("Unable to load keystore: {}", keyStorePath, e)
        } catch (e: KeyStoreException) {
            logger.error("Unable to load keystore: {}", keyStorePath, e)
        }
        throw IllegalArgumentException("Unable to load keystore")
    }

    @Bean
    fun jwtSigningKey(keyStore: KeyStore): RSAPrivateKey {
        try {
            val key: Key = keyStore.getKey(keyAlias, privateKeyPassphrase.toCharArray())
            if (key is RSAPrivateKey) {
                return key
            }
        } catch (e: UnrecoverableKeyException) {
            logger.error("Unable to load private key from keystore: {}", keyStorePath, e)
        } catch (e: NoSuchAlgorithmException) {
            logger.error("Unable to load private key from keystore: {}", keyStorePath, e)
        } catch (e: KeyStoreException) {
            logger.error("Unable to load private key from keystore: {}", keyStorePath, e)
        }
        throw IllegalArgumentException("Unable to load private key")
    }

    @Bean
    fun jwtValidationKey(keyStore: KeyStore): RSAPublicKey {
        try {
            val certificate: Certificate = keyStore.getCertificate(keyAlias)
                ?: throw IllegalArgumentException("Unable to load RSA public key")
            val publicKey: PublicKey = certificate.publicKey
            if (publicKey is RSAPublicKey) {
                return publicKey
            }
        } catch (e: KeyStoreException) {
            logger.error("Unable to load private key from keystore: {}", keyStorePath, e)
        }
        throw IllegalArgumentException("Unable to load RSA public key")
    }

    @Bean
    fun jwtDecoder(rsaPublicKey: RSAPublicKey?): JwtDecoder {
        return NimbusJwtDecoder.withPublicKey(rsaPublicKey).build()
    }
}