package frc.robot.controls;

import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.AutoCommand;
import frc.robot.commands.BangBangDrive;
import frc.robot.commands.BangBangTest;
import frc.robot.commands.DoNothing;
import frc.robot.commands.PIDCommand;
import frc.robot.constants.Constants;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Test;
import lib.controllers.GameController;
import lib.controllers.GameController.Axis;
import lib.controllers.GameController.Button;

/**
 * Driver controls for the generic game controller.
 */
public class GameControllerDriverConfig extends BaseDriverConfig {
  private final GameController controller = new GameController(Constants.DRIVER_JOY);
  Drivetrain drivetrain;
  Test test;

  public GameControllerDriverConfig(Drivetrain drive, Test test) {
    super(drive);
    drivetrain = drive;
    this.test = test;
  }

  @Override
  public void configureControls() {
    // TODO 4.1.1: Change to your auto command
    // controller.get(Button.A).onTrue(new BangBangDrive(drivetrain, 5));
    controller.get(Button.A).onTrue(new RunCommand(() -> {
      drivetrain.arcadeDrive(10, 90);
    }, drivetrain));
    // controller.get(Button.B).onTrue(new SequentialCommandGroup(new BangBangDrive(drivetrain, 5), new AutoCommand(drivetrain)));
    controller.get(Button.B).onTrue(new PIDCommand(test, 2));
    // TODO 4.1.3: Add Bang-Bang drive command

    // TODO 4.1.4: Add subsystem Bang-Bangs

    // TODO 4.2.2: Make robot spin while a button is pressed

    // TODO 4.3.1: Add more triggers
  }

  @Override
  public double getRawLeftTranslation() {
    // - because down is positive
    return -controller.get(Axis.LEFT_Y);
  }
  @Override
  public double getRawRightTranslation() {
    // - because down is positive
    return -controller.get(Axis.RIGHT_Y);
  }

  @Override
  public double getRawTurn() {
    return controller.get(Axis.LEFT_X);
  }

  @Override
  public boolean getIsSlowMode() {
    return controller.RIGHT_TRIGGER_BUTTON.getAsBoolean();
  }
}
