package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import java.time.Instant;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.hal.SimDeviceJNI;
import edu.wpi.first.hal.SimDouble;
import edu.wpi.first.hal.simulation.SimDeviceDataJNI;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.estimator.DifferentialDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.units.Velocity;
import edu.wpi.first.units.Voltage;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.constants.Constants;

import frc.robot.constants.DriveConstants;

public class Drivetrain extends SubsystemBase {

  private CANSparkMax leftMotor1;

  private CANSparkMax rightMotor1;

  // TODO 2.1.1: Create DifferentialDrivetrainSim object (don't define it here)
  DifferentialDrivetrainSim driveSim;
  // TODO 2.2.1: Create gyro (AHRS)
  AHRS gyro = new AHRS(SPI.Port.kMXP);
  // TODO 2.2.3: Create DifferentialDriveKinematics
  DifferentialDriveKinematics kinematics = new DifferentialDriveKinematics(DriveConstants.TRACK_WIDTH);
  // TODO 2.2.4: Create DifferentialDrivePoseEstimator
  //DifferentialDrivePoseEstimator poseEstimator;
  DifferentialDrivePoseEstimator poseEstimator;
  // TODO 6.1.5: Create Feedforward and PIDs
  SimpleMotorFeedforward feed = new SimpleMotorFeedforward(DriveConstants.S, DriveConstants.V, DriveConstants.A);
  PIDController pidLeft = new PIDController(DriveConstants.P,DriveConstants.I, DriveConstants.D);
   PIDController pidRight = new PIDController(DriveConstants.P,DriveConstants.I, DriveConstants.D);

  Field2d field = new Field2d();
  
  SimDouble yawSim;


  public Drivetrain() {

    // TODO 1.1.2: Initialize motors
    leftMotor1 = new CANSparkMax(DriveConstants.LEFT_MOTOR_1_ID,  MotorType.kBrushless);
   // leftMotor2 = new CANSparkMax(DriveConstants.LEFT_MOTOR_2_ID, MotorType.kBrushless);
    rightMotor1 = new CANSparkMax(DriveConstants.RIGHT_MOTOR_1_ID, MotorType.kBrushless);
   // rightMotor2 = new CANSparkMax(DriveConstants.RIGHT_MOTOR_2_ID, MotorType.kBrushless);

    // TODO 1.1.3: Set motors to brake mode
    leftMotor1.setIdleMode(IdleMode.kBrake);
   // leftMotor2.setIdleMode(IdleMode.kBrake);
    rightMotor1.setIdleMode(IdleMode.kBrake);
   // rightMotor2.setIdleMode(IdleMode.kBrake);
    // TODO 1.1.4: Make motor2s follow motor1s
   // leftMotor2.follow(leftMotor1);
   // rightMotor2.follow(rightMotor1);
    // TODO 1.2.4: Invert motors if necessary
    leftMotor1.setInverted(true);
    rightMotor1.setInverted(true);

    SmartDashboard.putData("Davids Field", field);
    SmartDashboard.putData("Drivetrain Pose Reset", new InstantCommand(() -> resetEncoder(), this));

    // TODO 2.1.1: Define DifferentialDrivetrainSim if the robot isn't real
    if (RobotBase.isSimulation()) {
      driveSim = new DifferentialDrivetrainSim(DriveConstants.MOTOR, DriveConstants.GEAR_RATIO, 0.03, 0.01,
          DriveConstants.WHEEL_DIAMETER / 2, DriveConstants.TRACK_WIDTH / 2, null);

      int dev = SimDeviceDataJNI.getSimDeviceHandle("navX-Sensor[0]");
      yawSim = new SimDouble(SimDeviceDataJNI.getSimValueHandle(dev, "Yaw")); 

    }
        poseEstimator = new DifferentialDrivePoseEstimator(kinematics, gyro.getRotation2d(), getLeftPosition(), getRightPosition(), new Pose2d(0, 0 , new Rotation2d()));
  }
  public void resetEncoder(){
    leftMotor1.getEncoder().setPosition(0);
    rightMotor1.getEncoder().setPosition(0);
    gyro.reset();
    poseEstimator.resetPosition(gyro.getRotation2d(), getLeftPosition(), getRightPosition(), new Pose2d());
    driveSim.setPose(new Pose2d());
  }
  /*
   * This will be called every 20ms, or 50 times per second
   */
  @Override
  public void periodic() {

    // TODO 2.2.5: Update odometry
    poseEstimator.update(gyro.getRotation2d(), getLeftPosition()*DriveConstants.WHEEL_CIRCUMFERENCE, getRightPosition()*DriveConstants.WHEEL_CIRCUMFERENCE);
    // TODO 1.2.2: Call tankDrive()
    //tankDrive(Robot.driver.getLeftTranslation(), Robot.driver.getRightTranslation());
    field.setRobotPose(poseEstimator.getEstimatedPosition());

    // TODO 3.1.1: Remove all of the tank drive code in this method
    SmartDashboard.putString("Robot Pose", poseEstimator.getEstimatedPosition().toString());

  }

