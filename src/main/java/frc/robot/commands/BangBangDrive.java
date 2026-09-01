package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drivetrain;

public class BangBangDrive extends Command {
    Drivetrain drivetrain;
    double setpoint;

    public BangBangDrive(Drivetrain drivetrainInput, double setpointInput) { // instructions didnt indicate type for "setpoint". Guessed it was a double
        drivetrain = drivetrainInput;
        setpoint = setpointInput;
    }

    @Override
    public void initialize() {
        drivetrain.resetEncoders();
    }

    @Override
    public void execute() {
        // In an if statement to determine hwich direction to drive and set hte motor output. use getAveragePosition() from Drivetrain
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.tankDrive(0, 0);
    }

    @Override
    public boolean isFinished() {
        return Math.abs(drivetrain.getAveragePosition() - setpoint) < 0.01;
    }
}
