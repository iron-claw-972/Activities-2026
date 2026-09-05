package frc.robot.util.ShuffleBoard.Tabs;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.NewSubsystem;
import frc.robot.util.ShuffleBoard.ShuffleBoardTabs;


public class SubsystemTab extends ShuffleBoardTabs {
    private NewSubsystem subsystem;
   // TODO 2.3.11: Create variable for subsystem

    
    public SubsystemTab(NewSubsystem subsystem) {
        this.subsystem = subsystem; // or however your project obtains it
    }

    public void createEntries() {
        tab = Shuffleboard.getTab("Subsystem");

        // TODO 2.4.7: Add Mechanism2d
        tab.add("Mechanism", subsystem.getMechanism());

        // TODO 3.3.13: Add command buttons
        tab.add("Command", new InstantCommand(() -> subsystem.spinTo(90)));

        // TODO 5.3.1: Add PID
        tab.add("PID Controller", subsystem.getPID());

    }
    public void update(){}
}