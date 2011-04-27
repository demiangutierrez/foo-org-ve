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

import stateMachine.DocumentField;
import stateMachine.Event;
import stateMachine.Role;
import stateMachine.State;
import stateMachine.StateMachine;
import stateMachine.StateMachinePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>State Machine</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link stateMachine.impl.StateMachineImpl#getEventList <em>Event List</em>}</li>
 *   <li>{@link stateMachine.impl.StateMachineImpl#getStateList <em>State List</em>}</li>
 *   <li>{@link stateMachine.impl.StateMachineImpl#getName <em>Name</em>}</li>
 *   <li>{@link stateMachine.impl.StateMachineImpl#getPackage <em>Package</em>}</li>
 *   <li>{@link stateMachine.impl.StateMachineImpl#getInitial <em>Initial</em>}</li>
 *   <li>{@link stateMachine.impl.StateMachineImpl#getFieldList <em>Field List</em>}</li>
 *   <li>{@link stateMachine.impl.StateMachineImpl#getRoleList <em>Role List</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class StateMachineImpl extends EObjectImpl implements StateMachine {
  /**
   * The cached value of the '{@link #getEventList() <em>Event List</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getEventList()
   * @generated
   * @ordered
   */
  protected EList<Event> eventList;

  /**
   * The cached value of the '{@link #getStateList() <em>State List</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getStateList()
   * @generated
   * @ordered
   */
  protected EList<State> stateList;

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
   * The default value of the '{@link #getPackage() <em>Package</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPackage()
   * @generated
   * @ordered
   */
  protected static final String PACKAGE_EDEFAULT = null;

  /**
   * The cached value of the '{@link #getPackage() <em>Package</em>}' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getPackage()
   * @generated
   * @ordered
   */
  protected String package_ = PACKAGE_EDEFAULT;

  /**
   * The cached value of the '{@link #getInitial() <em>Initial</em>}' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getInitial()
   * @generated
   * @ordered
   */
  protected State initial;

  /**
   * The cached value of the '{@link #getFieldList() <em>Field List</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getFieldList()
   * @generated
   * @ordered
   */
  protected EList<DocumentField> fieldList;

  /**
   * The cached value of the '{@link #getRoleList() <em>Role List</em>}' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see #getRoleList()
   * @generated
   * @ordered
   */
  protected EList<Role> roleList;

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected StateMachineImpl() {
    super();
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  protected EClass eStaticClass() {
    return StateMachinePackage.Literals.STATE_MACHINE;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Event> getEventList() {
    if (eventList == null) {
      eventList = new EObjectContainmentEList<Event>(Event.class, this, StateMachinePackage.STATE_MACHINE__EVENT_LIST);
    }
    return eventList;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<State> getStateList() {
    if (stateList == null) {
      stateList = new EObjectContainmentEList<State>(State.class, this, StateMachinePackage.STATE_MACHINE__STATE_LIST);
    }
    return stateList;
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
      eNotify(new ENotificationImpl(this, Notification.SET, StateMachinePackage.STATE_MACHINE__NAME, oldName, name));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public String getPackage() {
    return package_;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setPackage(String newPackage) {
    String oldPackage = package_;
    package_ = newPackage;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, StateMachinePackage.STATE_MACHINE__PACKAGE, oldPackage, package_));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public State getInitial() {
    if (initial != null && initial.eIsProxy()) {
      InternalEObject oldInitial = (InternalEObject)initial;
      initial = (State)eResolveProxy(oldInitial);
      if (initial != oldInitial) {
        if (eNotificationRequired())
          eNotify(new ENotificationImpl(this, Notification.RESOLVE, StateMachinePackage.STATE_MACHINE__INITIAL, oldInitial, initial));
      }
    }
    return initial;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public State basicGetInitial() {
    return initial;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public void setInitial(State newInitial) {
    State oldInitial = initial;
    initial = newInitial;
    if (eNotificationRequired())
      eNotify(new ENotificationImpl(this, Notification.SET, StateMachinePackage.STATE_MACHINE__INITIAL, oldInitial, initial));
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<DocumentField> getFieldList() {
    if (fieldList == null) {
      fieldList = new EObjectContainmentEList<DocumentField>(DocumentField.class, this, StateMachinePackage.STATE_MACHINE__FIELD_LIST);
    }
    return fieldList;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public EList<Role> getRoleList() {
    if (roleList == null) {
      roleList = new EObjectContainmentEList<Role>(Role.class, this, StateMachinePackage.STATE_MACHINE__ROLE_LIST);
    }
    return roleList;
  }

  /**
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  @Override
  public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
    switch (featureID) {
      case StateMachinePackage.STATE_MACHINE__EVENT_LIST:
        return ((InternalEList<?>)getEventList()).basicRemove(otherEnd, msgs);
      case StateMachinePackage.STATE_MACHINE__STATE_LIST:
        return ((InternalEList<?>)getStateList()).basicRemove(otherEnd, msgs);
      case StateMachinePackage.STATE_MACHINE__FIELD_LIST:
        return ((InternalEList<?>)getFieldList()).basicRemove(otherEnd, msgs);
      case StateMachinePackage.STATE_MACHINE__ROLE_LIST:
        return ((InternalEList<?>)getRoleList()).basicRemove(otherEnd, msgs);
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
      case StateMachinePackage.STATE_MACHINE__EVENT_LIST:
        return getEventList();
      case StateMachinePackage.STATE_MACHINE__STATE_LIST:
        return getStateList();
      case StateMachinePackage.STATE_MACHINE__NAME:
        return getName();
      case StateMachinePackage.STATE_MACHINE__PACKAGE:
        return getPackage();
      case StateMachinePackage.STATE_MACHINE__INITIAL:
        if (resolve) return getInitial();
        return basicGetInitial();
      case StateMachinePackage.STATE_MACHINE__FIELD_LIST:
        return getFieldList();
      case StateMachinePackage.STATE_MACHINE__ROLE_LIST:
        return getRoleList();
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
      case StateMachinePackage.STATE_MACHINE__EVENT_LIST:
        getEventList().clear();
        getEventList().addAll((Collection<? extends Event>)newValue);
        return;
      case StateMachinePackage.STATE_MACHINE__STATE_LIST:
        getStateList().clear();
        getStateList().addAll((Collection<? extends State>)newValue);
        return;
      case StateMachinePackage.STATE_MACHINE__NAME:
        setName((String)newValue);
        return;
      case StateMachinePackage.STATE_MACHINE__PACKAGE:
        setPackage((String)newValue);
        return;
      case StateMachinePackage.STATE_MACHINE__INITIAL:
        setInitial((State)newValue);
        return;
      case StateMachinePackage.STATE_MACHINE__FIELD_LIST:
        getFieldList().clear();
        getFieldList().addAll((Collection<? extends DocumentField>)newValue);
        return;
      case StateMachinePackage.STATE_MACHINE__ROLE_LIST:
        getRoleList().clear();
        getRoleList().addAll((Collection<? extends Role>)newValue);
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
      case StateMachinePackage.STATE_MACHINE__EVENT_LIST:
        getEventList().clear();
        return;
      case StateMachinePackage.STATE_MACHINE__STATE_LIST:
        getStateList().clear();
        return;
      case StateMachinePackage.STATE_MACHINE__NAME:
        setName(NAME_EDEFAULT);
        return;
      case StateMachinePackage.STATE_MACHINE__PACKAGE:
        setPackage(PACKAGE_EDEFAULT);
        return;
      case StateMachinePackage.STATE_MACHINE__INITIAL:
        setInitial((State)null);
        return;
      case StateMachinePackage.STATE_MACHINE__FIELD_LIST:
        getFieldList().clear();
        return;
      case StateMachinePackage.STATE_MACHINE__ROLE_LIST:
        getRoleList().clear();
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
      case StateMachinePackage.STATE_MACHINE__EVENT_LIST:
        return eventList != null && !eventList.isEmpty();
      case StateMachinePackage.STATE_MACHINE__STATE_LIST:
        return stateList != null && !stateList.isEmpty();
      case StateMachinePackage.STATE_MACHINE__NAME:
        return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
      case StateMachinePackage.STATE_MACHINE__PACKAGE:
        return PACKAGE_EDEFAULT == null ? package_ != null : !PACKAGE_EDEFAULT.equals(package_);
      case StateMachinePackage.STATE_MACHINE__INITIAL:
        return initial != null;
      case StateMachinePackage.STATE_MACHINE__FIELD_LIST:
        return fieldList != null && !fieldList.isEmpty();
      case StateMachinePackage.STATE_MACHINE__ROLE_LIST:
        return roleList != null && !roleList.isEmpty();
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
    result.append(", package: ");
    result.append(package_);
    result.append(')');
    return result.toString();
  }

} //StateMachineImpl
