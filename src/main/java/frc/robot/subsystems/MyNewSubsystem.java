package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANSparkMax;

import frc.robot.constants.Constants;
import frc.robot.constants.DriveConstants;

public class MyNewSubsystem extends SubsystemBase{
    private CANSparkMax motor;
    private SingleJointedArmSim armSim;
    public MyNewSubsystem() {
        motor = new CANSparkMax(DriveConstants.SUBSYSTEM_MOTOR, MotorType.kBrushless);
        motor.getEncoder().setPosition(0);
        armSim = new SingleJointedArmSim(DCMotor.getFalcon500(1), 1, 4, DriveConstants.WHEEL_DIAMETER / 2, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, false, 0);
        mechanism2d.getRoot("pivot", 50, 50).append(ligament2d);
    }
    public Mechanism2d mechanism2d = new Mechanism2d(100, 100);
    private MechanismLigament2d ligament2d = new MechanismLigament2d("arm", 30, 0);

    public void setMotor(double speed) {
        motor.set(speed);
    }
    public void stopMotor() {
        motor.set(0);
    }
    public double getPosition() {
        return motor.getEncoder().getPosition();
    }
    
    @Override
    public void periodic() {
        // Implementation for periodic updates
        double angle = Units.radiansToDegrees(armSim.getAngleRads());
        double setPoint = 60;
        double difference = setPoint - angle;
        setMotor(difference / 600.0);
    }

    @Override
    public void simulationPeriodic() {
        armSim.setInput(motor.get());
        double motorVoltage = (motor.get() * Constants.ROBOT_VOLTAGE);
        armSim.update(Constants.LOOP_TIME);
        ligament2d.setAngle(Units.radiansToDegrees(armSim.getAngleRads()));
    }
    public void SingleJointedArmSim(){
        
    }
}
