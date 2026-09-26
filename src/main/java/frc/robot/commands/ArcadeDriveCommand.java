package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.Drivetrain;

//3.1.3

public class ArcadeDriveCommand extends Command{
    private Drivetrain drive;

    //3.1.4

    public ArcadeDriveCommand(Drivetrain drive){
        this.drive = drive;
        addRequirements(drive);
    }

    @Override
    public void initialize(){

    }

    //3.1.5

    @Override
    public void execute(){
        drive.arcadeDrive(Robot.driver.getForwardTranslation(), Robot.driver.getRightTranslation());
        //System.out.println(Robot.driver.getTurn());
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
