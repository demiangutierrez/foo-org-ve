/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package stateMachine;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>EField State</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see stateMachine.StateMachinePackage#getEFieldState()
 * @model
 * @generated
 */
public enum EFieldState implements Enumerator {
  /**
   * The '<em><b>EDITABLE</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #EDITABLE_VALUE
   * @generated
   * @ordered
   */
  EDITABLE(0, "EDITABLE", "EDITABLE"),

  /**
   * The '<em><b>READONLY</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #READONLY_VALUE
   * @generated
   * @ordered
   */
  READONLY(1, "READONLY", "READONLY"),

  /**
   * The '<em><b>HIDDEN</b></em>' literal object.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #HIDDEN_VALUE
   * @generated
   * @ordered
   */
  HIDDEN(2, "HIDDEN", "HIDDEN");

  /**
   * The '<em><b>EDITABLE</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>EDITABLE</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #EDITABLE
   * @model
   * @generated
   * @ordered
   */
  public static final int EDITABLE_VALUE = 0;

  /**
   * The '<em><b>READONLY</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>READONLY</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #READONLY
   * @model
   * @generated
   * @ordered
   */
  public static final int READONLY_VALUE = 1;

  /**
   * The '<em><b>HIDDEN</b></em>' literal value.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of '<em><b>HIDDEN</b></em>' literal object isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @see #HIDDEN
   * @model
   * @generated
   * @ordered
   */
  public static final int HIDDEN_VALUE = 2;

  /**
   * An array of all the '<em><b>EField State</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private static final EFieldState[] VALUES_ARRAY =
    new EFieldState[] {
      EDITABLE,
      READONLY,
      HIDDEN,
    };

  /**
   * A public read-only list of all the '<em><b>EField State</b></em>' enumerators.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static final List<EFieldState> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

  /**
   * Returns the '<em><b>EField State</b></em>' literal with the specified literal value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static EFieldState get(String literal) {
    for (int i = 0; i < VALUES_ARRAY.length; ++i) {
      EFieldState result = VALUES_ARRAY[i];
      if (result.toString().equals(literal)) {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>EField State</b></em>' literal with the specified name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static EFieldState getByName(String name) {
    for (int i = 0; i < VALUES_ARRAY.length; ++i) {
      EFieldState result = VALUES_ARRAY[i];
      if (result.getName().equals(name)) {
        return result;
      }
    }
    return null;
  }

  /**
   * Returns the '<em><b>EField State</b></em>' literal with the specified integer value.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public static EFieldState get(int value) {
    switch (value) {
      case EDITABLE_VALUE: return EDITABLE;
      case READONLY_VALUE: return READONLY;
      case HIDDEN_VALUE: return HIDDEN;
    }
    return null;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private final int value;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private final String name;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private final String literal;

  /**
   * Only this class can construct instances.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  private EFieldState(int value, String name, String literal) {
    this.value = value;
    this.name = name;
    this.literal = literal;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public int getValue() {
    return value;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getName() {
    return name;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getLiteral() {
    return literal;
  }

  /**
   * Returns the literal value of the enumerator, which is its string representation.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString() {
    return literal;
  }
  
} //EFieldState
