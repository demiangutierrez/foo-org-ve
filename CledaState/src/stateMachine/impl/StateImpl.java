/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package stateMachine.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;

import stateMachine.FieldState;
import stateMachine.State;
import stateMachine.StateMachinePackage;
import stateMachine.Trans;
import stateMachine.TransSet;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>State</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link stateMachine.impl.StateImpl#getTransList <em>Trans List</em>}</li>
 *   <li>{@link stateMachine.impl.StateImpl#getName <em>Name</em>}</li>
 *   <li>{@link stateMachine.impl.StateImpl#getFieldState <em>Field State</em>}</li>
 *   <li>{@link stateMachine.impl.StateImpl#getTransSetList <em>Trans Set List</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class StateImpl extends EObjectImpl implements State {
  /**
   * The cached value of the '{@link #getTransList() <em>Trans List</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTransList()
   * @generated
   * @ordered
   */
  protected EList<Trans> transList;

  /**
   * The default value of the '{@link #getName() <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getName()
   * @generated
   * @ordered
   */
  protected static final String NAME_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getName()
   * @generated
   * @ordered
   */
  protected String name = NAME_EDEFAULT;

  /**
   * The cached value of the '{@link #getFieldState() <em>Field State</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFieldState()
   * @generated
   * @ordered
   */
  protected EList<FieldState> fieldState;

  /**
   * The cached value of the '{@link #getTransSetList() <em>Trans Set List</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getTransSetList()
   * @generated
   * @ordered
   */
  protected EList<TransSet> transSetList;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected StateImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return StateMachinePackage.Literals.STATE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Trans> getTransList() {
    if (transList == null) {
      transList = new EObjectContainmentEList<Trans>(Trans.class, this, StateMachinePackage.STATE__TRANS_LIST);
    }
    return transList;
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
  public void setName(String newName) {
    String oldName = name;
    name = newName;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, StateMachinePackage.STATE__NAME, oldName, name));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<FieldState> getFieldState() {
    if (fieldState == null) {
      fieldState = new EObjectContainmentEList<FieldState>(FieldState.class, this, StateMachinePackage.STATE__FIELD_STATE);
    }
    return fieldState;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<TransSet> getTransSetList() {
    if (transSetList == null) {
      transSetList = new EObjectContainmentEList<TransSet>(TransSet.class, this, StateMachinePackage.STATE__TRANS_SET_LIST);
    }
    return transSetList;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case StateMachinePackage.STATE__TRANS_LIST:
        return ((InternalEList<?>)getTransList()).basicRemove(otherEnd, msgs);
      case StateMachinePackage.STATE__FIELD_STATE:
        return ((InternalEList<?>)getFieldState()).basicRemove(otherEnd, msgs);
      case StateMachinePackage.STATE__TRANS_SET_LIST:
        return ((InternalEList<?>)getTransSetList()).basicRemove(otherEnd, msgs);
    }
    return super.eInverseRemove(otherEnd, featureID, msgs);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
      case StateMachinePackage.STATE__TRANS_LIST:
        return getTransList();
      case StateMachinePackage.STATE__NAME:
        return getName();
      case StateMachinePackage.STATE__FIELD_STATE:
        return getFieldState();
      case StateMachinePackage.STATE__TRANS_SET_LIST:
        return getTransSetList();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @SuppressWarnings("unchecked")
  @Override
  public void eSet(int featureID, Object newValue) {
    switch (featureID) {
      case StateMachinePackage.STATE__TRANS_LIST:
        getTransList().clear();
        getTransList().addAll((Collection<? extends Trans>)newValue);
        return;
      case StateMachinePackage.STATE__NAME:
        setName((String)newValue);
        return;
      case StateMachinePackage.STATE__FIELD_STATE:
        getFieldState().clear();
        getFieldState().addAll((Collection<? extends FieldState>)newValue);
        return;
      case StateMachinePackage.STATE__TRANS_SET_LIST:
        getTransSetList().clear();
        getTransSetList().addAll((Collection<? extends TransSet>)newValue);
        return;
    }
    super.eSet(featureID, newValue);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eUnset(int featureID) {
    switch (featureID) {
      case StateMachinePackage.STATE__TRANS_LIST:
        getTransList().clear();
        return;
      case StateMachinePackage.STATE__NAME:
        setName(NAME_EDEFAULT);
        return;
      case StateMachinePackage.STATE__FIELD_STATE:
        getFieldState().clear();
        return;
      case StateMachinePackage.STATE__TRANS_SET_LIST:
        getTransSetList().clear();
        return;
    }
    super.eUnset(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public boolean eIsSet(int featureID) {
    switch (featureID) {
      case StateMachinePackage.STATE__TRANS_LIST:
        return transList != null && !transList.isEmpty();
      case StateMachinePackage.STATE__NAME:
        return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
      case StateMachinePackage.STATE__FIELD_STATE:
        return fieldState != null && !fieldState.isEmpty();
      case StateMachinePackage.STATE__TRANS_SET_LIST:
        return transSetList != null && !transSetList.isEmpty();
    }
    return super.eIsSet(featureID);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public String toString() {
    if (eIsProxy()) return super.toString();

    StringBuffer result = new StringBuffer(super.toString());
    result.append(" (name: ");
    result.append(name);
    result.append(')');
    return result.toString();
  }

} //StateImpl
