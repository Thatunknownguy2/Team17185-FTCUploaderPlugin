package org.team17185.ftcuploader.actions;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.team17185.ftcuploader.tasks.BuildUploadTask;

public class UploadBuildAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        VirtualFile virtualFile = e.getData(PlatformDataKeys.VIRTUAL_FILE);

        ActionManager.getInstance().getAction("SaveAll").actionPerformed(e);

        ProgressManager.getInstance().run(new BuildUploadTask(e.getProject(), "Uploading and Building...", virtualFile));
    }
}
