package edu.uoc.hagendazs.macadamianut.config.db

import org.springframework.beans.factory.config.BeanDefinition
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Role
import org.springframework.core.KotlinDetector
import org.springframework.transaction.annotation.AnnotationTransactionAttributeSource
import org.springframework.transaction.annotation.ProxyTransactionManagementConfiguration
import org.springframework.transaction.interceptor.DelegatingTransactionAttribute
import org.springframework.transaction.interceptor.TransactionAttribute
import org.springframework.transaction.interceptor.TransactionAttributeSource
import java.lang.reflect.AnnotatedElement
import java.lang.reflect.Method


@Configuration
class TransactionConfig : ProxyTransactionManagementConfiguration() {
    /**
     * Define a custom [TransactionAttributeSource] that will roll back transactions
     * on checked Exceptions if the annotated method or class is written in Kotlin.
     *
     * Kotlin doesn't have a notion of checked exceptions, but [Transactional] assumes
     * Java semantics and does *not* roll back on checked exceptions. This can become
     * an issue if a Kotlin class explicitly throws Exception or calls into a Java
     * method which throws checked exceptions.
     *
     * @see: https://github.com/spring-projects/spring-framework/issues/23473
     */
    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    override fun transactionAttributeSource(): TransactionAttributeSource {

        return object : AnnotationTransactionAttributeSource() {
            override fun determineTransactionAttribute(element: AnnotatedElement): TransactionAttribute? {
                val transactionAttribute = super.determineTransactionAttribute(element)
                    ?: return null
                val isKotlinClass = when (element) {
                    is Class<*> -> KotlinDetector.isKotlinType(element)
                    is Method -> KotlinDetector.isKotlinType(element.declaringClass)
                    else -> false
                }
                if (isKotlinClass) {
                    return object : DelegatingTransactionAttribute(transactionAttribute) {
                        override fun rollbackOn(ex: Throwable): Boolean {
                            return super.rollbackOn(ex) || ex is Exception
                        }
                    }
                }
                return transactionAttribute
            }
        }

    }
}