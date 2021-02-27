// RobotBuilder Version: 3.1
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package frc.robot;

import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.util.Units;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public class Constants {


    public static final class DriveConstants {
        public static final int kLeft = 0;
        public static final int kRight = 1;

        public static final SerialPort.Port kAHRS = SerialPort.Port.kUSB;

        //By index: Enc A, Enc B, Absolute
        public static final int[] kEncoderLeft = {0,1,2}; 
        public static final int[] kEncoderRight = {3,4,5}; 

        //The hexbore encoder has 2048 cycles per revolution, since it is quadrature, it has 8192 pulses.
        //https://www.revrobotics.com/content/docs/REV-11-1271-DS.pdf
        public static final double kDistancePerPulse = Units.inchesToMeters(6)*Math.PI/(2048*4);

        public static final double kSR = 1.68;
        public static final double kVR = 2.85;
        public static final double kAR = 0.0046;

        public static final double kSL = 1.77;
        public static final double kVL = 2.64;
        public static final double kAL = 0.00317;

        public static final double kTrackWidth = 1.1361;
		public static final double Speed = Units.feetToMeters(10.55);

		public static final boolean bLeftInverted = false;
		public static final boolean bRightInverted = false;
		public static final double kAcceleration = 0;
    }

    public static final class IntakeConstants {
        public static final int kIntake = 4;
    }
}

