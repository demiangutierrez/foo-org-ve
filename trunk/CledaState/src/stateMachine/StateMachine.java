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
 * A representation of the model object '<em><b>State Machine</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link stateMachine.StateMachine#getEventList <em>Event List</em>}</li>
 *   <li>{@link stateMachine.StateMachine#getStateList <em>State List</em>}</li>
 *   <li>{@link stateMachine.StateMachine#getName <em>Name</em>}</li>
 *   <li>{@link stateMachine.StateMachine#getPackage <em>Package</em>}</li>
 *   <li>{@link stateMachine.StateMachine#getInitial <em>Initial</em>}</li>
 *   <li>{@link stateMachine.StateMachine#getFieldList <em>Field List</em>}</li>
 *   <li>{@link stateMachine.StateMachine#getRoleList <em>Role List</em>}</li>
 * </ul>
 * </p>
 *
 * @see stateMachine.StateMachinePackage#getStateMachine()
 * @model
 * @generated
 */
public interface StateMachine extends EObject {
  /**
   * Returns the value of the '<em><b>Event List</b></em>' containment reference list.
   * The list contents are of type {@link stateMachine.Event}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Event List</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Event List</em>' containment reference list.
   * @see stateMachine.StateMachinePackage#getStateMachine_EventList()
   * @model containment="true" required="true"
   * @generated
   */
  EList<Event> getEventList();

  /**
   * Returns the value of the '<em><b>State List</b></em>' containment reference list.
   * The list contents are of type {@link stateMachine.State}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>State List</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>State List</em>' containment reference list.
   * @see stateMachine.StateMachinePackage#getStateMachine_StateList()
   * @model containment="true" required="true"
   * @generated
   */
  EList<State> getStateList();

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
   * @see stateMachine.StateMachinePackage#getStateMachine_Name()
   * @model
   * @generated
   */
  String getName();

  /**
   * Sets the value of the '{@link stateMachine.StateMachine#getName <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Name</em>' attribute.
   * @see #getName()
   * @generated
   */
  void setName(String value);

  /**
   * Returns the value of the '<em><b>Package</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Package</em>' attribute isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Package</em>' attribute.
   * @see #setPackage(String)
   * @see stateMachine.StateMachinePackage#getStateMachine_Package()
   * @model
   * @generated
   */
  String getPackage();

  /**
   * Sets the value of the '{@link stateMachine.StateMachine#getPackage <em>Package</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Package</em>' attribute.
   * @see #getPackage()
   * @generated
   */
  void setPackage(String value);

  /**
   * Returns the value of the '<em><b>Initial</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Initial</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Initial</em>' reference.
   * @see #setInitial(State)
   * @see stateMachine.StateMachinePackage#getStateMachine_Initial()
   * @model required="true"
   * @generated
   */
  State getInitial();

  /**
   * Sets the value of the '{@link stateMachine.StateMachine#getInitial <em>Initial</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Initial</em>' reference.
   * @see #getInitial()
   * @generated
   */
  void setInitial(State value);

  /**
   * Returns the value of the '<em><b>Field List</b></em>' containment reference list.
   * The list contents are of type {@link stateMachine.DocumentField}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Field List</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Field List</em>' containment reference list.
   * @see stateMachine.StateMachinePackage#getStateMachine_FieldList()
   * @model containment="true"
   * @generated
   */
  EList<DocumentField> getFieldList();

  /**
   * Returns the value of the '<em><b>Role List</b></em>' containment reference list.
   * The list contents are of type {@link stateMachine.Role}.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Role List</em>' containment reference list isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Role List</em>' containment reference list.
   * @see stateMachine.StateMachinePackage#getStateMachine_RoleList()
   * @model containment="true"
   * @generated
   */
  EList<Role> getRoleList();

} // StateMachine
