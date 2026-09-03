
package frc.robot.commands;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import frc.robot.constants.DriveConstants;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj2.command.Command;

public class RobotRotate extends Command{
    private Drivetrain drive;
    private Rotation2d startAngle;

    public RobotRotate(Drivetrain drivetrain) {
        this.drive = drivetrain;
    }
    @Override
    public void initialize() {
        drive.arcadeDrive(0, 0.5);
        startAngle = drive.getGyroAngle();
    }
    @Override
    public void execute() {

    }

    @Override
    public void end(boolean interrupted){
        drive.arcadeDrive(0, 0);
    }

    public boolean isFinished() {
        if(Math.abs(drive.getGyroAngle().getDegrees() - startAngle.getDegrees()) >= 90){
            end(false);
            return true;
        }
        
        return false;
    }

}



