package com.example.messenger;

public class Chat {

    private String sender;
    private String receiver;
    private String chat;

    public Chat(String sender, String receiver, String chat) {
        this.sender = sender;
        this.receiver = receiver;
        this.chat = chat;
    }

    public Chat() {
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getChat() {
        return chat;
    }

    public void setChat(String chat) {
        this.chat = chat;
    }
}
