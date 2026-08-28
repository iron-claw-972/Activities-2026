package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drivetrain;

public class AutoDriveCommand extends Command {

    private final Drivetrain drivetrain;
    private int cycles;

    public AutoDriveCommand(Drivetrain drivetrain) {
        this.drivetrain = drivetrain;
        addRequirements(drivetrain);
    }

    @Override
    public void initialize() {
        cycles = 0;
    }

    @Override
    public void execute() {

        if (cycles < 75) {
            // Drive forward and turn right
            drivetrain.arcadeDrive(0.5, 0.3);

        } else if (cycles < 150) {
            // Drive forward and turn left
            drivetrain.arcadeDrive(0.5, -0.3);

        } else if (cycles < 225) {
            // Drive straight
            drivetrain.arcadeDrive(0.5, 0);

        } else {
            // Stop
            drivetrain.arcadeDrive(0, 0);
        }

        cycles++;
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.arcadeDrive(0, 0);
    }

    @Override
    public boolean isFinished() {
        return cycles >= 225;
    }
}