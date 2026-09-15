package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class newmotor extends SubsystemBase {
    
    private static final double WHEEL_MOI = 0;
    private static final double WHEEL_RADIUS = 0;
    private final CANSparkMax motor;
    private final SingleJointedArmSim armSim;
    private final Mechanism2d mechanism = new Mechanism2d(100, 100);
    private final MechanismLigament2d wheelLigament;
    private double motorOutput = 0;
    private double simulatedPosition = 0;
            
    public newmotor() {
        motor = new CANSparkMax(5, MotorType.kBrushless);
                
        motor.getEncoder().setPosition(0);
        
        armSim = new SingleJointedArmSim(
            DCMotor.getFalcon500(1),1,
            WHEEL_MOI,
            WHEEL_RADIUS,
            Double.NEGATIVE_INFINITY,
            Double.POSITIVE_INFINITY,
            false,
            0
        );

        wheelLigament = mechanism.getRoot("pivot", 50, 50)
        .append(new MechanismLigament2d("Wheel", 30, 0));
    }

    public void set(double speed) {
        motorOutput = speed;
        motor.set(speed);
    }

    public void stop() {
        set(0);
    }

    public double getPosition() {
        if (RobotBase.isSimulation()) {
            return simulatedPosition;
        }

        return motor.getEncoder().getPosition();
    }

    public Mechanism2d getMechanism() {
    return mechanism;
    }

    @Override
    public void periodic() {
        set(0.05);
    }

    @Override
    public void simulationPeriodic() {
        double volateg = motorOutput * RobotController.getBatteryVoltage();
        
        armSim.setInputVoltage(volateg);;
        armSim.update(0.02);

        simulatedPosition = armSim.getAngleRads();

        wheelLigament.setAngle(Math.toDegrees(simulatedPosition));
    }
}

