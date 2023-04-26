package com.example.encryptachat

import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import java.security.*
import java.security.spec.ECGenParameterSpec
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.security.MessageDigest
import javax.crypto.KeyAgreement

class KeyAgreement {

    companion object {
        // Define the basepoint and key derivation function
        private val ecSpec = ECGenParameterSpec("secp256r1")
        private val keyPairGen = KeyPairGenerator.getInstance("EC")
        private val basePoint: PublicKey

        init {
            keyPairGen.initialize(ecSpec)
            basePoint = keyPairGen.generateKeyPair().public
        }

        private val kdf = { input: ByteArray -> MessageDigest.getInstance("SHA-256").digest(input) }

        // Define the validation field encryption algorithm
        private fun encryptValidationField(key: ByteArray, input: ByteArray): ByteArray {
            val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
            val keySpec = SecretKeySpec(kdf(key), "AES")
            cipher.init(Cipher.ENCRYPT_MODE, keySpec)
            return cipher.doFinal(input)
        }

        // Define the message encryption algorithm
        private fun encryptMessage(key: ByteArray, input: ByteArray): ByteArray {
            val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
            val keySpec = SecretKeySpec(kdf(key), "AES")
            cipher.init(Cipher.ENCRYPT_MODE, keySpec)
            return cipher.doFinal(input)
        }

        // Helper function to decrypt message using the session key
        private fun decryptMessage(key: ByteArray, input: ByteArray): ByteArray {
            val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
            val keySpec = SecretKeySpec(kdf(key), "AES")
            cipher.init(Cipher.DECRYPT_MODE, keySpec)
            return cipher.doFinal(input)
        }

        fun performKeyAgreement(
            firstPartyPrivateKey: PrivateKey,
            secondPartyPublicKeyBytes: ByteArray,
            validationFieldBytes: ByteArray,
            encryptedMessage: ByteArray
        ) {
            // Second Party receives public key from First Party
            val secondPartyPublicKeySpec = X509EncodedKeySpec(secondPartyPublicKeyBytes)
            val secondPartyPublicKey =
                KeyFactory.getInstance("EC").generatePublic(secondPartyPublicKeySpec)

            // Second Party calculates session key
            val keyAgreement = KeyAgreement.getInstance("ECDH")
            keyAgreement.init(firstPartyPrivateKey)
            keyAgreement.doPhase(secondPartyPublicKey, true)
            val sessionKey = kdf(keyAgreement.generateSecret())

            // Second Party decrypts validation field to recover First Party's seed
            val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
            val keySpec = SecretKeySpec(sessionKey, "AES")
            cipher.init(Cipher.DECRYPT_MODE, keySpec)
            val seed = cipher.doFinal(validationFieldBytes)

            // Second Party recovers First Party's private key and public key
            val firstPartyPrivateKeySpec = PKCS8EncodedKeySpec(seed)
            val firstPartyPrivateKey =
                KeyFactory.getInstance("EC").generatePrivate(firstPartyPrivateKeySpec)
            val firstPartyPublicKey = basePoint.encoded

            // Second Party compares recovered public key to received public key
            if (firstPartyPublicKey.contentEquals(secondPartyPublicKeyBytes)) {
                // Use the computed session key to perform intended cryptographic task
                val decryptedMessage = decryptMessage(sessionKey, encryptedMessage)
                println("Decrypted message: ${String(decryptedMessage)}")
            } else {
                // Notify First Party that cryptographic task failed
                println("Cryptographic task failed")
            }

            // Helper function to decrypt message using the session key
            fun decryptMessage(key: ByteArray, input: ByteArray): ByteArray {
                val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
                val keySpec = SecretKeySpec(kdf(key), "AES")
                cipher.init(Cipher.DECRYPT_MODE, keySpec)
                return cipher.doFinal(input)
            }

        }
    }

}