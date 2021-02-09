/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/**
 * The Intake arm is pneumatic pistons operated by a single solenoid
 * value. The arm is in the UP position when the solenoid is off
 * and in the down position when the solenoid is energized.
 */
public class IntakeArmSubsystem extends SubsystemBase {
  /**
   * Creates a new IntakeArmSubsystem.
   */

  private final Solenoid m_armSolenoid = new Solenoid(Constants.IntakeConstants.ArmSolenoidChannel);

  // Upward motion takes about 0.875 seconds
  private final int[] m_upArrestPoints = { 26, 32 };
  
  // Downward takes 0.625 seconds
  private final int[] m_downArrestPoints = { 15, 24 };
  
  private int[] m_arrestTogglePoints;
  private int m_arrestIndex = 0;
  private int m_Index = 0;
  private boolean m_targetDir;

  public IntakeArmSubsystem() {
    // Ensure arm is in up state at start
    setSolenoid( Constants.IntakeConstants.ArmSolenoidUp );
  
    System.out.println("ARM SUBSYSTEM UP");
    
  
  }

  private void setSolenoid( boolean value ) {
     m_armSolenoid.set( value );

    SmartDashboard.putString( "Arm State",
      value == Constants.IntakeConstants.ArmSolenoidUp ? "UP" : "DOWN" );
  }

  /**
   * Raise arm
   */
  public void raise() {
    // begin raise arm sequence
    // total raise time is 0.875 seconds
    // arrest at .5 seconds  = 25 intervals

    System.out.println("RAISE ARM");
    m_targetDir = Constants.IntakeConstants.ArmSolenoidUp;
    m_arrestTogglePoints = m_upArrestPoints;
    startAction();
  } 

  public void lower() {
    System.out.println("LOWER ARM");
    m_targetDir = Constants.IntakeConstants.ArmSolenoidDown;
    m_arrestTogglePoints = m_downArrestPoints;
    startAction();
  }

  private void startAction() {
    setSolenoid( m_targetDir );
    m_arrestIndex = 0;
    m_Index = 1;

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    
    if (m_Index > 0) {
      if (m_arrestIndex < m_arrestTogglePoints.length) {
        // reached current arrest target?
        if (m_Index == m_arrestTogglePoints[m_arrestIndex]) {
          // set solenoid to opposite when on even index, 
          // normal on odd
          if ((m_arrestIndex % 2) == 0) {
            setSolenoid( ! m_targetDir);
          } else {
            setSolenoid( m_targetDir );
          }

          // advance to next arrest point,
          // check if last arrest has been executed
          m_arrestIndex++;
        }
      }

      // make sure with finish the process once all the arrest
      // points have been covered
      if (m_arrestIndex >= m_arrestTogglePoints.length) {
        m_Index = 0;
        setSolenoid( m_targetDir );
      } else {
        m_Index++;
      }

    }
  }
}