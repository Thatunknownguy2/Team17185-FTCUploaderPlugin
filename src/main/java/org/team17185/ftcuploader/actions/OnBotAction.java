package org.team17185.ftcuploader.actions;

import com.intellij.ide.BrowserUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;
import org.team17185.ftcuploader.settings.PluginSettingsState;

public class OnBotAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        PluginSettingsState settingsState = PluginSettingsState.getInstance();
        BrowserUtil.open("http://%s:8080/?page=java/editor.html&pop=true".formatted(settingsState.controllerConsoleIp));
    }
}
