/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package stateMachine;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see stateMachine.StateMachineFactory
 * @model kind="package"
 * @generated
 */
public interface StateMachinePackage extends EPackage {
  /**
   * The package name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNAME = "stateMachine";

  /**
   * The package namespace URI.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_URI = "http://stateMachine/1.0";

  /**
   * The package namespace name.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  String eNS_PREFIX = "stateMachine";

  /**
   * The singleton instance of the package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  StateMachinePackage eINSTANCE = stateMachine.impl.StateMachinePackageImpl.init();

  /**
   * The meta object id for the '{@link stateMachine.impl.StateMachineImpl <em>State Machine</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see stateMachine.impl.StateMachineImpl
   * @see stateMachine.impl.StateMachinePackageImpl#getStateMachine()
   * @generated
   */
  int STATE_MACHINE = 0;

  /**
   * The feature id for the '<em><b>Event List</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STATE_MACHINE__EVENT_LIST = 0;

  /**
   * The feature id for the '<em><b>State List</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STATE_MACHINE__STATE_LIST = 1;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STATE_MACHINE__NAME = 2;

  /**
   * The feature id for the '<em><b>Package</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STATE_MACHINE__PACKAGE = 3;

  /**
   * The feature id for the '<em><b>Initial</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STATE_MACHINE__INITIAL = 4;

  /**
   * The feature id for the '<em><b>Field List</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STATE_MACHINE__FIELD_LIST = 5;

  /**
   * The feature id for the '<em><b>Role List</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STATE_MACHINE__ROLE_LIST = 6;

  /**
   * The number of structural features of the '<em>State Machine</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STATE_MACHINE_FEATURE_COUNT = 7;

  /**
   * The meta object id for the '{@link stateMachine.impl.StateImpl <em>State</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see stateMachine.impl.StateImpl
   * @see stateMachine.impl.StateMachinePackageImpl#getState()
   * @generated
   */
  int STATE = 1;

  /**
   * The feature id for the '<em><b>Trans List</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STATE__TRANS_LIST = 0;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STATE__NAME = 1;

  /**
   * The feature id for the '<em><b>Field State</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STATE__FIELD_STATE = 2;

  /**
   * The feature id for the '<em><b>Trans Set List</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STATE__TRANS_SET_LIST = 3;

  /**
   * The number of structural features of the '<em>State</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int STATE_FEATURE_COUNT = 4;

  /**
   * The meta object id for the '{@link stateMachine.impl.EventImpl <em>Event</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see stateMachine.impl.EventImpl
   * @see stateMachine.impl.StateMachinePackageImpl#getEvent()
   * @generated
   */
  int EVENT = 2;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EVENT__NAME = 0;

  /**
   * The number of structural features of the '<em>Event</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int EVENT_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link stateMachine.impl.TransImpl <em>Trans</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see stateMachine.impl.TransImpl
   * @see stateMachine.impl.StateMachinePackageImpl#getTrans()
   * @generated
   */
  int TRANS = 3;

  /**
   * The feature id for the '<em><b>Event</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRANS__EVENT = 0;

  /**
   * The feature id for the '<em><b>Target</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRANS__TARGET = 1;

  /**
   * The number of structural features of the '<em>Trans</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRANS_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link stateMachine.impl.DocumentFieldImpl <em>Document Field</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see stateMachine.impl.DocumentFieldImpl
   * @see stateMachine.impl.StateMachinePackageImpl#getDocumentField()
   * @generated
   */
  int DOCUMENT_FIELD = 4;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DOCUMENT_FIELD__NAME = 0;

  /**
   * The number of structural features of the '<em>Document Field</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int DOCUMENT_FIELD_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link stateMachine.impl.FieldStateImpl <em>Field State</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see stateMachine.impl.FieldStateImpl
   * @see stateMachine.impl.StateMachinePackageImpl#getFieldState()
   * @generated
   */
  int FIELD_STATE = 5;

  /**
   * The feature id for the '<em><b>State</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIELD_STATE__STATE = 0;

  /**
   * The feature id for the '<em><b>Field Ref</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIELD_STATE__FIELD_REF = 1;

  /**
   * The number of structural features of the '<em>Field State</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int FIELD_STATE_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link stateMachine.impl.RoleImpl <em>Role</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see stateMachine.impl.RoleImpl
   * @see stateMachine.impl.StateMachinePackageImpl#getRole()
   * @generated
   */
  int ROLE = 6;

  /**
   * The feature id for the '<em><b>Name</b></em>' attribute.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ROLE__NAME = 0;

  /**
   * The number of structural features of the '<em>Role</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int ROLE_FEATURE_COUNT = 1;

  /**
   * The meta object id for the '{@link stateMachine.impl.TransSetImpl <em>Trans Set</em>}' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see stateMachine.impl.TransSetImpl
   * @see stateMachine.impl.StateMachinePackageImpl#getTransSet()
   * @generated
   */
  int TRANS_SET = 7;

