package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkBase.IdleMode;

import edu.wpi.first.math.estimator.DifferentialDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
  DifferentialDrivetrainSim driveTrainSim;
  // TODO 2.2.1: Create gyro (AHRS)
  private AHRS gyro = new AHRS(SPI.Port.kMXP);
  // TODO 2.2.3: Create DifferentialDriveKinematics
  private final DifferentialDriveKinematics driveKinematics = new DifferentialDriveKinematics(DriveConstants.TRACK_WIDTH);
  // TODO 2.2.4: Create DifferentialDrivePoseEstimator
  private final DifferentialDrivePoseEstimator driveEstimator = new DifferentialDrivePoseEstimator(driveKinematics, getGyroAngle(), 0, 0, new Pose2d());
  // TODO 6.1.5: Create Feedforward and PIDs


  public Drivetrain() {

    // TODO 1.1.2: Initialize motors
    leftMotor1 = new CANSparkMax(1, MotorType.kBrushless);
    leftMotor2 = new CANSparkMax(2, MotorType.kBrushless);
    rightMotor1 = new CANSparkMax(3, MotorType.kBrushless);
    rightMotor2 = new CANSparkMax(4, MotorType.kBrushless);
    
    // TODO 1.1.3: Set motors to brake mode
    leftMotor1.setIdleMode(IdleMode.kBrake);
    rightMotor1.setIdleMode(IdleMode.kBrake);
    // TODO 1.1.4: Make motor2s follow motor1s
    leftMotor2.follow(leftMotor1);
    rightMotor2.follow(rightMotor1);
    // TODO 1.2.4: Invert motors if necessary

    if (RobotBase.isSimulation()) {
      driveTrainSim = new DifferentialDrivetrainSim(DriveConstants.MOTOR, DriveConstants.GEAR_RATIO, 4.0, 20.0, DriveConstants.WHEEL_DIAMETER / 2, DriveConstants.TRACK_WIDTH, null);
    }
  }


   /**
   * This will be called every 20ms, or 50 times per second
   */
  @Override
  public void periodic(){
    // TODO 2.2.5: Update odometry
    driveEstimator.update(getGyroAngle(), getLeftPosition() * DriveConstants.circumference, getRightPosition() * DriveConstants.circumference);
    // TODO 1.2.2: Call tankDrive()
    

    //System.out.println(Robot.driver.getForwardTranslation());

    // TODO 3.1.1: Remove all of the tank drive code in this method

    // TODO 2.1.3: Update sim if in simulation
    SmartDashboard.putString("Pose", getPose().toString());
    
  }

  @Override
  public void simulationPeriodic() {
    System.out.println("Left Motor Output: " + leftMotor1.get() + " Right Motor Output: " + rightMotor1.get());
    driveTrainSim.setInputs(leftMotor1.get()*12, rightMotor1.get()*12);
    driveTrainSim.update(Constants.LOOP_TIME);

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
    leftMotor1.set(leftPower * 0.25);
    rightMotor1.set(rightPower * 0.25);
    //System.out.println(leftMotor1.get());    
    


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
    double leftMotor1 = throttle + turn;
    double rightMotor1 = throttle - turn;
    tankDrive(leftMotor1, rightMotor1);

  }

  public Pose2d getPose(){
    // TODO 2.2.6: Implement this method
    return driveTrainSim.getPose();
  }

  public void resetEncoders(){
    // TODO 3.3.7: Reset encoders

  }

  // TODO 2.2.2: Implement these 4 methods
  public double getLeftPosition(){
    return leftMotor1.getEncoder().getPosition();
    //getEncoder function helps you get position
    //CREATE wrapper function 
  }
  public double getRightPosition(){
    return rightMotor1.getEncoder().getPosition();
  }
  public void setLeftPosition(double position){
    leftMotor1.getEncoder().setPosition(position);
  }
  public void setRightPosition(double position){
    rightMotor1.getEncoder().setPosition(position);
  }

  public double getAveragePosition(){
    return ((rightMotor1.getEncoder().getPosition() + leftMotor1.getEncoder().getPosition()) / 2);
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



