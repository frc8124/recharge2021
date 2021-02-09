/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.ColorMatch;

import edu.wpi.first.wpilibj.util.Color;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean constants. This class should not be used for any other
 * purpose. All constants should be declared globally (i.e. public static). Do
 * not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the constants are needed, to reduce verbosity.
 */
public final class Constants {

    public static final class DriveConstants {
        public static final int kLeftMotor1Port = 0;
        public static final int kLeftMotor2Port = 1;
        public static final int kRightMotor1Port = 2;
        public static final int kRightMotor2Port = 3;
    
        //public static final int[] kLeftEncoderPorts = new int[]{0, 1};
        //public static final int[] kRightEncoderPorts = new int[]{2, 3};
        //public static final boolean kLeftEncoderReversed = false;
        //public static final boolean kRightEncoderReversed = true;
    
        //public static final int kEncoderCPR = 1024;
        //public static final double kWheelDiameterInches = 6;
        //public static final double kEncoderDistancePerPulse =
            // Assumes the encoders are directly mounted on the wheel shafts
        //    (kWheelDiameterInches * Math.PI) / (double) kEncoderCPR;
      }

    public static final class IntakeConstants {
        public static final int ArmSolenoidChannel = 3;
        public static final boolean ArmSolenoidUp = false;
        public static final boolean ArmSolenoidDown = true;
      }
    
    public static final class ColorWheelConstants {
      public static final int ColorWheelSolenoidChannel = 2;
      public static final int ColorWheelMotorChannel = 9;
      public static final Color kBlueTarget = ColorMatch.makeColor(0.121,0.428,0.447);
      public static final Color kGreenTarget = ColorMatch.makeColor(0.164,0.584,0.25);
      public static final Color kRedTarget = ColorMatch.makeColor(0.524,0.347,0.129);
      public static final Color kYellowTarget = ColorMatch.makeColor(0.314,0.564,0.121);
      public static final boolean ArmUp = true;
      public static final boolean ArmDown = false;
      public static final double MotorSpeed = 0.4;
      public static final int ArmMicroSwitchPort = 0; 
    }
}
