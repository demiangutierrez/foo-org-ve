/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package stateMachine.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import stateMachine.DocumentField;
import stateMachine.EFieldState;
import stateMachine.FieldState;
import stateMachine.StateMachinePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Field State</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link stateMachine.impl.FieldStateImpl#getState <em>State</em>}</li>
 *   <li>{@link stateMachine.impl.FieldStateImpl#getFieldRef <em>Field Ref</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class FieldStateImpl extends EObjectImpl implements FieldState {
  /**
   * The default value of the '{@link #getState() <em>State</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getState()
   * @generated
   * @ordered
   */
  protected static final EFieldState STATE_EDEFAULT = EFieldState.EDITABLE;

  /**
   * The cached value of the '{@link #getState() <em>State</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getState()
   * @generated
   * @ordered
   */
  protected EFieldState state = STATE_EDEFAULT;

  /**
   * The cached value of the '{@link #getFieldRef() <em>Field Ref</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFieldRef()
   * @generated
   * @ordered
   */
  protected DocumentField fieldRef;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected FieldStateImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return StateMachinePackage.Literals.FIELD_STATE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EFieldState getState() {
    return state;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setState(EFieldState newState) {
    EFieldState oldState = state;
    state = newState == null ? STATE_EDEFAULT : newState;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, StateMachinePackage.FIELD_STATE__STATE, oldState, state));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public DocumentField getFieldRef() {
    if (fieldRef != null && fieldRef.eIsProxy()) {
      InternalEObject oldFieldRef = (InternalEObject)fieldRef;
      fieldRef = (DocumentField)eResolveProxy(oldFieldRef);
      if (fieldRef != oldFieldRef) {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, StateMachinePackage.FIELD_STATE__FIELD_REF, oldFieldRef, fieldRef));
      }
    }
    return fieldRef;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public DocumentField basicGetFieldRef() {
    return fieldRef;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setFieldRef(DocumentField newFieldRef) {
    DocumentField oldFieldRef = fieldRef;
    fieldRef = newFieldRef;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, StateMachinePackage.FIELD_STATE__FIELD_REF, oldFieldRef, fieldRef));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public Object eGet(int featureID, boolean resolve, boolean coreType) {
    switch (featureID) {
      case StateMachinePackage.FIELD_STATE__STATE:
        return getState();
      case StateMachinePackage.FIELD_STATE__FIELD_REF:
        if (resolve) return getFieldRef();
        return basicGetFieldRef();
    }
    return super.eGet(featureID, resolve, coreType);
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public void eSet(int featureID, Object newValue) {
    switch (featureID) {
      case StateMachinePackage.FIELD_STATE__STATE:
        setState((EFieldState)newValue);
        return;
      case StateMachinePackage.FIELD_STATE__FIELD_REF:
        setFieldRef((DocumentField)newValue);
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
      case StateMachinePackage.FIELD_STATE__STATE:
        setState(STATE_EDEFAULT);
        return;
      case StateMachinePackage.FIELD_STATE__FIELD_REF:
        setFieldRef((DocumentField)null);
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
      case StateMachinePackage.FIELD_STATE__STATE:
        return state != STATE_EDEFAULT;
      case StateMachinePackage.FIELD_STATE__FIELD_REF:
        return fieldRef != null;
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
    result.append(" (state: ");
    result.append(state);
    result.append(')');
    return result.toString();
  }

} //FieldStateImpl
