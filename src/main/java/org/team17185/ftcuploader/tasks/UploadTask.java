package org.team17185.ftcuploader.tasks;

import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.NlsContexts;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.team17185.ftcuploader.settings.PluginSettingsState;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class UploadTask extends Task.Backgroundable {
    private final VirtualFile virtualFile;

    public UploadTask(@Nullable Project project, @NlsContexts.ProgressTitle @NotNull String title, VirtualFile virtualFile) {
        super(project, title);

        this.virtualFile = virtualFile;
    }

    @Override
    public void run(@NotNull ProgressIndicator indicator) {
        indicator.setIndeterminate(true);

        try {
            HttpClient httpClient = HttpClient.newHttpClient();

            if (attemptSave(httpClient).contains("\"success\": \"false\"")) {
                NotificationGroupManager.getInstance()
                        .getNotificationGroup("FTC Uploader Notifications")
                        .createNotification("Error: could not upload to OnBot", NotificationType.ERROR)
                        .notify(getProject());

                return;
            }

            NotificationGroupManager.getInstance()
                    .getNotificationGroup("FTC Uploader Notifications")
                    .createNotification("Successfully uploaded " + virtualFile.getName(), NotificationType.INFORMATION)
                    .notify(getProject());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();

            NotificationGroupManager.getInstance()
                    .getNotificationGroup("FTC Uploader Notifications")
                    .createNotification("Error: could not connect to OnBot", NotificationType.ERROR)
                    .notify(getProject());
        }
    }

    private String attemptSave(HttpClient httpClient) throws IOException, InterruptedException {
        PluginSettingsState settingsState = PluginSettingsState.getInstance();
        String path = virtualFile.getPath();

        if (path.contains("/TeamCode/src/main/java/")) {
            path = "/src" + path.substring(path.indexOf("/org"));
        } else {
            path = path.substring(path.indexOf("/src"));
        }

        String encodedFile = URLEncoder.encode(new String(virtualFile.contentsToByteArray()), StandardCharsets.UTF_8);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://%s:8080/java/file/save?f=".formatted(settingsState.controllerConsoleIp) + path))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString("data=%s".formatted(encodedFile)))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}
