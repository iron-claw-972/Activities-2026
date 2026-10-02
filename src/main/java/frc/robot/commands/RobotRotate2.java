package frc.robot.commands;

import java.util.Currency;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drivetrain;

public class RobotRotate2 extends Command {
    private Drivetrain drive;
    private int numOfRotations;
    private double wantedAngle;
    private double currentAngle;

    public RobotRotate2(Drivetrain drive, int numOfRotations) {
        this.numOfRotations = numOfRotations;
        this.drive = drive;
    }
    @Override
    public void initialize() {
        //drive.resetEncoders();
        currentAngle = drive.getGyroAngle().getDegrees();
        wantedAngle = currentAngle + numOfRotations * 360;
    }
    @Override
    public void execute(){
        if (numOfRotations > 0){
            drive.arcadeDrive(0,1);
        }
        else if (numOfRotations < 0){
            drive.arcadeDrive(0,-1);
        }
        
        currentAngle = drive.getGyroAngle().getDegrees();

    }
    @Override
    public void end(boolean interrupted){
        drive.arcadeDrive(0, 0);
    }
    @Override
    public boolean isFinished(){
        if (wantedAngle-currentAngle >= 0.0){
            return false;
        } else{
            return true;
        }
    }
}

