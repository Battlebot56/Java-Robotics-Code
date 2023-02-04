
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
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.cameraserver.CameraServer;


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
  
  private final SendableChooser<Double> autChooser = new SendableChooser<Double>();

  int delay = 0;
  int auto = 0;
  double autoStart = 0;
  double goForAuto = 0;

  

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

    autChooser.addOption("Auto1", (double) 1);
    autChooser.addOption("Auto2", (double) 2);

    SmartDashboard.putData(autChooser);

    CameraServer.startAutomaticCapture(0);

    //add a thing on the dashboard to turn off auto if needed
    /*SmartDashboard.putNumber("Auto1", 1);
    goForAuto = SmartDashboard.getNumber("Auto1", 1);
    SmartDashboard.putNumber("Auto2", 2);
    goForAuto = SmartDashboard.getNumber("Auto2", 2);*/
  }
  

  @Override
  public void autonomousInit() {
    //get a time for auton start to do events based on time later
    autoStart = Timer.getFPGATimestamp();
  
  }

  /* This function is called periodically during autonomous. 
  start with intake up robot facing a ball, drive forward, stop and run intake so intake falls down before ball, move forward and pickup ball, 
  stop intake and turn 180, drive twards the goal, stop motors and run belt feeder and shooters */
  @Override
  public void autonomousPeriodic() {
    double autoTimeElapsed = Timer.getFPGATimestamp() - autoStart;
      
      Double autoCode = 1.0;                                            // BEN CHANGE THIS TO 1 OR 2
      /*if(autoTimeElapsed<0.5){
        driveLeftA.set(-0.6);
        driveLeftB.set(-0.6);
        driveRightA.set(-0.6);
        driveRightB.set(-0.6);
        intake.set(VictorSPXControlMode.PercentOutput, -0.6);
      }
      else if( autoTimeElapsed<1.59){
        driveLeftA.set(0);
        driveLeftB.set(0);
        driveRightA.set(0);
        driveRightB.set(0);
      }
      else if(autoTimeElapsed<2.32){
        driveLeftA.set(-0.2);
        driveLeftB.set(-0.2);
        driveRightA.set(0.2);
        driveRightB.set(0.2);
      }
      else if(autoTimeElapsed<2.41){
        driveLeftA.set(0);
        driveLeftB.set(0);
        driveRightA.set(0);
        driveRightB.set(0);
      }
      else if(autoTimeElapsed<3.18){
        driveLeftA.set(-0.6);
        driveLeftB.set(-0.6);
        driveRightA.set(-0.6);
        driveRightB.set(-0.6);
      }
      else if(autoTimeElapsed<3.8){
          driveLeftA.set(-0.2);
          driveLeftB.set(-0.2);
          driveRightA.set(-0.2);
          driveRightB.set(-0.2);
      }
        else if(autoTimeElapsed<3.95){
          driveLeftA.set(0);
          driveLeftB.set(0);
          driveRightA.set(0);
          driveRightB.set(0);
          shooterWheelA.set(-0.8);
          shooterWheelB.set(0.8);
          }
        else if(autoTimeElapsed<4.62){
          driveLeftA.set(-0.2);
          driveLeftB.set(-0.2);
          driveRightA.set(0.2);
          driveRightB.set(0.2);
          }
          else if(autoTimeElapsed<5.2){
            driveLeftA.set(-0.6);
            driveLeftB.set(-0.6);
            driveRightA.set(-0.6);
            driveRightB.set(-0.6);
            }
            else if(autoTimeElapsed<8){
              driveLeftA.set(0);
              driveLeftB.set(0);
              driveRightA.set(0);
              driveRightB.set(0);
              belt.set(VictorSPXControlMode.PercentOutput, 0.5);
              feedWheel.set(VictorSPXControlMode.PercentOutput, 0.5);
              intake.set(VictorSPXControlMode.PercentOutput, -0.7);
            }
      else{
        driveLeftA.set(0);
        driveLeftB.set(0);
        driveRightA.set(0);
        driveRightB.set(0); 
        shooterWheelA.set(0);
          shooterWheelB.set(0);
        intake.set(VictorSPXControlMode.PercentOutput, 0);
      }
      /* Auto 1 */
    if(autoCode == 1){
      if(autoTimeElapsed<1.2){
        driveLeftA.set(-0.2);
        driveLeftB.set(-0.2);
        driveRightA.set(-0.2);
        driveRightB.set(-0.2);
      }

     else if(autoTimeElapsed<3){
        driveLeftA.set(0);
        driveLeftB.set(0);
        driveRightA.set(0);
        driveRightB.set(0);
        intake.set(VictorSPXControlMode.PercentOutput, -0.6);
        belt.set(VictorSPXControlMode.PercentOutput, 0.5);
      }

      else if(autoTimeElapsed<3.8){
        driveLeftA.set(0.4);
        driveLeftB.set(0.4);
        driveRightA.set(0.4);
        driveRightB.set(0.4);
        intake.set(VictorSPXControlMode.PercentOutput, -0.6);
        belt.set(VictorSPXControlMode.PercentOutput, 0.5);
      }

      else if(autoTimeElapsed<5){                                     // turn left
        driveLeftA.set(0.2);
        driveLeftB.set(0.2);
        driveRightA.set(-0.2);
        driveRightB.set(-0.2);
        intake.set(VictorSPXControlMode.PercentOutput, 0);
        belt.set(VictorSPXControlMode.PercentOutput, 0);
      }

      else if(autoTimeElapsed<5.2){
        driveLeftA.set(-0.4);
        driveLeftB.set(-0.4);
        driveRightA.set(-0.4);
        driveRightB.set(-0.4);
      }

      else if(autoTimeElapsed<5.50){
        driveLeftA.set(-0.2);
        driveLeftB.set(-0.2);
        driveRightA.set(0.2);
        driveRightB.set(0.2);
      }

      else if(autoTimeElapsed<8){
        driveLeftA.set(0);
        driveLeftB.set(0);
        driveRightA.set(0);
        driveRightB.set(0);
        delay++;
        shooterWheelA.set(-0.75);
        shooterWheelB.set(0.7);
        if(delay>50){
        belt.set(VictorSPXControlMode.PercentOutput, 0.5);
        feedWheel.set(VictorSPXControlMode.PercentOutput, 0.5);
        }
      }
      else if(autoTimeElapsed<9.5){
        driveLeftA.set(0.4);
        driveLeftB.set(0.4);
        driveRightA.set(0.4);
        driveRightB.set(0.4);
      }


      else {
      driveLeftA.set(0);
      driveLeftB.set(0);
      driveRightA.set(0);
      driveRightB.set(0);
      intake.set(VictorSPXControlMode.PercentOutput, 0);
      belt.set(VictorSPXControlMode.PercentOutput, 0);
      feedWheel.set(VictorSPXControlMode.PercentOutput, 0);
      shooterWheelA.set(0);
      shooterWheelB.set(0);
      }
    }


    /***************************************
                  * Auto 2 *
    ***************************************/
    /*else if(autoCode == 2){
      if(autoTimeElapsed<0.45){                                        // 1) forward + intake + belt *DONE*
        driveLeftA.set(-0.6);
        driveLeftB.set(-0.6);
        driveRightA.set(-0.6);
        driveRightB.set(-0.6);
        intake.set(VictorSPXControlMode.PercentOutput, -0.6);
        belt.set(VictorSPXControlMode.PercentOutput, 0.5);
        }
      

      else if(autoTimeElapsed<1){                                     // 2) turn right *DONE* *TEST*
      driveLeftA.set(-0.2);
      driveLeftB.set(-0.2);
      driveRightA.set(0.2);
      driveRightB.set(0.2);
      }

      else if(autoTimeElapsed<0){                                     // 3) forward + belt off *DONE* *TEST*
      driveLeftA.set(0.5);
      driveLeftB.set(0.5);
      driveRightA.set(0.5);
      driveRightB.set(0.5);
      belt.set(VictorSPXControlMode.PercentOutput, 0);
      }

     else if(autoTimeElapsed<0){                                      // 4) turn right + shooters + intake off *DONE* *TEST*
      driveLeftA.set(-0.2);
      driveLeftB.set(-0.2);
      driveRightA.set(0.2);
      driveRightB.set(0.2);
      shooterWheelA.set(-0.7);
      shooterWheelB.set(0.9);
      }

    else if(autoTimeElapsed<0){                                       // 5) stop + feed wheel + belt *DONE* *TEST*
      driveLeftA.set(0);
      driveLeftB.set(0);
      driveRightA.set(0);
      driveRightB.set(0); 
      feedWheel.set(VictorSPXControlMode.PercentOutput, 0.5);
      belt.set(VictorSPXControlMode.PercentOutput, 0.5);
      }

    else if(autoTimeElapsed<0){                                       // 6) reverse + feed wheel off + shooters off + belt off *DONE* *TEST*
      driveLeftA.set(0.5);
      driveLeftB.set(0.5);
      driveRightA.set(0.5);
      driveRightB.set(0.5); 
      feedWheel.set(VictorSPXControlMode.PercentOutput, 0);
      shooterWheelA.set(0);
      shooterWheelB.set(0);
      belt.set(VictorSPXControlMode.PercentOutput, 0);
      }

    else if(autoTimeElapsed<0){                                       // 7) turn left + intake + belt *DONE* *TEST*
      driveLeftA.set(0.4);
      driveLeftB.set(0.4);
      driveRightA.set(-0.4);
      driveRightB.set(-0.4);
      intake.set(VictorSPXControlMode.PercentOutput, -0.6);
      belt.set(VictorSPXControlMode.PercentOutput, 0.5);
      }

    else if(autoTimeElapsed<0){                                       // 8) forward *DONE* *TEST*
      driveLeftA.set(-0.3);
      driveLeftB.set(-0.3);
      driveRightA.set(-0.3);
      driveRightB.set(-0.3);  
      }

    else if(autoTimeElapsed<0){                                       // 9) turn right + belt off *DONE* *TEST*
      driveLeftA.set(-0.4);
      driveLeftB.set(-0.4);
      driveRightA.set(0.4);
      driveRightB.set(0.4);  
      belt.set(VictorSPXControlMode.PercentOutput, 0.5);
      }
      

    else if(autoTimeElapsed<0){  
      shooterWheelA.set(-0.8);                                        // 10) shooter *DONE* *TEST*
      shooterWheelB.set(0.8);
      }

    else if(autoTimeElapsed<0){                                       // 11) stop + feed wheel + belt *DONE* *TEST*
      driveLeftA.set(0);
      driveLeftB.set(0);
      driveRightA.set(0);
      driveRightB.set(0);   
      feedWheel.set(VictorSPXControlMode.PercentOutput, 0.5);
      belt.set(VictorSPXControlMode.PercentOutput, 0.5);
      }

    else {                                                            // stop everything
      driveLeftA.set(0);
      driveLeftB.set(0);
      driveRightA.set(0);
      driveRightB.set(0);
      intake.set(VictorSPXControlMode.PercentOutput, 0);
      belt.set(VictorSPXControlMode.PercentOutput, 0);
      feedWheel.set(VictorSPXControlMode.PercentOutput, 0);
      shooterWheelA.set(0);
      shooterWheelB.set(0);
      } 
    } */
  }




  /* This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {
    
  }

  /* This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    auto = 0;


    /* driving controls */
    double forward = driverController.getRawAxis(1);
    double turn = -driverController.getRawAxis(0);
    
    driveLeftA.set(forward+(turn*0.5));
    driveLeftB.set(forward+(turn*0.5)); 
    driveRightA.set(forward-(turn*0.5));
    driveRightB.set(forward-(turn*0.5));

    

    /* Intake controls and shooting */

    if(functionController.getRawAxis(2)>0.5){
    intake.set(VictorSPXControlMode.PercentOutput, -0.6);
    belt.set(VictorSPXControlMode.PercentOutput, 0.6);
    }
    
    else if(functionController.getRawButton(5)){
      intake.set(VictorSPXControlMode.PercentOutput, 0.6);
    }

    else if(functionController.getRawButton(2)){
      intake.set(VictorSPXControlMode.PercentOutput, 0.4);
      belt.set(VictorSPXControlMode.PercentOutput, -0.4);
      feedWheel.set(VictorSPXControlMode.PercentOutput, -0.4);
    }

    else if(functionController.getRawAxis(3)>0.5){
      delay++;
      shooterWheelA.set(-0.75);
      shooterWheelB.set(0.7);
      if(delay>60){
        belt.set(VictorSPXControlMode.PercentOutput, 0.5);
        feedWheel.set(VictorSPXControlMode.PercentOutput, 0.5);
      } 
    }
    
    else{
      intake.set(ControlMode.PercentOutput, 0);
      belt.set(VictorSPXControlMode.PercentOutput, 0);
      feedWheel.set(VictorSPXControlMode.PercentOutput, 0);
      delay = 0;
      shooterWheelA.set(0);
      shooterWheelB.set(0);
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