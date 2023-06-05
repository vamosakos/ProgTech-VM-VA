package form;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegistrationFormTest {
    @Test
    public void registerSuccessful() {
        RegistrationForm registrationForm = new RegistrationForm(null);
        assertNotNull(registrationForm.addUserToDatabase("Uj felhasznalo", "newuser@newuser.com", "12345", 0));
    }

}