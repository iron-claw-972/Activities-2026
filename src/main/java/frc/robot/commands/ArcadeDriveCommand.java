package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.subsystems.Drivetrain;

public class ArcadeDriveCommand extends Command {
    Drivetrain drive;

    public ArcadeDriveCommand(Drivetrain drive){
        this.drive = drive;
        addRequirements(drive);
    }
    @Override
    public void execute(){
        drive.arcadeDrive(Robot.driver.getForwardTranslation(), Robot.driver.getTurn());
    }

    
}
