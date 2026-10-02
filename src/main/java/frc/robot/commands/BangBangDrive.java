package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drivetrain;

public class BangBangDrive extends Command {
    private Drivetrain drive;
    private double setpoint;
    PIDController pid = new PIDController(0.1, 0, 0);
    
    //private double pastPosition;

    public BangBangDrive(Drivetrain drive, double setpoint) {
        this.drive = drive;
        this.setpoint = setpoint;
    }


    @Override
    public void initialize(){
        drive.resetEncoders();
        pid.setTolerance(0.1);
        //pastPosition = drive.getAveragePosition();
    }

    @Override
    public void execute(){
       /* */ if (setpoint > 0){
            drive.arcadeDrive(1, 0);
        } else if (setpoint < 0) {
            drive.arcadeDrive(-1, 0);
        } else {
            drive.arcadeDrive(0, 0);
        }

    }
    @Override
    public void end(boolean interrupted){
        drive.arcadeDrive(0, 0);
    }
    @Override
    public boolean isFinished(){
        if (drive.getAveragePosition() <= setpoint + 0.1 && drive.getAveragePosition() >= setpoint - 0.1){
            return true;
        } else{
        return false;
    }
    }
}




























