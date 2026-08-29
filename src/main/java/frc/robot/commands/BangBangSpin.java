package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drivetrain;

public class BangBangSpin extends Command {

    private final Drivetrain drivetrain;
    private final double setpoint;

    public BangBangSpin(Drivetrain drivetrain, double setpoint) {
        this.drivetrain = drivetrain;
        this.setpoint = setpoint;

        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {
        drivetrain.resetEncoders();
    }

    @Override
    public void execute() {
        if (drivetrain.getAveragePosition() < setpoint) {
            drivetrain.tankDrive(0.5, 0.5);
        } else {
            drivetrain.tankDrive(-0.5, -0.5);
        }
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.tankDrive(0, 0);
    }

    @Override
    public boolean isFinished() {
        return Math.abs(drivetrain.getAveragePosition() - setpoint) < 0.015;    
    }

}
