/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.ColorMatch;
import com.revrobotics.ColorMatchResult;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ColorWheelConstants;



public class ColorWheelSubsystem extends SubsystemBase {
 
  private final Solenoid m_color = new Solenoid(ColorWheelConstants.ColorWheelSolenoidChannel);
  private final ColorMatch m_colorMatcher = new ColorMatch();
  private final I2C.Port i2cPort = I2C.Port.kOnboard;
  private final ColorSensorV3 m_colorsensor = new ColorSensorV3(i2cPort);
  private final WPI_VictorSPX m_colorMotor = new WPI_VictorSPX(ColorWheelConstants.ColorWheelMotorChannel);

  private final PowerDistributionPanel m_PDP = new PowerDistributionPanel();

  private final DigitalInput m_WheelTouchSwitch = new DigitalInput(ColorWheelConstants.ArmMicroSwitchPort);

  /** 
   * Creates a new ColorWheelSubsystem.
   */


  public ColorWheelSubsystem() {
    setSolenoid(ColorWheelConstants.ArmDown);

    // Load target colors into color matcher 
    m_colorMatcher.addColorMatch(ColorWheelConstants.kBlueTarget);
    m_colorMatcher.addColorMatch(ColorWheelConstants.kGreenTarget);
    m_colorMatcher.addColorMatch(ColorWheelConstants.kRedTarget);
    m_colorMatcher.addColorMatch(ColorWheelConstants.kYellowTarget);
  
    }

  /**
   * private function to set arm solenoid
  */
  private void setSolenoid(boolean value) {
    m_color.set(value);
  }

  /**
   * raise arm
   */
  public void raise() {
    setSolenoid(ColorWheelConstants.ArmUp);
  }

  /**
   * lower arm
  */
  public void lower() {
    setSolenoid(ColorWheelConstants.ArmDown);
  }

  private void setMotor(boolean value){
    m_colorMotor.set( value ? ColorWheelConstants.MotorSpeed : 0 );
  }

  public void motorStart() {
    setMotor(true);    
  }

  public void motorStop(){
    setMotor(false);  
  }

  /**
   * Determine which color wheel is positioned at
   */
  private String checkColor() {
    Color detected = m_colorsensor.getColor();

    ColorMatchResult match = m_colorMatcher.matchClosestColor( detected );
    
    SmartDashboard.putNumber("Red", detected.red);
    SmartDashboard.putNumber("Green", detected.green);
    SmartDashboard.putNumber("Blue", detected.blue);

    SmartDashboard.putNumber("Proximity", m_colorsensor.getProximity());

    String matched = "None";

    if (match.confidence > 0.91) {
      if (match.color == ColorWheelConstants.kBlueTarget) {
        matched = "Blue";
      } else if (match.color == ColorWheelConstants.kRedTarget) {
        matched = "Red";
      } else if (match.color == ColorWheelConstants.kGreenTarget) {
        matched = "Green";
      } else if (match.color == ColorWheelConstants.kYellowTarget) {
        matched = "Yellow";
      }
    }

    return matched;
  }

  public boolean touchingWheel() {
    return !m_WheelTouchSwitch.get();
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  

    SmartDashboard.putNumber("Color Wheel CURRENT AMPS", m_PDP.getCurrent(4) );
    SmartDashboard.putNumber("Total CURRENT AMPS", m_PDP.getTotalCurrent() );

    SmartDashboard.putString("Color", checkColor() );

    SmartDashboard.putBoolean("Touching", touchingWheel() );
  }
}
