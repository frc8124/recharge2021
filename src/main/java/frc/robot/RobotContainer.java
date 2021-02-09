/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import frc.robot.Constants.IntakeConstants;
import frc.robot.commands.DefaultDrive;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.ColorWheelSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.IntakeArmSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.StartEndCommand;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);

  // Controlling joystick for drive
  private final Joystick m_Joystick = new Joystick(0);
  private final DriveSubsystem m_driveSubsystem = new DriveSubsystem();
  private final DefaultDrive m_defaultDrive = new DefaultDrive(m_driveSubsystem,
    () -> m_Joystick.getY(), () -> m_Joystick.getX() );

  // Intake System
  private final IntakeArmSubsystem m_intakeArmSubsystem = new IntakeArmSubsystem();

  // ColorWheel Arm
  private final ColorWheelSubsystem m_ColorWheelSubsystem = new ColorWheelSubsystem();

  /**
   * The container for the robot.  Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    m_driveSubsystem.setDefaultCommand( m_defaultDrive );
  }

  /**
   * Use this method to define your button->command mappings.  Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
   * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}. QLM was here
   */
  private void configureButtonBindings() {
  
    // Assign joystick button to intake arm raise and lower functions

    new JoystickButton(m_Joystick, 5)
      .whenPressed(new InstantCommand(m_intakeArmSubsystem::raise, m_intakeArmSubsystem));

    // Turn off the shooter when the 'B' button is pressed
    new JoystickButton(m_Joystick, 3)
      .whenPressed(new InstantCommand(m_intakeArmSubsystem::lower, m_intakeArmSubsystem));

    // Control ColorWheel arm
    new JoystickButton(m_Joystick, 6)
      .whenPressed(new InstantCommand(m_ColorWheelSubsystem::raise, m_ColorWheelSubsystem));

    new JoystickButton(m_Joystick, 4)
      .whenPressed(new InstantCommand(m_ColorWheelSubsystem::lower, m_ColorWheelSubsystem));

    new JoystickButton(m_Joystick, 2)
      .whenPressed( new InstantCommand(m_ColorWheelSubsystem::motorStart, m_ColorWheelSubsystem))
      .whenReleased(new InstantCommand(m_ColorWheelSubsystem::motorStop, m_ColorWheelSubsystem));
    
    new JoystickButton(m_Joystick, 10)
      .toggleWhenPressed(

        new SequentialCommandGroup(

          new InstantCommand(m_ColorWheelSubsystem::raise, m_ColorWheelSubsystem),

          new StartEndCommand( 
           () -> { System.out.println("STARTING"); m_driveSubsystem.forward(.34); },
           () -> { System.out.println("ENDING"); m_driveSubsystem.forward(0); },
            m_driveSubsystem).withInterrupt( m_ColorWheelSubsystem::touchingWheel ),

          new StartEndCommand(
            m_ColorWheelSubsystem::motorStart,
            m_ColorWheelSubsystem::motorStop,
            m_ColorWheelSubsystem).withTimeout(5),

            new StartEndCommand( 
           () -> { System.out.println("STARTING"); m_driveSubsystem.forward(-.5); },
           () -> { System.out.println("ENDING"); m_driveSubsystem.forward(0); },
            m_driveSubsystem).withTimeout(1),
        
            new InstantCommand(m_ColorWheelSubsystem::lower, m_ColorWheelSubsystem)
          )
          
      );
    }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   * as was here
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
  }
}
