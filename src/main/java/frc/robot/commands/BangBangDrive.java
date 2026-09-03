package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drivetrain;

public class BangBangDrive extends Command {
    Drivetrain drivetrain;
    double setpoint;

    public BangBangDrive(Drivetrain drivetrainInput, double setpointInput) {
        drivetrain = drivetrainInput;
        setpoint = setpointInput;
        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {
        drivetrain.resetEncoders();
    }

    @Override
    public void execute() {
        if (drivetrain.getAveragePosition() < setpoint) {
            drivetrain.arcadeDrive(10, 0);
        } else {
            drivetrain.arcadeDrive(-10, 0);
        }
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.tankDrive(0, 0);
    }

    @Override
    public boolean isFinished() {
        return Math.abs(drivetrain.getAveragePosition() - setpoint) < 0.02;
    }
}
