package frc.robot.controls;

import java.util.function.BooleanSupplier;

import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.commands.BangBang2;
import frc.robot.commands.BangBangDrive;
import frc.robot.commands.DoNothing;
import frc.robot.commands.RobotRotate2;
import frc.robot.commands.pidCommand;
import frc.robot.constants.Constants;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.MyNewSubsystem;
import lib.controllers.GameController;
import lib.controllers.GameController.Axis;
import lib.controllers.GameController.Button;

/**
 * Driver controls for the generic game controller.
 */
public class GameControllerDriverConfig extends BaseDriverConfig {
  private final GameController controller = new GameController(Constants.DRIVER_JOY);
  private Drivetrain drive;
  private MyNewSubsystem subsystem;
  double time;
  WaitUntilCommand waitTime = new WaitUntilCommand(time);
  public GameControllerDriverConfig(Drivetrain drive, MyNewSubsystem subsystem) {
    super(drive);
    this.drive = drive;
    this.subsystem = subsystem;
  }

  @Override
  public void configureControls() {
    // TODO 4.1.1: Change to your auto command
    controller.get(Button.A).onTrue(new RobotRotate2(drive, 1));
    // TODO 4.1.3: Add Bang-Bang drive command
    controller.get(Button.B).onTrue(new pidCommand(subsystem, 10));
    //controller.get(Button.B).onFalse(new BangBangDrive(drive, 0));
    // TODO 4.1.4: Add subsystem Bang-Bangs
    controller.get(Button.Y).onTrue(new BangBang2(drive, 10));
        //controller.get(Button.X).onFalse(new BangBang2(drive, 0));
    // TODO 4.2.2: Make robot spin while a button is pressed
    controller.get(Button.LB).whileTrue(new RunCommand(() -> drive.arcadeDrive(0, 1), drive));

    // TODO 4.3.1: Add more triggers
    controller.get(Button.A).onTrue(new SequentialCommandGroup(new BangBang2(drive, 10), new WaitUntilCommand(() -> counter()),new RobotRotate2(drive, 1)));
    controller.get(Button.X).onTrue(new ParallelCommandGroup(new BangBang2(drive, 10), new RobotRotate2(drive, 2)));
    //controller.get(Button.X).onTrue(new ConditionalCommand(new BangBang2(drive, 10), new RobotRotate2(drive, 1), counter()));
  }

public Boolean counter(){
  int count = 0;
  return count > 200;
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
    return controller.get(Axis.RIGHT_X);
  }

  @Override
  public boolean getIsSlowMode() {
    return controller.RIGHT_TRIGGER_BUTTON.getAsBoolean();
  }

}
