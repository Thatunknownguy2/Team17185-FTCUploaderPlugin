package org.team17185.ftcuploader.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.progress.ProgressManager;
import org.jetbrains.annotations.NotNull;
import org.team17185.ftcuploader.tasks.BuildTask;
public class BuildAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        ProgressManager.getInstance().run(new BuildTask(e.getProject(), "Building code..."));
    }
}
