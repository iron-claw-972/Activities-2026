package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.Drivetrain;

public class ArcadeDriveCommand extends Command {
    Drivetrain drivetrain;

    public ArcadeDriveCommand(Drivetrain drivetrainInput) {
        drivetrain = drivetrainInput;
        addRequirements(drivetrain);
    }

    @Override
    public void execute() {
        drivetrain.arcadeDrive(Robot.driver.getForwardTranslation(), Robot.driver.getTurn());
        System.out.println(Robot.driver.getTurn() + ":" + Robot.driver.getForwardTranslation());
    }
}
