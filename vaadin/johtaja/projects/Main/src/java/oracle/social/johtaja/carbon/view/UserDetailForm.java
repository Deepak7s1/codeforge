package oracle.social.johtaja.carbon.view;

import com.vaadin.data.Item;
import com.vaadin.data.validator.StringLengthValidator;

import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Form;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;

import java.util.Arrays;
import java.util.List;

import oracle.social.johtaja.app.MainApplication;
import oracle.social.johtaja.model.container.UsersContainer;


public class UserDetailForm extends Form {
    public final static String UI_FORM_ID = "Form:user";


    private GridLayout formLayout;


    public UserDetailForm(MainApplication mapp) {
        /*
         * Enable buffering so that commit() must be called for the form before
         * input is written to the data. (Form input is not written immediately
         * through to the underlying object.)
         */
        setWriteThrough(false);

        setSizeFull();
        addStyleName("form");

        // Create form layout as a 3x3 grid.
        formLayout = new GridLayout(3, 3);
        formLayout.setMargin(true, false, false, true);
        formLayout.setSpacing(true);
        setLayout(formLayout);

        // FieldFactory for customizing the fields and adding validators
        setFormFieldFactory(new UserFieldFactory());

        mapp.addUIReference(UI_FORM_ID, this);
    }


    /*
    * Override to get control over where fields are placed.
    */

    @Override
    protected void attachField(Object propertyId, Field field) {
        if (propertyId.equals("userId")) {
            formLayout.addComponent(field, 0, 0);
        }
        else if (propertyId.equals("displayName")) {
            formLayout.addComponent(field, 1, 0, 2, 0);
        }
        else if (propertyId.equals("emailAddress")) {
            formLayout.addComponent(field, 0, 1, 2, 1);
        }
        else if (propertyId.equals("enabled")) {
            formLayout.addComponent(field, 0, 2);
        }
        else if (propertyId.equals("deleted")) {
            formLayout.addComponent(field, 2, 2);
        }
    }


    @Override
    public void setItemDataSource(Item newDataSource) {
        if (newDataSource != null) {
            List<Object> orderedProperties = Arrays.asList(UsersContainer.NATURAL_COL_ORDER);
            super.setItemDataSource(newDataSource, orderedProperties);

            //setReadOnly(true);
        }
    }


    private class UserFieldFactory extends DefaultFieldFactory {

        public UserFieldFactory() {
        }

        @Override
        public Field createField(Item item, Object propertyId, Component uiContext) {
            Field f;
            f = super.createField(item, propertyId, uiContext);

            if ("userId".equals(propertyId)) {
                TextField tf = (TextField)f;
                tf.setRequired(true);
                tf.setRequiredError("Please enter a First Name");
                tf.setWidth("8em");
            }
            else if ("displayName".equals(propertyId)) {
                TextField tf = (TextField)f;
                tf.setRequired(true);
                tf.setRequiredError("Please enter a Last Name");
                tf.setWidth("20em");
                tf.addValidator(new StringLengthValidator("Display must be 3-50 characters", 3, 50, false));
            }
            else if ("emailAddress".equals(propertyId)) {
                TextField tf = (TextField)f;
                tf.setWidth("30em");
            }

            return f;
        }

        private PasswordField createPasswordField(Object propertyId) {
            PasswordField pf = new PasswordField();
            pf.setCaption(DefaultFieldFactory.createCaptionByPropertyId(propertyId));
            return pf;
        }
    }

}
