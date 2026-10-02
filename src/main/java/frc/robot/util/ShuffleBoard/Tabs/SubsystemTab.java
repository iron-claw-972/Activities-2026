
package frc.robot.util.ShuffleBoard.Tabs;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.MyNewSubsystem;
import frc.robot.util.ShuffleBoard.ShuffleBoardTabs;
import frc.robot.commands.RobotRotate2;
import frc.robot.commands.pidCommand;

public class SubsystemTab extends ShuffleBoardTabs {
    // TODO 2.3.11: Create variable for subsystem
    public MyNewSubsystem subsystem;
    public Drivetrain drive;
    public RobotRotate2 command;

    public SubsystemTab(MyNewSubsystem subsystem, Drivetrain drive) {
        this.subsystem = subsystem;
        this.drive = drive;
    }

    public void createEntries(){
        tab = Shuffleboard.getTab("Subsystem");

        // TODO 2.4.7: Add Mechanism2d
        tab.add("thing", subsystem.mechanism2d);

        // TODO 3.3.13: Add command buttons
        tab.add("RobotRotate2", new RobotRotate2(drive, 1));
        // TODO 5.3.1: Add PID
        tab.add("pidCommand", new pidCommand(subsystem,10));
    }


    public void update(){}
}
