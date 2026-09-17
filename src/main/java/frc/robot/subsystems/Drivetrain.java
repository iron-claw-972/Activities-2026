package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.math.estimator.DifferentialDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.Constants;
import frc.robot.constants.DriveConstants;

public class Drivetrain extends SubsystemBase {
  
  private CANSparkMax leftMotor1;
  private CANSparkMax leftMotor2;
  private CANSparkMax rightMotor1;
  private CANSparkMax rightMotor2;

  // TODO 2.1.1: Create DifferentialDrivetrainSim object (don't define it here)
  private DifferentialDrivetrainSim trainSim;
  // TODO 2.2.1: Create gyro (AHRS)
  private AHRS gyro;
  // TODO 2.2.3: Create DifferentialDriveKinematics
  private DifferentialDriveKinematics kinematics;
  // TODO 2.2.4: Create DifferentialDrivePoseEstimator
  private DifferentialDrivePoseEstimator poseEstimator;
  // TODO 6.1.5: Create Feedforward and PIDs


  public Drivetrain() {

    // TODO 1.1.2: Initialize motors
    leftMotor1 = new CANSparkMax(DriveConstants.LEFT_MOTOR_1_ID, MotorType.kBrushless);
    leftMotor2 = new CANSparkMax(DriveConstants.LEFT_MOTOR_2_ID, MotorType.kBrushless);
    rightMotor1 = new CANSparkMax(DriveConstants.RIGHT_MOTOR_1_ID, MotorType.kBrushless);
    rightMotor2 = new CANSparkMax(DriveConstants.RIGHT_MOTOR_2_ID, MotorType.kBrushless);

    // TODO 1.1.3: Set motors to brake mode
    leftMotor1.setIdleMode(CANSparkMax.IdleMode.kBrake);
    leftMotor2.setIdleMode(CANSparkMax.IdleMode.kBrake);
    rightMotor1.setIdleMode(CANSparkMax.IdleMode.kBrake);
    rightMotor2.setIdleMode(CANSparkMax.IdleMode.kBrake);

    // TODO 1.1.4: Make motor2s follow motor1s
    leftMotor2.follow(leftMotor1);
    rightMotor2.follow(rightMotor1);

    // TODO 1.2.4: Invert motors if necessary
    leftMotor1.setInverted(false);
    rightMotor1.setInverted(true);

    // TODO 2.1.1: Define DifferentialDrivetrainSim if the robot isn't real
    if (RobotBase.isSimulation()) {
      trainSim = new DifferentialDrivetrainSim(
        DriveConstants.DRIVETRAIN_PLANT,
        DriveConstants.MOTOR,
        DriveConstants.GEAR_RATIO,
        DriveConstants.TRACK_WIDTH,
        DriveConstants.WHEEL_DIAMETER, null
      );
    }

    // TODO 2.2.5: Update odometry
    gyro = new AHRS(SPI.Port.kMXP);

    kinematics =
      new DifferentialDriveKinematics(DriveConstants.TRACK_WIDTH);

    poseEstimator = new DifferentialDrivePoseEstimator(
      kinematics, 
      getGyroAngle(), 
      getLeftPosition(), 
      getRightPosition(), 
      new Pose2d()
    );
  }

  /**
  * This will be called every 20ms, or 50 times per second
  */
  @Override
  public void simulationPeriodic(){
    if (trainSim != null) {
      trainSim.update(Constants.LOOP_TIME);
    }
  }

  public Rotation2d getGyroAngle() {
      return Rotation2d.fromDegrees(-gyro.getAngle());
  }

  public Pose2d getPose() {
    return poseEstimator.getEstimatedPosition();
  }

    // TODO 3.1.1: Remove all of the tank drive code in this method

    // TODO 2.1.3: Update sim if in simulation

  /**
   * Drives the robot using tank drive controls. Tank drive is slightly easier to code but less
   * intuitive to control than arcade drive.
   *
   * @param leftPower the commanded power to the left motors (-1 to 1)
   * @param rightPower the commanded power to the right motors (-1 to 1)
   */

  // TODO 1.2.2: Call tankDrive()
  public void tankDrive(double leftPower, double rightPower) {
    // TODO 1.2.1: Implement tankDrive
    leftMotor1.set(leftPower);
    rightMotor1.set(rightPower);
    // TODO 2.1.2: If in sim, set sim inputs
    if (RobotBase.isSimulation()) {
      trainSim.setInputs(leftPower * 12.0, rightPower *12.0);
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
    left.motorOutput = throttle + turn
    rightMotor1.turnThrottle;
    tankDrive(throttle, turn);
  }
  public void resetEncoders(){
    // TODO 3.3.7: Reset encoders
  }
  // TODO 2.2.2: Implement these 4 methods
  public double getLeftPosition(){
    return 0;
  }
  public double getRightPosition(){
    return 0;
  }
  public double getAveragePosition(){
    return 0;
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
