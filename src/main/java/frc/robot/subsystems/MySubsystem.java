package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.constants.DriveConstants;
import com.revrobotics.CANSparkLowLevel.MotorType;

public class MySubsystem extends SubsystemBase{

    //2.3.3
    
    private CANSparkMax Motor;

    //2.4.1

    private final SingleJointedArmSim armSim = new SingleJointedArmSim(
        DCMotor.getNEO(1), 
        1.0, 
        1.0, 
        0.4, 
        0, 
        Double.POSITIVE_INFINITY, 
        false, 
        0);

    //2.4.2

    private Mechanism2d mechanism = new Mechanism2d(100, 100);
    
    //2.4.3

    private MechanismLigament2d arm = new MechanismLigament2d("Arm", 10, 0);

    public MySubsystem(){
        
        Motor = new CANSparkMax(15, MotorType.kBrushless);

        //2.4.4

        mechanism.getRoot("Pivot", 50, 50).append(arm);

        SmartDashboard.putData("Justin's Motor",mechanism);
    }

    @Override
    public void periodic(){
        setSpeed(0.5);

    }

    //4.4.5

    @Override
    public void simulationPeriodic(){
        armSim.setInputVoltage(getSpeed()*12);
        setPosition(Units.radiansToRotations(armSim.getAngleRads()));

        //4.4.6

        arm.setAngle(Units.rotationsToDegrees(getPosition()));
    }

    public void setSpeed(double speed){
        Motor.set(speed);
    }

    public double getSpeed(){
        return Motor.get();
    }

    public void setPosition(double position){
        Motor.getEncoder().setPosition(position);
    }

    public double getPosition(){
        return Motor.getEncoder().getPosition();
    }

    public Mechanism2d getMechanism2d(){
        return mechanism;
    }

} 