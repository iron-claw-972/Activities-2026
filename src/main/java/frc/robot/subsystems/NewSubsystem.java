package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class NewSubsystem extends SubsystemBase {

    private CANSparkMax motor;
    private SingleJointedArmSim armSim;
    private Mechanism2d mechanism;
    private MechanismLigament2d wheel;

    // PID controller
    private PIDController pid = new PIDController(0.1, 0, 0);

    public NewSubsystem() {

        motor = new CANSparkMax(10, MotorType.kBrushless);

        motor.getEncoder().setPosition(0);

        // Configure PID tolerance
        pid.setTolerance(0.05);

        armSim = new SingleJointedArmSim(
            DCMotor.getFalcon500(1),
            1,
            0.001,
            0.05,
            Double.NEGATIVE_INFINITY,
            Double.POSITIVE_INFINITY,
            false,
            0
        );

        mechanism = new Mechanism2d(100, 100);

        wheel = new MechanismLigament2d("wheel", 30, 0);

        mechanism.getRoot("pivot", 50, 50).append(wheel);
    }

    public Mechanism2d getMechanism() {
        return mechanism;
    }

    public void setMotor(double speed) {
        if (RobotBase.isReal()) {
            motor.set(speed);
        } else {
            armSim.setInputVoltage(
                speed * RobotController.getBatteryVoltage()
            );
        }
    }

    public void stopMotor() {
        setMotor(0);
    }

    public double getPosition() {
        if (RobotBase.isReal()) {
            return motor.getEncoder().getPosition();
        } else {
            return Units.radiansToDegrees(armSim.getAngleRads());
        }
    }

    // Set the PID target
    public void spinTo(double angle) {
        pid.reset();
        pid.setSetpoint(angle);
    }

    @Override
    public void periodic() {

        // Calculate PID output and send it to the motor
        double output = pid.calculate(getPosition());
        setMotor(output);

        // Update simulation
        if (!RobotBase.isReal()) {
            armSim.update(0.02);

            wheel.setAngle(
                Units.radiansToDegrees(armSim.getAngleRads())
            );
        }
    }

    // Check if the PID has reached its target
    public boolean atSetpoint() {
        return pid.atSetpoint();
    }

    // Return the PID controller
    public PIDController getPID() {
        return pid;
    }
}