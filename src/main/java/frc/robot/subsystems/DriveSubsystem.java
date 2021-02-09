/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;

public class DriveSubsystem extends SubsystemBase {
  
  private final WPI_VictorSPX m_leftMotor = new WPI_VictorSPX( DriveConstants.kLeftMotor1Port );
  private final WPI_VictorSPX m_leftMotorSlave = new WPI_VictorSPX( DriveConstants.kLeftMotor2Port );  
  private final WPI_VictorSPX m_rightMotor = new WPI_VictorSPX( DriveConstants.kRightMotor1Port );
  private final WPI_VictorSPX m_rightMotorSlave = new WPI_VictorSPX( DriveConstants.kRightMotor2Port );

  private final DifferentialDrive m_robotDrive = new DifferentialDrive(m_rightMotor, m_leftMotor);

  private double m_currentForward = 0;
  private double m_currentRotation = 0;

  /**
   * Creates a new RobotDrive.
   */
  public DriveSubsystem() {

    m_leftMotorSlave.follow(m_leftMotor);
    m_rightMotorSlave.follow(m_rightMotor);

  }

  // Apply arcade drive style forward/rotation factors

  public void arcadeDrive(double forward, double rotation) {

    m_currentForward = forward;
    m_currentRotation = rotation;
  
  }
  
  /**
   * move forward at given speed.
   */
  public void forward(double speed) {
    m_currentForward = -speed;
    m_currentRotation = 0;
    
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Forward", m_currentForward);
    m_robotDrive.arcadeDrive(m_currentForward, m_currentRotation);

  }

  
}
