/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package stateMachine.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import stateMachine.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see stateMachine.StateMachinePackage
 * @generated
 */
public class StateMachineAdapterFactory extends AdapterFactoryImpl {
  /**
   * The cached model package.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected static StateMachinePackage modelPackage;

  /**
   * Creates an instance of the adapter factory.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  public StateMachineAdapterFactory() {
    if (modelPackage == null) {
      modelPackage = StateMachinePackage.eINSTANCE;
    }
  }

  /**
   * Returns whether this factory is applicable for the type of the object.
   * <!-- begin-user-doc -->
   * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
   * <!-- end-user-doc -->
   * @return whether this factory is applicable for the type of the object.
   * @generated
   */
  @Override
  public boolean isFactoryForType(Object object) {
    if (object == modelPackage) {
      return true;
    }
    if (object instanceof EObject) {
      return ((EObject)object).eClass().getEPackage() == modelPackage;
    }
    return false;
  }

  /**
   * The switch that delegates to the <code>createXXX</code> methods.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @generated
   */
  protected StateMachineSwitch<Adapter> modelSwitch =
    new StateMachineSwitch<Adapter>() {
      @Override
      public Adapter caseStateMachine(StateMachine object) {
        return createStateMachineAdapter();
      }
      @Override
      public Adapter caseState(State object) {
        return createStateAdapter();
      }
      @Override
      public Adapter caseEvent(Event object) {
        return createEventAdapter();
      }
      @Override
      public Adapter caseTrans(Trans object) {
        return createTransAdapter();
      }
      @Override
      public Adapter caseDocumentField(DocumentField object) {
        return createDocumentFieldAdapter();
      }
      @Override
      public Adapter caseFieldState(FieldState object) {
        return createFieldStateAdapter();
      }
      @Override
      public Adapter caseRole(Role object) {
        return createRoleAdapter();
      }
      @Override
      public Adapter caseTransSet(TransSet object) {
        return createTransSetAdapter();
      }
      @Override
      public Adapter defaultCase(EObject object) {
        return createEObjectAdapter();
      }
    };

  /**
   * Creates an adapter for the <code>target</code>.
   * <!-- begin-user-doc -->
   * <!-- end-user-doc -->
   * @param target the object to adapt.
   * @return the adapter for the <code>target</code>.
   * @generated
   */
  @Override
  public Adapter createAdapter(Notifier target) {
    return modelSwitch.doSwitch((EObject)target);
  }


  /**
   * Creates a new adapter for an object of class '{@link stateMachine.StateMachine <em>State Machine</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see stateMachine.StateMachine
   * @generated
   */
  public Adapter createStateMachineAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link stateMachine.State <em>State</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see stateMachine.State
   * @generated
   */
  public Adapter createStateAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link stateMachine.Event <em>Event</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see stateMachine.Event
   * @generated
   */
  public Adapter createEventAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link stateMachine.Trans <em>Trans</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see stateMachine.Trans
   * @generated
   */
  public Adapter createTransAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link stateMachine.DocumentField <em>Document Field</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see stateMachine.DocumentField
   * @generated
   */
  public Adapter createDocumentFieldAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link stateMachine.FieldState <em>Field State</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see stateMachine.FieldState
   * @generated
   */
  public Adapter createFieldStateAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link stateMachine.Role <em>Role</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see stateMachine.Role
   * @generated
   */
  public Adapter createRoleAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for an object of class '{@link stateMachine.TransSet <em>Trans Set</em>}'.
   * <!-- begin-user-doc -->
   * This default implementation returns null so that we can easily ignore cases;
   * it's useful to ignore a case when inheritance will catch all the cases anyway.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @see stateMachine.TransSet
   * @generated
   */
  public Adapter createTransSetAdapter() {
    return null;
  }

  /**
   * Creates a new adapter for the default case.
   * <!-- begin-user-doc -->
   * This default implementation returns null.
   * <!-- end-user-doc -->
   * @return the new adapter.
   * @generated
   */
  public Adapter createEObjectAdapter() {
    return null;
  }

} //StateMachineAdapterFactory
