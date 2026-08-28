package frc.robot.commands;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Robot;
import frc.robot.subsystems.Drivetrain;

public class ArcadeDrive extends Command {

    private final Drivetrain drivetrain;

    public ArcadeDrive(Drivetrain drivetrain) {
        this.drivetrain = drivetrain;

        addRequirements(drivetrain);
    }

    @Override
    public void execute() {

        drivetrain.arcadeDrive(
            Robot.driver.getForwardTranslation(),
            Robot.driver.getTurn()
        );
    }
}