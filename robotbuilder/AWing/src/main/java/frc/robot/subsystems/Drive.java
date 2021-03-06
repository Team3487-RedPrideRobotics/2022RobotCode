// RobotBuilder Version: 3.1
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package frc.robot.subsystems;


import frc.robot.commands.*;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS


/**
 *
 */
public class Drive extends SubsystemBase {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
private Spark left;
private Spark right;
private RobotDrive robotDriveGroup;
private Encoder leftEncoder;
private Encoder rightEncoder;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    
    /**
    *
    */
    public Drive() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
left = new Spark(0);
 addChild("Left",left);
 left.setInverted(false);

right = new Spark(1);
 addChild("Right",right);
 right.setInverted(false);

robotDriveGroup = new RobotDrive(left, right);
 
 robotDriveGroup.setSafetyEnabled(true);
robotDriveGroup.setExpiration(0.1);
robotDriveGroup.setSensitivity(0.5);
robotDriveGroup.setMaxOutput(1.0);

robotDriveGroup.setInvertedMotor(RobotDrive.MotorType.kRearRight, true);

leftEncoder = new Encoder(0, 1, false, EncodingType.k4X);
 addChild("Left Encoder",leftEncoder);
 leftEncoder.setDistancePerPulse(1.0);
leftEncoder.setPIDSourceType(PIDSourceType.kRate);

rightEncoder = new Encoder(2, 3, false, EncodingType.k4X);
 addChild("Right Encoder",rightEncoder);
 rightEncoder.setDistancePerPulse(1.0);
rightEncoder.setPIDSourceType(PIDSourceType.kRate);


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run

    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run when in simulation

    }

    // Put methods for controlling this subsystem
    // here. Call these from Commands.

}