  /**
   * The feature id for the '<em><b>Trans List</b></em>' containment reference list.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRANS_SET__TRANS_LIST = 0;

  /**
   * The feature id for the '<em><b>Fired By</b></em>' reference.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRANS_SET__FIRED_BY = 1;

  /**
   * The number of structural features of the '<em>Trans Set</em>' class.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   * @ordered
   */
  int TRANS_SET_FEATURE_COUNT = 2;

  /**
   * The meta object id for the '{@link stateMachine.EFieldState <em>EField State</em>}' enum.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @see stateMachine.EFieldState
   * @see stateMachine.impl.StateMachinePackageImpl#getEFieldState()
   * @generated
   */
  int EFIELD_STATE = 8;


  /**
   * Returns the meta object for class '{@link stateMachine.StateMachine <em>State Machine</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>State Machine</em>'.
   * @see stateMachine.StateMachine
   * @generated
   */
  EClass getStateMachine();

  /**
   * Returns the meta object for the containment reference list '{@link stateMachine.StateMachine#getEventList <em>Event List</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Event List</em>'.
   * @see stateMachine.StateMachine#getEventList()
   * @see #getStateMachine()
   * @generated
   */
  EReference getStateMachine_EventList();

  /**
   * Returns the meta object for the containment reference list '{@link stateMachine.StateMachine#getStateList <em>State List</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>State List</em>'.
   * @see stateMachine.StateMachine#getStateList()
   * @see #getStateMachine()
   * @generated
   */
  EReference getStateMachine_StateList();

  /**
   * Returns the meta object for the attribute '{@link stateMachine.StateMachine#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see stateMachine.StateMachine#getName()
   * @see #getStateMachine()
   * @generated
   */
  EAttribute getStateMachine_Name();

  /**
   * Returns the meta object for the attribute '{@link stateMachine.StateMachine#getPackage <em>Package</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Package</em>'.
   * @see stateMachine.StateMachine#getPackage()
   * @see #getStateMachine()
   * @generated
   */
  EAttribute getStateMachine_Package();

  /**
   * Returns the meta object for the reference '{@link stateMachine.StateMachine#getInitial <em>Initial</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Initial</em>'.
   * @see stateMachine.StateMachine#getInitial()
   * @see #getStateMachine()
   * @generated
   */
  EReference getStateMachine_Initial();

  /**
   * Returns the meta object for the containment reference list '{@link stateMachine.StateMachine#getFieldList <em>Field List</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Field List</em>'.
   * @see stateMachine.StateMachine#getFieldList()
   * @see #getStateMachine()
   * @generated
   */
  EReference getStateMachine_FieldList();

  /**
   * Returns the meta object for the containment reference list '{@link stateMachine.StateMachine#getRoleList <em>Role List</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Role List</em>'.
   * @see stateMachine.StateMachine#getRoleList()
   * @see #getStateMachine()
   * @generated
   */
  EReference getStateMachine_RoleList();

  /**
   * Returns the meta object for class '{@link stateMachine.State <em>State</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>State</em>'.
   * @see stateMachine.State
   * @generated
   */
  EClass getState();

  /**
   * Returns the meta object for the containment reference list '{@link stateMachine.State#getTransList <em>Trans List</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Trans List</em>'.
   * @see stateMachine.State#getTransList()
   * @see #getState()
   * @generated
   */
  EReference getState_TransList();

  /**
   * Returns the meta object for the attribute '{@link stateMachine.State#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see stateMachine.State#getName()
   * @see #getState()
   * @generated
   */
  EAttribute getState_Name();

  /**
   * Returns the meta object for the containment reference list '{@link stateMachine.State#getFieldState <em>Field State</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Field State</em>'.
   * @see stateMachine.State#getFieldState()
   * @see #getState()
   * @generated
   */
  EReference getState_FieldState();

  /**
   * Returns the meta object for the containment reference list '{@link stateMachine.State#getTransSetList <em>Trans Set List</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Trans Set List</em>'.
   * @see stateMachine.State#getTransSetList()
   * @see #getState()
   * @generated
   */
  EReference getState_TransSetList();

  /**
   * Returns the meta object for class '{@link stateMachine.Event <em>Event</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Event</em>'.
   * @see stateMachine.Event
   * @generated
   */
  EClass getEvent();

  /**
   * Returns the meta object for the attribute '{@link stateMachine.Event#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see stateMachine.Event#getName()
   * @see #getEvent()
   * @generated
   */
  EAttribute getEvent_Name();

  /**
   * Returns the meta object for class '{@link stateMachine.Trans <em>Trans</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Trans</em>'.
   * @see stateMachine.Trans
   * @generated
   */
  EClass getTrans();

