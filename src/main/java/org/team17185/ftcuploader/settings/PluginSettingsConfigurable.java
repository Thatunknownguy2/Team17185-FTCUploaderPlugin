package org.team17185.ftcuploader.settings;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.util.NlsContexts;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class PluginSettingsConfigurable implements Configurable {
    private PluginSettingsComponent settingsComponent;

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public @NlsContexts.ConfigurableName String getDisplayName() {
        return "FTCUploader Settings";
    }

    @Override
    public @Nullable JComponent getPreferredFocusedComponent() {
        return settingsComponent.getPreferredFocusedComponent();
    }

    @Override
    public @Nullable JComponent createComponent() {
        settingsComponent = new PluginSettingsComponent();
        return settingsComponent.getPanel();
    }

    @Override
    public boolean isModified() {
        PluginSettingsState settingsState = PluginSettingsState.getInstance();

        boolean modified = !settingsComponent.getControllerUrlString().equals(settingsState.controllerConsoleIp);
        modified |= settingsComponent.getTimeoutInt() != settingsState.buildTimeoutInt;

        return modified;
    }

    @Override
    public void apply() throws ConfigurationException {
        PluginSettingsState settingsState = PluginSettingsState.getInstance();
        settingsState.controllerConsoleIp = settingsComponent.getControllerUrlString();
        settingsState.buildTimeoutInt = settingsComponent.getTimeoutInt();
    }

    @Override
    public void reset() {
        PluginSettingsState settingsState = PluginSettingsState.getInstance();
        settingsComponent.setControllerUrlString(settingsState.controllerConsoleIp);
        settingsComponent.setTimeoutInt(settingsState.buildTimeoutInt);
    }

    @Override
    public void disposeUIResources() {
        settingsComponent = null;
    }
}
