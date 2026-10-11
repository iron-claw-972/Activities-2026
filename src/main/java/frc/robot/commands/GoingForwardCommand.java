package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drivetrain;

public class GoingForwardCommand extends Command {
    private Drivetrain drive;

    public GoingForwardCommand(Drivetrain drive){   
        this.drive = drive;
    }

    @Override
    public void initialize(){
        //addRequirements(drive);
    }

    @Override
    public void execute(){
        drive.arcadeDrive(1.0, 0);
    }

    @Override
    public boolean isFinished(){
        return false;
    }

    @Override
    public void end(boolean interrupted){
        drive.arcadeDrive(0, 0);

    }


    
}