  @Override
  public void simulationPeriodic() {
    //Gerry said to check this out for voltage imputs to sim
    //System.out.println("Left Motor Voltage: " + leftMotor1.get()*12 + " Right Motor Voltage: " + rightMotor1.get()*12);
    driveSim.setInputs(leftMotor1.get()*12, rightMotor1.get()*12);
    driveSim.update(Constants.LOOP_TIME);

    setLeftPosition(driveSim.getLeftPositionMeters()/DriveConstants.WHEEL_CIRCUMFERENCE);
    setRightPosition(driveSim.getRightPositionMeters()/DriveConstants.WHEEL_CIRCUMFERENCE);
    
    setGyroAngle(driveSim.getHeading());
  }  

  /**
   * Drives the robot using tank drive controls. Tank drive is slightly easier to
   * code but less
   * intuitive to control than arcade drive.
   *
   * @param leftPower  the commanded power to the left motors (-1 to 1)
   * @param rightPower the commanded power to the right motors (-1 to 1)
   */
  public void tankDrive(double leftPower, double rightPower) {
    // TODO 1.2.1: Implement tankDrive
    if (RobotBase.isReal()) {
      
    }
    // TODO 2.1.2: If in sim, set sim inputs
    else {
      leftMotor1.set(leftPower * 0.25);
      rightMotor1.set(rightPower * 0.25);

      driveSim.setInputs(leftPower * 0.25 * Constants.ROBOT_VOLTAGE, rightPower * 0.25 * Constants.ROBOT_VOLTAGE);
    }

  }

  /**
   * Drives the robot using arcade controls.
   *
   * @param forward the commanded forward movement
   * @param turn    the commanded turn rotation
   */
  public void arcadeDrive(double throttle, double turn) {
    // TODO 3.1.2: Implement arcadeDrive
    double leftOutput = (throttle + turn);
    double rightOutput = (throttle - turn);
    tankDrive(leftOutput, rightOutput);
  }

  public Pose2d getPose() {
    // TODO 2.2.6: Implement this method
    return poseEstimator.getEstimatedPosition();
  }

  public void resetEncoders() {
    // TODO 3.3.7: Reset encoders

  }

  // TODO 2.2.2: Implement these 4 methods
  public void setLeftPosition(double position) {
    leftMotor1.getEncoder().setPosition(position);
  }

  public void setRightPosition(double position) {
    rightMotor1.getEncoder().setPosition(position);
  }


  public double getLeftPosition() {
    return leftMotor1.getEncoder().getPosition();
  }

  public double getRightPosition() {
    return rightMotor1.getEncoder().getPosition();
  }

  public double getAveragePosition() {
    return (getLeftPosition() + getRightPosition()) / 2.0;
  }

  public Rotation2d getGyroAngle() {
    return gyro.getRotation2d();
  }

  public void tankDriveVolts(double left, double right) {
    // TODO 6.1.1: Implement this
    leftMotor1.setVoltage(left);
    rightMotor1.setVoltage(right);
  }

  // TODO 6.2.1: Implement these 2 methods
  public double getLeftSpeed() {
    return leftMotor1.get();
    //get velocity and return meters per seconed
  }

  public double getRightSpeed() {
    return rightMotor1.get();
  }

  public void feedforwardDrive(double throttle, double turn) {
    // TODO 6.2.2: Create wheel speeds
    var speed = new ChassisSpeeds(throttle, 0 , -turn);
    DifferentialDriveWheelSpeeds wheelSpeeds = kinematics.toWheelSpeeds(speed);
    // TODO 6.2.3: Calculate voltages and call tankDriveVolts()
    double leftVelocity = wheelSpeeds.leftMetersPerSecond;
    double leftVoltage = feed.calculate(leftVelocity) + pidLeft.calculate(getLeftSpeed(), leftVelocity);
    double rightVelocity = wheelSpeeds.rightMetersPerSecond;
    double rightVoltage = feed.calculate(rightVelocity) + pidRight.calculate(getRightSpeed(), rightVelocity);
    tankDriveVolts(leftVoltage, rightVoltage);
  }
  public void setGyroAngle(Rotation2d angle) {
    // TODO 3.3.6: Implement this method
    
    yawSim.set(angle.getDegrees());
  }

  public void resetRobotPose() {
    // TODO 3.3.5: Implement this method
    poseEstimator.resetPosition(getGyroAngle(), getLeftPosition(), getRightPosition(), new Pose2d());
  }
}
