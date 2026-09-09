package com.AjAkrampoor.Academy.util.telegram;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Service
public class TelegramService {

    @Value("${telegram.bot.token}")
    private String token;

    public void sendMessage(long chatId, String text) throws Exception {

        TelegramClient client = new OkHttpTelegramClient(token);

        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .build();

        client.execute(message);
    }
}
