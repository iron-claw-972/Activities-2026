
        
package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;


public class NewSubsystem extends SubsystemBase {

    private CANSparkMax motor;
    private SingleJointedArmSim armSim;
    private Mechanism2d mechanism;
    private MechanismLigament2d wheel;

    public NewSubsystem() {

        motor = new CANSparkMax(10, MotorType.kBrushless);

        motor.getEncoder().setPosition(0);

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

    @Override
    public void periodic() {

        if (!RobotBase.isReal()) {
            armSim.update(0.02);

            wheel.setAngle(
                Units.radiansToDegrees(armSim.getAngleRads())
            );

        }
    }

    public Object someMethod() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'someMethod'");
    }

    public static NewSubsystem getInstance() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getInstance'");
    }
}

    

