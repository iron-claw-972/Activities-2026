package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.estimator.DifferentialDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
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
  DifferentialDrivetrainSim driveSim;
  AHRS gyro;
  DifferentialDriveKinematics driveKinematics;
  DifferentialDrivePoseEstimator poseEstimator;

  // TODO 6.1.5: Create Feedforward and PIDs


  public Drivetrain() {
    if (RobotBase.isReal()) {
      leftMotor1 = new CANSparkMax(DriveConstants.LEFT_MOTOR_1_ID, MotorType.kBrushless);
      leftMotor2 = new CANSparkMax(DriveConstants.LEFT_MOTOR_2_ID, MotorType.kBrushless);
      rightMotor1 = new CANSparkMax(DriveConstants.RIGHT_MOTOR_1_ID, MotorType.kBrushless);
      rightMotor2 = new CANSparkMax(DriveConstants.RIGHT_MOTOR_2_ID, MotorType.kBrushless);
      leftMotor1.setIdleMode(IdleMode.kBrake);
      leftMotor2.setIdleMode(IdleMode.kBrake);
      rightMotor1.setIdleMode(IdleMode.kBrake);
      rightMotor2.setIdleMode(IdleMode.kBrake);

      leftMotor2.follow(leftMotor1);
      rightMotor2.follow(rightMotor1);
    } else {
      driveSim = new DifferentialDrivetrainSim(DriveConstants.DRIVETRAIN_PLANT, DriveConstants.MOTOR, DriveConstants.GEAR_RATIO, DriveConstants.TRACK_WIDTH, DriveConstants.WHEEL_DIAMETER / 2, DriveConstants.MEASUREMENT_STD_DEVS);
    }

    gyro = new AHRS(SPI.Port.kMXP);
    driveKinematics = new DifferentialDriveKinematics(DriveConstants.TRACK_WIDTH);
    System.out.println(getGyroAngle());
    poseEstimator = new DifferentialDrivePoseEstimator(driveKinematics, getGyroAngle(), getLeftPosition(), getRightPosition(), new Pose2d());
  }

   /**
   * This will be called every 20ms, or 50 times per second
   */
  @Override
  public void periodic(){
    // TODO 3.1.1: Remove all of the tank drive code in this method

    tankDrive(Robot.driver.getLeftTranslation(), Robot.driver.getLeftTranslation());
    poseEstimator.update(getGyroAngle(), getLeftPosition(), getRightPosition());
    if (RobotBase.isSimulation()) {
      driveSim.update(Constants.LOOP_TIME);
    }
  }

  @Override
  public void simulationPeriodic() {}

  /**
   * Drives the robot using tank drive controls. Tank drive is slightly easier to code but less
   * intuitive to control than arcade drive.
   *
   * @param leftPower the commanded power to the left motors (-1 to 1)
   * @param rightPower the commanded power to the right motors (-1 to 1)
   */
  public void tankDrive(double leftPower, double rightPower) {
    // TODO 1.2.1: Implement tankDrive

    // TODO 2.1.2: If in sim, set sim inputs
    if (RobotBase.isReal()) {
      leftMotor1.set(leftPower * 0.25);
      rightMotor1.set(rightPower * 0.25);
    } else {
      driveSim.setInputs(leftPower * 0.25 * Constants.ROBOT_VOLTAGE, rightPower * 0.25 * Constants.ROBOT_VOLTAGE);
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
    return poseEstimator.getEstimatedPosition();
  }

  public void resetEncoders(){
    // TODO 3.3.7: Reset encoders

  }

  public double getLeftPosition(){
    if (RobotBase.isReal()) {
      return leftMotor1.getEncoder().getPosition() / DriveConstants.GEAR_RATIO * Math.PI * DriveConstants.WHEEL_DIAMETER;
    } else {
      return driveSim.getLeftPositionMeters();
    }
  }
  public double getRightPosition(){
    if (RobotBase.isReal()) {
      return rightMotor1.getEncoder().getPosition() / DriveConstants.GEAR_RATIO * Math.PI * DriveConstants.WHEEL_DIAMETER;
    } else {
      return driveSim.getRightPositionMeters();
    }
  }
  public double getAveragePosition(){
    return (getLeftPosition() + getRightPosition()) / 2;
  }
  public Rotation2d getGyroAngle(){
    if (RobotBase.isReal()) {
      return gyro.getRotation2d();
    } else {
      return driveSim.getHeading();
    }
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
