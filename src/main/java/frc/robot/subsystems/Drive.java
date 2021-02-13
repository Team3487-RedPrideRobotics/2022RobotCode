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


import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import edu.wpi.first.wpilibj.shuffleboard.ComplexWidget;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

/**
 *
 */
public class Drive extends SubsystemBase {
    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTANTS

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    private Spark sLeft;
    private Spark sRight;

    private Encoder eLeft;
    private Encoder eRight;

    SimpleMotorFeedforward fLeft = new SimpleMotorFeedforward(2.28,2.64,0.000793);
    SimpleMotorFeedforward fRight = new SimpleMotorFeedforward(2.14,2.87,0.00181);

    PIDController pidLeft  = new PIDController(3.09*(10^-29), 0,0);
    PIDController pidRight  = new PIDController(4.65*(10^-14), 0,0);

    private DifferentialDrive robotDriveGroup;

    private ComplexWidget driveStatus;

    private NetworkTableEntry governor;
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DECLARATIONS
    
    /**
    *
    */
    public Drive() {

        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        sLeft = new Spark(Constants.DriveConstants.kLeft);
        addChild("Left",sLeft);
        sLeft.setInverted(false);
        eLeft = new Encoder(Constants.DriveConstants.kEncoderLeft[0], Constants.DriveConstants.kEncoderLeft[1], Constants.DriveConstants.kEncoderLeft[2]);
        eLeft.setDistancePerPulse(0.479/2048.0);

        sRight = new Spark(Constants.DriveConstants.kRight);
        addChild("Right",sRight);
        sRight.setInverted(true);
        eRight = new Encoder(Constants.DriveConstants.kEncoderRight[0], Constants.DriveConstants.kEncoderRight[1], Constants.DriveConstants.kEncoderRight[2]);
        eRight.setDistancePerPulse(0.479/2048.0);

        robotDriveGroup = new DifferentialDrive(sLeft, sRight);

        driveStatus = Shuffleboard.getTab("Drive")
            .add(robotDriveGroup);

        governor = Shuffleboard.getTab("Drive").addPersistent("Speed Governor", 5).getEntry();
        
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
    }

    public void simpleTank(double left, double right) {
        robotDriveGroup.tankDrive(-left, -right);
    }

    public void pidTank(double left, double right) {
        //sLeft.setVoltage(fLeft.calculate(left)+pidLeft.calculate(eLeft.getRate(), left));
        sLeft.setVoltage(fLeft.calculate(left));//+pidLeft.calculate(eLeft.getRate(), left));
        //sRight.setVoltage(fRight.calculate(right)+pidRight.calculate(eRight.getRate(),right));
        sRight.setVoltage(fRight.calculate(right));//+pidRight.calculate(eRight.getRate(),right));
        
        sLeft.feed();
        sRight.feed();
        robotDriveGroup.feed();
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

