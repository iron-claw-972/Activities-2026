package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.constants.Constants;
import frc.robot.constants.DriveConstants;

public class thing extends SubsystemBase {

    private CANSparkMax motor;
    MechanismLigament2d myMechLig2D = new MechanismLigament2d("Ligament", 30, 30);
    public Mechanism2d myMech2D = new Mechanism2d(100, 100);
    SingleJointedArmSim armSim = new SingleJointedArmSim(DCMotor.getNEO(1), 1.0, 6.7, 0.05, Double.POSITIVE_INFINITY,
    Double.NEGATIVE_INFINITY, false, 0);


    double speed = 0.5;

    public thing() {
        motor = new CANSparkMax(8, MotorType.kBrushless);
        myMech2D.getRoot("pivot", 50, 50).append(myMechLig2D);
        stopMotor();

    }

    void setMotor(double speed) {

        motor.set(speed);

    }

    void stopMotor() {

        motor.set(0);

    }

    double getPosition() {

        return motor.getEncoder().getPosition();

    }

    void setPosition(double pos) {
        motor.getEncoder().setPosition(pos);
        System.out.print(motor.getEncoder().getPosition());

    }


    @Override
    public void periodic() {

        setMotor(speed);

    }

    @Override
    public void simulationPeriodic() {
        armSim.setInput(speed * 12);
        armSim.update(Constants.LOOP_TIME);
        setPosition(armSim.getAngleRads());
        myMechLig2D.setAngle(Units.radiansToDegrees(armSim.getAngleRads()));

    }
}