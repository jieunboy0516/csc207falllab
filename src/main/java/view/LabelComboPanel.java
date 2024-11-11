package view;

import javax.swing.*;

/**
 * A panel containing a label and a combo box.
 */
class LabelComboPanel extends JPanel {
    LabelComboPanel(JLabel label, JComboBox<String> comboBox) {
        this.add(label);
        this.add(comboBox);
    }
}
