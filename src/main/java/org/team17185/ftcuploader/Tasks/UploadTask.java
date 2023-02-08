package org.team17185.ftcuploader.Tasks;

import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.NlsContexts;
import com.intellij.openapi.vfs.VirtualFile;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.entity.mime.ByteArrayBody;
import org.apache.hc.client5.http.entity.mime.HttpMultipartMode;
import org.apache.hc.client5.http.entity.mime.MultipartEntityBuilder;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicNameValuePair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.Arrays;

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
            CloseableHttpClient httpClient = HttpClients.createDefault();

                if (attemptSave(httpClient).contains("\"success\": \"false\"")) {
                    NotificationGroupManager.getInstance()
                            .getNotificationGroup("FTC Uploader Notifications")
                            .createNotification("Error: could not upload to OnBot", NotificationType.ERROR)
                            .notify(getProject());

                    httpClient.close();
                    return;
                }

            NotificationGroupManager.getInstance()
                    .getNotificationGroup("FTC Uploader Notifications")
                    .createNotification("Successfully uploaded " + virtualFile.getName(), NotificationType.INFORMATION)
                    .notify(getProject());

            httpClient.close();
        } catch (IOException | ParseException e) {
            e.printStackTrace();

            NotificationGroupManager.getInstance()
                    .getNotificationGroup("FTC Uploader Notifications")
                    .createNotification("Error: could not connect to OnBot", NotificationType.ERROR)
                    .notify(getProject());
        }
    }

    private String attemptSave(CloseableHttpClient httpClient) throws IOException, ParseException {
        String path = virtualFile.getPath();

        if (path.contains("/TeamCode/src/main/java/")) {
            path = "/src" + path.substring(path.indexOf("/org"));
        } else {
            path = path.substring(path.indexOf("/src"));
        }

        HttpPost post = new HttpPost("http://192.168.49.1:8080/java/file/save?f=" + path);

        BasicNameValuePair[] params = new BasicNameValuePair[] {new BasicNameValuePair("data", new String(virtualFile.contentsToByteArray()))};

        HttpEntity entity = new UrlEncodedFormEntity(Arrays.asList(params));
        post.setEntity(entity);

        CloseableHttpResponse response = httpClient.execute(post);
        String stringResponse = EntityUtils.toString(response.getEntity());

        response.close();
        return stringResponse;
    }
}
