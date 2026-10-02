package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.subsystems.Drivetrain;

public class BangBang2 extends Command {
    private Drivetrain drive;
    private double setpoint;
    private double initialPosition;

    public BangBang2(Drivetrain drivetrain, double setpoint) {
        this.drive = drivetrain;
        this.setpoint = setpoint;
    }


    @Override
    public void initialize(){
        initialPosition = drive.getAveragePosition();
    }

    @Override
    public void execute(){
        if (setpoint > 0){
            drive.arcadeDrive(1, 0);
        } else {
            drive.arcadeDrive(-1, 0);
        }

    }
    @Override
    public void end(boolean interrupted){
        drive.arcadeDrive(0, 0);
    }
    @Override
    public boolean isFinished(){
        if (((setpoint+initialPosition) - drive.getAveragePosition()) < 0){
            return true;
        } else{
            return false;
        }
    }
}




