package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.estimator.DifferentialDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
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

  // TODO 2.1.1: Create DifferentialDrivetrainSim object
  private DifferentialDrivetrainSim drivetrainSim;

  // TODO 2.2.1: Create gyro (AHRS)
  private AHRS gyro;

  // TODO 2.2.3: Create DifferentialDriveKinematics
  private DifferentialDriveKinematics kinematics;

  // TODO 2.2.4: Create DifferentialDrivePoseEstimator
  private DifferentialDrivePoseEstimator poseEstimator;

  // TODO 6.1.5: Create Feedforward and PIDs
  private SimpleMotorFeedforward feedforward;
  private PIDController leftPID;
  private PIDController rightPID;

  public Drivetrain() {

    // TODO 1.1.2: Initialize motors
    leftMotor1 = new CANSparkMax(
        DriveConstants.LEFT_MOTOR_1_ID,
        MotorType.kBrushless);

    // leftMotor2 = new CANSparkMax(
    //     DriveConstants.LEFT_MOTOR_2_ID,
    //     MotorType.kBrushless);

    rightMotor1 = new CANSparkMax(
        DriveConstants.RIGHT_MOTOR_1_ID,
        MotorType.kBrushless);

    // rightMotor2 = new CANSparkMax(
    //     DriveConstants.RIGHT_MOTOR_2_ID,
    //     MotorType.kBrushless);

    // TODO 1.1.3: Set motors to brake mode
    leftMotor1.setIdleMode(IdleMode.kBrake);
    // leftMotor2.setIdleMode(IdleMode.kBrake);

    rightMotor1.setIdleMode(IdleMode.kBrake);
    // rightMotor2.setIdleMode(IdleMode.kBrake);

    // TODO 1.1.4: Make motor2s follow motor1s
    // leftMotor2.follow(leftMotor1);
    // rightMotor2.follow(rightMotor1);

    gyro = new AHRS();

    // TODO 1.2.4: Invert motors if necessary
    rightMotor1.setInverted(true);

    // TODO 2.1.1: Define DifferentialDrivetrainSim if the robot isn't real
    if (RobotBase.isSimulation()) {
      drivetrainSim = new DifferentialDrivetrainSim(
          DriveConstants.DRIVETRAIN_PLANT,
          DriveConstants.MOTOR,
          DriveConstants.GEAR_RATIO,
          DriveConstants.TRACK_WIDTH,
          DriveConstants.WHEEL_DIAMETER / 2,
          DriveConstants.MEASUREMENT_STD_DEVS);
    }

    // TODO 2.2.3: Initialize kinematics
    kinematics = new DifferentialDriveKinematics(
        DriveConstants.TRACK_WIDTH);

    // TODO 6.1.5: Initialize Feedforward and PIDs
    feedforward = new SimpleMotorFeedforward(
        DriveConstants.S,
        DriveConstants.V,
        DriveConstants.A);

    leftPID = new PIDController(
        DriveConstants.P,
        DriveConstants.I,
        DriveConstants.D);

    rightPID = new PIDController(
        DriveConstants.P,
        DriveConstants.I,
        DriveConstants.D);

    poseEstimator = new DifferentialDrivePoseEstimator(
        kinematics,
        getGyroAngle(),
        getLeftPosition(),
        getRightPosition(),
        getPose2d());
  }

  private Pose2d getPose2d() {
    return new Pose2d();
  }

  /**
   * This will be called every 20ms, or 50 times per second
   */
  @Override
  public void periodic() {

    // TODO 2.2.5: Update odometry
    poseEstimator.update(
        getGyroAngle(),
        getLeftPosition(),
        getRightPosition());

    // TODO 1.2.2: Call tankDrive()
    // tankDrive(0.15, 0.15);

    // TODO 3.1.1: Remove all of the tank drive code in this method

    // TODO 2.1.3: Update sim if in simulation
    if (RobotBase.isSimulation()) {
      drivetrainSim.update(Constants.LOOP_TIME);
    }
  }

  /**
   * Drives the robot using tank drive controls.
   *
   * @param leftPower  the commanded power to the left motors (-1 to 1)
   * @param rightPower the commanded power to the right motors (-1 to 1)
   */
  public void tankDrive(double leftPower, double rightPower) {

    // TODO 1.2.1: Implement tankDrive
    leftMotor1.set(leftPower);
    rightMotor1.set(rightPower);

    // TODO 2.1.2: If in sim, set sim inputs
    if (RobotBase.isSimulation()) {
      drivetrainSim.setInputs(
          leftPower * 12.0,
          rightPower * 12.0);
    }
  }

  /**
   * Drives the robot using arcade controls.
   *
   * @param throttle the commanded forward movement
   * @param turn     the commanded turn rotation
   */
  public void arcadeDrive(double throttle, double turn) {

    double leftPower = throttle + turn;
    double rightPower = throttle - turn;

    tankDrive(leftPower, rightPower);
  }

  public Pose2d getPose() {

    // TODO 2.2.6: Implement this method
    return poseEstimator.getEstimatedPosition();
  }

  public void resetEncoders() {

    leftMotor1.getEncoder().setPosition(0);
    rightMotor1.getEncoder().setPosition(0);

    poseEstimator.resetPosition(
        gyro.getRotation2d(),
        0,
        0,
        poseEstimator.getEstimatedPosition());
  }

  // Returns the average distance traveled by both sides

  // TODO 2.2.2: Implement these 4 methods
  public double getLeftPosition() {

    if (RobotBase.isReal()) {
      return leftMotor1.getEncoder().getPosition()
          * DriveConstants.DISTANCE_PER_MOTOR_ROTATION;
    }

    return drivetrainSim.getLeftPositionMeters();
  }

  public double getRightPosition() {

    if (RobotBase.isReal()) {
      return rightMotor1.getEncoder().getPosition()
          * DriveConstants.DISTANCE_PER_MOTOR_ROTATION;
    }

    return drivetrainSim.getRightPositionMeters();
  }

  public double getAveragePosition() {

    return (getLeftPosition() + getRightPosition()) / 2.0;
  }

  public Rotation2d getGyroAngle() {

    if (RobotBase.isReal()) {
      return gyro.getRotation2d();
    }

    return drivetrainSim.getHeading();
  }

  public void tankDriveVolts(double left, double right) {

    leftMotor1.setVoltage(left);
    rightMotor1.setVoltage(right);
  }

  // TODO 6.2.1: Calculate left wheel velocity
  public double getLeftSpeed() {

    if (RobotBase.isReal()) {
      return leftMotor1.getEncoder().getVelocity()
          * DriveConstants.DISTANCE_PER_MOTOR_ROTATION;
    }

    return drivetrainSim.getLeftVelocityMetersPerSecond();
  }

  // TODO 6.2.1: Calculate right wheel velocity
  public double getRightSpeed() {

    if (RobotBase.isReal()) {
      return rightMotor1.getEncoder().getVelocity()
          * DriveConstants.DISTANCE_PER_MOTOR_ROTATION;
    }

    return drivetrainSim.getRightVelocityMetersPerSecond();
  }

  // TODO 6.2.2: Feedforward + PID velocity control
  public void feedforwardDrive(double throttle, double turn) {

    DifferentialDriveWheelSpeeds wheelSpeeds =
        kinematics.toWheelSpeeds(
            new ChassisSpeeds(
                throttle,
                0,
                -turn));

    double leftMetersPerSecond =
        wheelSpeeds.leftMetersPerSecond;

    double rightMetersPerSecond =
        wheelSpeeds.rightMetersPerSecond;

    double leftVoltage =
        feedforward.calculate(leftMetersPerSecond)
        + leftPID.calculate(
            getLeftSpeed(),
            leftMetersPerSecond);

    double rightVoltage =
        feedforward.calculate(rightMetersPerSecond)
        + rightPID.calculate(
            getRightSpeed(),
            rightMetersPerSecond);

    tankDriveVolts(leftVoltage, rightVoltage);
  }
}