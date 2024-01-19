package org.team17185.ftcuploader.settings;

import com.intellij.ui.JBIntSpinner;
import com.intellij.ui.components.JBLabel;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.FormBuilder;

import javax.swing.*;

public class PluginSettingsComponent {
    private final JPanel mainPanel;
    private final JBTextField controllerIpText = new JBTextField();
    private final JBIntSpinner timeoutSpinner = new JBIntSpinner(20000, 0, Integer.MAX_VALUE);

    public PluginSettingsComponent() {
        mainPanel = FormBuilder.createFormBuilder()
                .addLabeledComponent(new JBLabel("Robot Controller Console IP: "), controllerIpText, 1, true)
                .addLabeledComponent(new JBLabel("Millisecond Timeout of Builds"), timeoutSpinner, 1, true)
                .addComponentFillVertically(new JPanel(), 0)
                .getPanel();
    }

    public JPanel getPanel() {
        return mainPanel;
    }

    public JComponent getPreferredFocusedComponent() {
        return controllerIpText;
    }

    public String getControllerUrlString() {
        return controllerIpText.getText();
    }

    public void setControllerUrlString(String newUrl) {
        controllerIpText.setText(newUrl);
    }

    public int getTimeoutInt() {
        return timeoutSpinner.getNumber();
    }

    public void setTimeoutInt(int newTimeout) {
        timeoutSpinner.setNumber(newTimeout);
    }
}
