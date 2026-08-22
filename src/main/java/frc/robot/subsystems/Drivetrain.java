package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.estimator.DifferentialDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.constants.Constants;
import frc.robot.constants.DriveConstants;

public class Drivetrain extends SubsystemBase {
  
  private CANSparkMax leftMotor1;
  private CANSparkMax leftMotor2;
  private CANSparkMax rightMotor1;
  private CANSparkMax rightMotor2;

  // TODO 2.1.1: Create DifferentialDrivetrainSim object (don't define it here)
  DifferentialDrivetrainSim drivetrainSim;

  // TODO 2.2.1: Create gyro (AHRS)

   AHRS gyro = new AHRS(SPI.Port.kMXP);
  // TODO 2.2.3: Create DifferentialDriveKinematics

  DifferentialDriveKinematics DDK;

  // TODO 2.2.4: Create DifferentialDrivePoseEstimator

    DifferentialDrivePoseEstimator DDPE;

  // TODO 6.1.5: Create Feedforward and PIDs


  public Drivetrain() {

    // TODO 1.1.2: Initialize motors
    leftMotor1 = new CANSparkMax(DriveConstants.LEFT_MOTOR_1_ID, MotorType.kBrushless);
    leftMotor2 = new CANSparkMax(DriveConstants.LEFT_MOTOR_2_ID, MotorType.kBrushless);
    rightMotor1 = new CANSparkMax(DriveConstants.RIGHT_MOTOR_1_ID, MotorType.kBrushless);
    rightMotor2 = new CANSparkMax(DriveConstants.RIGHT_MOTOR_2_ID, MotorType.kBrushless);


    // TODO 1.1.3: Set motors to brake mode
  
    leftMotor1.setIdleMode(IdleMode.kBrake);
    leftMotor2.setIdleMode(IdleMode.kBrake);
    rightMotor1.setIdleMode(IdleMode.kBrake);
    rightMotor2.setIdleMode(IdleMode.kBrake);


    // TODO 1.1.4: Make motor2s follow motor1s

    rightMotor2.follow(rightMotor1);
    leftMotor2.follow(leftMotor1);

    // TODO 1.2.4: Invert motors if necessary

    // TODO 2.1.1: Define DifferentialDrivetrainSim if the robot isn't real
    if (!RobotBase.isReal()) {
      drivetrainSim = new DifferentialDrivetrainSim(DriveConstants.DRIVETRAIN_PLANT,DriveConstants.MOTOR,DriveConstants.GEAR_RATIO,DriveConstants.TRACK_WIDTH,DriveConstants.WHEEL_DIAMETER/2,DriveConstants.MEASUREMENT_STD_DEVS);
    } 

    DDPE = new DifferentialDrivePoseEstimator(DDK, new Rotation2d(), getLeftPosition(), getAveragePosition(), new Pose2d());
    DDK = new DifferentialDriveKinematics(DriveConstants.TRACK_WIDTH);

  }

   /**
   * This will be called every 20ms, or 50 times per second
   */
  @Override
  public void periodic(){
    // TODO 2.2.5: Update odometry
    DDPE.update(getGyroAngle(), getLeftPosition(), getRightPosition());
    // TODO 1.2.2: Call tankDrive()
    tankDrive(Robot.driver.getLeftTranslation()*.25,Robot.driver.getRightTranslation()*.25);
    // TODO 3.1.1: Remove all of the tank drive code in this method

    // TODO 2.1.3: Update sim if in simulation
    if(RobotBase.isSimulation()) {
      drivetrainSim.update(Constants.LOOP_TIME);
    }
    
  }

  /**
   * Drives the robot using tank drive controls. Tank drive is slightly easier to code but less
   * intuitive to control than arcade drive.
   *
   * @param leftPower the commanded power to the left motors (-1 to 1)
   * @param rightPower the commanded power to the right motors (-1 to 1)
   */
  public void tankDrive(double leftPower, double rightPower) {
    // TODO 1.2.1: Implement tankDrive
    if (RobotBase.isReal()) {
    leftMotor1.set(leftPower);
    rightMotor1.set(rightPower);
    }
    // TODO 2.1.2: If in sim, set sim inputs
    else if (!RobotBase.isReal()) {
         leftMotor1.set(Robot.driver.getLeftTranslation()*Constants.ROBOT_VOLTAGE);
         leftMotor2.set(Robot.driver.getRightTranslation()*Constants.ROBOT_VOLTAGE);
    } 

  }

  /**
   * Drives the robot using arcade controls.
   *
   * @param forward the commanded forward movement
   * @param turn the commanded turn rotation
   */
  public void arcadeDrive(double throttle, double turn) {
    // TODO 3.1.2: Implement arcadeDrive
    
  }

  public Pose2d getPose(){
    // TODO 2.2.6: Implement this method
    return DDPE.getEstimatedPosition();
  }

  public void resetEncoders(){
    // TODO 3.3.7: Reset encoders

  }

  // TODO 2.2.2: Implement these 4 methods

  public double getLeftPosition(){

    if (RobotBase.isReal()) {

      return ( leftMotor1.getEncoder().getPosition() ) / Math.PI * DriveConstants.WHEEL_DIAMETER * DriveConstants.GEAR_RATIO;
      
    } else {

  return drivetrainSim.getLeftPositionMeters();

    }
  }
  public double getRightPosition(){

    if (RobotBase.isReal()) {

      return ( rightMotor1.getEncoder().getPosition() ) / Math.PI * DriveConstants.WHEEL_DIAMETER * DriveConstants.GEAR_RATIO;
      
    } else {

      return drivetrainSim.getRightPositionMeters();

    }

  }
  public double getAveragePosition(){
    if (RobotBase.isReal()) {

      return ((( rightMotor1.getEncoder().getPosition() ) / Math.PI * DriveConstants.WHEEL_DIAMETER * DriveConstants.GEAR_RATIO ) + ( leftMotor1.getEncoder().getPosition() ) / Math.PI * DriveConstants.WHEEL_DIAMETER * DriveConstants.GEAR_RATIO ) / 2;
      
      
    } else {

      return ( drivetrainSim.getRightPositionMeters() + drivetrainSim.getLeftPositionMeters() ) / 2;

    }
  }
  public Rotation2d getGyroAngle(){

      return gyro.getRotation2d();
  }

  public void tankDriveVolts(double left, double right){
    // TODO 6.1.1: Implement this

  }

  // TODO 6.2.1: Implement these 2 methods
  public double getLeftSpeed(){
    return 0;
  }
  public double getRightSpeed(){
    return 0;
  }

  public void feedforwardDrive(double throttle, double turn){
    // TODO 6.2.2: Create wheel speeds

    // TODO 6.2.3: Calculate voltages and call tankDriveVolts()

  }
}
