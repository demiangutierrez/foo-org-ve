/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package stateMachine;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>State</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link stateMachine.State#getTransList <em>Trans List</em>}</li>
 *   <li>{@link stateMachine.State#getName <em>Name</em>}</li>
 *   <li>{@link stateMachine.State#getFieldState <em>Field State</em>}</li>
 *   <li>{@link stateMachine.State#getTransSetList <em>Trans Set List</em>}</li>
 * </ul>
 * </p>
 *
 * @see stateMachine.StateMachinePackage#getState()
 * @model
 * @generated
 */
public interface State extends EObject {
  /**
   * Returns the value of the '<em><b>Trans List</b></em>' containment reference list.
   * The list contents are of type {@link stateMachine.Trans}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Trans List</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Trans List</em>' containment reference list.
   * @see stateMachine.StateMachinePackage#getState_TransList()
   * @model containment="true"
   * @generated
   */
  EList<Trans> getTransList();

  /**
   * Returns the value of the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Name</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Name</em>' attribute.
   * @see #setName(String)
   * @see stateMachine.StateMachinePackage#getState_Name()
   * @model
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link stateMachine.State#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

  /**
   * Returns the value of the '<em><b>Field State</b></em>' containment reference list.
   * The list contents are of type {@link stateMachine.FieldState}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Field State</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Field State</em>' containment reference list.
   * @see stateMachine.StateMachinePackage#getState_FieldState()
   * @model containment="true"
   * @generated
   */
  EList<FieldState> getFieldState();

  /**
   * Returns the value of the '<em><b>Trans Set List</b></em>' containment reference list.
   * The list contents are of type {@link stateMachine.TransSet}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Trans Set List</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Trans Set List</em>' containment reference list.
   * @see stateMachine.StateMachinePackage#getState_TransSetList()
   * @model containment="true"
   * @generated
   */
  EList<TransSet> getTransSetList();

} // State
