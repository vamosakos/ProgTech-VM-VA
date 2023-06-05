package form;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginFormTest {
    @Test
    public void loginReturnUser() {
        LoginForm loginForm = new LoginForm(null);
        assertNotNull(loginForm.getAuthenticatedUser("test@test.com", "12345"));
    }
}