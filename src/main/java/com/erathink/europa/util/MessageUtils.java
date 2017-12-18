package com.erathink.europa.util;

/**
 * Created by fengyun on 2017/12/17.
 */
import java.util.Locale;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

/**
 * @author zzzhu@erathink.com
 */
@Component
public class MessageUtils {
    private static MessageUtils messageUtils;

    @PostConstruct
    public void init() {
        messageUtils = this;
        messageUtils.context = this.context;
        messageUtils.msg = this.context.getBean(MessageSource.class);
    }

    @Autowired
    private ApplicationContext context;

    private MessageSource msg;

    /**
     * getting message content by key for default Locale
     *
     * @param key
     *            key defined in messages_XX.properties file
     * @return message content
     */
    public static String get(String key) {
        return get(key, null, null);
    }

    /**
     * getting message content by key for default Locale
     *
     * @param key
     *            key defined in messages_XX.properties file
     * @return message content
     */
    public static String get(String key, String... args) {
        return get(key, args, null);
    }

    /**
     * getting message content by key for locale
     *
     * @param key
     *            key defined in messages_XX.properties file
     * @param local
     *            the local of message defined, if no messages file defined for
     *            this local, it returns message from default locale
     * @return message content
     */
    public static String get(String key,  Object[] args, Locale locale) {
        return Optional.ofNullable(key).map(k -> {
            return Optional.ofNullable(messageUtils.msg).map(m -> {
                return m.getMessage(k, args, k, locale);
            }).orElse(k);
        }).orElse(null);
    }

}

