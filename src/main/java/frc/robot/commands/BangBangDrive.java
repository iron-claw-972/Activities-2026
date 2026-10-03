package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.Drivetrain;

//3.3.1

public class BangBangDrive extends Command{
    private Drivetrain drive;
    private double futurePosition;
    private double setpoint;

    private double tolerance = 0.1;

    //3.3.2

    public BangBangDrive(Drivetrain drive, double setpoint){
        this.drive = drive;
        this.setpoint = setpoint;
        addRequirements(drive);
    }

    @Override
    public void initialize(){

        //3.3.3

        //startingPosition = drive.getAveragePosition();
        drive.resetEncoders();
        this.futurePosition = drive.getAveragePosition()+setpoint;
    }

    @Override
    public void execute(){

        //3.3.4
        double error = futurePosition - drive.getAveragePosition();

        if(error > 0){
            drive.arcadeDrive(0.25, 0);
        } else if(error < 0){
            drive.arcadeDrive(-0.25, 0);
        }
    }

    @Override
    public boolean isFinished(){

        //3.3.6

        return (Math.abs((futurePosition-drive.getAveragePosition())) < tolerance);
    }

    @Override
    public void end(boolean interrupted){

        //3.3.5

        drive.tankDrive(0, 0);
    }
}
