package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.system.plant.DCMotor;
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

    public Test() {
        testMotor = new CANSparkMax(0, MotorType.kBrushless);
        testMotor.getEncoder().setPosition(0);
        armSim = new SingleJointedArmSim(DCMotor.getFalcon500(1), 1, 5, 5, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, false, 0);
        mechanism = new Mechanism2d(100, 100);
        mechanismLigament = new MechanismLigament2d("Test Subsystem Mechanism Ligament 2D", 35, 0);
        mechanism.getRoot("pivot", 50, 50).append(mechanismLigament);
    }

    public void setSpeed(double speed) {
        testMotor.getEncoder().setPosition(speed);
    }

    public void stopSpinning() {
        setSpeed(0);
    }

    public double getPosition() {
        return testMotor.getEncoder().getPosition();
    }

    public void periodic() {
        setSpeed(0.05);
    }

    @Override
    public void simulationPeriodic() {
        testMotor.setVoltage(0.05 * Constants.ROBOT_VOLTAGE);
        armSim.update(1); // todo: find dt
        setSpeed(armSim.getAngleRads());
        mechanismLigament.setAngle(getPosition());
    }
}
