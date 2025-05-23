package edu.ntnu.idi.idatt.domain.entity.monopoly;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import edu.ntnu.idi.idatt.domain.entity.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests the {@link AssetsAccount} class.
 *
 * <p>The tests cover the class's primary functionalities:
 * account initialization, money transactions, property transactions, and rent payments.
 * </p>
 */
public class AssetsAccountTest {
  private AssetsAccount account;
  private double startingBalance;
  private Player owner;
  private AssetsAccount otherAccount;
  private Property property;

  /**
   * Sets up the test environment before each test.
   */
  @BeforeEach
  void setUp() {
    startingBalance = 1000;
    owner = new Player("TestPlayer");
    account = new AssetsAccount(owner, startingBalance);
    Player otherPlayer = new Player("OtherPlayer");
    otherAccount = new AssetsAccount(otherPlayer, startingBalance);
    property = new Property("TestProperty", 200, 50);
  }

  // ---------- Positive tests ----------

  /**
   * Tests if the AssetsAccount is initialized correctly with
   * valid parameters.
   *
   * <p>Expected: The account should be initialized with the correct
   * balance, owner, and no properties.</p>
   */
  @Test
  void testValidInitialization() {
    assertNotNull(account);
    assertEquals(startingBalance, account.getBalance());
    assertEquals(0, account.getProperties().size());
    assertEquals(owner, account.getOwner());
  }

  /**
   * Tests if the AssetsAccount can credit and debit money correctly.
   *
   * <p>Expected: The account should be able to credit and debit,
   * and update the balance accordingly.</p>
   */
  @Test
  void testMoneyTransaction() {
    double currentBalance = account.getBalance();
    account.debit(currentBalance);
    assertEquals(0, account.getBalance());

    account.credit(currentBalance);
    assertEquals(currentBalance, account.getBalance());
  }

  /**
   * Tests if the AssetsAccount can pay rent to another account
   * correctly when there is enough money.
   *
   * <p>Expected: The account should be able to pay rent and
   * update the balances of both accounts accordingly.</p>
   */
  @Test
  void testPayRentWithEnoughMoney() {
    double rent = account.getBalance();
    account.payRent(otherAccount, rent);
    assertEquals(startingBalance - rent, account.getBalance());
    assertEquals(startingBalance + rent, otherAccount.getBalance());
    assertFalse(account.isBankrupt());
  }

  /**
   * Tests if the AssetsAccount can buy a property correctly.
   *
   * <p>Expected: The account should be able to buy the property
   * if it has enough money, and the property should be added to
   * the account's properties.</p>
   */
  @Test
  void testBuyProperty() {
    account.buyProperty(property);
    assertEquals(1, account.getProperties().size());
    assertTrue(account.getProperties().contains(property));
    assertEquals(startingBalance - property.getCost(), account.getBalance());
  }

  /**
   * Tests if the AssetsAccount can pay rent to another account
   * correctly when there is not enough money.
   *
   * <p>Expected: The account should go bankrupt and the other account
   * should receive the remaining balance. The bankrupt account
   * should also release its properties.</p>
   */
  @Test
  void testPayRentWithNotEnoughMoney() {
    account.buyProperty(property);
    double currentBalance = account.getBalance();
    double rent = currentBalance + 100;
    account.payRent(otherAccount, rent);
    assertTrue(account.isBankrupt());
    assertEquals(startingBalance + currentBalance, otherAccount.getBalance());
    assertEquals(0, account.getProperties().size());
  }

  // ---------- Negative tests ----------

  /**
   * Tests if the AssetsAccount throws an exception when constructed
   * with a null owner.
   *
   * <p>Expected: IllegalArgumentException should be thrown.</p>
   */
  @Test
  void testInvalidInitialization() {
    assertThrows(IllegalArgumentException.class,
          () -> new AssetsAccount(null, startingBalance), "Owner cannot be null"
    );
  }

  /**
   * Tests if the AssetsAccount throws an exception when trying to
   * debit a negative amount or debit more than the available balance.
   *
   * <p>Expected: IllegalArgumentException should be thrown in both cases.</p>
   */
  @Test
  void testInvalidDebit() {
    assertThrows(IllegalArgumentException.class, () -> account.debit(-100));
    double currentBalance = account.getBalance();
    assertThrows(IllegalArgumentException.class, () -> account.debit(currentBalance + 100));
  }

  /**
   * Tests if the AssetsAccount throws an exception when trying to
   * credit a negative amount.
   *
   * <p>Expected: IllegalArgumentException should be thrown.</p>
   */
  @Test
  void testInvalidCredit() {
    assertThrows(IllegalArgumentException.class, ()
          -> account.credit(-100), "Amount must be positive"
    );
  }

  /**
   * Tests if the AssetsAccount throws an exception when trying to buy a property
   * that it cannot afford or if the property is null.
   *
   * <p>Expected: IllegalStateException should be thrown in both cases.</p>
   */
  @Test
  void testInvalidBuyProperty() {
    account.debit(startingBalance);
    Property property2 = new Property("TestProperty", startingBalance + 1, 50);
    assertThrows(IllegalArgumentException.class, () -> account.buyProperty(property2));
    assertEquals(0, account.getProperties().size());

    assertThrows(IllegalArgumentException.class, () -> account.buyProperty(null));
  }

  /**
   * Tests if the AssetsAccount throws an exception when trying to pay rent
   * to another account that is null or if the rent amount is negative.
   *
   * <p>Expected: IllegalArgumentException should be thrown in both cases.</p>
   */
  @Test
  void testInvalidPayRent() {
    assertThrows(IllegalArgumentException.class, () -> account.payRent(null, 100));
    assertThrows(IllegalArgumentException.class, () -> account.payRent(otherAccount, -100));
  }
}
