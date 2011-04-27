/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package stateMachine;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Field State</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link stateMachine.FieldState#getState <em>State</em>}</li>
 *   <li>{@link stateMachine.FieldState#getFieldRef <em>Field Ref</em>}</li>
 * </ul>
 * </p>
 *
 * @see stateMachine.StateMachinePackage#getFieldState()
 * @model
 * @generated
 */
public interface FieldState extends EObject {
  /**
   * Returns the value of the '<em><b>State</b></em>' attribute.
   * The literals are from the enumeration {@link stateMachine.EFieldState}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>State</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>State</em>' attribute.
   * @see stateMachine.EFieldState
   * @see #setState(EFieldState)
   * @see stateMachine.StateMachinePackage#getFieldState_State()
   * @model
   * @generated
   */
  EFieldState getState();

  /**
   * Sets the value of the '{@link stateMachine.FieldState#getState <em>State</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>State</em>' attribute.
   * @see stateMachine.EFieldState
   * @see #getState()
   * @generated
   */
  void setState(EFieldState value);

  /**
   * Returns the value of the '<em><b>Field Ref</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Field Ref</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Field Ref</em>' reference.
   * @see #setFieldRef(DocumentField)
   * @see stateMachine.StateMachinePackage#getFieldState_FieldRef()
   * @model required="true"
   * @generated
   */
  DocumentField getFieldRef();

  /**
   * Sets the value of the '{@link stateMachine.FieldState#getFieldRef <em>Field Ref</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Field Ref</em>' reference.
   * @see #getFieldRef()
   * @generated
   */
  void setFieldRef(DocumentField value);

} // FieldState
