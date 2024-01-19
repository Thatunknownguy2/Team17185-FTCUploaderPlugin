package org.team17185.ftcuploader.settings;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(
        name = "org.team17185.ftcuploader.settings.AppSettingsState",
        storages = @Storage("FTCUploaderSettings.xml")
)
public class PluginSettingsState implements PersistentStateComponent<PluginSettingsState> {
    public String controllerConsoleIp = "192.168.43.1";
    public int buildTimeoutInt = 20000; // Uses int instead of long as JBIntSpinner is used

    public static PluginSettingsState getInstance() {
        return ApplicationManager.getApplication().getService(PluginSettingsState.class);
    }

    @Override
    public @Nullable PluginSettingsState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull PluginSettingsState state) {
        XmlSerializerUtil.copyBean(state, this);
    }
}
