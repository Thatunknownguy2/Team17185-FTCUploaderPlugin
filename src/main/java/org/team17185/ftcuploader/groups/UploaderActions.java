package org.team17185.ftcuploader.groups;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

public class UploaderActions extends DefaultActionGroup {
    @Override
    public void update(@NotNull AnActionEvent e) {
        VirtualFile virtualFile = e.getData(PlatformDataKeys.VIRTUAL_FILE);

        if (virtualFile == null || !virtualFile.isInLocalFileSystem() || virtualFile.isDirectory() || !virtualFile.getPath().contains("/src/")) {
            e.getPresentation().setVisible(false);
        }
    }
}
