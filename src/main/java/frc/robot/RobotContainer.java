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

import java.io.IOException;
import java.nio.file.Path;

import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryUtil;
// BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.subsystems.*;
import frc.robot.subsystems.camera.Camera;
import frc.robot.Constants.AutoConstants;
import frc.robot.Constants.DriveConstants;
import frc.robot.commands.*;

// END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=IMPORTS

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {

    public static final String controlJoystick = "Joysticks";
    public static final String controlXbox = "Xbox";
    private String currentScheme = "";

    private GenericHID leftStick;
    private GenericHID rightStick;
    
    private JoystickButton intakeButton;
    private JoystickButton outtakeButton;

    private static RobotContainer m_robotContainer = new RobotContainer();

    private SendableChooser<String> controlScheme;

    private SendableChooser<String>  PathChooser;

    private NetworkTableEntry cameraEntry;

    //Subsystems
    private Drive m_drive = new Drive();
    private Intake m_intake = new Intake();
    private Camera m_camera = new Camera();

    //Commands
    private Command teleopCommand = new TeleopCommand(m_drive);
    private Command intakeForward = new IntakeCommand(m_intake, true);
    private Command intakeOut = new IntakeCommand(m_intake, false);

    private RobotContainer() {
        controlScheme = new SendableChooser<String>();
        controlScheme.setDefaultOption(controlJoystick, controlJoystick);
        controlScheme.addOption(controlXbox, controlXbox);
        Shuffleboard.getTab("Teleop").add("Control Scheme", controlScheme);

        cameraEntry = Shuffleboard.getTab("Vision").add("Vision Debug",false).withWidget(BuiltInWidgets.kToggleButton).getEntry();
        cameraEntry.addListener(event -> {
            if(cameraEntry.getBoolean(false)) {
                m_camera.startCapture();
            } else {
                m_camera.stopCapture();
            }
        }, EntryListenerFlags.kNew|EntryListenerFlags.kImmediate| EntryListenerFlags.kUpdate);

        PathChooser = new SendableChooser<String>();

       /**
        * Link to each of our paths here. Be sure to
        * put them inside the "deploy/paths/" folder.
        * ---------------------------------------------
        * FORMAT:
        * PathChooser.addOption("[name of path]", "[location of path]";
        */

        PathChooser.setDefaultOption("None", "none");
        PathChooser.addOption("Barrel Run", "Barrel racing.wpilib.json");
        PathChooser.addOption("Bounce Path", "Bounce Path.wpilib.json");
        PathChooser.addOption("Slalom Path", "salaom.wpilib.json");
        PathChooser.addOption("Blue Path A", "Blue Path ( A).wpilib.json");
        PathChooser.addOption("Blue Path B", "Blue Path (B).wpilib.json");
        PathChooser.addOption("Red Path A", "red path (A).wpilib.json");
        PathChooser.addOption("Red Path B", "Red Path (B).wpilib.json");
        
        Shuffleboard.getTab("Auto").add("Auto Path", PathChooser);
    }

    public static RobotContainer getInstance() {
        return m_robotContainer;
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be
     * created by instantiating a {@link GenericHID} or one of its subclasses
     * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
     * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        intakeButton.whileHeld(intakeForward);
        outtakeButton.whileHeld(intakeOut);
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // The selected command will be run in autonomous
        String PathString = PathChooser.getSelected();
        if(PathString == "none"){
            return new WaitCommand(1);
        }
        Trajectory AutoTrajectory = new Trajectory();
        PathString = "Paths".concat(PathString);
        try {
            Path PathJSON = Filesystem.getDeployDirectory().toPath().resolve(PathString);
            AutoTrajectory = TrajectoryUtil.fromPathweaverJson(PathJSON);
        } catch (IOException ex) {
            DriverStation.reportError("Unable to open trajectory: " + PathString, ex.getStackTrace());
        }

        // this is a really long and confusing constructor, so here's basically what it wants
        RamseteCommand ramseteCommand = new RamseteCommand(
            AutoTrajectory, // the trajectory to follow
            m_drive::getPose, // a function that returns the robot's pose2d
            new RamseteController(AutoConstants.kRamseteB, AutoConstants.kRamseteZeta), // a ramsete controller (these B and Zeta values are basically universal)
            new SimpleMotorFeedforward(DriveConstants.kSC, // a feedforward that controls both sides of the drive
                                       DriveConstants.kVC, // (make sure you're using the combined characterization values, not individual ones)
                                       DriveConstants.kAC),
            m_drive.getKinematics(), // the robot's kinematics (not a function that returns the kinematics, just the kinematics itself)
            m_drive::getRates, // a function that returns the encoder rates in the form of DifferentialDriveWheelSpeeds
            new PIDController(DriveConstants.kPL, 0, 0), // a PID controller for the left side
            new PIDController(DriveConstants.kPR, 0, 0), // a PID controller for the right side
            m_drive::setRawVoltage, // a function that controlls the voltage of the drive (look at subsystems/drive.java so see how this should be layed out)
            m_drive // the drive subsystem itself
        );
        m_drive.resetOdometry(AutoTrajectory.getInitialPose());

        // Run path following command, then stop at the end.
        return ramseteCommand.andThen(() -> m_drive.setRawVoltage(0, 0));
    }

    public void checkControls() {
        String selected = controlScheme.getSelected();
        if(selected.equals(currentScheme)) {
            return;
        }

        if(selected.equals(controlJoystick)) {
            leftStick = new Joystick(0);
            rightStick = new Joystick(1);
            intakeButton = new JoystickButton(rightStick, 1);
            outtakeButton = new JoystickButton(leftStick, 1);
        } else {
            leftStick = new XboxController(4);
            rightStick = null;
            intakeButton = new JoystickButton(leftStick, 6);
            outtakeButton = new JoystickButton(leftStick, 5);
        }
        
        configureButtonBindings();

        currentScheme = selected;
    }

	public Command getTeleopCommand() {
		return teleopCommand;
	}

    public double[] getSticks() {
        if(currentScheme.equals(controlJoystick)) {
            return new double[] {-leftStick.getY(), -rightStick.getY()};
        } else {
            return new double[] {-leftStick.getY(Hand.kLeft), -leftStick.getY(Hand.kRight)};
        }
    }

}
