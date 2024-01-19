package org.team17185.ftcuploader.tasks;

import com.intellij.execution.filters.TextConsoleBuilderFactory;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.NlsContexts;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.Content;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.team17185.ftcuploader.settings.PluginSettingsState;
import org.team17185.ftcuploader.utils.WSClient;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.WebSocket;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class BuildTask extends Task.Backgroundable {
    public BuildTask(@Nullable Project project, @NlsContexts.ProgressTitle @NotNull String title) {
        super(project, title);
    }

    @Override
    public void run(@NotNull ProgressIndicator progressIndicator) {
        try {
            progressIndicator.setIndeterminate(true);

            ToolWindow toolWindow = ToolWindowManager.getInstance(getProject()).getToolWindow("FTC Uploader Output");

            ToolWindowManager.getInstance(getProject()).invokeLater(() -> {
                Content lastContent = toolWindow.getContentManager().getContent(0);
                if (lastContent != null) {
                    toolWindow.getContentManager().removeContent(lastContent, true);
                }
            });

            ConsoleView consoleView = TextConsoleBuilderFactory.getInstance().createBuilder(toolWindow.getProject()).getConsole();

            ToolWindowManager.getInstance(getProject()).invokeLater(() -> {
                Content content = toolWindow.getContentManager().getFactory().createContent(consoleView.getComponent(), "Build Output", true);
                toolWindow.getContentManager().addContent(content);
                toolWindow.show();
                consoleView.print("Building Files...", ConsoleViewContentType.NORMAL_OUTPUT);
            });

            CountDownLatch latchAwaiter = new CountDownLatch(3);
            WSClient wsClient = new WSClient(latchAwaiter);
            PluginSettingsState settingsState = PluginSettingsState.getInstance();

            WebSocket ws = HttpClient
                    .newHttpClient()
                    .newWebSocketBuilder()
                    .buildAsync(URI.create("ws://%s:8081/".formatted(settingsState.controllerConsoleIp)), wsClient)
                    .join();

            ws.sendText("{\"namespace\":\"system\",\"type\":\"subscribeToNamespace\",\"payload\":\"ONBOTJAVA\"}", true);
            ws.sendText("{\"namespace\":\"ONBOTJAVA\",\"type\":\"build:launch\",\"payload\":\"\"}", true);

            if (!latchAwaiter.await(settingsState.buildTimeoutInt, TimeUnit.MILLISECONDS)) {
                // Message timeout
                ToolWindowManager.getInstance(getProject()).invokeLater(() -> consoleView.print("\n\nError: Timed Out!", ConsoleViewContentType.ERROR_OUTPUT));
                return;
            }

            ws.sendClose(1000, "Close").join();

            if (!wsClient.isSuccess()) { // Get the error
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("http://%s:8080/java/build/wait".formatted(settingsState.controllerConsoleIp)))
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                ToolWindowManager.getInstance(getProject()).invokeLater(() ->
                        consoleView.print("\n" + response.body() + "\n\nBuild FAILED!", ConsoleViewContentType.ERROR_OUTPUT));

                return;
            }

            // Send success message
            ToolWindowManager.getInstance(getProject()).invokeLater(() -> consoleView.print("\n\nBuild SUCCESSFUL!", ConsoleViewContentType.NORMAL_OUTPUT));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
