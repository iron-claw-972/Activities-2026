package frc.robot.controls;

import frc.robot.commands.BangBangDrive;
import frc.robot.commands.DoNothing;
import frc.robot.commands.ForwardAutoCommand;
import frc.robot.constants.Constants;
import frc.robot.subsystems.Drivetrain;
import lib.controllers.GameController;
import lib.controllers.GameController.Axis;
import lib.controllers.GameController.Button;

/**
 * Driver controls for the generic game controller.
 */
public class GameControllerDriverConfig extends BaseDriverConfig {
  private final GameController controller = new GameController(Constants.DRIVER_JOY);

  public GameControllerDriverConfig(Drivetrain drive) {
    super(drive);
  }

  @Override
  public void configureControls() {
    // TODO 4.1.1: Change to your auto command
    controller.get(Button.A).onTrue(new ForwardAutoCommand(getDrivetrain()));
    // TODO 4.1.3: Add Bang-Bang drive command
    controller.get(Button.B).onTrue(new BangBangDrive(getDrivetrain(), 0.25));
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
    return -controller.get(Axis.RIGHT_TRIGGER);
  }

  @Override
  public double getRawTurn() {
    return controller.get(Axis.RIGHT_X);
  }

  @Override
  public boolean getIsSlowMode() {
    return controller.RIGHT_TRIGGER_BUTTON.getAsBoolean();
  }
}
