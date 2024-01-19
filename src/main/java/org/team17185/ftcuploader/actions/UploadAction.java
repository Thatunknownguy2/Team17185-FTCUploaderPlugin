package org.team17185.ftcuploader.actions;

import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.team17185.ftcuploader.tasks.UploadTask;


public class UploadAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        VirtualFile virtualFile = e.getData(PlatformDataKeys.VIRTUAL_FILE);

        ActionManager.getInstance().getAction("SaveAll").actionPerformed(e);

        ProgressManager.getInstance().run(new UploadTask(e.getProject(), "Uploading: " + virtualFile.getName(), virtualFile));
    }
}