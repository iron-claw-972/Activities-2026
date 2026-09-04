package frc.robot.controls;

import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;

import frc.robot.commands.AutoDriveCommand;
import frc.robot.commands.BangBangDrive;
import frc.robot.commands.BangBangSpin;
import frc.robot.commands.BangBangSubsystem;
import frc.robot.constants.Constants;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.NewSubsystem;

import lib.controllers.GameController;
import lib.controllers.GameController.Axis;
import lib.controllers.GameController.Button;
import lib.controllers.GameController.DPad;

/**
 * Driver controls for the generic game controller.
 */
public class GameControllerDriverConfig extends BaseDriverConfig {

  private final GameController controller =
      new GameController(Constants.DRIVER_JOY);

  private NewSubsystem subsystem;

  public GameControllerDriverConfig(Drivetrain drive, NewSubsystem subsystem) {
    super(drive);
    this.subsystem = subsystem;
  }

  @Override
  public void configureControls() {

    // TODO 4.1.1: Change to your auto command
    controller.get(Button.A).onTrue(
        new AutoDriveCommand(getDrivetrain())
    );

    // TODO 4.1.3: Add Bang-Bang drive command
    controller.get(Button.B).onTrue(
        new BangBangDrive(getDrivetrain(), 5.0)
    );

    // TODO 4.1.4: Add subsystem Bang-Bangs
    controller.get(Button.X).onTrue(
        new BangBangSubsystem(subsystem, 2.0)
    );

    controller.get(Button.X).onFalse(
        new BangBangSubsystem(subsystem, 0.0)
    );

    // TODO 4.2.2: Make robot spin while a button is pressed
    controller.get(Button.Y).whileTrue(
        new RunCommand(
            () -> getDrivetrain().tankDrive(0.5, -0.5),
            getDrivetrain()
        )
    );

    // TODO 4.3.1: Add more triggers
controller.get(controller.LEFT_TRIGGER_BUTTON).onTrue(
    new SequentialCommandGroup(
        new BangBangDrive(getDrivetrain(), 5.0),
        new BangBangSpin(getDrivetrain(), 3.0)
    )
);

    controller.get(controller.RIGHT_TRIGGER_BUTTON).onTrue(
        new SequentialCommandGroup(
            new BangBangDrive(getDrivetrain(), 5.0),
            new WaitUntilCommand(
                () -> getDrivetrain().getAveragePosition() >= 3.0
            )
        )
    );

    controller.get(DPad.UP).onTrue(
        new ConditionalCommand(
            new BangBangDrive(getDrivetrain(), 5.0),
            new BangBangSpin(getDrivetrain(), 3.0),
            () -> getDrivetrain().getAveragePosition() > 0
        )
    );
  }

  @Override
  public double getRawLeftTranslation() {
    //  because down is positive
    return controller.get(Axis.LEFT_Y);
  }

  @Override
  public double getRawRightTranslation() {
    // because down is positive
    return controller.get(Axis.RIGHT_Y);
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