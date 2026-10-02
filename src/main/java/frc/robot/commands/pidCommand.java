package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.MyNewSubsystem;

public class pidCommand extends Command {
    private MyNewSubsystem subsystem;
    private double Setpoint;

    public pidCommand(MyNewSubsystem subsystem, double Setpoint) {
        this.subsystem = subsystem;
        this.Setpoint = Setpoint;
    }

    @Override
    public void initialize() {
        // pid.reset();
        subsystem.spinTo(Setpoint);
        //System.out.println("pid is running");
    }

    @Override
    public void execute() {
        //System.out.println("pid is running");
        subsystem.spinTo(Setpoint);

    }

    @Override
    public boolean isFinished() {
        return subsystem.atSetpoint();
    }

}
