package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.NewSubsystem;

public class BangBangSubsystem extends Command {

    private final NewSubsystem subsystem;
    private final double setpoint;

    public BangBangSubsystem(NewSubsystem subsystem, double setpoint) {
        this.subsystem = subsystem;
        this.setpoint = setpoint;

        addRequirements(subsystem);
    }

  

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        if (subsystem.getPosition() < setpoint) {
            subsystem.setMotor(.1);
        } else {
            subsystem.setMotor(-.1);
        }
    }

    @Override
    public void end(boolean interrupted) {
        subsystem.setMotor(0);
    }

    @Override
    public boolean isFinished() {
        return Math.abs(subsystem.getPosition() - setpoint) < 0.015;

        
    }
}
