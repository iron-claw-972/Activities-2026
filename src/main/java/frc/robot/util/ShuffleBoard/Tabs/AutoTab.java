package frc.robot.util.ShuffleBoard.Tabs;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import frc.robot.commands.AutoDriveCommand;
import frc.robot.commands.BangBangDrive;
import frc.robot.commands.BangBangSpin;
import frc.robot.commands.BangBangSubsystem;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.NewSubsystem;
import frc.robot.util.ShuffleBoard.ShuffleBoardTabs;

public class AutoTab extends ShuffleBoardTabs {

    private final SendableChooser<Command> autoCommand = new SendableChooser<>();

    private Drivetrain drivetrain;
    private NewSubsystem subsystem;

    public AutoTab(Drivetrain drivetrain, NewSubsystem subsytem){
        this.subsystem = subsytem;
        this.drivetrain = drivetrain;
    }

    public void createEntries() {
        tab = Shuffleboard.getTab("Auto");

        // Default auto
        autoCommand.setDefaultOption("Do Nothing", new PrintCommand("This will do nothing!"));

        // Add auto commands here
        // TODO 3.2.7: Add your auto command here
        autoCommand.addOption(
                "Zigzag Auto",
                new AutoDriveCommand(drivetrain));

        // TODO 3.3.8: Add your Bang-Bang drive command here
        autoCommand.addOption(
                "Bang Bang Drive",
                new BangBangDrive(drivetrain, 1.0));
        // TODO 3.3.11: Add your Bang-Bang command for your subsystem here
        autoCommand.addOption(
                "Bang Bang Subsystem",
                new BangBangSubsystem(subsystem, 1.0));

        tab.add(autoCommand);
        tab.add("Spin 1 Rotation", new BangBangSpin(drivetrain, 1));
        tab.add("Spin 2 Rotations", new BangBangSpin(drivetrain, 2));
        tab.add("Spin 5 Rotations", new BangBangSpin(drivetrain, 5));
    }

    public void update() {
    }

    public SendableChooser<Command> getChooser() {
        return autoCommand;
    }
}
