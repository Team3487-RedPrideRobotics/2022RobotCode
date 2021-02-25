// RobotBuilder Version: 3.1
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.

package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.DriveOption;
import frc.robot.RobotContainer;
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import frc.robot.subsystems.Drive;
import edu.wpi.first.wpilibj.GenericHID.Hand;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

/**
 *
 */
public class TeleopCommand extends CommandBase {

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS
        private final Drive m_drive;
    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_DECLARATIONS

        private NetworkTableEntry enablePID;
        private NetworkTableEntry pidEnabled;

        private NetworkTableEntry controllerThreshold;

        private String controller;

    // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS


    public TeleopCommand(Drive subsystem) {

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=CONSTRUCTORS
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=VARIABLE_SETTING
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES

        enablePID = Shuffleboard.getTab("Teleop").addPersistent("Enable Autocorrect", false).withWidget(BuiltInWidgets.kToggleButton).getEntry();
        pidEnabled = Shuffleboard.getTab("Teleop").add("Autocorrect Enabled", false).getEntry();
        controllerThreshold = Shuffleboard.getTab("Teleop").addPersistent("Enable PID Threshold", 0.01).withWidget(BuiltInWidgets.kNumberSlider).getEntry();

        m_drive = subsystem;
        addRequirements(m_drive);


    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
       tankDriveExec();
    }

    public void tankDriveExec() {
        double left = 0;
        double right = 0;

        if(RobotContainer.getInstance().getDriveOption() == "joysticks") {
            left = RobotContainer.getInstance().getLeftStick().getY();
            right = RobotContainer.getInstance().getRightStick().getY();
        } else {
            left = RobotContainer.getInstance().getXboxController().getY(Hand.kLeft);
            right = RobotContainer.getInstance().getXboxController().getY(Hand.kRight);            
        }

        if(enablePID.getBoolean(false) && (Math.abs(left) > controllerThreshold.getDouble(0.01)) && (Math.abs(right) > controllerThreshold.getDouble(0.01))) {
            m_drive.pidTank(-left, -right);
            pidEnabled.setBoolean(true);
        } else {
            m_drive.simpleTank(-left, -right);
            pidEnabled.setBoolean(false);
        }
    }



    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public boolean runsWhenDisabled() {
        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DISABLED
        return false;

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=DISABLED
    }
}