  /**
   * Returns the meta object for the reference '{@link stateMachine.Trans#getEvent <em>Event</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Event</em>'.
   * @see stateMachine.Trans#getEvent()
   * @see #getTrans()
   * @generated
   */
  EReference getTrans_Event();

  /**
   * Returns the meta object for the reference '{@link stateMachine.Trans#getTarget <em>Target</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Target</em>'.
   * @see stateMachine.Trans#getTarget()
   * @see #getTrans()
   * @generated
   */
  EReference getTrans_Target();

  /**
   * Returns the meta object for class '{@link stateMachine.DocumentField <em>Document Field</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Document Field</em>'.
   * @see stateMachine.DocumentField
   * @generated
   */
  EClass getDocumentField();

  /**
   * Returns the meta object for the attribute '{@link stateMachine.DocumentField#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see stateMachine.DocumentField#getName()
   * @see #getDocumentField()
   * @generated
   */
  EAttribute getDocumentField_Name();

  /**
   * Returns the meta object for class '{@link stateMachine.FieldState <em>Field State</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Field State</em>'.
   * @see stateMachine.FieldState
   * @generated
   */
  EClass getFieldState();

  /**
   * Returns the meta object for the attribute '{@link stateMachine.FieldState#getState <em>State</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>State</em>'.
   * @see stateMachine.FieldState#getState()
   * @see #getFieldState()
   * @generated
   */
  EAttribute getFieldState_State();

  /**
   * Returns the meta object for the reference '{@link stateMachine.FieldState#getFieldRef <em>Field Ref</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Field Ref</em>'.
   * @see stateMachine.FieldState#getFieldRef()
   * @see #getFieldState()
   * @generated
   */
  EReference getFieldState_FieldRef();

  /**
   * Returns the meta object for class '{@link stateMachine.Role <em>Role</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Role</em>'.
   * @see stateMachine.Role
   * @generated
   */
  EClass getRole();

  /**
   * Returns the meta object for the attribute '{@link stateMachine.Role#getName <em>Name</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the attribute '<em>Name</em>'.
   * @see stateMachine.Role#getName()
   * @see #getRole()
   * @generated
   */
  EAttribute getRole_Name();

  /**
   * Returns the meta object for class '{@link stateMachine.TransSet <em>Trans Set</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for class '<em>Trans Set</em>'.
   * @see stateMachine.TransSet
   * @generated
   */
  EClass getTransSet();

  /**
   * Returns the meta object for the containment reference list '{@link stateMachine.TransSet#getTransList <em>Trans List</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the containment reference list '<em>Trans List</em>'.
   * @see stateMachine.TransSet#getTransList()
   * @see #getTransSet()
   * @generated
   */
  EReference getTransSet_TransList();

  /**
   * Returns the meta object for the reference '{@link stateMachine.TransSet#getFiredBy <em>Fired By</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for the reference '<em>Fired By</em>'.
   * @see stateMachine.TransSet#getFiredBy()
   * @see #getTransSet()
   * @generated
   */
  EReference getTransSet_FiredBy();

  /**
   * Returns the meta object for enum '{@link stateMachine.EFieldState <em>EField State</em>}'.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the meta object for enum '<em>EField State</em>'.
   * @see stateMachine.EFieldState
   * @generated
   */
  EEnum getEFieldState();

  /**
   * Returns the factory that creates the instances of the model.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @return the factory that creates the instances of the model.
   * @generated
   */
  StateMachineFactory getStateMachineFactory();

  /**
   * <!-- begin-user-doc -->
   * Defines literals for the meta objects that represent
   * <ul>
   *   <li>each class,</li>
   *   <li>each feature of each class,</li>
   *   <li>each enum,</li>
   *   <li>and each data type</li>
   * </ul>
   * <!-- end-user-doc -->
   * @generated
   */
  interface Literals {
    /**
     * The meta object literal for the '{@link stateMachine.impl.StateMachineImpl <em>State Machine</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see stateMachine.impl.StateMachineImpl
     * @see stateMachine.impl.StateMachinePackageImpl#getStateMachine()
     * @generated
     */
    EClass STATE_MACHINE = eINSTANCE.getStateMachine();

