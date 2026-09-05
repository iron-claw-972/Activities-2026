package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Test;

public class SpinXRotationCommand extends Command {
    Drivetrain drivetrain;
    Test test;
    double rotationCount;

    public SpinXRotationCommand(Test test, double rotationCount) {
        this.test = test;
        this.rotationCount = rotationCount;
        addRequirements(test);
    }

    @Override
    public void initialize() {
        test.setPosition(test.getPosition() + rotationCount);
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}