package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.NewSubsystem;

public class SubsystemPIDCommand extends Command {

    private NewSubsystem subsystem;
    private double setpoint;

    public SubsystemPIDCommand(NewSubsystem subsystem, double setpoint) {
        this.subsystem = subsystem;
        this.setpoint = setpoint;

        addRequirements(subsystem);
    }

    @Override
    public void initialize() {
        subsystem.spinTo(setpoint);
    }

    @Override
    public boolean isFinished() {
        return subsystem.atSetpoint();
    }
}
