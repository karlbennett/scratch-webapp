package scratch.spring.webapp.data;

import org.dbunit.dataset.ITable;
import org.junit.Test;
import org.springframework.dao.DataIntegrityViolationException;
import scratch.spring.webapp.AbstractDataTester;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import java.util.Collections;

import static org.junit.Assert.assertEquals;
import static scratch.spring.webapp.test.Utils.EMAIL;
import static scratch.spring.webapp.test.Utils.EMAIL_FOUR;
import static scratch.spring.webapp.test.Utils.EMAIL_ONE;
import static scratch.spring.webapp.test.Utils.EMAIL_THREE;
import static scratch.spring.webapp.test.Utils.EMAIL_TWO;
import static scratch.spring.webapp.test.Utils.FIRST_NAME;
import static scratch.spring.webapp.test.Utils.FIRST_NAME_FOUR;
import static scratch.spring.webapp.test.Utils.FIRST_NAME_TWO;
import static scratch.spring.webapp.test.Utils.LAST_NAME;
import static scratch.spring.webapp.test.Utils.LAST_NAME_VALUE;
import static scratch.spring.webapp.test.Utils.USERS;
import static scratch.spring.webapp.test.Utils.USER_ONE;
import static scratch.spring.webapp.test.Utils.USER_TABLE;
import static scratch.spring.webapp.test.Utils.USER_THREE;
import static scratch.spring.webapp.test.Utils.USER_TWO;

/**
 * @author Karl Bennett
 */
public class UserTest extends AbstractDataTester {

    @Test
    public void testConstructUserExistingId() throws Exception {

        assertEquals("user one should be populated correctly.", USER_ONE, new User(1L));
        assertEquals("user two should be populated correctly.", USER_TWO, new User(2L));
        assertEquals("user three should be populated correctly.", USER_THREE, new User(3L));
    }

    @Test(expected = EntityNotFoundException.class)
    public void testConstructNonExistentUser() throws Exception {

        new User(-1L);
    }

    @Test
    public void testCreate() throws Exception {

        User user = new User();
        user.setEmail(EMAIL_FOUR);
        user.setFirstName(FIRST_NAME_FOUR);
        user.setLastName(LAST_NAME_VALUE);

        user.create();

        ITable table = getTableRow(USER_TABLE, EMAIL_FOUR);

        assertEquals("a row with email four should have been created.", EMAIL_FOUR, table.getValue(0, EMAIL));
        assertEquals("a row with first name four should have been created.",
                FIRST_NAME_FOUR, table.getValue(0, FIRST_NAME));
        assertEquals("a row with the last name should have been created.",
                LAST_NAME_VALUE, table.getValue(0, LAST_NAME));
    }

    @Test
    public void testCreateExistingUser() throws Exception {

        USER_ONE.create();
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testCreateUserWithExistingEmail() throws Exception {

        User user = new User();
        user.setEmail(EMAIL_ONE);
        user.setFirstName(FIRST_NAME_FOUR);
        user.setLastName(LAST_NAME_VALUE);

        user.create();
    }

    @Test
    public void testAll() throws Exception {

        assertEquals("all the persisted users should be returned.", USERS, User.all());

        truncateTable(USER_TABLE);

        assertEquals("no persisted users should be returned.", Collections.<User>emptyList(), User.all());
    }

    @Test
    public void testUpdate() throws Exception {

        User user = new User(2L);

        user.setEmail(EMAIL_FOUR);

        user.update();

        ITable table = getTableRow(USER_TABLE, EMAIL_FOUR);

        assertEquals("a row with email four should have been created.", EMAIL_FOUR, table.getValue(0, EMAIL));
        assertEquals("the first name should still be the first name two value.",
                FIRST_NAME_TWO, table.getValue(0, FIRST_NAME));
        assertEquals("the last name should still be the last name value.",
                LAST_NAME_VALUE, table.getValue(0, LAST_NAME));
    }

    @Test(expected = EntityNotFoundException.class)
    public void testUpdateNonExistentUser() throws Exception {

        User user = new User(-1L);

        user.update();
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testUpdateWithExistingEmail() throws Exception {

        User user = new User(1L);

        user.setEmail(EMAIL_TWO);

        user.update();
    }

    @Test
    public void testDelete() throws Exception {

        new User(3L).delete();

        ITable table = getTableRow(USER_TABLE, EMAIL_THREE);

        assertEquals("the row with user three should not exists.", 0, table.getRowCount());
    }

    @Test(expected = EntityNotFoundException.class)
    public void testDeleteNonExistentUser() throws Exception {

        new User(-1L).delete();
    }
}