    /**
     * The meta object literal for the '<em><b>Event List</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference STATE_MACHINE__EVENT_LIST = eINSTANCE.getStateMachine_EventList();

    /**
     * The meta object literal for the '<em><b>State List</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference STATE_MACHINE__STATE_LIST = eINSTANCE.getStateMachine_StateList();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute STATE_MACHINE__NAME = eINSTANCE.getStateMachine_Name();

    /**
     * The meta object literal for the '<em><b>Package</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute STATE_MACHINE__PACKAGE = eINSTANCE.getStateMachine_Package();

    /**
     * The meta object literal for the '<em><b>Initial</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference STATE_MACHINE__INITIAL = eINSTANCE.getStateMachine_Initial();

    /**
     * The meta object literal for the '<em><b>Field List</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference STATE_MACHINE__FIELD_LIST = eINSTANCE.getStateMachine_FieldList();

    /**
     * The meta object literal for the '<em><b>Role List</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference STATE_MACHINE__ROLE_LIST = eINSTANCE.getStateMachine_RoleList();

    /**
     * The meta object literal for the '{@link stateMachine.impl.StateImpl <em>State</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see stateMachine.impl.StateImpl
     * @see stateMachine.impl.StateMachinePackageImpl#getState()
     * @generated
     */
    EClass STATE = eINSTANCE.getState();

    /**
     * The meta object literal for the '<em><b>Trans List</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference STATE__TRANS_LIST = eINSTANCE.getState_TransList();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute STATE__NAME = eINSTANCE.getState_Name();

    /**
     * The meta object literal for the '<em><b>Field State</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference STATE__FIELD_STATE = eINSTANCE.getState_FieldState();

    /**
     * The meta object literal for the '<em><b>Trans Set List</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference STATE__TRANS_SET_LIST = eINSTANCE.getState_TransSetList();

    /**
     * The meta object literal for the '{@link stateMachine.impl.EventImpl <em>Event</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see stateMachine.impl.EventImpl
     * @see stateMachine.impl.StateMachinePackageImpl#getEvent()
     * @generated
     */
    EClass EVENT = eINSTANCE.getEvent();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute EVENT__NAME = eINSTANCE.getEvent_Name();

    /**
     * The meta object literal for the '{@link stateMachine.impl.TransImpl <em>Trans</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see stateMachine.impl.TransImpl
     * @see stateMachine.impl.StateMachinePackageImpl#getTrans()
     * @generated
     */
    EClass TRANS = eINSTANCE.getTrans();

    /**
     * The meta object literal for the '<em><b>Event</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference TRANS__EVENT = eINSTANCE.getTrans_Event();

    /**
     * The meta object literal for the '<em><b>Target</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference TRANS__TARGET = eINSTANCE.getTrans_Target();

    /**
     * The meta object literal for the '{@link stateMachine.impl.DocumentFieldImpl <em>Document Field</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see stateMachine.impl.DocumentFieldImpl
     * @see stateMachine.impl.StateMachinePackageImpl#getDocumentField()
     * @generated
     */
    EClass DOCUMENT_FIELD = eINSTANCE.getDocumentField();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute DOCUMENT_FIELD__NAME = eINSTANCE.getDocumentField_Name();

    /**
     * The meta object literal for the '{@link stateMachine.impl.FieldStateImpl <em>Field State</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see stateMachine.impl.FieldStateImpl
     * @see stateMachine.impl.StateMachinePackageImpl#getFieldState()
     * @generated
     */
    EClass FIELD_STATE = eINSTANCE.getFieldState();

    /**
     * The meta object literal for the '<em><b>State</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute FIELD_STATE__STATE = eINSTANCE.getFieldState_State();

    /**
     * The meta object literal for the '<em><b>Field Ref</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference FIELD_STATE__FIELD_REF = eINSTANCE.getFieldState_FieldRef();

    /**
     * The meta object literal for the '{@link stateMachine.impl.RoleImpl <em>Role</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see stateMachine.impl.RoleImpl
     * @see stateMachine.impl.StateMachinePackageImpl#getRole()
     * @generated
     */
    EClass ROLE = eINSTANCE.getRole();

    /**
     * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EAttribute ROLE__NAME = eINSTANCE.getRole_Name();

    /**
     * The meta object literal for the '{@link stateMachine.impl.TransSetImpl <em>Trans Set</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see stateMachine.impl.TransSetImpl
     * @see stateMachine.impl.StateMachinePackageImpl#getTransSet()
     * @generated
     */
    EClass TRANS_SET = eINSTANCE.getTransSet();

    /**
     * The meta object literal for the '<em><b>Trans List</b></em>' containment reference list feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference TRANS_SET__TRANS_LIST = eINSTANCE.getTransSet_TransList();

    /**
     * The meta object literal for the '<em><b>Fired By</b></em>' reference feature.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    EReference TRANS_SET__FIRED_BY = eINSTANCE.getTransSet_FiredBy();

    /**
     * The meta object literal for the '{@link stateMachine.EFieldState <em>EField State</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see stateMachine.EFieldState
     * @see stateMachine.impl.StateMachinePackageImpl#getEFieldState()
     * @generated
     */
    EEnum EFIELD_STATE = eINSTANCE.getEFieldState();

  }

} //StateMachinePackage
