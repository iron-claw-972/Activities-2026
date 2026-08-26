package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Test extends SubsystemBase {
    private CANSparkMax testMotor;
    SingleJointedArmSim armSim;

    public Test() {
        testMotor = new CANSparkMax(0, MotorType.kBrushless);
        testMotor.getEncoder().setPosition(0);
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
}
