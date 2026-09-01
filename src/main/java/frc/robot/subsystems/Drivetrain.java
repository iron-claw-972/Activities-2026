package frc.robot.subsystems;

import com.ctre.phoenix6.controls.Follower;
import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.math.estimator.DifferentialDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.Constants;
import frc.robot.constants.DriveConstants;
import frc.robot.util.MotorFactory;

public class Drivetrain extends SubsystemBase {
  
  private CANSparkMax leftMotor1;
  private CANSparkMax leftMotor2;
  private CANSparkMax rightMotor1;
  private CANSparkMax rightMotor2;

  // TODO 2.1.1: Create DifferentialDrivetrainSim object (don't define it here)

  // TODO 2.2.1: Create gyro (AHRS)

  // TODO 2.2.3: Create DifferentialDriveKinematics

  // TODO 2.2.4: Create DifferentialDrivePoseEstimator

  // TODO 6.1.5: Create Feedforward and PIDs


  public Drivetrain() {

    // TODO 1.1.2: Initialize motors
    leftMotor1 = new CANSparkMax(1, MotorType.kBrushless);
    leftMotor2 = new CANSparkMax(2, MotorType.kBrushless);
    rightMotor1 = new CANSparkMax(3, MotorType.kBrushless);
    rightMotor2 = new CANSparkMax(4, MotorType.kBrushless);

    // TODO 1.1.3: Set motors to brake mode
    leftMotor1.setIdleMode(CANSparkMax.IdleMode.kBrake);
    rightMotor2.setIdleMode(CANSparkMax.IdleMode.kBrake);

    // TODO 1.1.4: Make motor2s follow motor1s
    leftMotor2.follow(leftMotor1);
    rightMotor2.follow(rightMotor1);

    // TODO 1.2.4: Invert motors if necessary
    leftMotor1.setInverted(false);
    rightMotor1.setInverted(true);

    // TODO 2.1.1: Define DifferentialDrivetrainSim if the robot isn't real
    if (RobotBase.isSimulation()) {
      private DifferentialDrivetrainSim driveSim;
        DriveConstants.DRIVETRAIN_PLANT,
        DriveConstants.MOTOR,
        DriveConstants.GEAR_RATIO,
        DriveConstants.TRACK_WIDTH,
        DriveConstants.WHEEL_DIAMETER / 2.0,
      );
    }
  }

  public void tankDrive(double leftPower, double rightPower) {
    leftMotor1.set(leftPower);
    rightMotor1.set(rightPower);
  } 

   /**
   * This will be called every 20ms, or 50 times per second
   */
  @Override
  public void simulatorPeriodic(){
    driveSim.getLeftPositionMeters();
    driveSim.getRightPositionMeters();
    driveSim.update(Constants.LOOP_TIME);
  }

    // TODO 2.2.5: Update odometry
  private AHRS gyro;
  gyro = new AHRS(SPI.Por.kMXP);
  public Rotation2d getGyroAngle() {
    return Rotation2d.fromDegrees(-gyro.getAngle());
  }

  private DifferentialDrivePoseEstimator poseEstimator;

  poseEstimator = new DifferentialDrivePoseEstimator(
    kinematics,
    getGyroAngle(),
    getLeftPosition(),
    getRightPosition(),
    new Pose2d()
  );

  DifferentialDriveKinematics kinematics =
    new DifferentialDriveKinematics(DriveConstants.TRACK_WIDTH);

  ChassisSpeeds
  double linearVelocity = chassisSpeeds.vxMetersPerSecond;
  double angularVelocity = chassisSpeeds.omegaRadiansPerSecond;


  DifferentialDrivePoseEstimator = new DifferentialDrivePoseEstimator(kinematics, getGyroAngle(), getLeftPosition(), getRightPosition(), new Pose2d());

    // TODO 1.2.2: Call tankDrive()
    void tankDrive(leftPower, rightPower);







    // TODO 3.1.1: Remove all of the tank drive code in this method

    // TODO 2.1.3: Update sim if in simulation

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
    return new Pose2d();
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
  public Rotation2d getGyroAngle(){
    return null;
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
