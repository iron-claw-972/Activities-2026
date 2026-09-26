package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.Drivetrain;

public class ForwardAutoCommand extends Command{
     private Drivetrain drive;

    //3.2.3

    public ForwardAutoCommand(Drivetrain drive){
        this.drive = drive;
        addRequirements(drive);
    }

    //3.2.4

    @Override
    public void initialize(){
        drive.tankDrive(1, 1);
    }

    //3.2.5

    @Override
    public void execute(){
        
    }

    //3.2.7

    @Override
    public boolean isFinished(){
        return false;
    }

    //3.2.6

    @Override
    public void end(boolean interrupted){
        
    }
}
