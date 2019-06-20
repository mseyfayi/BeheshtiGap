package util;

import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;

/**
 * The Field is an abstract class with some static methods about the textFields
 */
public abstract class Fields {
    /**
     * Checks some fields to not be empty
     * @param fields The given fields to check
     * @return true if all of the fields wasn't empty
     */
    public static boolean notEmpty(TextField... fields) {
        boolean res = true;
        for (TextField field : fields) {
            if (field.getText().isEmpty()) {
                makeAlertField(field);
                res = false;
            }
        }
        return res;
    }

    /**
     * Checks text of some fields to be as number
     * @param fields The given fields to check
     * @return true if text of all of the fields was number
     */
    public static boolean numberFormat(TextInputControl... fields) {
        boolean res = true;
        for (TextInputControl field : fields) {
            try {
                Integer.parseInt(field.getText());
            } catch (NumberFormatException e) {
                makeAlertField(field);
                res = false;
            }
        }
        return res;
    }

    /**
     * Makes the given field empty and make change the color of the prompt text into 'red'
     * @param field the given field to change
     */
    public static void makeAlertField(TextInputControl field) {
        field.setText("");
        field.getStyleClass().add("alert-field");
        field.setOnInputMethodTextChanged(event -> {
            field.getStyleClass().remove("alert-field");
        });
    }

    //todo javadoc
    public static void makeAlertField(TextInputControl... fields){
        for (TextInputControl field : fields) {
            makeAlertField(field);
        }
    }
}
