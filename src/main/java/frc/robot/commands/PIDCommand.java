package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Test;

public class PIDCommand extends Command {
    Test test;
    double setpoint;

    public PIDCommand(Test test, double setpoint) {
        this.test = test;
        this.setpoint = setpoint;
        addRequirements(test);
    }

    @Override
    public void initialize() {
        test.spinTo(setpoint);
    }

    @Override
    public boolean isFinished() {
        return test.atSetpoint();
    }
}
