
package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Robot extends TimedRobot {
  /* Definitions for the hardware. Change this if you change what stuff you have plugged in */
  CANSparkMax driveLeftA = new CANSparkMax(1, MotorType.kBrushless);                  // left front drive motor intilize
  CANSparkMax driveLeftB = new CANSparkMax(2, MotorType.kBrushless);                  // left back drive motor intilize
  CANSparkMax driveRightA = new CANSparkMax(3, MotorType.kBrushless);                 // right front drive motor intilize
  CANSparkMax driveRightB = new CANSparkMax(4, MotorType.kBrushless);                 // right back drive motor intilize
  CANSparkMax shooterWheelA = new CANSparkMax(5, MotorType.kBrushless);               // front shooter wheel intilize
  CANSparkMax shooterWheelB = new CANSparkMax(6, MotorType.kBrushless);               // back shooter wheel intilize
  VictorSPX intake = new VictorSPX(7);                                
  VictorSPX belt = new VictorSPX(8);
  VictorSPX feedWheel = new VictorSPX(9);

  Joystick driverController = new Joystick(1);
  GenericHID functionController = new GenericHID(0);
  
  int shootPause = 0;
  double autoStart = 0;
  boolean goForAuto = false;

  @Override
  public void robotInit() {
    //Configure motors to turn correct direction. You may have to invert some of your motors
    driveLeftA.setInverted(true);
    driveLeftA.burnFlash();
    driveLeftB.setInverted(true);
    driveLeftB.burnFlash();
    driveRightA.setInverted(false);
    driveRightA.burnFlash();
    driveRightB.setInverted(false);
    driveRightB.burnFlash();
    shooterWheelA.setInverted(false);
    shooterWheelA.burnFlash();
    shooterWheelB.setInverted(false);
    shooterWheelB.burnFlash();

    //add a thing on the dashboard to turn off auto if needed
    SmartDashboard.putBoolean("Go For Auto", false);
    goForAuto = SmartDashboard.getBoolean("Go For Auto", false);
  }
  
  @Override
  public void autonomousInit() {
    //get a time for auton start to do events based on time later
    autoStart = Timer.getFPGATimestamp();
    //check dashboard icon to ensure good to do auto
    goForAuto = SmartDashboard.getBoolean("Go For Auto", false);
  }

  /* This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    driveLeftA.set(0);
    driveLeftB.set(0);
    driveRightA.set(0);
    driveRightB.set(0);
  }

  /* This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {}

  /* This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    /* driving controls */
    double forward = -driverController.getRawAxis(1);
    double backward = driverController.getRawAxis(1);
    double turn = -driverController.getRawAxis(0);

    if(driverController.getRawAxis(1)>0.7){
      driveLeftA.set(forward);
      driveLeftB.set(forward);
      driveRightA.set(forward);
      driveRightB.set(forward);
    }
    else{
      driveLeftA.set(0);
      driveLeftB.set(0);
      driveRightA.set(0);
      driveRightB.set(0);
    }

    if(driverController.getRawAxis(1)<0.3){
      driveRightA.set(backward);
      driveRightB.set(backward);
      driveRightA.set(backward);
      driveRightB.set(backward);
    }
    else{
      driveRightA.set(0);
      driveRightB.set(0);
      driveRightA.set(0);
      driveRightB.set(0);
    }

    /*if(driverController.getRawAxis(0)>0.8){
      driveLeftA.set(turn);
      driveLeftB.set(turn);
      driveRightA.set(1-turn);
      driveRightB.set(1-turn);
    }
    else{
      driveLeftA.set(0);
      driveLeftB.set(0);
      driveRightA.set(0);
      driveRightB.set(0);
    }

    if(driverController.getRawAxis(0)<0.2){
      driveLeftA.set(1-turn);
      driveLeftB.set(1-turn);
      driveRightA.set(turn);
      driveRightB.set(turn);
    }
    else{
      driveLeftA.set(0);
      driveLeftB.set(0);
      driveRightA.set(0);
      driveRightB.set(0);
    }
    */
    /* Intake controls */
    if(functionController.getRawAxis(2)>0.5){
      intake.set(VictorSPXControlMode.PercentOutput, 0.7);
    }
    else{
      intake.set(VictorSPXControlMode.PercentOutput, 0);
    }

    if(functionController.getRawAxis(3)>0.5){
      shooterWheelA.set(0.4);
      shooterWheelB.set(0.5);
      shootPause++;
      if(shootPause >25){
      belt.set(VictorSPXControlMode.PercentOutput, 0.5);
      feedWheel.set(VictorSPXControlMode.PercentOutput,0.5);
      }
      
    }
    else{
      shooterWheelA.set(0);
      shooterWheelB.set(0);
      shootPause = 0;
      belt.set(VictorSPXControlMode.PercentOutput, 0);
      feedWheel.set(VictorSPXControlMode.PercentOutput,0);
      intake.set(VictorSPXControlMode.PercentOutput, 0);
    }
  }

  @Override
  public void disabledInit() {
    driveLeftA.set(0);
    driveLeftB.set(0);
    driveRightA.set(0);
    driveRightB.set(0);
    intake.set(ControlMode.PercentOutput, 0);
    belt.set(ControlMode.PercentOutput, 0);
    feedWheel.set(ControlMode.PercentOutput, 0);
  }
    
}