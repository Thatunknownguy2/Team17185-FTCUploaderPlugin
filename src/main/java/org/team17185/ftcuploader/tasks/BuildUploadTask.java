package org.team17185.ftcuploader.tasks;

import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.NlsContexts;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BuildUploadTask extends Task.Backgroundable {
    private final VirtualFile virtualFile;

    public BuildUploadTask(@Nullable Project project, @NlsContexts.ProgressTitle @NotNull String title, VirtualFile virtualFile) {
        super(project, title);
        this.virtualFile = virtualFile;
    }

    @Override
    public void run(@NotNull ProgressIndicator progressIndicator) {
        UploadTask uploadTask = new UploadTask(getProject(), "Uploading: " + virtualFile.getName(), virtualFile);
        uploadTask.run(progressIndicator);

        BuildTask buildTask = new BuildTask(getProject(), "Building code...");
        buildTask.run(progressIndicator);
    }
}
