package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.cameraserver.CameraServer;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;
  CANSparkMax driveLeftA = new CANSparkMax(1, MotorType.kBrushless);                  // left front drive motor intilize
  CANSparkMax driveLeftB = new CANSparkMax(2, MotorType.kBrushless);                  // left back drive motor intilize
  CANSparkMax driveRightA = new CANSparkMax(3, MotorType.kBrushless);                 // right front drive motor intilize
  CANSparkMax driveRightB = new CANSparkMax(4, MotorType.kBrushless);                 // right back drive motor intilize
  CANSparkMax armJoint1 = new CANSparkMax(5, MotorType.kBrushless);                   // arm connected to the bot intilize
  CANSparkMax armJoint2 = new CANSparkMax(6, MotorType.kBrushless);                   // arm connected the arm and claw intilize
  CANSparkMax claw = new CANSparkMax(7, MotorType.kBrushless);                        // claw on arm intilize

  Joystick driverController = new Joystick(1);
  GenericHID functionController = new GenericHID(0);
  
  private final SendableChooser<Double> autChooser = new SendableChooser<Double>();


  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    double forward = driverController.getRawAxis(1);
    double turn = -driverController.getRawAxis(0);
    
    driveLeftA.set(forward+(turn*0.5));
    driveLeftB.set(forward+(turn*0.5)); 
    driveRightA.set(forward-(turn*0.5));
    driveRightB.set(forward-(turn*0.5));

    if(functionController.getRawAxis(1)>0.3){
      armJoint1.set(0.2);
    }
    else if(functionController.getRawAxis(1)<0.3){
      armJoint1.set(-0.2);
    }

    else if(functionController.getRawAxis(2)>0.3){
      armJoint1.set(0.2);
    }
    else if(functionController.getRawAxis(2)<0.3){
      armJoint1.set(-0.2);
    }

    else if(functionController.getRawAxis(3)>0.5){
      claw.set(0.2);
    }
    else if(functionController.getRawAxis(3)<0.5){
      claw.set(-0.2);
    }

    else{
      armJoint1.set(0);
      armJoint2.set(0);
      claw.set(0);
    }

  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {
    }
  

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}
