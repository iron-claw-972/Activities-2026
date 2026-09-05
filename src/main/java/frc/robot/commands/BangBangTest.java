package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Test;

public class BangBangTest extends Command {
    Test test;
    double setpoint;

    public BangBangTest(Test testInput, double setpointInput) {
        test = testInput;
        setpoint = setpointInput;
        addRequirements(test);
    }

    @Override
    public void execute() {
        if (test.getPosition() < setpoint) {
            test.setSpeed(40);
        } else {
            test.setSpeed(-40);
        }
    }

    @Override
    public void end(boolean interrupted) {
        test.stopSpinning();
    }

    @Override
    public boolean isFinished() {
        return Math.abs(test.getPosition() - setpoint) < 0.02;
    }
}
