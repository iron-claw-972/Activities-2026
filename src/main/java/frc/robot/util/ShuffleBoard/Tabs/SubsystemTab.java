
package frc.robot.util.ShuffleBoard.Tabs;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import frc.robot.subsystems.MyNewSubsystem;
import frc.robot.util.ShuffleBoard.ShuffleBoardTabs;


public class SubsystemTab extends ShuffleBoardTabs {
    // TODO 2.3.11: Create variable for subsystem
    public MyNewSubsystem subsystem;

    public SubsystemTab(MyNewSubsystem subsystem) {
        this.subsystem = subsystem;
    }

    public void createEntries(){
        tab = Shuffleboard.getTab("Subsystem");

        // TODO 2.4.7: Add Mechanism2d
        tab.add("thing", subsystem.mechanism2d);

        // TODO 3.3.13: Add command buttons

        // TODO 5.3.1: Add PID

    }


    public void update(){}
}
