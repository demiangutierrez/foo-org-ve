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

import stateMachine.Role;
import stateMachine.StateMachinePackage;
import stateMachine.Trans;
import stateMachine.TransSet;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Trans Set</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link stateMachine.impl.TransSetImpl#getTransList <em>Trans List</em>}</li>
 *   <li>{@link stateMachine.impl.TransSetImpl#getFiredBy <em>Fired By</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class TransSetImpl extends EObjectImpl implements TransSet {
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
   * The cached value of the '{@link #getFiredBy() <em>Fired By</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFiredBy()
   * @generated
   * @ordered
   */
  protected Role firedBy;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected TransSetImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return StateMachinePackage.Literals.TRANS_SET;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Trans> getTransList() {
    if (transList == null) {
      transList = new EObjectContainmentEList<Trans>(Trans.class, this, StateMachinePackage.TRANS_SET__TRANS_LIST);
    }
    return transList;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Role getFiredBy() {
    if (firedBy != null && firedBy.eIsProxy()) {
      InternalEObject oldFiredBy = (InternalEObject)firedBy;
      firedBy = (Role)eResolveProxy(oldFiredBy);
      if (firedBy != oldFiredBy) {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, StateMachinePackage.TRANS_SET__FIRED_BY, oldFiredBy, firedBy));
      }
    }
    return firedBy;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public Role basicGetFiredBy() {
    return firedBy;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setFiredBy(Role newFiredBy) {
    Role oldFiredBy = firedBy;
    firedBy = newFiredBy;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, StateMachinePackage.TRANS_SET__FIRED_BY, oldFiredBy, firedBy));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case StateMachinePackage.TRANS_SET__TRANS_LIST:
        return ((InternalEList<?>)getTransList()).basicRemove(otherEnd, msgs);
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
      case StateMachinePackage.TRANS_SET__TRANS_LIST:
        return getTransList();
      case StateMachinePackage.TRANS_SET__FIRED_BY:
        if (resolve) return getFiredBy();
        return basicGetFiredBy();
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
      case StateMachinePackage.TRANS_SET__TRANS_LIST:
        getTransList().clear();
        getTransList().addAll((Collection<? extends Trans>)newValue);
        return;
      case StateMachinePackage.TRANS_SET__FIRED_BY:
        setFiredBy((Role)newValue);
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
      case StateMachinePackage.TRANS_SET__TRANS_LIST:
        getTransList().clear();
        return;
      case StateMachinePackage.TRANS_SET__FIRED_BY:
        setFiredBy((Role)null);
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
      case StateMachinePackage.TRANS_SET__TRANS_LIST:
        return transList != null && !transList.isEmpty();
      case StateMachinePackage.TRANS_SET__FIRED_BY:
        return firedBy != null;
    }
    return super.eIsSet(featureID);
  }

} //TransSetImpl
