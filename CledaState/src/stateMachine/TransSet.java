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
 * A representation of the model object '<em><b>Trans Set</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link stateMachine.TransSet#getTransList <em>Trans List</em>}</li>
 *   <li>{@link stateMachine.TransSet#getFiredBy <em>Fired By</em>}</li>
 * </ul>
 * </p>
 *
 * @see stateMachine.StateMachinePackage#getTransSet()
 * @model
 * @generated
 */
public interface TransSet extends EObject {
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
   * @see stateMachine.StateMachinePackage#getTransSet_TransList()
   * @model containment="true"
   * @generated
   */
  EList<Trans> getTransList();

  /**
   * Returns the value of the '<em><b>Fired By</b></em>' reference.
   * <!-- begin-user-doc -->
   * <p>
   * If the meaning of the '<em>Fired By</em>' reference isn't clear,
   * there really should be more of a description here...
   * </p>
   * <!-- end-user-doc -->
   * @return the value of the '<em>Fired By</em>' reference.
   * @see #setFiredBy(Role)
   * @see stateMachine.StateMachinePackage#getTransSet_FiredBy()
   * @model required="true"
   * @generated
   */
  Role getFiredBy();

  /**
   * Sets the value of the '{@link stateMachine.TransSet#getFiredBy <em>Fired By</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param value the new value of the '<em>Fired By</em>' reference.
   * @see #getFiredBy()
   * @generated
   */
  void setFiredBy(Role value);

} // TransSet
