package org.team17185.ftcuploader.utils;

import java.net.http.WebSocket;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.CountDownLatch;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WSClient implements WebSocket.Listener {
    private final CountDownLatch latch;
    private boolean success = false;

    public WSClient(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void onOpen(WebSocket webSocket) {
        WebSocket.Listener.super.onOpen(webSocket);
    }

    @Override
    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
        Pattern pattern = Pattern.compile("\"status\\\\\":\\\\\"(.*)\\\\\"");
        Matcher matcher = pattern.matcher(data.toString());

        if (!matcher.find())
            return WebSocket.Listener.super.onText(webSocket, data, last);

        if (matcher.group(1).equals("SUCCESSFUL"))
            success = true;

        latch.countDown();
        return WebSocket.Listener.super.onText(webSocket, data, last);
    }

    @Override
    public void onError(WebSocket webSocket, Throwable error) {
        WebSocket.Listener.super.onError(webSocket, error);
    }

    public boolean isSuccess() {
        return success;
    }
}