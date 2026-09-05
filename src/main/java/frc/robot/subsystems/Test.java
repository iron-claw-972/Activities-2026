package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.Constants;

public class Test extends SubsystemBase {
    private CANSparkMax testMotor;
    SingleJointedArmSim armSim;
    public Mechanism2d mechanism;
    MechanismLigament2d mechanismLigament;
    PIDController pidController;

    public Test() {
        testMotor = new CANSparkMax(0, MotorType.kBrushless);
        testMotor.getEncoder().setPosition(0);
        armSim = new SingleJointedArmSim(DCMotor.getFalcon500(1), 1, 5, 5, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, false, 0);
        mechanism = new Mechanism2d(100, 100);
        mechanismLigament = new MechanismLigament2d("Test Subsystem Mechanism Ligament 2D", 35, 0);
        mechanism.getRoot("pivot", 50, 50).append(mechanismLigament);

        pidController = new PIDController(0.5, 0, 3);
        pidController.setTolerance(0.1);
    }

    public void setSpeed(double speed) {
        testMotor.set(speed);
    }

    public double getSpeed() {
        return testMotor.get();
    }

    public void stopSpinning() {
        setSpeed(0);
    }

    public void setPosition(double pos) {
        testMotor.getEncoder().setPosition(pos);
    }

    public double getPosition() {
        return testMotor.getEncoder().getPosition();
    }

    public void periodic() {
        // setSpeed();
        System.out.println(getPosition());
        setSpeed(pidController.calculate(getPosition()));
    }

    @Override
    public void simulationPeriodic() {
        testMotor.setVoltage(getSpeed() * Constants.ROBOT_VOLTAGE);
        armSim.setInputVoltage(getSpeed() * Constants.ROBOT_VOLTAGE);
        armSim.update(Constants.LOOP_TIME);
        setPosition(Units.radiansToRotations(armSim.getAngleRads()));
        mechanismLigament.setAngle(Units.rotationsToDegrees(getPosition()));
    }

    public void spinTo(double setpoint) {
        pidController.reset();
        pidController.setSetpoint(setpoint);
    }

    public boolean atSetpoint() {
        return pidController.atSetpoint();
    }
    
    public PIDController getPid() {
        return pidController;
    }
}
